<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="./error"%>

<jsp:include page="header.jsp" />
<jsp:include page="sideMenu.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<div class="show-top-grids">
		<div class="col-sm-8 single-left">
			<div class="song">
				<div class="video-grid">
				<br><br>
					<form>
						<h2>Add new playlist</h2>
						<br>
						<div class="input-container">
							<i class="fal fa-calendar-edit icon"></i> <input class="input-field"
								type="text" placeholder="Playlist Name" name="playlistName">
						</div>

						<button type="submit" class="btn">Create playlist</button>
					</form>
				</div>
			</div>
		</div>

		<div class="col-md-4 single-right">
			<link href="FinalProject/css/addToPlaylist.css" rel='stylesheet' type='text/css'
				media="all" />
			<h2>Add video to Playlist</h2>
			<div class="single-grid-right">
				<div class="single-right-grids">

					<label class="container"> Playlist0 <input type="checkbox">
						<span class="checkmark"></span>
					</label> <label class="container"> Playlist1 <input type="checkbox">
						<span class="checkmark"></span>
					</label> <label class="container"> Playlist2 <input type="checkbox">
						<span class="checkmark"></span>
					</label> <input type="submit"  class="btn" value="Add to playlist" />

				</div>
			</div>


		</div>
		<div class="clearfix"></div>
	</div>
</div>


<div class="clearfix"></div>

<script src="FinalProject/js/bootstrap.min.js"></script>

</body>
</html>