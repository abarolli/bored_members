package io.onicodes.boredmembers.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.onicodes.boredmembers.dao.BoredRoomDAO;
import io.onicodes.boredmembers.entity.BoredRoom;
import io.onicodes.boredmembers.entity.Member;
import io.onicodes.boredmembers.messagemodels.ChatMessage;
import io.onicodes.boredmembers.model.BoredRoomModel;
import io.onicodes.boredmembers.service.BoredRoomService;
import io.onicodes.boredmembers.service.MemberService;

@Controller
@RequestMapping("/app")
public class AppController {
	
	@Autowired
	private BoredRoomDAO roomDao;
	
	@Autowired
	private BoredRoomService roomService;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/rooms")
	public String showRooms(Model model, Principal principal) {
		
		model.addAttribute("rooms", roomDao.getAllBoredRooms());
		model.addAttribute("memberService", memberService);
		model.addAttribute("member", memberService.getMemberByName(principal.getName()));
		
		return "public-rooms";
	}
	
	
	@GetMapping("/createNewRoom")
	public String showCreateRoomForm(Model model) {
		
		model.addAttribute("room", new BoredRoomModel());
		return "create-room-form";
	}
	
	@PostMapping("/processNewRoom")
	public void processNewRoom(
			HttpServletResponse response,
			HttpServletRequest request,
			@ModelAttribute("room") BoredRoomModel room) throws IOException {
		
		
		roomDao.saveRoom(room);
		response.sendRedirect(request.getContextPath() + "/app/rooms/");
	}
	
	@PostMapping("/joinRoom")
	public String joinRoom(@RequestParam("roomId") String id, Principal principal) {
		int intId = Integer.parseInt(id);
		Member member = memberService.getMemberByName(principal.getName());
		BoredRoom room = roomService.getBoredRoomById(intId);
		memberService.joinRoom(member, room);
		return "join-room-confirmation";
	}
	
	@GetMapping("/rooms/{id}")
	public String enterRoom(Model model, @PathVariable("id") Integer id) {
		BoredRoom room = roomService.getBoredRoomById(id);
		model.addAttribute("messages", roomService.getAllMessages(room));
		
		return "chat-room";
	}

}
