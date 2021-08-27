package io.onicodes.boredmembers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.onicodes.boredmembers.dao.BoredRoomDAO;
import io.onicodes.boredmembers.dao.MemberDAO;
import io.onicodes.boredmembers.dao.MessageDAO;
import io.onicodes.boredmembers.entity.Message;

@Service
public class MessageServiceImpl implements MessageService {

	private MemberDAO memberDao;
	private MessageDAO messageDao;
	private BoredRoomDAO boredRoomDao;
	
	
	@Autowired
	public MessageServiceImpl(MemberDAO memberDao, MessageDAO messageDao, BoredRoomDAO boredRoomDao) {
		this.memberDao = memberDao;
		this.messageDao = messageDao;
		this.boredRoomDao = boredRoomDao;
	}
	
	@Override
	@Transactional
	public void deleteMessage(int id) {
		messageDao.deleteMessage(id);
		System.out.println("Message deleted!");
	}

	@Override
	@Transactional
	public void deleteMessage(Message message) {
		messageDao.deleteMessage(message);
	}

}
