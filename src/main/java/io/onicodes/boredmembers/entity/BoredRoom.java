package io.onicodes.boredmembers.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="bored_rooms")
public class BoredRoom {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @ManyToMany(cascade={
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REFRESH
    }, fetch=FetchType.LAZY)
    @JoinTable(
        name="member_room_memberships",
        joinColumns=@JoinColumn(name="room_id"),
        inverseJoinColumns=@JoinColumn(name="member_id")
    )
    private List<Member> members;

    @OneToMany(cascade={
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REFRESH
    }, fetch=FetchType.LAZY, mappedBy="room")
    private List<Message> messages;

    public BoredRoom() {}

    public BoredRoom(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
