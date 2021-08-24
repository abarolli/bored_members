package io.onicodes.boredmembers.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MemberModel {
	
	@NotNull(message="Please fill in username!")
	@Size(min=1, max=99, message="Username must be between 1 and 99 characters long!")
	private String username;
	
	@NotNull(message="Please fill in avatar name!")
	@Size(min=1, max=99, message="Username must be between 1 and 99 characters long!")
	private String avatarName;
	
	@NotNull(message="Please fill in password!")
	private String password;
	
	@NotNull(message="Please fill in matching password!")
	private String matchingPassword;
	
	public MemberModel() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getAvatarName() {
		return avatarName;
	}

	public void setAvatarName(String avatarName) {
		this.avatarName = avatarName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
}
