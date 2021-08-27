package io.onicodes.boredmembers.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.onicodes.boredmembers.service.MemberService;
import io.onicodes.boredmembers.service.MessageService;

@RestController
@RequestMapping("/chat/api")
public class ChatMessageRestController {
	
	private MessageService messageService;
	
	@Autowired
	public ChatMessageRestController(MessageService messageService) {
		this.messageService = messageService;
	}

	public ChatMessageRestController() {}
	
	@PostMapping("/deleteMessage")
	public void deleteMessage(@RequestParam("messageId") String id) {
		System.out.println("Deleting message " + id);
		messageService.deleteMessage(Integer.parseInt(id));
	}
}
