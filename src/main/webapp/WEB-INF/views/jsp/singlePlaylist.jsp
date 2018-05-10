  <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="./error"%>

<style>
.button {
	background-color: #21DEEF;
	border: none;
	color: white;
	border-radius: 3px;
	padding: 10px 25px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	margin: 4px 2px;
	cursor: pointer;
	font-size: 16px
}
</style>
<jsp:include page="header.jsp" />
<jsp:include page="sideMenu.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

	<c:if test="${ not empty currentVideo}">
	<div class="show-top-grids">
		<div class="col-sm-8 single-left">
			<div class="song">
			     <div id="songInfo" class="song-info">
					<h3>${currentVideo.title}</h3>
				</div>
				<div id="currentVideo" class="video-grid">
					<video width="640" height="264" controls>
						<source src="${currentVideo.videoUrl}" type="video/mp4">
					</video>
				</div>
			</div>
			<div class="song-grid-right">
				<div id="videoOptions" class="share">
					<h5>Options</h5>
					<ul>
						<!--   put -> {get currentVideo.views}  -->
						<li class="view">${currentVideo.views}</li>
						<!--   put -> {get currentVideo.likes}  -->
						<li><a  class="icon like">${currentVideo.likes}</a></li>
						<!--   put -> {get currentVideo.dislikes}  -->
						<li><a  class="icon dribbble-icon">${currentVideo.dislikes}</a></li>
					</ul>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="published">
				<script src="FinalProject/js/jquery.min.js"></script>
				<script>
					$(document).ready(function() {
						size_li = $("#myList li").size();
						x = 1;
						$('#myList li:lt(' + x + ')').show();
						$('#loadMore').click(function() {
							x = (x + 1 <= size_li) ? x + 1 : size_li;
							$('#myList li:lt(' + x + ')').show();
						});
						$('#showLess').click(function() {
							x = (x - 1 < 0) ? 1 : x - 1;
							$('#myList li').not(':lt(' + x + ')').hide();
						});
					});
				</script>
				<div class="load_more">
					<div id="currentVideoAuthor" class="main_video_info">
						<img src="${currentVideo.profilePictureUrl}" alt="user_profile_picture"
							id="profile-pic" width="100" height="100">
						<p class="author">
							By <a href="./profile?channelId=${currentVideo.channelId}"
								class="author">${currentVideo.username}</a> <br> <span>Published
								on: ${currentVideo.uploadDate}</span>
						</p>

						<div class="heading-right">
							<!-- here ajax   -->
							<c:if test="${not empty sessionScope.channelId}">
							        <c:if test="${not empty logged}">
							       <a  onclick="removeVideoFromPlaylist(${currentVideo.videoId},${playlistId},${playlistVideos[0].videoId })"class="play-icon popup-with-zoom-anim">REMOVE VIDEO FROM PLAYLIST</a>
						           </c:if>
						   </c:if>
						</div>

					</div>

					<br>
					<ul id="myList">
						<br>
						<br>
						<br>
						<br>
						<br>
						<li style="display: list-item;">
							<p>${currentVideo.description}</p>
						</li>
						
					</ul>
				</div>
			</div>
			<div class="all-comments">
				<div class="all-comments-info">
					<h3>COMMENTS</h3>
					<script src="FinalProject/js/comments.js"></script>
					<div class="box">
					<c:if test="${not empty sessionScope.channelId}">
						<textarea id="message" placeholder="Message" name="message"></textarea>
							<br>
							<div id ="commentButton">
							    <button onclick="writeComment2(${currentVideo.videoId},${playlistId})" class="button">SEND</button>
							</div>
							
							<div class="clearfix"></div>
					</c:if>
					</div>
					<div class="all-comments-buttons"></div>
				</div>
				<div id="comments">
					<c:forEach items="${currentVideo.comments}" var="comment">

						<div class="media-grids">
							<div class="media">
								<!-- currentVideoComments.username or channelname here  -->
								<h5>${comment.username}</h5>
								<div class="media-left">
									<a href="./profile?channelId=${comment.channelId}">
										<img src="${comment.profilePicture}" width="65" height="65" style="border-radius: 50%"></img>
									</a>
								</div>
								<div class="media-body">
									<!-- currentVideoComments.content  here  -->
									<p>${comment.message}</p>
									<!-- currentVideoComments.date  -->
									<span>Posted on : ${comment.uploadDate} </span>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>


		<div class="col-md-4 single-right">
			<h3>PLAYLIST VIDEOS</h3>
			<!-- alvideosForPlaylist  -->

			<div id="playlistVideos" class="single-grid-right">
				<!-- here playlistVideos  -->
				<c:forEach items="${playlistVideos}" var="video">
					<div class="single-right-grids">
						<div  class="col-md-4 single-right-grid-left">
						
							<a onclick="openVideo(${video.videoId},${playlistId})"> <img 
								src="${video.photoUrl}" alt="">
							</a>
						</div>
						<div class="col-md-8 single-right-grid-right">
							<!-- video.title -->
							<a class="title">${video.title}</a>
							<p class="author">
								<!-- video.name and video.id for link to author profile -->
								By <a href="./profile?channelId=${video.channelId}" class="author">${video.username}</a>
							</p>
						</div>
						<div class="clearfix"></div>
					</div>
				</c:forEach>
			</div>

		</div>
		<div class="clearfix"></div>
	</div>
	</c:if>
	<c:if test="${empty currentVideo}">
	   <div class="show-top-grids">
	   <h3>PLAYLIST IS EMPTY</h3>
	   <div class="clearfix"></div>
	</div>
	</c:if>
	<!-- footer -->
	<jsp:include page="footer.jsp" />
	<!-- //footer -->
</div>
<div class="clearfix"></div>

<!-- Bootstrap core JavaScript
    ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="FinalProject/js/bootstrap.min.js"></script>
<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
<script>


function removeVideoFromPlaylist(videoId,playlistId,nextVideoId){
	$.ajax({
		url : 'removeVideoFromPlaylist',
		type : 'post',
		data: {videoId: videoId, playlistId: playlistId},
		success: function(response){
			openVideo(nextVideoId,playlistId);
			}

    });
}


function openVideo(videoId,playlistId) {
	var video;
	$.ajax({
		url : 'changeVideo',
		type : 'get',
		data: {videoId: videoId, playlistId: playlistId},
		success: function(response){
                video=response.currentVideo[0]; 
                $('#songInfo').empty();
				$('#songInfo').append(
						'<h3>'+video.title+'</h3>'
						);
				$('#currentVideo').empty();
				$('#currentVideo').append(
								'<video width="640" height="264" controls>'
								 +'  <source src="'+video.videoUrl+'" type="video/mp4">'
							     +'</video>'
								);
				$('#videoOptions').empty();
				$('#videoOptions').append(
						'	<h5>Options</h5>'
						+'   <ul>'
						
						+'	 	<li class="view">'+video.views+'</li>'
						
						+'	 	<li><a class="icon like">'+video.likes+'</a></li>'
					
						+'  	<li><a class="icon dribbble-icon">'+video.dislikes+'</a></li>'
						+'  </ul>'
						); 
				$('#currentVideoAuthor').empty();
				$('#currentVideoAuthor').append(
						' <img src= "'+video.profilePictureUrl+'" alt="user_profile_picture width="100" height="100" "'
						+' 	id="profile-pic">'
						+'  <p class="author">'
						+'       	By <a href="./profile?channelId='+video.channelId+'" class="author">'+video.username+'</a> <br> <span></span>'
						+' </p>'
						+' <div class="heading-right">'
						+' 	<a onclick="removeVideoFromPlaylist('+video.videoId+',${playlistId},'+response.playlistVideos[0].videoId+')" class="play-icon popup-with-zoom-anim">REMOVE VIDEO FROM PLAYLIST</a>'
						+' </div>'
						);
				
				$('#commentButton').empty();
				$('#commentButton').append(
						'<button onclick="writeComment2('+video.videoId+',${playlistId})" class="button">SEND</button>'
						);
				$('#comments').empty();
				for (i = 0; i < video.comments.length; i++) {
				$('#comments').append(
						'<div class="media-grids">'
						+'<div class="media">'
							<!-- currentVideoComments.username or channelname here  -->
							+'	<h5>'+video.comments[i].username+'</h5>'
							+'<div class="media-left">'
							+'	<a href="./profile?channelId=${comment.channelId}">'
							+'		<img src="'+video.comments[i].profilePicture+'" width="65" height="65" style="border-radius: 50%"></img>'
							+'	</a>'
							+'</div>'
							+'<div class="media-body">'
						
							+'	<p>'+video.comments[i].message+'</p>'
								<!-- currentVideoComments.date  -->
							+'	<span>Posted on : '+video.comments[i].uploadDate+' </span>'
							+'</div>'
							+'</div>'
							+'</div>'
					);
				}
				$('#playlistVideos').empty();
				for (i = 0; i < response.playlistVideos.length; i++) {
				$('#playlistVideos').append(
						
						'	<div class="single-right-grids">'
						+'	<div class="col-md-4 single-right-grid-left">'
						+'		<a onclick="openVideo('+response.playlistVideos[i].videoId+',${playlistId})"> <img'
						+'			src="'+response.playlistVideos[i].photoUrl+'" alt="">'
						+'		</a>'
						+'	</div>'
						+'	<div class="col-md-8 single-right-grid-right">'
						+'		<a href="" class="title">'+response.playlistVideos[i].title+'</a>'
						+'		<p class="author">'
						+'			By <a href="'+response.playlistVideos[i].username+'" class="author">'
						           +response.playlistVideos[i].username+'</a>'
						+'		</p>'
						+'	</div>'
						+' <div class="clearfix"></div>'
						+'</div>'
						
				  );
				}
		}
	});				
}	
</script>

</body>
</html>