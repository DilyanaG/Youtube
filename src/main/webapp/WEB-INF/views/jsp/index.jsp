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
						      <h3 id=actionTitle>Recent Videos</h3>
						</c:otherwise>
					</c:choose>
						
					</div>
					<div id=videos>
					
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
	$(document).ready(function() {
		$("#searchForm").submit(function() {
									
		   var searchWord = $('#searchForm').find('input[name="searchWord"]').val();
		   loadVideos('searchWord');
		   });
	});
	
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
				
			       $('#actionTitle').empty();
			       $('#actionTitle').append(action+' VIDEOS');
			       $('#videos').empty();
				  for (i = 0; i < response.videos.length; i++) {
				  $('#videos').append(
						 ' <div class="col-md-4 resent-grid recommended-grid slider-top-grids">'
						+'	<div class="resent-grid-img recommended-grid-img">'
						+'		<a href="./video?videoId='+response.videos[i].videoId+'">'
						+'			<img src= "'+response.videos[i].photoUrl+'">'
						+'		</a>'
						+'		<div class="time">'
						+'			<p></p>'
						+'		</div>'
						+'		<div class="clck">'
						+'			<span class="glyphicon glyphicon-time" aria-hidden="true"></span>'
						+'		</div>'
						+'	</div>'
						+'	<div class="resent-grid-info recommended-grid-info">'
						+'		<h3><a href="./video?videoId='+response.videos[i].videoId+'" class="title title-info">'+response.videos[i].title+'</a></h3>'
						+'		<ul>'
						+'			<li><p class="author author-info"><a href="./profile?channelId='+response.videos[i].channelId+'" class="author">AUTHOR</a></p></li>'
						+'			<li class="right-list"><p class="views views-info"></p>'+response.videos[i].views+'</li>'
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