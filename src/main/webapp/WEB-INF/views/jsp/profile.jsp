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
			<img src="${sessionScope.photoUrl}" alt="" width="150" height="150" style="margin-left: 50px"/>
			<li class="active"><a href="" class="home-icon"> <span
					aria-hidden="true" style="margin-left: 50px"> ${profile.username}</span> 
			</a></li>
			<!-- TODO add links  and change if condition to  ${not empty sessionScope.user}  -->
		<c:if test="${not empty sessionScope.channelId}">
                <li>
                    <a href="./profile?channelId=${sessionScope.channelId}" class="user-icon">
                        <span class="glyphicon glyphicon-globe" aria-hidden="true"></span>MY PROFILE</a>
                </li>
		</c:if>
			<li><a href="" class="user-icon"><span
					class="glyphicon glyphicon-home glyphicon-blackboard"
					aria-hidden="true"></span>VIDEOS</a></li>
			<li onclick="getPlaylist(${profile.channelId})"><a class="sub-icon"> <span
					class="glyphicon glyphicon-home glyphicon-hourglass"
					aria-hidden="true"> </span>PLAYLISTS
			</a></li>



			<li><a class="menu1"><span class="glyphicon glyphicon-film "
					aria-hidden="true"></span>SUBSCRIPTIONS<span
					class="glyphicon glyphicon-menu-down" aria-hidden="true"></span></a></li>
			<ul class="cl-effect-2">
				<!--  add link here got to  user profile  -->
				<c:forEach items="${profile.subscriptions}" var="subscription">
					<li><a href="./profile?channelId=${subscription.id}">${subscription.name}</a></li>
				</c:forEach>
			</ul>
			
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
		     <div id= subscribeButton>
		     <script src="FinalProject/js/subscribe.js"></script>
				<c:if test = "${not empty sessionScope.channelId}">
				   <c:if test = "${ sessionScope.channelId !=profile.channelId}">
				     <c:choose>
				       <c:when test="${not empty subscribe}">
				             <button onclick="unsubscribe(${profile.channelId})" class="button">UNSUBSCRIBE</button>
				       </c:when>
				       <c:otherwise>
						      <button onclick="subscribe(${profile.channelId})"class="button ">SUBSCRIBE</button>
						</c:otherwise>
				     </c:choose>
				     </c:if>
				</c:if>
		     </div>
		
		

			<div class="recommended-info">
			<br><br><br>
				<h3>Videos</h3>

			</div>
			<c:forEach items="${profile.videos}" var="video">

				<div class="col-md-4 resent-grid recommended-grid slider-top-grids">
					<div class="resent-grid-img recommended-grid-img">
						<a href="./video?videoId=${video.videoId}"> <img
							src="${video.photoUrl}" alt=""></a>
						<div class="time">
							<p></p>
						</div>
						<div class="clck"></div>
					</div>
					<div class="resent-grid-info recommended-grid-info">
						<h3>
							<a href="./video?videoId=${video.videoId}"
								class="title title-info"> ${video.title} </a>
						</h3>
						<ul>
							<li><p class="author author-info">
									<a style="color: black" href="#" class="author">${video.username}
									</a>
								</p></li>
							<li class="right-list">
								<p class="views views-info">${video.views}</p>
							</li>
							<li class="right-list"></li>

						</ul>


					</div>

					<p>
						<c:if test="${video.channelId==channelId}">
							<button class="button ">DELETE</button>

						</c:if>
					</p>
				</div>
			</c:forEach>
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
	function getPlaylist(channelId) {
		$
				.ajax({
					url : 'playlists',
					type : 'get',
					data : {
						channelId : channelId
					},
					success : function(response) {
						console.log(response.comments[2].content);
						console.log(response.playlists.length);
						$('#middle').empty();

						$('#middle')
								.append(
										'<div class="top-grids">'
												+ ' <div class="recommended-info">'
												+ '      <h3 >PLAYLISTS</h3>'
												+ '		<c:if test="${not empty videos}">'
												+ '          <button class="button " onclick="createPlaylist()" >CREATE PLAYLIST </button>'
												+ '		</c:if>' + '	</div>');

						var i;
						for (i = 0; i < response.playlists.length; i++) {
							$('#middle')
									.append(
											'<div class="col-md-4 resent-grid recommended-grid slider-top-grids">'
													+ '    <div class="resent-grid-img recommended-grid-img">'
													+ '		<a href="./playlist?playlistId='
													+ response.playlists[i].playlistId
													+ '"><img src="FinalProject/images/t3.jpg" alt=""></a>'
													+ '		<div class="time">'
													+ '			<p>4:04</p>'
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
													+ '</a>'
													+ '		</h3>'
													+ '	</div>'
													+ '	<p>'
													+ ' <c:if test="${playlist.channelId==channelId}">'
													+ '   <button class="button ">DELETE</button>'
													+ '	 </c:if>'
													+ '</p>'
													+ ' </div>');
						}
						$('#middle').append(
								'<div class="clearfix"></div>' + ' </div>');
					}
				});

	}
</script>


</body>
</html>