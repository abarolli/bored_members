package io.onicodes.boredmembers.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import io.onicodes.boredmembers.entity.BoredRoom;
import io.onicodes.boredmembers.entity.Member;
import io.onicodes.boredmembers.model.MemberModel;

public interface MemberService extends UserDetailsService {
	void saveMember(MemberModel member);
	Member getMemberById(int id);
	Member getMemberByName(String username);
	void sendMessageToRoom(Member member, String messageStr, BoredRoom room);
	boolean joinRoom(Member member, BoredRoom room);
	boolean isAlreadyMemberOf(Member member, BoredRoom room);
}
