function isLike(videoId, isLike) {
						$.ajax({
							url : 'likes',
							type : 'post',
							data : {
								videoId : videoId,
								isLike : isLike
							},
							success : function(response) {
								$('#likes').empty();
								$('#likes').append(response.likesCount);
								$('#dislikes').empty();
								$('#dislikes').append(response.dislikesCount);
							}
						});
					}