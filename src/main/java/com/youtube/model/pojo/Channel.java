package com.youtube.model.pojo;


public class Channel {
	private static final int DEFAULT_FOLLOWERS = 0;

	private int channelId;
	private final User user;
		
	public Channel(int channelId,User user){
		this(user);
		setChannelId(channelId);
	}
	
	public Channel(User user) {
		this.user=user;

	}
	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public User getUser() {
		return user;
	}


}
