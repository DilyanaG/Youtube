<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="./error"%>

<style>
.button {
	background-color: aqua; /* Green */
	border: none;
	color: white;
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
<div class="col-sm-3 col-md-2 sidebar"
	style="background-color: WhiteSmoke;">
	<div class="top-navigation">
		<div class="t-menu"></div>
		<div class="t-img">
			<img src="${sessionScope.photoUrl}" alt="">
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="drop-navigation drop-navigation">
		<ul class="nav nav-sidebar">
			<img src="${sessionScope.photoUrl}" alt="" width="150" height="150"
				style="margin-left: 50px" />
			<li class="active"><a href="" class="home-icon"> <span
					aria-hidden="true" style="margin-left: 50px">
						${profile.username}</span>
			</a></li>
			<li onclick="getVideos(${profile.channelId},'date')"><a
				class="user-icon"><span
					class="glyphicon glyphicon-home glyphicon-blackboard"
					aria-hidden="true"></span>VIDEOS </a></li>
			<li onclick="getPlaylists(${profile.channelId},'createDate')"><a
				class="sub-icon"> <span
					class="glyphicon glyphicon-home glyphicon-hourglass"
					aria-hidden="true"> </span>PLAYLISTS
			</a></li>



			<li><a class="menu1"><span class="glyphicon glyphicon-film "
					aria-hidden="true"></span>SUBSCRIPTIONS<span
					class="glyphicon glyphicon-menu-down" aria-hidden="true"></span> </a></li>

			<ul class="cl-effect-2">
				<!--  add link here got to  user profile  -->
				<c:forEach items="${profile.subscriptions}" var="subscription">
					<li><a href="./profile?channelId=${subscription.id}">${subscription.name}</a></li>
				</c:forEach>
			</ul>
			<c:if test="${not empty sessionScope.channelId}">
			<c:if test="${sessionScope.channelId != profile.channelId}">
				<li><a href="./profile?channelId=${sessionScope.channelId}"
					class="user-icon"><span
						class="glyphicon glyphicon-home glyphicon-blackboard"
						aria-hidden="true"></span>GO TO MY PROFILE </a></li>
			</c:if>
			</c:if>

			<!-- script-for-menu -->
			<script>
				$("li a.menu1").click(function() {

					$("ul.cl-effect-2").slideToggle(300, function() {
						// Animation complete.
					});
				});
			</script>
		</ul>
		<!-- script-for-menu -->
		<script>
			$(".top-navigation").click(function() {
				$(".drop-navigation").slideToggle(300, function() {
					// Animation complete.
				});
			});
		</script>

	</div>
</div>
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<div id="middle" class="main-grids">
		<div class="top-grids">
			<div id=subscribeButton>
				<c:if test="${not empty sessionScope.channelId}">
					<c:if test="${ sessionScope.channelId !=profile.channelId}">
						<c:choose>
							<c:when test="${not empty subscribe}">
								<button onclick="unsubscribe(${profile.channelId})"
									class="button">SUBSCRIBED</button>
							</c:when>
							<c:otherwise>
								<button onclick="subscribe(${profile.channelId})"
									class="button ">SUBSCRIBE</button>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:if>
			</div>



			<div id="info" class="recommended-info"></div>
			<div id="content"></div>
			<div class="clearfix"></div>
		</div>
	</div>


	<jsp:include page="footer.jsp" />
</div>


<div class="clearfix"></div>
<!-- Bootstrap core JavaScript
    ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="FinalProject/js/bootstrap.min.js"></script>
<!-- Just to make our placeholder images work. Don't actually copy the next line! -->

<script>

$( document ).ready(function() {
	getVideos( ${ profile.channelId } ,"date");
});

  
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
		     		' <button onclick="unsubscribe('+channelId+')" class="button">SUBSCRIBED</button>');
			}
	  });
}   

function sortVideoByViews(channelId){
    getVideos(channelId,"views");	
}
function sortVideoByLikes(channelId){
    getVideos(channelId,"likes");	
}
function sortVideoByUploadDate(channelId){
    getVideos(channelId,"date");	
}
function sortVideoByTitle(channelId){
    getVideos(channelId,"title");	
}



function getVideos(channelId,sortBy) {
	$
			.ajax({
				url : 'channelVideos',
				type : 'get',
				data : {
					channelId : channelId,
					sortBy: sortBy
				},
				success : function(response) {
					console.log(response.videos.length);
					$('#info').empty();

					$('#info')
							.append(
									'<ul style="list-style-type: none" >'
									+ '<li>'
									+'    <p></p>'
									+'    <h3 >Videos</h3>'
									+' </li>'
									+ '  <li>'
									+ '	  <div class="dropdown">'
									+ '    <button style="color: aqua" class="btn btn-default dropdown-toggle button" type="button" id="menu1"  data-toggle="dropdown">SORT BY'
									+ '    <span class="caret"></span></button>'
									+ '    <ul class="dropdown-menu" role="menu" aria-labelledby="menu1">'
									+ '      <li onclick="sortVideoByViews(${profile.channelId})" role="presentation"><a role="menuitem" tabindex="-1">VIEWS</a></li>'
									+ '      <li onclick="sortVideoByTitle(${profile.channelId})" role="presentation"><a role="menuitem" tabindex="-1">TITLE</a></li>'
									+ '      <li onclick="sortVideoByLikes(${profile.channelId})" role="presentation"><a role="menuitem" tabindex="-1">LIKES</a></li>'
									+ '      <li onclick="sortVideoByUploadDate(${profile.channelId})" role="presentation"><a role="menuitem" tabindex="-1">UPLOAD DATE</a></li>'

									+ '   </ul>'
									+ '  </div>'
									+' </li>'
									+ '</ul>'
								
									);

					var i;
					$('#content').empty();
					for (i = 0; i < response.videos.length; i++) {
						$('#content').append(
										'<div class="col-md-4 resent-grid recommended-grid slider-top-grids">'
												+ '    <div class="resent-grid-img recommended-grid-img">'
												+ '		<a href="./video?videoId='
												+ response.videos[i].videoId
												+ '"><img src="'+response.videos[i].photoUrl+'" alt=""></a>'
												+ '		<div class="time">'
												+ '			<p></p>'
												+ '		</div>'
												+ '		<div class="clck">'
												+ '			<span class="glyphicon glyphicon-time" aria-hidden="true"></span>'
												+ '		</div>'
												+ '	</div>'
												+ '	<div class="resent-grid-info recommended-grid-info">'
												+ '		<h3>'
												+ '			<a href="./video?videoId='
												+ response.videos[i].videoId
												+ '" class="title title-info" >'
												+ response.videos[i].title
												+ '</a> </h3>'
												+ '	<ul>'
												+ '		<li class="right-list"><p class="views views-info"></p> VIEWS : '+response.videos[i].views+'</li>'
												+ '	</ul>'
												+ '	</div>'
												+ '	<p id="deleteButton">'
												+ '  <c:if test="${ sessionScope.channelId == profile.channelId  }">'
												+ '       <button onclick="deleteVideo('+response.videos[i].videoId+')" class="button">DELETE</button>'
												+ '	  </c:if>'
										
												+ '</p>'
												+ ' </div>'
												);
					}
				}
			});

}

function sortPlaylistsByName(channelId){
	getPlaylists(channelId,"name");	
}
function sortPlaylistsByCreateDate(channelId){
	getPlaylists(channelId,"date");	
}
function sortPlaylistsByLastAddedVideoDate(channelId){
	getPlaylists(channelId,"last");	
}


function getPlaylists(channelId,sortBy) {
	$.ajax({
				url : 'playlists',
				type : 'get',
				data : {
					channelId : channelId,
					sortBy: sortBy
				},
				success : function(response) {

					$('#info').empty();

					$('#info').append(
							          '<ul style="list-style-type: none" >'
									+ '<li>'
									+ '    <p></p>'
									+ '    <h3 >PLAYLISTS</h3>'
									+ ' </li>'
									+ '  <li>'
									+ '	  <div class="dropdown">'
									+ '    <button style="color: aqua" class="btn btn-default dropdown-toggle button" type="button" id="menu1"  data-toggle="dropdown">SORT BY'
									+ '    <span class="caret"></span></button>'
									+ '    <ul class="dropdown-menu" role="menu" aria-labelledby="menu1">'
									+ '      <li onclick="sortPlaylistsByName(${profile.channelId})" role="presentation"><a role="menuitem" tabindex="-1">NAME</a></li>'
									+ '      <li onclick="sortPlaylistsByCreateDate(${profile.channelId})" role="presentation"><a role="menuitem" tabindex="-1">CREATE DATE</a></li>'
									+ '      <li onclick="sortPlaylistsByLastAddedVideoDate(${profile.channelId})"role="presentation"><a role="menuitem" tabindex="-1">LAST VIDEO ADD DATE</a></li>'
									+ '   </ul>'
									+ '  </div>'
									+ ' </li>'
									+ '</ul>'
									);

					var i;
					$('#content').empty();
					for (i = 0; i < response.playlists.length; i++) {
						$('#content').append(
										'<div class="col-md-4 resent-grid recommended-grid slider-top-grids">'
												+ '    <div class="resent-grid-img recommended-grid-img">'
												+ '		<a href="./playlist?playlistId='
												+ response.playlists[i].playlistId
												+ '"><img src="'+response.playlists[i].photoUrl+'" alt=""></a>'
												+ '		<div class="time">'
												+ '			<p></p>'
												+ '		</div>'
												+ '		<div class="clck">'
												+ '			<span class="glyphicon glyphicon-time" aria-hidden="true"></span>'
												+ '		</div>'
												+ '	</div>'
												+ '	<div class="resent-grid-info recommended-grid-info">'
												+ '		<h3>'
												+ '			<a href="./playlist?playlistId='
												+ response.playlists[i].playlistId
												+ '" class="title title-info" >'
												+ response.playlists[i].playlistName
												+ '</a> </h3>'
												+ '	<ul>'
												+ '		<li class="right-list"><p class="views views-info"></p> create date : '+response.playlists[i].createDate+'</li>'
												+ '		<li class="right-list"><p class="views views-info"></p> last added  : '+response.playlists[i].lastVideoAddDate+'</li>'
												+ '	</ul>'
												+ '	</div>'
												+ '	<p id="deleteButton">'
												+ '  <c:if test="${ sessionScope.channelId == profile.channelId  }">'
												+ '       <button onclick="deletePlaylist('+response.playlists[i].playlistId+')" class="button">DELETE</button>'
												+ '	  </c:if>'
										
												+ '</p>'
												+ ' </div>'
												);
					}
				}
			});

}







function deleteVideo(videoId) {
	  $.ajax({
			url : 'deleteVideo'+ '?' + $.param({
				"videoId" : videoId
				}),
			type : 'delete', 
			success : function(response) {
				getVideos(${profile.channelId},'date');
			}
	  });
	
}
function deletePlaylist(playlistId) {
	  $.ajax({
			url : 'deletePlaylist'+ '?' + $.param({
				"playlistId" : playlistId
				}),
			type : 'delete', 
			success : function(response) {
				getPlaylists(${ profile.channelId },'date');
			}
	  });
	
}
</script>


</body>
</html>