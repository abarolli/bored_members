package io.onicodes.boredmembers.dao;


import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.onicodes.boredmembers.entity.BoredRoom;
import io.onicodes.boredmembers.entity.Message;
import io.onicodes.boredmembers.model.BoredRoomModel;

@Repository
public class BoredRoomDAOImpl implements BoredRoomDAO {

    private EntityManager factory;

    @Autowired
    public BoredRoomDAOImpl(EntityManager factory) {
        this.factory = factory;
    }
    
    @Override
    @Transactional
	public List<BoredRoom> getAllBoredRooms() {
		Session session = factory.unwrap(Session.class);
		
		String queryStr = "from BoredRoom";
		return session.createQuery(queryStr, BoredRoom.class).getResultList();
	}

    @Override
    @Transactional
    public List<Message> getAllMessages(BoredRoom room) {
    	
    	return room.getMessages();
    }
    
    
	@Override
	public void saveRoom(BoredRoomModel room) {
		
		BoredRoom newRoom = new BoredRoom(room.getName(), room.getDescription());
		saveRoom(newRoom);
	}

	@Override
    public BoredRoom getBoredRoomById(int id) {
        
        Session session = factory.unwrap(Session.class);
        BoredRoom room = session.get(BoredRoom.class, id);
        return room;
    }

    @Override
    public void saveRoom(BoredRoom room) {

        if (room != null) {
        	Session session = factory.unwrap(Session.class);
        	session.saveOrUpdate(room);
        }
    }
    
    @Override
    public void updateRoom(BoredRoom room) {
    	
    	if (room != null) {
    		Session session = factory.unwrap(Session.class);
    		session.update(room);
    	}
    }
    
	@Override
	public List<BoredRoom> getBoredRoomsByName(String name) {
		
		String queryStr = String.format("""
			from BoredRoom br
			where br.name == %s	
		""", name);
		
		Session session = factory.unwrap(Session.class);
//		session.beginTransaction();
		List<BoredRoom> rooms = session.createNamedQuery(queryStr, BoredRoom.class).getResultList();
//		session.getTransaction().commit();
		return rooms;
	}

	@Override
	public void deleteBoredRoom(BoredRoom room) {
		
		if (room != null) {
			Session session = factory.unwrap(Session.class);
//			session.beginTransaction();
			session.delete(room);
//			session.getTransaction().commit();
		}
	}
    
    
}
