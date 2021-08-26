package io.onicodes.boredmembers.controller;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import io.onicodes.boredmembers.entity.BoredRoom;
import io.onicodes.boredmembers.entity.Member;
import io.onicodes.boredmembers.entity.Message;
import io.onicodes.boredmembers.messagemodels.AuthoredChatMessage;
import io.onicodes.boredmembers.messagemodels.ChatMessage;
import io.onicodes.boredmembers.service.BoredRoomService;
import io.onicodes.boredmembers.service.MemberService;

@Controller
public class ChatController {

	@Autowired
	private BoredRoomService roomService;
	@Autowired
	private MemberService memberService;
	
	@MessageMapping("/chat/{roomId}")
	@SendTo("/app/rooms/{roomId}")
	public AuthoredChatMessage chatToRoom(
			@DestinationVariable("roomId") Integer roomId, 
			ChatMessage message,
			Principal principal) {
		
		BoredRoom room = roomService.getBoredRoomById(roomId);
		Member member = memberService.getMemberByName(principal.getName());
		
		memberService.sendMessageToRoom(member, message.getContent(), room);
		return new AuthoredChatMessage(member.getAvatarName(), message.getContent());
	}
}