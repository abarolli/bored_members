package io.onicodes.boredmembers.dao;

import java.util.List;

import io.onicodes.boredmembers.entity.BoredRoom;
import io.onicodes.boredmembers.entity.Message;
import io.onicodes.boredmembers.model.BoredRoomModel;

public interface BoredRoomDAO {
	
	List<BoredRoom> getAllBoredRooms();
    
    BoredRoom getBoredRoomById(int id);
    
    List<BoredRoom> getBoredRoomsByName(String name);
    
    List<Message> getAllMessages(BoredRoom room);
    
    void deleteBoredRoom(BoredRoom room);

    void saveRoom(BoredRoomModel room);
    
    void saveRoom(BoredRoom room);
    
    void updateRoom(BoredRoom room);
}