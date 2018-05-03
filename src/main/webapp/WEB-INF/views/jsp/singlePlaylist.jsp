  <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="./error"%>


<jsp:include page="header.jsp" />
<jsp:include page="sideMenu.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<div class="show-top-grids">
		<div class="col-sm-8 single-left">
			<div class="song">
				<div id="currentVideo" class="video-grid">
					<!--  in src put -> {get currentVideo.videoUrl}  -->
					<iframe src="https://www.youtube.com/embed/oYiT-vLjhC4"
						allowfullscreen=""></iframe>
				</div>
			</div>
			<div class="song-grid-right">
				<div id="videoOptions" class="share">
					<h5>Options</h5>
					<ul>
						<li><a href="#" class="icon fb-icon">Share</a></li>
						<!--   put -> {get currentVideo.views}  -->
						<li class="view">200 Views</li>
						<!--   put -> {get currentVideo.likes}  -->
						<li><a href="#" class="icon like">26 Likes</a></li>
						<!--   put -> {get currentVideo.dislikes}  -->
						<li><a href="#" class="icon dribbble-icon">9 Dislikes</a></li>
					</ul>
				</div>
			</div>
			<div class="clearfix"></div>
			<div class="published">
				<script src="/FinalProject/js/jquery.min.js"></script>
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
						<img src="images/male.png" alt="user_profile_picture"
							id="profile-pic">
						<p class="author">
							<!--   put -> ./profile?channelId={get currentVideo.channelId} 
						 and {get currentVideo.username} 
						  and {get currentVideo.createDate} -->
							By <a href="#" class="author">John Maniya</a> <br> <span>Published
								on: 5 June 2015</span>
						</p>

						<div class="heading-right">
							<!-- here ajax   -->
							<a href="#small-dialog8" class="play-icon popup-with-zoom-anim">Subscribe</a>
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
							<p>Nullam fringilla sagittis tortor ut rhoncus.</p>
						</li>
						<li>
							<div class="load-grids">
								<div class="load-grid">
									<p>Category</p>
								</div>
								<div class="load-grid">
									<a href="movies.html">Entertainment</a>
								</div>
								<div class="clearfix"></div>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="all-comments">
				<div class="all-comments-info">
					<h3>COMMENTS</h3>
					<div class="box">
						<form>

							<textarea placeholder="Message" required=" "></textarea>
							<input type="submit" value="SEND">
							<div class="clearfix"></div>
						</form>
					</div>
					<div class="all-comments-buttons"></div>
				</div>
				<div id="comments">
					<c:forEach items="${currentVideoComments}" var="comment">

						<div class="media-grids">
							<div class="media">
								<!-- currentVideoComments.username or channelname here  -->
								<h5>Tom Brown</h5>
								<div class="media-left">
									<a href="#"> </a>
								</div>
								<div class="media-body">
									<!-- currentVideoComments.content  here  -->
									<p>Maecenas ssdasd</p>
									<!-- currentVideoComments.date  -->
									<span>Posted on : 12 June 2015 </span>
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
				<c:forEach items="${currentVideoComments}" var="video">
					<div class="single-right-grids">
						<div class="col-md-4 single-right-grid-left">
							<a onclick="openVideo(${video.videoId})"> <img
								src="/FinalProject/images/r1.jpg" alt="">
							</a>
						</div>
						<div class="col-md-8 single-right-grid-right">
							<!-- video.title -->
							<a href="" class="title">video title</a>
							<p class="author">
								<!-- video.name and video.id for link to author profile -->
								By <a href="#" class="author">AUTHOR HERE</a>
							</p>
						</div>
						<div class="clearfix"></div>
					</div>
				</c:forEach>
			</div>

		</div>
		<div class="clearfix"></div>
	</div>
	<!-- footer -->
	<jsp:include page="footer.jsp" />
	<!-- //footer -->
</div>
<div class="clearfix"></div>

<!-- Bootstrap core JavaScript
    ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/FinalProject/js/bootstrap.min.js"></script>
<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
<script>
function openVideo(id) {
	var video;
	$.ajax({
		url : 'changeVideo',
		type : 'get',
		data: {videoId: id },
		success: function(response){
			
		       console.log(response.currentVideo[0].videoId)
		       console.log(response.comments[0].commentId)
		       console.log(response.playlistVideos[0].videoId)
                video=response.currentVideo[0]; 
				$('#currentVideo').empty();
				$('#currentVideo').append(
						      '<iframe src="https://www.youtube.com/embed/oYiT-vLjhC4"'
							 +'allowfullscreen=""></iframe>'
						);
				$('#videoOptions').empty();
				$('#videoOptions').append(
						'	<h5>Options</h5>'
						+'   <ul>'
						+'	    <li><a href="#" class="icon fb-icon">Share</a></li>'
						+'	 	<!--   put -> video.views -->'
						+'	 	<li class="view">200 Views</li>'
						+'	 	<!--   put -> video.likes  -->'
						+'	 	<li><a href="#" class="icon like">26 Likes</a></li>'
						+'		<!--   put -> {get currentVideo.dislikes}  -->'
						+'  	<li><a href="#" class="icon dribbble-icon">9 Dislikes</a></li>'
						+'  </ul>'
						);
				$('#currentVideoAuthor').empty();
				$('#currentVideoAuthor').append(
						' <img src="images/male.png" alt="user_profile_picture"'
						+' 	id="profile-pic">'
						+'  <p class="author">'
						+'       	By <a href="#" class="author">John Maniya</a> <br> <span>Published'
						+' 		on: 5 June 2015</span>'
						+' </p>'
						+' <div class="heading-right">'
						+' 	<a href="#small-dialog8" class="play-icon popup-with-zoom-anim">Subscribe</a>'
						+' </div>'
						);
				
				$('#comments').empty();
				for (i = 0; i < response.comments.length; i++) {
				$('#comments').append(
							'<div class="media-grids">'
							+' <div class="media">'
							+'	 <h5>'+response.comments[i].content+'</h5>'
							+'	<div class="media-left">'
							+'		<a href="#"> </a>'
							+'	</div>'
							+'	<div class="media-body">'		
							+'		<p>'+response.comments[i].content+'</p>'	
							+'		<span>Posted on :'+response.comments[i].publicationDate.hour+':'
							                          +response.comments[i].publicationDate.minute+' ' 
							                          +response.comments[i].publicationDate.dayOfMonth+'  '
							                          +response.comments[i].publicationDate.month+' '
							                          +response.comments[i].publicationDate.year+' '
							
							+'</span>'
							+'		</div>'
							+' 	</div>'
							+'</div>'
						
					);
				}
				$('#playlistVideos').empty();
				for (i = 0; i < response.playlistVideos.length; i++) {
				$('#playlistVideos').append(
						
						'	<div class="single-right-grids">'
						+'	<div class="col-md-4 single-right-grid-left">'
						+'		<a onclick="openVideo('+response.playlistVideos[i].videoId+')"> <img'
						+'			src="images/r1.jpg" alt="">'
						+'		</a>'
						+'	</div>'
						+'	<div class="col-md-8 single-right-grid-right">'
						+'		<a href="" class="title">video title</a>'
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
};	
</script>

</body>
</html>