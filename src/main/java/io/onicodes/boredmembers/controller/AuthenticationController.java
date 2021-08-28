package io.onicodes.boredmembers.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.onicodes.boredmembers.entity.Member;
import io.onicodes.boredmembers.model.MemberModel;
import io.onicodes.boredmembers.service.MemberService;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/loginMember")
	public String login(Model model) {
		
		model.addAttribute("member", new MemberModel());
		return "login";
	}
	
	
	@GetMapping("/registerMember")
	public String showRegistrationForm(Model model) {
		
		model.addAttribute("member", new MemberModel());
		return "register";
	}
	
	
	@PostMapping("/processNewMember")
	public String processRegistrationMember(
			HttpServletRequest request,
			HttpServletResponse response,
			@Valid @ModelAttribute("member") MemberModel member,
			BindingResult validationResults,
			Model model) throws IOException {
		
		if (validationResults.hasErrors()) {
			return "register";
		}
		
		Member existingMember = memberService.getMemberByName(member.getUsername());
		if (existingMember != null) {
			return "register";
		}
		
		memberService.saveMember(member);
		
		response.sendRedirect(request.getContextPath() + "/authentication/loginMember");
		return null;
	}
}
