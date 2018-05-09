function writeComment(videoId) {
	var message = $('#message').val();
	$.ajax({
		url : 'comment',
		type : 'post',
		data : {
			videoId : videoId,
			message : message
		},
		success : function(response) {
			$("#message").val(''); 
		}
	});
}