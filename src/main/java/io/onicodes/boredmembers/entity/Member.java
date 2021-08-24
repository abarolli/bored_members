package io.onicodes.boredmembers.entity;

import java.util.ArrayList;
import java.util.Collection;
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
@Table(name="members")
public class Member {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="avatar_name")
    private String avatarName;

    @ManyToMany(cascade={
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REFRESH
    }, fetch=FetchType.LAZY)
    @JoinTable(
        name="member_roles",
        joinColumns=@JoinColumn(name="member_id"),
        inverseJoinColumns=@JoinColumn(name="role_id")
    )
    private Collection<Role> roles;

    @ManyToMany(cascade={
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REFRESH
    }, fetch=FetchType.EAGER)
    @JoinTable(
        name="member_room_memberships",
        joinColumns=@JoinColumn(name="member_id"),
        inverseJoinColumns=@JoinColumn(name="room_id")
    )
    private List<BoredRoom> memberships = new ArrayList<BoredRoom>();

    @OneToMany(cascade={
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REFRESH
    }, fetch=FetchType.LAZY, mappedBy="member")
    private List<Message> messages = new ArrayList<Message>();

    public Member() {}

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }
    

    public Member(String username, String password, String avatarName) {
		super();
		this.username = username;
		this.password = password;
		this.avatarName = avatarName;
	}

	public Member(String username, String password, List<BoredRoom> memberships) {
        this.username = username;
        this.password = password;
        this.memberships = memberships;
    }

    public Member(String username, String password, List<BoredRoom> memberships, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.memberships = memberships;
        this.roles = roles;
    }


    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<BoredRoom> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<BoredRoom> memberships) {
        this.memberships = memberships;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public List<Message> getMessages() {
        return messages;
    }

	public String getAvatarName() {
		return avatarName;
	}

	public void setAvatarName(String avatarName) {
		this.avatarName = avatarName;
	}
    
    
}