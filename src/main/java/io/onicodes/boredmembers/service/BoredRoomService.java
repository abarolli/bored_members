package io.onicodes.boredmembers.service;

import java.util.List;

import io.onicodes.boredmembers.entity.BoredRoom;
import io.onicodes.boredmembers.entity.Message;

public interface BoredRoomService {
	
	BoredRoom getBoredRoomById(int id);
	List<Message> getAllMessages(BoredRoom room);
}
