package io.onicodes.boredmembers.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.onicodes.boredmembers.dao.BoredRoomDAO;
import io.onicodes.boredmembers.dao.MemberDAO;
import io.onicodes.boredmembers.dao.MessageDAO;
import io.onicodes.boredmembers.entity.*;
import io.onicodes.boredmembers.model.MemberModel;

@Service
public class MemberServiceImpl implements MemberService {

	private MemberDAO memberDao;
	private MessageDAO messageDao;
	private BoredRoomDAO boredRoomDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private EntityManager factory;
	
	@Autowired
	public MemberServiceImpl(MemberDAO memberDao, MessageDAO messageDao, BoredRoomDAO boredRoomDao) {
		this.memberDao = memberDao;
		this.messageDao = messageDao;
		this.boredRoomDao = boredRoomDao;
	}
	
	
	@Override
	@Transactional
	public void saveMember(MemberModel member) {
		
		// RETRIEVE ENCODED PASSWORD
		String encodedPassword = passwordEncoder.encode(member.getPassword());
		Member newMember = new Member(member.getUsername(), encodedPassword, member.getAvatarName());
		memberDao.saveMember(newMember);
		System.out.println(newMember.getUsername());
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberDao.getMemberByName(username);
		if (member == null)
			throw new UsernameNotFoundException("Invalid username or password");
			
		return new User(member.getUsername(), member.getPassword(), mapRolesToAuthorities(member.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Member getMemberById(int id) {
		return memberDao.getMemberById(id);
	}
	
	@Override
	@Transactional
	public Member getMemberByName(String username) {
		return memberDao.getMemberByName(username);
	}

	@Override
	@Transactional
	public void sendMessageToRoom(Member member, String messageStr, BoredRoom room) {
		
		
		Message message = new Message(messageStr, member, room, new Date());
		messageDao.saveMessage(message);
	}

	@Override
	@Transactional
	public boolean joinRoom(Member member, BoredRoom room) {
		
		List<BoredRoom> memberships = member.getMemberships();
		for (var boredroom : memberships) {
			if (boredroom.getId() == room.getId()) {
				return false;
			}
		}
		memberships.add(room);
		
		memberDao.saveMember(member);
		return true;
	}


	@Override
	@Transactional
	public boolean isAlreadyMemberOf(Member member, BoredRoom room) {
		
		String queryStr = """
			select m
			from Member m
			JOIN m.memberships mm
			WHERE mm.id = :roomId
		""";
		List<Member> isAMember = factory.createQuery(queryStr, Member.class)
					   					.setParameter("roomId", room.getId())
				   						.getResultList();
		
		return !isAMember.isEmpty();
	}

}
