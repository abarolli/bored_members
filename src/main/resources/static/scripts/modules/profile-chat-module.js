import {urlEncodeFormData, SocketConnection, ChatTable} from "./chat-module.js";

class DistributedChatTable extends ChatTable {
	
	constructor(currentAvatar, table, csrf, socketConnection) {
		super(currentAvatar, table, csrf, socketConnection);
	}
	
	clearCurrentTable() {
		let newBlankChat = document.createElement("tbody");
		let oldChat = this.table.querySelector("tbody");
		if (oldChat)
			this.table.replaceChild(newBlankChat, oldChat);
	}
	
	init() {
		this.socketConnection.connect(this, this.currentAvatar);
	}
	
	getFromSendTo(subscriptionEndPoint, serverEndPoint) {
		this.socketConnection.serverEndPoint = serverEndPoint;
		this.socketConnection.subscribe(subscriptionEndPoint, this, this.currentAvatar);
	}
}

export {DistributedChatTable};