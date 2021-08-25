package io.onicodes.boredmembers.dao;


import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.onicodes.boredmembers.entity.Member;

@Repository
public class MemberDAOImpl implements MemberDAO {

//	USE ENTITYMANAGER WHEN WORKING WITH SPRING BOOT
    private EntityManager factory;

    @Autowired
    public MemberDAOImpl(EntityManager factory) {
        this.factory = factory;
    }

    @Override
    public Member getMemberById(int id) {
        
        Session session = factory.unwrap(Session.class);
//        session.beginTransaction();
        Member member = session.get(Member.class, id);
//        session.getTransaction().commit();
        return member;
    }

    @Override
    public Member getMemberByName(String username) {
        
        Session session = factory.unwrap(Session.class);
        String queryStr = String.format("""
            from Member m
            where m.username = '%s'
        """, username);
        List<Member> members = session.createQuery(queryStr, Member.class).getResultList();
        if (members.isEmpty()) {
        	return null;
        }
        return members.get(0);
    }

    @Override
    public void saveMember(Member member) {
        
        if (member != null) {
        	Session session = factory.unwrap(Session.class);
        	session.saveOrUpdate(member);
        }
    }
    
    @Override
    public void updateMember(Member member) {
    	
    	if (member != null) {
    		Session session = factory.unwrap(Session.class);
    		session.update(member);
    	}
    		
    }
    
	@Override
	public void deleteMember(Member member) {
		
		if (member != null) {
			Session session = factory.unwrap(Session.class);
//			session.beginTransaction();
			session.delete(member);		
//			session.getTransaction().commit();
		}
		
	}
}
