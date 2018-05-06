<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page errorPage="/error"%>

<!DOCTYPE html>
<html>
<head>
<title>YouTube</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords"
	content="My Play Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript">
	
	
	
	
	
	
	
 addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 
</script>
<!-- bootstrap -->
<link href="FinalProject/css/bootstrap.min.css" rel='stylesheet'
	type='text/css' media="all" />
<!-- //bootstrap -->
<link href="FinalProject/css/dashboard.css" rel="stylesheet">
<!-- Custom Theme files -->
<link href="FinalProject/css/style.css" rel='stylesheet'
	type='text/css' media="all" />
<script src="FinalProject/js/jquery-1.11.1.min.js"></script>
<!--start-smoth-scrolling-->
<!-- fonts -->
<link
	href='//fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
	rel='stylesheet' type='text/css'>
<link href='//fonts.googleapis.com/css?family=Poiret+One'
	rel='stylesheet' type='text/css'>
<!-- //fonts -->
</head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="./index">
					<h1>
						<img src="FinalProject/images/logo.png" alt="" />
					</h1>
				</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<div class="top-search">
					<form action="./index"  method="get" class="navbar-form navbar-right">
						<input id="searchForInput" type="text" class="form-control" name="search" placeholder="Search..."> 
							<input  type="submit" value="">
					</form>
				</div>
            
				<div class="header-top-right">
					<div class="file">
						<a href="./upload">Upload</a>
					</div>
                
					<c:choose>
						<c:when test="${not empty sessionScope.channelId}">

							<div class="signin">
								<a href="#small-dialog2" class="play-icon popup-with-zoom-anim">Settings</a>
								<!-- pop-up-box -->
								<script type="text/javascript"
									src="FinalProject/js/modernizr.custom.min.js"></script>
								<link href="FinalProject/css/popuo-box.css" rel="stylesheet"
									type="text/css" media="all">
								<script src="FinalProject/js/jquery.magnific-popup.js"
									type="text/javascript">
								</script>
								<!--//pop-up-box -->
								<div id="small-dialog2" class="mfp-hide">
									<h3>Settings</h3>
									<div class="social-sits">
										<h4>Change profile picture</h4>
										<br>

										<div class="upload-image">
											<img id="profileImage" src="http://lorempixel.com/100/100">
											<form>
												<input id="imageUpload" type="file" name="profile_photo"
													placeholder="Photo" required="" capture=""> <br>

												<input type="submit" value="Continue">
											</form>
										</div>

										<script>
											$("#profileImage").click(
													function(e) {
														$("#imageUpload")
																.click();
													});
										</script>
									</div>
									<div class="signup">
										<h4>Change password</h4>
										<form action="./changePassword" method="post">
											<input type="password" name="newPassword" placeholder="New Password"
												required="required" pattern=".{6,}"
												title="Minimum 6 characters required" autocomplete="off">
											<input type="password" name="oldPassword" placeholder="Old Password"
												required="required" pattern=".{6,}"
												title="Minimum 6 characters required" autocomplete="off">
											<input type="submit" value="Continue">
										</form>
									</div>
									<div class="clearfix"></div>
								</div>
						          
							</div>

							<div class="signin">
								<a href="#small-dialog" class="play-icon popup-with-zoom-anim">Sign
									Out</a>
								<div id="small-dialog" class="mfp-hide">
									<h3>Are you sure you want to log out?</h3>

									<div class="signup">
										<form action="./signout" method="get">
											<input type="submit" value="Continue">
										</form>
									</div>
									<div class="clearfix"></div>
								</div>

                               
							</div>
									<script>
											$(document).ready(function() {
											$('.popup-with-zoom-anim').magnificPopup({
												type: 'inline',
												fixedContentPos: false,
												fixedBgPos: true,
												overflowY: 'auto',
												closeBtnInside: true,
												preloader: false,
												midClick: true,
												removalDelay: 300,
												mainClass: 'my-mfp-zoom-in'
											});
																											
											});
									</script>
						</c:when>

						<c:otherwise>

							<div class="signin">
								<a href="#small-dialog2" class="play-icon popup-with-zoom-anim">Sign
									Up</a>
								<!-- pop-up-box -->
								<script type="text/javascript"
									src="FinalProject/js/modernizr.custom.min.js"></script>
								<link href="FinalProject/css/popuo-box.css" rel="stylesheet"
									type="text/css" media="all" />
								<script src="FinalProject/js/jquery.magnific-popup.js"
									type="text/javascript"></script>
								<!--//pop-up-box -->
								<div id="small-dialog2" class="mfp-hide">
									<h3>Create Account</h3>
									<div class="social-sits">
										<div class="facebook-button">
											<a href="#">Connect with Facebook</a>
										</div>
										<div class="chrome-button">
											<a href="#">Connect with Google</a>
										</div>
										<div class="button-bottom">
											<p>
												Already have an account? <a href="#small-dialog"
													class="play-icon popup-with-zoom-anim">Login</a>
											</p>
										</div>
									</div>
										<div class="signup">
										<form action="./signup" method="post">
											<input type="text" class="email" name="username" placeholder="Username"
												maxlength="50"
												pattern="^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$"
												title="Enter a valid username" /> 
											<input type="password" name="password"
												placeholder="Password" required="required" pattern=".{6,}"
												title="Minimum 6 characters required" autocomplete="off" />
											<input type="text" class="email" name="email" placeholder="Email"
												required="required"
												pattern="([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?"
												title="Enter a valid email" /> 
											<input type="submit" value="Sign Up" />
										</form>
									</div>
									<div class="clearfix"></div>
								</div>
								<div id="small-dialog3" class="mfp-hide">
									<div class="signup"></div>
									<div class="clearfix"></div>
								</div>
								<div id="small-dialog7" class="mfp-hide">
									<h3>Create Account</h3>
									<div class="social-sits">
										<div class="facebook-button">
											<a href="#">Connect with Facebook</a>
										</div>
										<div class="chrome-button">
											<a href="#">Connect with Google</a>
										</div>
										<div class="button-bottom">
											<p>
												Already have an account? <a href="#small-dialog"
													class="play-icon popup-with-zoom-anim">Login</a>
											</p>
										</div>
									</div>
									<div class="signup">
										<form action="./login" method="post">
											<input type="text" class="email" placeholder="Email"
												required="required"
												pattern="([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?"
												title="Enter a valid email" />
												 <input type="password"
												placeholder="Password" required="required" pattern=".{6,}"
												title="Minimum 6 characters required" autocomplete="off" />
											<input type="submit" value="Sign In" />
										</form>
									</div>
									<div class="clearfix"></div>
								</div>
							<script>
											$(document).ready(function() {
											$('.popup-with-zoom-anim').magnificPopup({
												type: 'inline',
												fixedContentPos: false,
												fixedBgPos: true,
												overflowY: 'auto',
												closeBtnInside: true,
												preloader: false,
												midClick: true,
												removalDelay: 300,
												mainClass: 'my-mfp-zoom-in'
											});
																											
											});
									</script>
							</div>
							<div class="signin">
								<a href="#small-dialog" class="play-icon popup-with-zoom-anim">Sign
									In</a>
								<div id="small-dialog" class="mfp-hide">
									<h3>Login</h3>
									<div class="social-sits">
										<div class="facebook-button">
											<a href="#">Connect with Facebook</a>
										</div>
										<div class="chrome-button">
											<a href="#">Connect with Google</a>
										</div>
										<div class="button-bottom">
											<p>
												New account? <a href="#small-dialog2"
													class="play-icon popup-with-zoom-anim">Signup</a>
											</p>
										</div>
									</div>
									<div class="signup">
										<form action="./login" method="post">
											<input type="text" class="email" name="username"
												placeholder="Enter username" required="required" /> <input
												type="password" placeholder="Password" name="password"
												required="required" pattern=".{6,}"
												title="Minimum 6 characters required" autocomplete="off" />
											<input type="submit" value="LOGIN" />
										</form>
										<div class="forgot">
											<!-- <a href="#">Forgot password ?</a>-->
										</div>
									</div>

									<div class="clearfix"></div>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					<div class="clearfix"></div>
				</div>

			</div>
			<div class="clearfix"></div>

		</div>


</nav>