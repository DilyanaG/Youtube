<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page errorPage="./error"%>

<jsp:include page="header.jsp" />
<jsp:include page="sideMenu.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<div class="show-top-grids">
		<div class="col-sm-8 single-left">
			<div class="song">
				<div class="video-grid">
					<br> <br>
					<div id="createPlaylistId">
						<h2 style="font-family: Book Antiqua">Add new playlist</h2>
						<br>
						<div class="input-container">
							<i class="fal fa-calendar-edit icon"
								style="background-color: #21DEEF; color: white;"></i>
								<input id ="playlistName"
								class="input-field" type="text" placeholder="Playlist Name"
								name="playlistName" style="border: 2px solid #21DEEF;">
						</div>

						<button id="aa" class="btn"
							style="background-color: #21DEEF; color: white;">Create
							playlist</button>
					</div>
				</div>
			</div>
		</div>

		<div class="col-md-4 single-right">
			<link href="FinalProject/css/addToPlaylist.css" rel='stylesheet'
				type='text/css' media="all" />
			<h2 style="font-family: Book Antiqua">Add video to Playlist</h2>
			<hr>
			<div class="single-grid-right" id="playlists">
				
					<div  class="single-right-grids">
					<form action="./addVideoToPlaylist?videoId=${videoId}" method="post">
					<div id="allPlaylists">
					<c:forEach items="${playlists}" var="playlist">
                        <input type="radio" name="playlistId" value="${playlist.playlistId}" checked>${ playlist.playlistName} <br>
                     </c:forEach>
                     </div>
                         <input type="submit" class="btn" value="Add to playlist"
							style="background-color: #21DEEF; color: white;" />
                     </form>
						
					</div>
				
			</div>


		</div>
		<div class="clearfix"></div>
	</div>
</div>


<div class="clearfix"></div>

<script src="FinalProject/js/bootstrap.min.js"></script>
 
  <script>
  $( "#aa" ).click(function() {
	  var playlistName=$( "#playlistName" ).val();
	  $.ajax({
			url : 'addNewPlaylist',
			type : 'post',
			data : {
				playlistName : playlistName
			},
			success : function(response) {
				$('#allPlaylists').empty();
				 for (i = 0; i < response.playlists.length; i++) {
					 $('#allPlaylists').append(
							 '<input type="radio" name="playlistId" value="'+response.playlists[i].playlistId+'" checked>'+response.playlists[i].playlistName+' <br>'		 
					 );
				 }
			

			}
	  });
	});
  </script>

</body>
</html>