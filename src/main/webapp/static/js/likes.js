function isLike(videoId, isLike) {
	$.ajax({
		url : 'likes',
		type : 'post',
		data : {
			videoId : videoId,
			isLike : isLike
		},
		success : function(response) {
			console.log("before empty likes");
			$('#likes').empty();
			$('#subscribeButton').append(likesCount);
			$('#dislikes').empty();
			$('#subscribeButton').append(dislikesCount);
		}
	});
}