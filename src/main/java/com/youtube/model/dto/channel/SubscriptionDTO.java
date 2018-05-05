package com.youtube.model.dto.channel;

import com.youtube.model.pojo.Channel;

public class SubscriptionDTO {
    
	private int id;
	private String name;
	
	
	
	
	public SubscriptionDTO(Channel subs) {
		this.id=subs.getChannelId();
		this.name=subs.getUser().getUserName();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
}
