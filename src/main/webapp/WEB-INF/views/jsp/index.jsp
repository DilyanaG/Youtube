<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>YouTube</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="My Play Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
    <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
    <!-- bootstrap -->
    <link href="css/bootstrap.min.css" rel='stylesheet' type='text/css' media="all" />
    <!-- //bootstrap -->
    <link href="css/dashboard.css" rel="stylesheet">
    <!-- Custom Theme files -->
    <link href="css/style.css" rel='stylesheet' type='text/css' media="all" />
    <script src="js/jquery-1.11.1.min.js"></script>
    <!--start-smoth-scrolling-->
    <!-- fonts -->
    <link href='//fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
        rel='stylesheet' type='text/css'>
    <link href='//fonts.googleapis.com/css?family=Poiret+One' rel='stylesheet' type='text/css'>
    <!-- //fonts -->
</head>

<body>
         <jsp:include page="header.jsp" />
        <jsp:include page="sideMenu.jsp" />
 
	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<div class="main-grids">
				<div class="top-grids">
					<div class="recommended-info">
						<h3>Recent Videos</h3>
					</div>
			<!-- TODO get colection of videos  -->
					<c:forEach items="${videos}" var="video" varStatus="loop">
					
					<c:if test="${loop.index ==3}">
					  	<div class="recommended-info">
						<h3>   </h3>
					</div>
					</c:if>
					<div class="col-md-4 resent-grid recommended-grid slider-top-grids">
						<div class="resent-grid-img recommended-grid-img">
							<a href="${video.id}"><img src="${video.photo}" alt=""></a>
							<div class="time">
								<p>${video.uploadDate}</p>
							</div>
							<div class="clck">
								<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
							</div>
						</div>
						<div class="resent-grid-info recommended-grid-info">
							<h3><a href="single.html" class="title title-info">${video.description}</a></h3>
							<ul>
								<li><p class="author author-info">
								<a href="#" class="author">${video.channel}</a>
								</p></li>
								<li class="right-list"><p class="views views-info">${video.views}</p></li>
							</ul>
						</div>
					</div>
					</c:forEach>
					<div class="clearfix"> </div>
				</div>
			</div>
		<jsp:include page="footer.jsp" />
	</div>
	
	
	<div class="clearfix"> </div>
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="js/bootstrap.min.js"></script>
	<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
</body>
			

</body>
</html>