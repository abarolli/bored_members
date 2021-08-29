
class SocketConnection {
	
	constructor(clientEndPoint, serverEndPoint) {
		this.socket = new SockJS(clientEndPoint);
		this.stompClient = Stomp.over(this.socket);
		this.serverEndPoint = serverEndPoint;
		this.subscription = null;
	}
	
	connect(chatTable, currentAvatar) {
		this.stompClient.connect({}, frame => {
			this.connCallback.call(this, frame, chatTable, currentAvatar);	
		});
	}
	
	setSubscription(subscription) {
		this.subscription = subscription;
	}
	
	connCallback(frame, chatTable, currentAvatar) {
		console.log("Connected!!" + frame);
		
		if (subscription != null) {
			this.stompClient.subscribe(this.subscription, message => {
				chatTable.displayMessage(JSON.parse(message.body), currentAvatar);
			});
		}
		else console.log("Subscription not set!")
	}
	
	sendMessage(e, chatInput) {
		if (e.key == "Enter") {
			e.preventDefault();
			this.stompClient.send(
				this.serverEndPoint, 
				{}, 
				JSON.stringify({"content" : chatInput.value})
			);
			chatInput.value = "";
		}
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
			trashIcon.addEventListener("click", this.deleteMessage);
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
			trashUi.addEventListener("click", this.deleteMessage);
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


let currentUserAvatar = document.querySelector(".user-avatar-name").innerText;
let table = document.querySelector(".chat-table");
let csrf = document.querySelector(".profile-controls")
                   .querySelector(".chat-form")
                   .firstElementChild.value;

let currentUrl = location.href;
if (currentUrl.endsWith("/"))
	currentUrl = currentUrl.slice(0, currentUrl.length - 1);
	
const roomId = currentUrl.slice(currentUrl.lastIndexOf("/") + 1);
console.log(roomId);

let serverEndPoint = "/boredrooms/chat/" + roomId;
let subscription = "/app/rooms/" + roomId;

let socketConnection = 
	new SocketConnection("/boredmembers_chatsocket", serverEndPoint);
socketConnection.setSubscription(subscription);	

let chatTable = 
	new ChatTable(currentUserAvatar, table, csrf, socketConnection);
	
window.onload = e => chatTable.init();
let chatInput = document.querySelector("#chat-input");
chatInput.addEventListener("keypress", e => {
	socketConnection.sendMessage(e, chatInput);	
});
