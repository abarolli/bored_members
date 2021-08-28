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
import io.onicodes.boredmembers.model.BoredRoomModel;
import io.onicodes.boredmembers.model.message.ChatMessage;
import io.onicodes.boredmembers.service.BoredRoomService;
import io.onicodes.boredmembers.service.MemberService;

@Controller
@RequestMapping("/app")
public class AppController {
	
	private BoredRoomDAO roomDao;
	
	private BoredRoomService roomService;
	
	private MemberService memberService;
	
	@Autowired
	public AppController(BoredRoomDAO roomDao, BoredRoomService roomService, MemberService memberService) {
		super();
		this.roomDao = roomDao;
		this.roomService = roomService;
		this.memberService = memberService;
	}
	
	
	@GetMapping("/rooms")
	public String showRooms(Model model) {
		
		model.addAttribute("rooms", roomDao.getAllBoredRooms());
		model.addAttribute("memberService", memberService);
		
		return "public-rooms";
	}

	@GetMapping("/createNewRoom")
	public String showCreateRoomForm(Model model) {
		
		model.addAttribute("room", new BoredRoomModel());
		model.addAttribute("memberService", memberService);
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
	
	@GetMapping("/rooms/{id}")
	public String enterRoom(Model model, @PathVariable("id") Integer id) {
		BoredRoom room = roomService.getBoredRoomById(id);
		model.addAttribute("messages", roomService.getAllMessages(room));
		model.addAttribute("memberService", memberService);
		
		return "chat-room";
	}
	
}
