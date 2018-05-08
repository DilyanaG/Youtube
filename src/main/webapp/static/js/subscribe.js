function unsubscribe(channelId){
	  $.ajax({
			url : 'subscribe'+ '?' + $.param({
				"channel" : channelId
				}),
			type : 'delete',
			success : function(response) {
				$('#subscribeButton').empty();
				 $('#subscribeButton').append(
		     		' <button onclick="subscribe('+channelId+')" class="button">SUBSCRIBE</button>');
			}
	  });
}

function subscribe(channelId){
	  $.ajax({
			url : 'subscribe',
			type : 'post',
			data : {
				channel : channelId
			},
			success : function(response) {
				$('#subscribeButton').empty();
				 $('#subscribeButton').append(
		     		' <button onclick="unsubscribe('+channelId+')" class="button">UNSUBSCRIBE</button>');
			}
	  });
}   