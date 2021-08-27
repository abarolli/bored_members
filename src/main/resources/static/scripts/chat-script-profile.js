
let chatTable = document.querySelector(".chat-table");
let currentUserAvatar = document.querySelector(".user-avatar-name").innerText;

function clearChat(chatTable) {
	let newBlankChat = document.createElement("tbody");
	let oldChat = chatTable.querySelector("tbody");
	if (oldChat != null)
		chatTable.replaceChild(newBlankChat, oldChat);
}

let subscription = null;
function connect(roomId) {
	
	clearChat(chatTable);
	
	if (subscription != null) {
		subscription.unsubscribe();	
	}
	
	let socket = new SockJS("/boredmembers_chatsocket");
	stompClient = Stomp.over(socket);
	
	
	stompClient.connect({}, function (frame) {
		console.log("Connected!!!");
		console.log(socket.readyState);
		subscription = stompClient.subscribe("/app/rooms/" + roomId, function (message) {
			displayMessage(JSON.parse(message.body), currentUserAvatar);
		});
	});
		
}

let profileSubscriptionsList = 
	document.querySelector(".profile-controls__controls #subscription-content");
console.log(profileSubscriptionsList);


let currentRoom = document.querySelector(".current-room");
let currentRoomId;
for (let sub of profileSubscriptionsList.children) {
	sub.addEventListener("click", e => {
		e.preventDefault();
		let targetId = e.target.dataset.id;
		let roomName = e.target.innerText;
		console.log(roomName);
		currentRoom.innerText = roomName;
		if (currentRoomId != targetId) {
			connect(targetId);
			
			fetch("/profile/api/room/" + targetId, {
				method: "GET"
			})
			.then(response => response.json())
			.then(messages => {
				for (let message of messages)
					displayMessage(message, currentUserAvatar);
			});			
		}
		currentRoomId = targetId;
	})
}


function displayMessage(message, currentAvatar) {
	let newMessage = chatTable.insertRow().insertCell();
	if (currentAvatar == message.author) {
		newMessage.classList.add("owner-chat");
		let trashUi = document.createElement("span");
		trashUi.classList.add("chat-message__trash-icon");
		trashUi.setAttribute("data-id", message.id);
		trashUi.addEventListener("click", deleteMessage);
		newMessage.appendChild(trashUi);	
	}
		
	newMessage.classList.add("chat-message")
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

window.onload = e => 
	chatTable.rows[chatTable.rows.length - 1].scrollIntoView({behavior: "smooth"});

let chatInput = document.querySelector("#chat-input");
chatInput.addEventListener("keypress", e => {
	if (e.key == "Enter") {
		e.preventDefault();
		stompClient.send(
			"/boredrooms/chat/" + currentRoomId, 
			{}, 
			JSON.stringify({"content" : chatInput.value})
		);
		chatInput.value = "";
	}
})




// DELETE MESSAGE FUNCTIONALITY =================================

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

let csrf = document.querySelector(".profile-controls__table-container")
                   .querySelector(".chat-form")
                   .firstElementChild.value;
console.log(csrf);
function deleteMessage(e) {
	let messageId = e.target.dataset.id;
	fetch("/chat/api/deleteMessage", {
		method: 'POST',
		body: urlEncodeFormData({
			"_csrf": csrf,
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
