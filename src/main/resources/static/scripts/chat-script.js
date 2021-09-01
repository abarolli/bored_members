import {
	urlEncodeFormData, 
	ChatTable, 
	SocketConnection
} from "./modules/chat-module.js";


let currentUserAvatar = document.querySelector(".user-avatar-name").innerText;
let table = document.querySelector(".chat-table");
let csrf = document.querySelector("[name='_csrf']").value;
console.log(csrf);

let currentUrl = location.href;
if (currentUrl.endsWith("/"))
	currentUrl = currentUrl.slice(0, currentUrl.length - 1);
	
const roomId = currentUrl.slice(currentUrl.lastIndexOf("/") + 1);
console.log(roomId);

let serverEndPoint = "/boredrooms/chat/" + roomId;
let subscription = "/app/rooms/" + roomId;

let socketConnection = 
	new SocketConnection("/boredmembers_chatsocket", serverEndPoint);
socketConnection.setSubscriptionEndPoint(subscription);	

let chatTable = 
	new ChatTable(currentUserAvatar, table, csrf, socketConnection);
	
window.onload = e => chatTable.init();
let chatInput = document.querySelector("#chat-input");
chatInput.addEventListener("keypress", e => {
	if (e.key == "Enter" && chatInput.value.trim() != "") {
		e.preventDefault();
		socketConnection.sendMessage(chatInput.value);
		chatInput.value = "";
	}
});