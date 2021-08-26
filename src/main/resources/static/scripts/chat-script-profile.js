
function connect(roomId) {
	let socket = new SockJS("/boredmembers_chatsocket");
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function (frame) {
		console.log("Connected!!!");
		stompClient.subscribe("/app/rooms/" + roomId, function (message) {
			displayMessage(JSON.parse(message.body));
		});
	});	
}

let profileSubscriptionsList = 
	document.querySelector(".profile-controls__controls #subscription-content");
console.log(profileSubscriptionsList);

let currentRoomId;
for (let sub of profileSubscriptionsList.children) {
	sub.addEventListener("click", e => {
		e.preventDefault();
		let targetId = e.target.dataset.id;
		if (currentRoomId != targetId) {
			connect(targetId);
			
			fetch("/profile/api/room/" + targetId, {
				method: "GET"
			})
			.then(response => response.json())
			.then(messages => {
				for (let message of messages)
					displayMessage(message);
			});			
		}
		currentRoomId = targetId;
	})
}


let chatTable = document.querySelector(".chat-table");
function displayMessage(message) {
	let newMessage = chatTable.insertRow().insertCell();
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