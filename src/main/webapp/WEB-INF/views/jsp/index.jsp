<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
						      <h3 id=actionTitle>Recent Videos</h3>
						</c:otherwise>
					</c:choose>
						
					</div>
					<div id=videos>
					<c:forEach items="${videos}" var="video" varStatus="loop">
					
					<div class="col-md-4 resent-grid recommended-grid slider-top-grids">
						<div class="resent-grid-img recommended-grid-img">
						<!-- href put videoid and src images/${video.photoUrl}-->
							<a href="./video?videoId=${video}"><img src="FinalProject/images/t3.jpg" alt=""></a>
							<div class="time">
								<p></p>
							</div>
							<div class="clck">
								<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
							</div>
						</div>
						<div class="resent-grid-info recommended-grid-info">
						<!-- href put videoid and src images/${video.photoUrl} and ${video.title}-->
							<h3><a href="./video?videoId=${video}" class="title title-info">TITLE</a></h3>
							<ul>
							<!-- href add ${video.channelId}  and  ${video.channelName}/username-->
								<li><p class="author author-info"><a href="./profile?channelId=${video}" class="author">AUTHOR</a></p></li>
								<!-- href add ${video.views} -->
								<li class="right-list"><p class="views views-info"></p>69</li>
							</ul>
						</div>
					</div>
					</c:forEach>
					</div>
					<div class="clearfix"> </div>
				</div>
			</div>
		<jsp:include page="footer.jsp" />
	</div>
	
	
	<div class="clearfix"> </div>
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="FinalProject/js/bootstrap.min.js"></script>
	<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
	<script>
	$( document ).ready(function() {
		 loadVideos('RECENT');
        });
	
	$(document).ready(function() 
			{
			   $('#mostPopularVideos').click(function(e) 
			   { 
			     loadVideos('MOST');
			   });
			});
	$(document).ready(function() 
			{
			   $('#RecentVideos').click(function(e) 
			   { 
			     loadVideos('RECENT');
			   });
			});
	
	function loadVideos(action) {
		
		$.ajax({
			url : 'videoLoader',
			type : 'get',
			data: {parametyr: action },
			success: function(response){
				
			       console.log(response.comments[0].commentId)
			       console.log(response.videos[0].videoId)
			       $('#actionTitle').empty();
			       $('#actionTitle').append(action+'  VIDEOS');
			       $('#videos').empty();
				  for (i = 0; i < response.videos.length; i++) {
				  $('#videos').append(
						 ' <div class="col-md-4 resent-grid recommended-grid slider-top-grids">'
						+'	<div class="resent-grid-img recommended-grid-img">'
						+'		<a href="./video?videoId=1"><img src="FinalProject/images/t3.jpg" alt=""></a>'
						+'		<div class="time">'
						+'			<p></p>'
						+'		</div>'
						+'		<div class="clck">'
						+'			<span class="glyphicon glyphicon-time" aria-hidden="true"></span>'
						+'		</div>'
						+'	</div>'
						+'	<div class="resent-grid-info recommended-grid-info">'
						+'		<h3><a href="./video?videoId=1" class="title title-info">TITLE</a></h3>'
						+'		<ul>'
						+'			<li><p class="author author-info"><a href="./profile?channelId=1" class="author">AUTHOR</a></p></li>'
						+'			<li class="right-list"><p class="views views-info"></p>69</li>'
						+'		</ul>'
						+'	</div>'
						+' </div>'
				  
				  
				  );
				 }  
			}
		});
	}
	</script>
  
</body>
</html>