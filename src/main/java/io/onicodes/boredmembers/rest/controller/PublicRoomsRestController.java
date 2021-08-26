package io.onicodes.boredmembers.rest.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.onicodes.boredmembers.entity.BoredRoom;
import io.onicodes.boredmembers.entity.Member;
import io.onicodes.boredmembers.service.BoredRoomService;
import io.onicodes.boredmembers.service.MemberService;

@RestController
@RequestMapping("/membership/api")
public class PublicRoomsRestController {

	private MemberService memberService;
	private BoredRoomService roomService;

	@Autowired
	public PublicRoomsRestController(MemberService memberService, BoredRoomService roomService) {
		this.memberService = memberService;
		this.roomService = roomService;
	}
	
	private class SubscriptionConfirmation {
		
		private boolean isSubscribing;
		private String roomName;
		
		public SubscriptionConfirmation() {}
		
		public SubscriptionConfirmation(boolean isSubscribing, String roomName) {
			this.isSubscribing = isSubscribing;
			this.roomName = roomName;
		}

		public boolean isSubscribing() {
			return isSubscribing;
		}

		public void setSubscribing(boolean isSubscribing) {
			this.isSubscribing = isSubscribing;
		}

		public String getRoomName() {
			return roomName;
		}

		public void setRoomName(String roomName) {
			this.roomName = roomName;
		}

	}

	@PostMapping("/joinRoom")
	public SubscriptionConfirmation joinRoom(@RequestParam("roomId") String id, Principal principal) {
		int intId = Integer.parseInt(id);
		Member member = memberService.getMemberByName(principal.getName());
		BoredRoom room = roomService.getBoredRoomById(intId);
		System.out.println(member.getUsername() + " is joining " + room.getName());
		memberService.joinRoom(member, room);
		return new SubscriptionConfirmation(true, room.getName());
	}
	
	@PostMapping("/leaveRoom")
	public SubscriptionConfirmation leaveRoom(@RequestParam("roomId") String id, Principal principal) {
		int intId = Integer.parseInt(id);
		Member member = memberService.getMemberByName(principal.getName());
		BoredRoom room = roomService.getBoredRoomById(intId);
		System.out.println(member.getUsername() + " is leaving " + room.getName());
		memberService.leaveRoom(member, room);
		return new SubscriptionConfirmation(false, room.getName());
	}
}
