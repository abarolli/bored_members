let canvas = document.getElementById("workflow-canvas");
console.log(canvas);



if (canvas.getContext) {
	let ctx = canvas.getContext("2d");
	
	let canvasX = canvas.getBoundingClientRect().x;
	let canvasY = canvas.getBoundingClientRect().y;
	console.log(canvasX, canvasY);
	
	const ContextFlags = {
		isMouseReady: false,
		isMouseDown: false,
		isDrawingTextBox: false
	};
	
	let pointerX, pointerY;
	let onMouseDownX, onMouseDownY;
	let GlobalCanvasObjects = {
		textBoxes: [],
		avatars: {}
	};
	
	
	let socket = new SockJS("/boredmembers_workflowsocket");
	let stompClient = Stomp.over(socket);
	
	let username = "";
	stompClient.connect({}, frame => {
		console.log(frame);
		username = frame.headers["user-name"];
		stompClient.subscribe("/app/workflow", returnedGlobalCanvasObjects => {
			console.log("recieved");
			GlobalCanvasObjects = JSON.parse(returnedGlobalCanvasObjects.body);
			console.log(GlobalCanvasObjects);
		});
		
	});
	
	
	function initEventListeners() {
		canvas.addEventListener("mouseenter", e => {
			console.log("mouseenter");
			ContextFlags.isMouseReady = true;
		});
		
		canvas.addEventListener("mouseleave", e => {
			console.log("mouseleave");
			ContextFlags.isMouseReady = false;
		});
		
		canvas.addEventListener("mousemove", e => {
			pointerX = Math.floor(e.clientX - canvasX);
			pointerY = Math.floor(e.clientY - canvasY);
			
			GlobalCanvasObjects.avatars[username] = {"coords": []}
			GlobalCanvasObjects.avatars[username].coords = [pointerX, pointerY];
			stompClient.send(
				"/boredrooms/workflow",
				{},
				JSON.stringify(GlobalCanvasObjects)
			);
		});
		
		
		let textBoxCheckBox = document.querySelector("[value='text-box']");
		textBoxCheckBox.addEventListener("click", e => {
			ContextFlags.isDrawingTextBox = textBoxCheckBox.checked;
		})
		
		canvas.addEventListener("mousedown", e => {
			if (ContextFlags.isMouseReady) {
				ContextFlags.isMouseDown = true;
				onMouseDownX = Math.floor(e.clientX - canvasX);
				onMouseDownY = Math.floor(e.clientY - canvasY);			
			}
		});
		
		canvas.addEventListener("mouseup", e => {
			if (ContextFlags.isMouseDown && ContextFlags.isDrawingTextBox) {
				GlobalCanvasObjects.textBoxes.push(new TextBox(onMouseDownX, onMouseDownY, pointerX - onMouseDownX, pointerY - onMouseDownY, ""));
				stompClient.send(
					"/boredrooms/workflow",
					{},
					JSON.stringify(GlobalCanvasObjects)
				);
			}
			ContextFlags.isMouseDown = false;
		});
		
		canvas.addEventListener("dblclick", e => {
			
		});
	}
	
	function drawAllTextBoxes() {
		GlobalCanvasObjects.textBoxes.forEach(box => {
			ctx.strokeStyle = box.strokeColor;
			ctx.strokeRect(box.x, box.y, box.width, box.height);	
		});
	}
	
	function drawAllAvatars() {
		for (let avatar in GlobalCanvasObjects.avatars) {
			let [x, y] = GlobalCanvasObjects.avatars[avatar].coords;
			ctx.fillText(avatar, x, y + 20);
			
			if (username != avatar) {
				ctx.beginPath();
				ctx.moveTo(x, y + 10);
				ctx.lineTo(x, y);
				ctx.lineTo(x + 10, y);
				ctx.stroke();
			}
		}
	}
	
	class TextBox {
		constructor(x, y, width, height, content) {
			this.x = x;
			this.y = y;
			this.height = height;
			this.width = width;
			this.strokeColor = "blue";
			this.content = content;
		}
	}
	
	function loop() {
		ctx.clearRect(0, 0, canvas.width, canvas.height);
		
		drawAllTextBoxes();
		
		drawAllAvatars();
		
		if (ContextFlags.isMouseDown) {
			console.log("ReadY");
			ctx.strokeRect(onMouseDownX, onMouseDownY, pointerX - onMouseDownX, pointerY - onMouseDownY);
		}
		
		
		requestAnimationFrame(loop);
	}
	
	initEventListeners();
	loop();
}