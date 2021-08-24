package io.onicodes.boredmembers.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.onicodes.boredmembers.dao.BoredRoomDAO;
import io.onicodes.boredmembers.model.BoredRoomModel;

@Controller
@RequestMapping("/app")
public class AppController {
	
	@Autowired
	private BoredRoomDAO roomDao;
	
	@GetMapping("/rooms")
	public String showRooms(Model model) {
		
		model.addAttribute("rooms", roomDao.getAllBoredRooms());
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
}
