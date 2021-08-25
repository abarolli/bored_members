package io.onicodes.boredmembers.dao;

import io.onicodes.boredmembers.entity.*;

public interface MemberDAO {
    Member getMemberById(int id);
    
    Member getMemberByName(String username);
    
    void deleteMember(Member member);
    void updateMember(Member member);
    void saveMember(Member member);
}
