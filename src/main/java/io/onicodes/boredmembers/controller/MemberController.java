package io.onicodes.boredmembers.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.onicodes.boredmembers.entity.Member;
import io.onicodes.boredmembers.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

	private MemberService memberService;
	
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/profile")
	public String getProfile(Model model, Principal principal) {
		
		Member member = memberService.getMemberByName(principal.getName());
		model.addAttribute("member", member);
		return "member-profile";
	}
}
