
let canvas = new fabric.Canvas("workflow-canvas");

globalCanvasObjects = {
	
};


let socket = new SockJS("/boredmembers_workflowsocket");
let stompClient = Stomp.over(socket);
stompClient.connect({}, frame => {
	stompClient.subscribe("/app/workflow/add", message => {
		let object = JSON.parse(message.body);
		
		let textboxAttributes = object.attributes;		
		let textbox = new fabric.Textbox(textboxAttributes.text, {
			width: textboxAttributes.width,
			fontSize: textboxAttributes.fontSize,
			backgroundColor: textboxAttributes.backgroundColor,
			left: textboxAttributes.left,
			top: textboxAttributes.top
		});
		
		textbox.on("moving", options => {
			stompClient.send(
				"/boredrooms/workflow/update",
				{},
				JSON.stringify(
					{
						id: object.id,
						attributes: {
							height: textbox.height,
							width: textbox.width,
							fontSize: textbox.fontSize,
							backgroundColor: textbox.backgroundColor,
							left: textbox.left,
							top: textbox.top
						}
					}
				)
			)
		});
		
		globalCanvasObjects[object.id] = textbox;
		console.log(globalCanvasObjects);
		canvas.add(textbox);
		canvas.setActiveObject(textbox);
	});
	
	stompClient.subscribe("/app/workflow/update", message => {
		let object = JSON.parse(message.body);
		newObjectAttributes = object.attributes;
		currentObjectState = globalCanvasObjects[object.id];
		console.log(object.id);
		currentObjectState.left = newObjectAttributes.left;
		currentObjectState.top = newObjectAttributes.top;
		canvas.renderAll();
		currentObjectState.drawControls(canvas.getContext());
	});
});

function initEventListeners() {
	
	function generateId() {
		let id = 0;
		while (true) {
			if (!(id in globalCanvasObjects))
				return id;
			++id;
		}
	}
	
	let addTextboxBtn = document.getElementById("add-textbox-btn");
	addTextboxBtn.addEventListener("click", e => {
		let textBoxId = generateId();
		
		stompClient.send(
			"/boredrooms/workflow/add",
			{},
			JSON.stringify(
				{
					id: textBoxId,
					attributes: {
						width: 125,
						fontSize: 20,
						backgroundColor: "lightblue",
						left: 5,
						top: 5,
						text: "Add text here"
					}
				}
			)
		);
	});
	
	let addArrowBtn = document.getElementById("add-arrow-btn");
	addArrowBtn.addEventListener("click", e => {
		let arrowId = generateId();
		
		/*stompClient.send(
			"/boredrooms/workflow/add",
			{},
			JSON.stringify(
				{
					id: arrowId,
					attributes: {
						
					}
				}
			)
		)*/
		console.log("line clicked");
		let line = new fabric.Line({
			width: 20,
			height:5,
		});
		canvas.add(line);
	});
}

initEventListeners();
