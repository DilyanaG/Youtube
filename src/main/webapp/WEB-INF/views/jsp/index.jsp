<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage = "./error" %>

       
       
     <jsp:include page="header.jsp" />
     <jsp:include page="sideMenu.jsp" />
 
	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<div id="middle" class="main-grids">
				<div class="top-grids">
					<div class="recommended-info">
					   <c:choose>
						<c:when test="${not empty errorMessage}">
						       <h3 style="color:red">${errorMessage}</h3>
						</c:when>
                         <c:when test="${not empty successMessage}">
						       <h3 >${successMessage}</h3>
						       <h3>${sessionScope.channelId}</h3>
						       
						</c:when>
						<c:otherwise>
						      <h3> ${message} </h3>
						</c:otherwise>
					</c:choose>
						
					
				       <c:forEach items="${videos}" var="video">
					    <div class="col-md-4 resent-grid recommended-grid slider-top-grids">
						 <div class="resent-grid-img recommended-grid-img">
					    		<a href="./video?videoId=${video.videoId}">
									<img src= "${video.photoUrl} " width="222" height="222"></img>
				      		</a>

								<div class="time">
									<p></p>
								</div>
							<div class="clck"></div>
							</div>
							<div class="resent-grid-info recommended-grid-info">
								<h3><a href="./video?videoId=${video.videoId}" class="title title-info">${video.title}</a></h3>
								<ul>
									<li><p class="author author-info">
									<a href="./profile?channelId=${video.channelId}" class="author">
									</a>${video.username}
									</p></li>
									<li class="right-list"><p class="views views-info"></p>${video.views}</li>
								</ul>
							</div>
							</div>
						</c:forEach> 
				<c:if test="${not empty playlist}">
						   <c:forEach items="${playlist}" var="playlist">
					    <div class="col-md-4 resent-grid recommended-grid slider-top-grids">
						 <div class="resent-grid-img recommended-grid-img">
					    		<a href="./playlist?playlistId=${playlist.playlistId}">
									<img src= "${playlist.photoUrl}" width="240" height="240"></img>
				      		</a>

								<div class="time">
									<p></p>
								</div>
							<div class="clck"></div>
							</div>
							<div class="resent-grid-info recommended-grid-info">
								<h3><a href="./playlist?playlistId=${playlist.playlistId}" class="title title-info">${playlist.playlistName}</a></h3>
								<ul>
								     <li class="right-list"><p class="views views-info"></p>#PLAYLIST</li>
									
								</ul>
							</div>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${not empty channels}">
						   <c:forEach items="${channels}" var="channel">
					    <div class="col-md-4 resent-grid recommended-grid slider-top-grids">
						 <div class="resent-grid-img recommended-grid-img">
					    		<a href="./profile?channelId=${channel.channelId}">
									<img src= "${channel.pictureUrl}" width="240" height="240" ></img>
				      		</a>

								<div class="time">
									<p></p>
								</div>
							<div class="clck"></div>
							</div>
							<div class="resent-grid-info recommended-grid-info">
								<h3><a href="./profile?channelId=${channel.channelId}" class="title title-info">${channel.username}</a></h3>
								<ul>
									<li class="right-list"><p class="views views-info"></p>#CHANNEL</li>
								</ul>
							</div>
							</div>
						</c:forEach>
					</c:if> 
			     <div class="clearfix"> </div>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	<div class="clearfix"> </div>
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="FinalProject/js/bootstrap.min.js"></script>
	<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
  
</body>
</html>