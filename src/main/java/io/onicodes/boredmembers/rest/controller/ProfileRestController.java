package io.onicodes.boredmembers.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.onicodes.boredmembers.entity.Message;
import io.onicodes.boredmembers.messagemodels.AuthoredChatMessage;
import io.onicodes.boredmembers.service.BoredRoomService;

@RestController
@RequestMapping("/profile/api")
public class ProfileRestController {
	
	@Autowired
	private BoredRoomService roomService;
	
	
	@GetMapping("/room/{roomId}")
	public List<AuthoredChatMessage> sendRoomContent(@PathVariable("roomId") String id) {
		
		List<Message> messages = roomService
								 .getBoredRoomById(Integer.parseInt(id))
								 .getMessages();
		
		List<AuthoredChatMessage> authoredMessages = new ArrayList<>();
		for (var message : messages) {
			authoredMessages.add(new AuthoredChatMessage(message.getMember().getAvatarName(), message.getContents()));
		}
		return authoredMessages;
	}
}
