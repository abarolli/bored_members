package io.onicodes.boredmembers.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.onicodes.boredmembers.entity.Member;
import io.onicodes.boredmembers.service.MemberService;

@Controller
public class HomeController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/")
	public String home(Model model) {
		
		model.addAttribute("memberService", memberService);
		
		return "index";
	}
	
	@GetMapping("/about")
	public String about() {
		
		return "about";
	}
}
