package io.onicodes.boredmembers.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.onicodes.boredmembers.entity.BoredRoom;
import io.onicodes.boredmembers.entity.Member;
import io.onicodes.boredmembers.entity.Message;

@Repository
public class MessageDAOImpl implements MessageDAO {

    private EntityManager factory;
    
    @Autowired
    public MessageDAOImpl(EntityManager factory) {
        this.factory = factory;
    }

    @Override
    public Message getMessageById(int id) {
        Session session = factory.unwrap(Session.class);
//        session.beginTransaction();
        Message message = session.get(Message.class, id);
//        session.getTransaction().commit();
        return message;
    }

    @Override
    public List<Message> getMessagesByMember(Member member) {

        if (member != null)
            return member.getMessages();

        return null;
    }

    @Override
    public void saveMessage(Message message) {
        
        if (message != null) {
        	Session session = factory.unwrap(Session.class);
        	
        	session.saveOrUpdate(message);  
        }
    }

	@Override
	public List<Message> getMessagesByRoom(BoredRoom room) {
		
		if (room != null)
			return room.getMessages();
		
		return null;
	}

	@Override
	public void deleteMessage(Message message) {
	
		if (message != null) {
			Session session = factory.unwrap(Session.class);
//			session.beginTransaction();
			session.delete(message);
//			session.getTransaction().commit();
		}
		
	}
}
