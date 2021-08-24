package io.onicodes.boredmembers.dao;

import java.util.List;

import io.onicodes.boredmembers.entity.BoredRoom;
import io.onicodes.boredmembers.entity.Member;
import io.onicodes.boredmembers.entity.Message;
                   
public interface MessageDAO {
    
    Message getMessageById(int id);

    List<Message> getMessagesByMember(Member member);
    
    List<Message> getMessagesByRoom(BoredRoom room);
    
    void deleteMessage(Message message);
    
    void saveMessage(Message message);
}