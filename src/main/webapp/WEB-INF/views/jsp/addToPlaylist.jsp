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
					<form action="./addToPlaylist" method="post">
						<h2 style="font-family: Book Antiqua">Add new playlist</h2>
						<br>
						<div class="input-container">
							<i class="fal fa-calendar-edit icon"
								style="background-color: #21DEEF; color: white;"></i> <input
								class="input-field" type="text" placeholder="Playlist Name"
								name="playlistName" style="border: 2px solid #21DEEF;">
						</div>

						<button type="submit" class="btn"
							style="background-color: #21DEEF; color: white;">Create
							playlist</button>
					</form>
				</div>
			</div>
		</div>

		<div class="col-md-4 single-right">
			<link href="FinalProject/css/addToPlaylist.css" rel='stylesheet'
				type='text/css' media="all" />
			<h2 style="font-family: Book Antiqua">Add video to Playlist</h2>
			<hr>
			<div class="single-grid-right" id="playlists">
				<c:forEach items="${playlists}" var="playlist">
					<div class="single-right-grids">
						<label class="container" style="font-family: Book Antiqua">
							${playlist.playlistName} <input type="checkbox"> <span
							class="checkmark"
							style="background-color: #21DEEF; color: white;"></span>
						</label> <input type="submit" class="btn" value="Add to playlist"
							style="background-color: #21DEEF; color: white;" />
					</div>
				</c:forEach>
			</div>


		</div>
		<div class="clearfix"></div>
	</div>
</div>


<div class="clearfix"></div>

<script src="FinalProject/js/bootstrap.min.js"></script>

</body>
</html>