package com.youtube.model.dto.channel;


import java.util.ArrayList;
import java.util.List;

import com.youtube.model.dto.video.VideoTopViewDTO;
import com.youtube.model.pojo.Channel;
import com.youtube.model.pojo.Video;

public class ProfileViewDTO {
  
	private int channelId;
	private String username;
	private String pictureUrl;
	private List<SubscriptionDTO> subscriptions= new ArrayList<>();
	
	
	
	public ProfileViewDTO(Channel currentUser, List<Channel> subscriptions) {
		this.channelId=currentUser.getChannelId();
		this.username=currentUser.getUser().getUserName();
		this.pictureUrl=currentUser.getUser().getPhotoURL()==null?"":currentUser.getUser().getPhotoURL();
		for(Channel subs:subscriptions){
			 this.subscriptions.add(new SubscriptionDTO(subs));
		}
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public List<SubscriptionDTO> getSubscriptions() {
		return subscriptions;
	}
	public void setSubscriptions(List<SubscriptionDTO> subscriptions) {
		this.subscriptions = subscriptions;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	
	
}
