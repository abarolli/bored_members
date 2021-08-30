
import {urlEncodeFormData, SocketConnection} from "./modules/chat-module.js";
import {DistributedChatTable} from "./modules/profile-chat-module.js";

let chatTable = document.querySelector(".chat-table");
let currentAvatar = document.querySelector(".user-avatar-name").innerText;
let csrf = document.querySelector("[name='_csrf']").value;
let socket = new SocketConnection("/boredmembers_chatsocket");
let table = new DistributedChatTable(currentAvatar, chatTable, csrf, socket);


table.init();

let memberSubscriptions = 
	document.querySelectorAll(
		".profile-controls__controls #subscription-content .list__item"
	);
memberSubscriptions.forEach(sub => {
	sub.addEventListener("click", e => {
		e.preventDefault();
		let chatRoomTitle = document.querySelector(".current-room");
		chatRoomTitle.textContent = e.target.textContent;
		let roomId = e.target.dataset.id;
		
		table.clearCurrentTable();
		fetch("/profile/api/room/" + roomId, {
			method: "GET",
			headers: {
				"Content-Type": "application/x-www-form-urlencoded"
			}
		})
		.then(response => response.json())
		.then(messages => {
			messages.forEach(message => {
				table.displayMessage(message, currentAvatar);
			});
		})
		
		table.getFromSendTo("/app/rooms/" + roomId, "/boredrooms/chat/" + roomId);
	});	
});


let chatInput = document.querySelector("#chat-input");
chatInput.addEventListener("keypress", e => {
	if (e.key == "Enter" && chatInput.value.trim() != "") {
		socket.sendMessage(chatInput.value);
		chatInput.value = "";
	}
})