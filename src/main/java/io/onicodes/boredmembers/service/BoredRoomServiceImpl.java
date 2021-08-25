package io.onicodes.boredmembers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.onicodes.boredmembers.dao.BoredRoomDAO;
import io.onicodes.boredmembers.entity.BoredRoom;
import io.onicodes.boredmembers.entity.Message;

@Service
public class BoredRoomServiceImpl implements BoredRoomService {
	
	@Autowired
	private BoredRoomDAO roomDao;
	
	@Override
	@Transactional
	public BoredRoom getBoredRoomById(int id) {
		
		return roomDao.getBoredRoomById(id);
	}

	
	@Transactional
	public List<Message> getAllMessages(BoredRoom room) {
		
		return roomDao.getAllMessages(room);
	}
}
