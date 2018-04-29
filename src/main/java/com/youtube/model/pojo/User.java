package com.youtube.model.pojo;

import java.net.URL;

public class User {
	
	private int userId;
	private final String userName;
	private String password;
	private final String email;
	private String  photoURL;

	public User(int id, String userName, String password, String email,String photoURL){
		this(userName, password, email);
		this.photoURL=photoURL;
		setUserId(id);
	}

	public User(String userName, String password, String email) {

		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}
 public String getPhotoURL() {
	return photoURL;
}
 public void setPhotoURL(String photoURL) {
	this.photoURL = photoURL;
}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", email=" + email
				+ "]";
	}
}


