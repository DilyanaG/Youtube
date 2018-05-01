<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
         <%@ page errorPage = "./error" %>
       
       
       
         <jsp:include page="header.jsp" />
        <jsp:include page="sideMenu.jsp" />
 
	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<div class="main-grids">
				<div class="top-grids">
					<div class="recommended-info">
						<h3>Recent Videos</h3>
					</div>
					<c:forEach items="${videos}" var="patka" varStatus="loop">
					
					<div class="col-md-4 resent-grid recommended-grid slider-top-grids">
						<div class="resent-grid-img recommended-grid-img">
							<a href="./videos/${loop.index}"><img src="images/t3.jpg" alt=""></a>
							<div class="time">
								<p>4:04</p>
							</div>
							<div class="clck">
								<span class="glyphicon glyphicon-time" aria-hidden="true"></span>
							</div>
						</div>
						<div class="resent-grid-info recommended-grid-info">
							<h3><a href="./videos/${loop.index}" class="title title-info">Nullam interdum metus a imperdiet pellentesque vitae pulvinar tortor</a></h3>
							<ul>
								<li><p class="author author-info"><a href="#" class="author">John Maniya</a></p></li>
								<li class="right-list"><p class="views views-info">71,174 views</p></li>
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
</html>