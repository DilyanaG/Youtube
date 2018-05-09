package com.youtube.model.dto.video;

public class LikesDTO {
	private int likesCount;
	private int dislikesCount;
	
	public LikesDTO(int likesCount, int dislikesCount) {
		this.likesCount = likesCount;
		this.dislikesCount = dislikesCount;
	}

	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	public int getDislikesCount() {
		return dislikesCount;
	}

	public void setDislikesCount(int dislikesCount) {
		this.dislikesCount = dislikesCount;
	}
	
	
}
