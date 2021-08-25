
const roomId = location.href[location.href.length - 1];
let socket = new SockJS("/boredmembers_chatsocket");
stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
	console.log("Connected!!!");
	stompClient.subscribe("/app/rooms/" + roomId, function (message) {
		displayMessage(JSON.parse(message.body).content);
	});
});


let chatTable = document.querySelector(".chat-table");
function displayMessage(message) {
	let newMessage = chatTable.insertRow().insertCell();
	newMessage.classList.add("chat-message");
	newMessage.innerText = message;
	newMessage.scrollIntoView({behavior: "smooth"});
}

window.onload = e => 
	chatTable.rows[chatTable.rows.length - 1].scrollIntoView({behavior: "smooth"});

let chatInput = document.querySelector("#chat-input");
chatInput.addEventListener("keypress", e => {
	if (e.key == "Enter") {
		e.preventDefault();
		stompClient.send(
			"/boredrooms/chat/" + roomId, 
			{}, 
			JSON.stringify({"content" : chatInput.value})
		);
		chatInput.value = "";
	}
})