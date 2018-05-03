<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>






<div class="col-sm-3 col-md-2 sidebar">>
	<div class="top-navigation">
		<div class="t-menu">MENU</div>
		<div class="t-img">
			<img src="FinalProject/images/lines.png" alt="" />
		</div>
		<div class="clearfix"></div>
	</div>
	<div id="side_menu" class="drop-navigation drop-navigation">

		<ul class="nav nav-sidebar">
			<!-- TODO CHANGE MENU LINKS -->
			<li class="active"><a href="./index" class="home-icon"> <span
					class="glyphicon glyphicon-home" aria-hidden="true"></span>Home
			</a></li>
			<li id=RecentVideos><a  class="user-icon"> <span
					class="glyphicon glyphicon-hourglass" aria-hidden="true"></span>Recent
			</a></li>
			<li id=mostPopularVideos><a  class="user-icon"> <span
					class="glyphicon glyphicon-star" aria-hidden="true"></span>Most
					popular
			</a></li>
		</ul>

<!-- TODO add links  and change if condition to  ${not empty sessionScope.user}  -->
		<c:if test="${not empty sessionScope.channelId}">
		
			<h4>More options</h4>
			<ul class="nav nav-sidebar">
                
                <li>
                    <a href="./profile?channelId=${sessionScope.channelId}" class="user-icon">
                        <span class="glyphicon glyphicon-globe" aria-hidden="true"></span>PROFILE</a>
                </li>
            </ul>
		</c:if>
		<!--
      </script>
      -->
		<!-- script-for-menu -->
		<script>
			$(".top-navigation").click(function() {
				$(".drop-navigation").slideToggle(300, function() {
					// Animation complete.
				});
			});
		</script>
		<!-- script-for-menu -->
		<script>
			$("li a.menu1").click(function() {
				$("ul.cl-effect-2").slideToggle(300, function() {
					// Animation complete.
				});
			});
		</script>
	</div>
</div>
<div class="clearfix"></div>
<div class="drop-menu">
	<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu4">
		<li role="presentation"><a role="menuitem" tabindex="-1" href="#">Regular
				link</a></li>
		<li role="presentation" class="disabled"><a role="menuitem"
			tabindex="-1" href="#">Disabled link</a></li>
		<li role="presentation"><a role="menuitem" tabindex="-1" href="#">Another
				link</a></li>
	</ul>
</div>

<script>

</script>