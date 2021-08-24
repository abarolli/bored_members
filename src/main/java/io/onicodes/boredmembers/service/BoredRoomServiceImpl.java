package io.onicodes.boredmembers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.onicodes.boredmembers.dao.BoredRoomDAO;
import io.onicodes.boredmembers.entity.BoredRoom;

@Service
public class BoredRoomServiceImpl implements BoredRoomService {
	
	@Autowired
	private BoredRoomDAO roomDao;
	
	@Override
	public BoredRoom getBoredRoomById(int id) {
		
		return roomDao.getBoredRoomById(id);
	}

}
