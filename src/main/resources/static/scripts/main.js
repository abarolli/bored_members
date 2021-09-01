const docBody = document.body;
window.addEventListener("load", e => {
    docBody.classList.remove("prevent-animation");
});


const collapsibles = document.querySelectorAll(".collapsible");
collapsibles.forEach(item => {
    item.addEventListener("click", e => {
        item.classList.toggle("collapsible--expanded");
    })
});


const sideMenuTogglers = document.querySelectorAll(".toggler");
const sideMenu = document.querySelector(".side-menu");
sideMenuTogglers.forEach(toggler => {
    toggler.addEventListener("click", e => {
        sideMenu.classList.toggle("collapsible-side-menu--expanded");
        docBody.classList.toggle("inactive");
    });
});


// Public rooms script ===================================================


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

let joinOrLeaveBtns = document.querySelectorAll(".join-leave-btn");
joinOrLeaveBtns.forEach(btn => {

	btn.addEventListener("click", joinRoomElseLeave);
});

let subscriptionContent = document.querySelector("#subscription-content");
console.log(subscriptionContent);
function joinRoomElseLeave(e) {
	e.preventDefault();
	let actionBtn = e.target;
	let roomId = actionBtn.dataset.id;
	console.log(actionBtn);
	
	let restPoint = undefined;
	let isLeaving = undefined;
	if (actionBtn.classList.contains("leave-btn")) {
		restPoint = "/membership/api/leaveRoom";
		isLeaving = true;		
	}
	else if (actionBtn.classList.contains("join-btn")) {
		restPoint = "/membership/api/joinRoom";
		isLeaving = false;		
	}
		
	let csrf = document.querySelector(".join-leave-form").firstElementChild.value;
	console.log(csrf);
	// need to encode request body for x-www-form-urlencoded data
	fetch(restPoint, {
		method: 'POST',
		body: urlEncodeFormData({
			"_csrf": csrf,
			"roomId": roomId
		}),
		headers: {
			"Content-Type": "application/x-www-form-urlencoded"
		}
	})
	.then(response => response.json())
	.then(data => {
		if (data.subscribing) {
			actionBtn.classList.replace("btn--primary", "btn--pink");
			actionBtn.classList.replace("join-btn", "leave-btn");
			actionBtn.innerText = "Leave";	
			
			let newSub = document.createElement("li");
			newSub.classList.add("list__item");
			newSub.setAttribute("data-id", roomId);
			let newSubLink = document.createElement("a");
			newSubLink.href = getNewRoomLink(roomId);
			newSubLink.innerText = data.roomName;
			newSub.appendChild(newSubLink);
			subscriptionContent.appendChild(newSub);
		}
		else {
			actionBtn.classList.replace("btn--pink", "btn--primary");
			actionBtn.classList.replace("leave-btn", "join-btn");
			actionBtn.innerText = "Join";
			
			for (let child of subscriptionContent.children) {
				if (child.dataset.id == roomId) {
					child.remove();
					break;
				}
			}
		}
	})
	.catch(ex => console.log(ex));
			
	
}

let roomTableData = document.querySelectorAll(".table__row__data");
roomTableData.forEach(datum => {
	datum.addEventListener("click", e => {
		const roomId = datum.dataset.id;
		
		location.href = getNewRoomLink(roomId);
	})
})

function getNewRoomLink(roomId) {
	let currentUrl = location.href;
	let newRoomLink;
	if (currentUrl.endsWith("/"))
		return currentUrl + roomId;
	else
		return currentUrl + "/" + roomId;
}