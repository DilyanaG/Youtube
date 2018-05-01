<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="./error"%>



<jsp:include page="header.jsp" />
<div class="col-sm-3 col-md-2 sidebar"
	style="background-color: WhiteSmoke;">
	<div class="top-navigation">
		<div class="t-menu">USER NAME HERE</div>
		<div class="t-img">
			<img src="images/male.png" alt="">
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="drop-navigation drop-navigation">
		<ul class="nav nav-sidebar">
			<img src="images/male.png" alt="">
			<li class="active"><a href="" class="home-icon"> <span
					aria-hidden="true"> </span>USER NAME HERE
			</a></li>
			<li><a href="" class="user-icon"><span
					class="glyphicon glyphicon-home glyphicon-blackboard"
					aria-hidden="true"></span>VIDEOS</a></li>
			<li><a href="" class="sub-icon"> <span
					class="glyphicon glyphicon-home glyphicon-hourglass"
					aria-hidden="true"> </span>PLAYLISTS
			</a></li>
			<li id = "create_playlist"><a href"" class="sub-icon"> <span
					class="glyphicon glyphicon-home glyphicon-hourglass"
					aria-hidden="true"> </span>CREATE PLAYLIST
			</a></li>
                	<script>
				$("#create_playlist").click(function() {
					$("#middle").empty()
					});
			</script>

			<li><a class="menu1"><span class="glyphicon glyphicon-film "
					aria-hidden="true"></span>ABONOMENTI<span
					class="glyphicon glyphicon-menu-down" aria-hidden="true"></span></a></li>
			<ul class="cl-effect-2">
				<!--  add link here got to  user profile  -->
				<li><a href="#">abonat name</a></li>
				<li><a href="#">abonat name2</a></li>
				<li><a href="#">abonaet name3</a></li>
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
<div id = "middle" class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<div class="main-grids">
		<div class="top-grids">
			<div class="recommended-info">
				<h3>Videos</h3>
			</div>
			<c:forEach items="${videos}" var="patka" varStatus="loop">

				<div class="col-md-4 resent-grid recommended-grid slider-top-grids">
					<div class="resent-grid-img recommended-grid-img">
						<a href="./videos/${loop.index}"><img src="images/t3.jpg"
							alt=""></a>
						<div class="time">
							<p>4:04</p>
						</div>
						<div class="clck">
							<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
						</div>
					</div>
					<div class="resent-grid-info recommended-grid-info">
						<h3>
							<a href="./videos/${loop.index}" class="title title-info">Nullam
								interdum metus a imperdiet pellentesque vitae pulvinar tortor</a>
						</h3>
						<ul>
							<li><p class="author author-info">
									<a href="#" class="author">John Maniya</a>
								</p></li>
							<li class="right-list">
								<p class="views views-info">71,174 views</p>
							</li>

						</ul>


					</div>

					<p>
						<c:if test="${not empty videos}">
							<button style="background-color: LightBlue">
								<a href=""> DELETE </a>
							</button>
						</c:if>
					<p></p>
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
<script src="js/bootstrap.min.js"></script>
<!-- Just to make our placeholder images work. Don't actually copy the next line! -->



</body>
</html>