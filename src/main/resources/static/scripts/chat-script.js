
const roomId = location.href[location.href.length - 1];
let socket = new SockJS("/boredmembers_chatsocket");
stompClient = Stomp.over(socket);
stompClient.connect({}, function (frame) {
	console.log("Connected!!?????!!!!!!");
	stompClient.subscribe("/app/rooms/" + roomId, function (message) {
		displayMessage(JSON.parse(message.body).content);
	});
});

let chatTable = document.querySelector(".chat-table");
function displayMessage(message) {
	let newMessage = chatTable.insertRow().insertCell();
	newMessage.innerText = message;
}

let chatInput = document.querySelector("#chat-input");
chatInput.addEventListener("keypress", e => {
	if (e.key == "Enter") {
		stompClient.send(
				"/boredrooms/chat/" + roomId, 
			{}, 
			JSON.stringify({"content" : chatInput.value}))
	}
})