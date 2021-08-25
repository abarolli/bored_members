package io.onicodes.boredmembers.entity;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="messages")
public class Message {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="contents")
    private String contents;

    @Column(name="date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    @ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
    @JoinColumn(name="room_id")
    private BoredRoom room;
                                 
    public Message() {}

    public Message(String contents, Member member, BoredRoom room, Date dateTime) {
        this.contents = contents;
        this.member = member;
        this.room = room;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public BoredRoom getRoom() {
        return room;
    }

    public void setRoom(BoredRoom room) {
        this.room = room;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
