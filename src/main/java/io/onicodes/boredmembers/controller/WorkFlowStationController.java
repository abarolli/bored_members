package io.onicodes.boredmembers.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import io.onicodes.boredmembers.model.workflow.Avatar;
import io.onicodes.boredmembers.model.workflow.CanvasObject;
import io.onicodes.boredmembers.model.workflow.GlobalCanvasObjects;

@Controller
public class WorkFlowStationController {
	
	private Map<String, Map<String, Avatar>> avatarsInRooms = new HashMap<>();
	
	@EventListener
	public void listenForSubscription(SessionSubscribeEvent event) {
		
		Map<String, ArrayList> subscription = 
				(Map<String, ArrayList>) event.getMessage().getHeaders().get("nativeHeaders");
		
		String destination = (String) subscription.get("destination").get(0);
		if (!avatarsInRooms.containsKey(destination))
			avatarsInRooms.put(destination, new HashMap<>());
		
		avatarsInRooms.get(destination).put(event.getUser().getName(), new Avatar(new int[] {0, 0}));
		avatarsInRooms.get(destination).forEach((name, avatar) -> {
			System.out.println(name + " is at " + avatar.getCoords());
		});
	}
	
	@EventListener
	public void listenForUnsubscription(SessionUnsubscribeEvent event) {
		System.out.println("Unsubbed");
	}
	
	@MessageMapping("/workflow/add")
	@SendTo("/app/workflow/add")
	public CanvasObject sendPointerInfo(CanvasObject canvasObject, Principal principal) {
		System.out.println(canvasObject.getId());
		return canvasObject;
	}
	
	@MessageMapping("/workflow/update")
	@SendTo("/app/workflow/update")
	public CanvasObject updateCanvas(CanvasObject canvasObject) {
		
	//		avatarsInRooms
	//		.get("/app/workflow")
	//		.put(
	//			principal.getName(),
	//			globalCanvasObjects.getAvatars().get(principal.getName())
	//		);
	//	
	//	globalCanvasObjects.setAvatars(avatarsInRooms.get("/app/workflow"));
		
		return canvasObject;
	}
}
