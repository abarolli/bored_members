
function urlEncodeFormData(data) {
	let formPayload = [];
	for (let property in data) {
		let encodedKey = encodeURIComponent(property);
		let encodedValue = encodeURIComponent(data[property]);
		formPayload.push(encodedKey + "=" + encodedValue);
	}
	formPayload = formPayload.join("&");
	return formPayload;
}

class SocketConnection {
	
	constructor(clientEndPoint, serverEndPoint=null) {
		this.socket = new SockJS(clientEndPoint);
		this.stompClient = Stomp.over(this.socket);
		this.serverEndPoint = serverEndPoint;
		this.subscriptionEndPoint = null;
		this.currentSubscription = null;
	}
	
	connect(chatTable, currentAvatar) {
		this.stompClient.connect({}, frame => {
			console.log("Connected!!\n" + frame);
			this.subscribe.call(this, this.subscriptionEndPoint, chatTable, currentAvatar);	
		});
	}
	
	setSubscriptionEndPoint(subscriptionEndPoint) {
		if (this.currentSubscription) {
			this.currentSubscription.unsubscribe();
		}
		this.subscriptionEndPoint = subscriptionEndPoint;
	}
	
	subscribe(subscriptionEndPoint, chatTable, currentAvatar) {
		console.log("ChatTable: ", chatTable);
		console.log("CurrentAvatar ", currentAvatar);
		if (this.socket.readyState == WebSocket.OPEN) {
			
			this.setSubscriptionEndPoint(subscriptionEndPoint);
			
			this.currentSubscription = 
				this.stompClient.subscribe(this.subscriptionEndPoint, message => {
					chatTable.displayMessage(JSON.parse(message.body), currentAvatar);
				});		
		}
		else console.log("You're not connected to the server!");
	}
	
	sendMessage(message) {
		if (this.serverEndPoint) {
			this.stompClient.send(
				this.serverEndPoint, 
				{}, 
				JSON.stringify({"content" : message})
			);
		}
		else console.log("There is no endpoint for that message");
	}
}


class ChatTable {
	
	constructor(currentAvatar, table, csrf, socketConnection) {
		this.table = table;
		this.csrf = csrf;
		this.socketConnection = socketConnection;
		this.currentAvatar = currentAvatar;
	}
	
	
	init() {
		this.socketConnection.connect(this, this.currentAvatar);
		this.initRenderMessages();
	}
	
	initRenderMessages() {
		let ownerChatRows = this.table.querySelectorAll(".owner-chat");
		
		ownerChatRows.forEach(row => {
			let trashIcon = row.querySelector(".chat-message__trash-icon");
			trashIcon.addEventListener("click", e => {
				this.deleteMessage.call(this, e);	
			});
		})
		
		let allChatRows = this.table.rows;
		allChatRows[allChatRows.length - 1].scrollIntoView({behavior: "smooth"});
	}
	
	displayMessage(message, currentAvatar) {
		let newMessage = this.table.insertRow().insertCell();
		
		if (currentAvatar == message.author) {
			newMessage.classList.add("owner-chat");
			let trashUi = document.createElement("span");
			trashUi.classList.add("chat-message__trash-icon");
			trashUi.setAttribute("data-id", message.id);
			
			trashUi.addEventListener("click", e => {
				this.deleteMessage.call(this, e);
			})
			newMessage.appendChild(trashUi);	
		}
			
		newMessage.classList.add("chat-message");
		let author = document.createElement("span");
		author.classList.add("chat-message__author");
		author.innerText = message.author;
		newMessage.appendChild(author);
		newMessage.appendChild(document.createElement("br"));
		let newMessageContent = document.createElement("span");
		newMessageContent.classList.add("chat-message__content");
		newMessageContent.innerText = message.content;
		newMessage.appendChild(newMessageContent);
		newMessage.scrollIntoView({behavior: "smooth"});
	}
	
	deleteMessage(e) {
		let messageId = e.target.dataset.id;
		fetch("/chat/api/deleteMessage", {
			method: 'POST',
			body: urlEncodeFormData({
				"_csrf": this.csrf,
				"messageId": messageId
			}),
			headers: {
				"Content-Type": "application/x-www-form-urlencoded"
			}
		}).catch(ex => console.log(ex));
		
		// call to parentElement twice to remove entire row <tr>
		let chatMessage = e.target.parentElement.parentElement;
		chatMessage.remove();
	}
}


export {urlEncodeFormData, SocketConnection, ChatTable};
