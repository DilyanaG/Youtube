<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ page errorPage = "/error" %>


   <jsp:include page="header.jsp" />
    <jsp:include page="sideMenu.jsp" />
    
	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<div class="show-top-grids">
			<div class="col-sm-8 single-left">
				<div class="song">
					<div class="song-info">
						<h3>Etiam molestie nisl eget consequat pharetra</h3>
					</div>
					<div class="video-grid">
						<iframe src="https://www.youtube.com/embed/oYiT-vLjhC4" allowfullscreen=""></iframe>
					</div>
				</div>
				<div class="song-grid-right">
					<div class="share">
						<h5>Options</h5>
						<ul>
							<li>
								<a href="#" class="icon fb-icon">Share</a>
							</li>
							<li>
								<a href="#" class="icon pinterest-icon">To Playlist</a>
							</li>
							<li class="view">200 Views</li>
							<li>
								<a href="#" class="icon like">26 Likes</a>
							</li>
							<li>
								<a href="#" class="icon dribbble-icon">9 Dislikes</a>
							</li>
						</ul>
					</div>
				</div>
				<div class="clearfix"> </div>
				<div class="published">
					<script src="js/jquery.min.js"></script>
					<script>
						$(document).ready(function () {
							size_li = $("#myList li").size();
							x = 1;
							$('#myList li:lt(' + x + ')').show();
							$('#loadMore').click(function () {
								x = (x + 1 <= size_li) ? x + 1 : size_li;
								$('#myList li:lt(' + x + ')').show();
							});
							$('#showLess').click(function () {
								x = (x - 1 < 0) ? 1 : x - 1;
								$('#myList li').not(':lt(' + x + ')').hide();
							});
						});
					</script>
					<div class="load_more">
						<div class="main_video_info">
							<img src="images/male.png" alt="user_profile_picture" id="profile-pic">
							<p class="author">By
								<a href="#" class="author">John Maniya</a>
								<br>
								<span>Published on: 5 June 2015</span>
							</p>

							<div class="heading-right">
								<a href="#small-dialog8" class="play-icon popup-with-zoom-anim">Subscribe</a>
							</div>

						</div>

						<br>
						<ul id="myList">
							<br><br><br><br><br>
							<li style="display: list-item;">
								<p>Nullam fringilla sagittis tortor ut rhoncus. Nam vel ultricies erat, vel sodales leo. Maecenas pellentesque, est
									suscipit laoreet tincidunt, ipsum tortor vestibulum leo, ac dignissim diam velit id tellus. Morbi luctus velit quis
									semper egestas. Nam condimentum sem eget ex iaculis bibendum. Nam tortor felis, commodo faucibus sollicitudin ac,
									luctus a turpis. Donec congue pretium nisl, sed fringilla tellus tempus in.</p>
							</li>
							<li>
								<div class="load-grids">
									<div class="load-grid">
										<p>Category</p>
									</div>
									<div class="load-grid">
										<a href="movies.html">Entertainment</a>
									</div>
									<div class="clearfix"> </div>
								</div>
							</li>
						</ul>
					</div>
				</div>
				<div class="all-comments">
					<div class="all-comments-info">
						<a href="#">All Comments (8,657)</a>
						<div class="box">
							<form>
								<textarea placeholder="Message" required=" "></textarea>
								<input type="submit" value="SEND">
								<div class="clearfix"> </div>
							</form>
						</div>
						<div class="all-comments-buttons">
						</div>
					</div>
					<div class="media-grids">
						<div class="media">
							<h5>Tom Brown</h5>
							<div class="media-left">
								<a href="#">

								</a>
							</div>
							<div class="media-body">
								<p>Maecenas ultricies rhoncus tincidunt maecenas imperdiet ipsum id ex pretium hendrerit maecenas imperdiet ipsum id
									ex pretium hendrerit</p>
								<span>Posted on : 12 June 2015 </span>
							</div>
						</div>
						<div class="media">
							<h5>Mark Johnson</h5>
							<div class="media-left">
								<a href="#">

								</a>
							</div>
							<div class="media-body">
								<p>Maecenas ultricies rhoncus tincidunt maecenas imperdiet ipsum id ex pretium hendrerit maecenas imperdiet ipsum id
									ex pretium hendrerit</p>
								<span>Posted on : 12 June 2015 </span>
							</div>
						</div>
						<div class="media">
							<h5>Steven Smith</h5>
							<div class="media-left">
								<a href="#">

								</a>
							</div>
							<div class="media-body">
								<p>Maecenas ultricies rhoncus tincidunt maecenas imperdiet ipsum id ex pretium hendrerit maecenas imperdiet ipsum id
									ex pretium hendrerit</p>
								<span>Posted on : 12 June 2015 </span>
							</div>
						</div>
						<div class="media">
							<h5>Marry Johne</h5>
							<div class="media-left">
								<a href="#">

								</a>
							</div>
							<div class="media-body">
								<p>Maecenas ultricies rhoncus tincidunt maecenas imperdiet ipsum id ex pretium hendrerit maecenas imperdiet ipsum id
									ex pretium hendrerit</p>
								<span>Posted on : 12 June 2015 </span>
							</div>
						</div>
						<div class="media">
							<h5>Mark Johnson</h5>
							<div class="media-left">
								<a href="#">

								</a>
							</div>
							<div class="media-body">
								<p>Maecenas ultricies rhoncus tincidunt maecenas imperdiet ipsum id ex pretium hendrerit maecenas imperdiet ipsum id
									ex pretium hendrerit</p>
								<span>Posted on : 12 June 2015 </span>
							</div>
						</div>
						<div class="media">
							<h5>Mark Johnson</h5>
							<div class="media-left">
								<a href="#">

								</a>
							</div>
							<div class="media-body">
								<p>Maecenas ultricies rhoncus tincidunt maecenas imperdiet ipsum id ex pretium hendrerit maecenas imperdiet ipsum id
									ex pretium hendrerit</p>
								<span>Posted on : 12 June 2015 </span>
							</div>
						</div>
						<div class="media">
							<h5>Peter Johnson</h5>
							<div class="media-left">
								<a href="#">

								</a>
							</div>
							<div class="media-body">
								<p>Maecenas ultricies rhoncus tincidunt maecenas imperdiet ipsum id ex pretium hendrerit maecenas imperdiet ipsum id
									ex pretium hendrerit</p>
								<span>Posted on : 12 June 2015 </span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4 single-right">
				<h3>More from this user</h3>
				<div class="single-grid-right">
					<div class="single-right-grids">
						<div class="col-md-4 single-right-grid-left">
							<a href="single.html">
								<img src="images/r1.jpg" alt="">
							</a>
						</div>
						<div class="col-md-8 single-right-grid-right">
							<a href="single.html" class="title"> Nullam interdum metus</a>
							<p class="author">By
								<a href="#" class="author">John Maniya</a>
							</p>
							<p class="views">2,114,200 views</p>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="single-right-grids">
						<div class="col-md-4 single-right-grid-left">
							<a href="single.html">
								<img src="images/r2.jpg" alt="">
							</a>
						</div>
						<div class="col-md-8 single-right-grid-right">
							<a href="single.html" class="title"> Nullam interdum metus</a>
							<p class="author">By
								<a href="#" class="author">John Maniya</a>
							</p>
							<p class="views">2,114,200 views </p>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="single-right-grids">
						<div class="col-md-4 single-right-grid-left">
							<a href="single.html">
								<img src="images/r3.jpg" alt="">
							</a>
						</div>
						<div class="col-md-8 single-right-grid-right">
							<a href="single.html" class="title"> Nullam interdum metus</a>
							<p class="author">By
								<a href="#" class="author">John Maniya</a>
							</p>
							<p class="views">2,114,200 views</p>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="single-right-grids">
						<div class="col-md-4 single-right-grid-left">
							<a href="single.html">
								<img src="images/r4.jpg" alt="">
							</a>
						</div>
						<div class="col-md-8 single-right-grid-right">
							<a href="single.html" class="title"> Nullam interdum metus</a>
							<p class="author">By
								<a href="#" class="author">John Maniya</a>
							</p>
							<p class="views">2,114,200 views</p>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="single-right-grids">
						<div class="col-md-4 single-right-grid-left">
							<a href="single.html">
								<img src="images/r5.jpg" alt="">
							</a>
						</div>
						<div class="col-md-8 single-right-grid-right">
							<a href="single.html" class="title"> Nullam interdum metus</a>
							<p class="author">By
								<a href="#" class="author">John Maniya</a>
							</p>
							<p class="views">2,114,200 views</p>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="single-right-grids">
						<div class="col-md-4 single-right-grid-left">
							<a href="single.html">
								<img src="images/r6.jpg" alt="">
							</a>
						</div>
						<div class="col-md-8 single-right-grid-right">
							<a href="single.html" class="title"> Nullam interdum metus</a>
							<p class="author">By
								<a href="#" class="author">John Maniya</a>
							</p>
							<p class="views">2,114,200 views</p>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="single-right-grids">
						<div class="col-md-4 single-right-grid-left">
							<a href="single.html">
								<img src="images/r1.jpg" alt="">
							</a>
						</div>
						<div class="col-md-8 single-right-grid-right">
							<a href="single.html" class="title"> Nullam interdum metus</a>
							<p class="author">By
								<a href="#" class="author">John Maniya</a>
							</p>
							<p class="views">2,114,200 views</p>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="single-right-grids">
						<div class="col-md-4 single-right-grid-left">
							<a href="single.html">
								<img src="images/r2.jpg" alt="">
							</a>
						</div>
						<div class="col-md-8 single-right-grid-right">
							<a href="single.html" class="title"> Nullam interdum metus</a>
							<p class="author">By
								<a href="#" class="author">John Maniya</a>
							</p>
							<p class="views">2,114,200 views</p>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="single-right-grids">
						<div class="col-md-4 single-right-grid-left">
							<a href="single.html">
								<img src="images/r3.jpg" alt="">
							</a>
						</div>
						<div class="col-md-8 single-right-grid-right">
							<a href="single.html" class="title"> Nullam interdum metus</a>
							<p class="author">By
								<a href="#" class="author">John Maniya</a>
							</p>
							<p class="views">2,114,200 views</p>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="single-right-grids">
						<div class="col-md-4 single-right-grid-left">
							<a href="single.html">
								<img src="images/r4.jpg" alt="">
							</a>
						</div>
						<div class="col-md-8 single-right-grid-right">
							<a href="single.html" class="title"> Nullam interdum metus</a>
							<p class="author">By
								<a href="#" class="author">John Maniya</a>
							</p>
							<p class="views">2,114,200 views</p>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="single-right-grids">
						<div class="col-md-4 single-right-grid-left">
							<a href="single.html">
								<img src="images/r5.jpg" alt="">
							</a>
						</div>
						<div class="col-md-8 single-right-grid-right">
							<a href="single.html" class="title"> Nullam interdum metus</a>
							<p class="author">By
								<a href="#" class="author">John Maniya</a>
							</p>
							<p class="views">2,114,200 views</p>
						</div>
						<div class="clearfix"> </div>
					</div>
					<div class="single-right-grids">
						<div class="col-md-4 single-right-grid-left">
							<a href="single.html">
								<img src="images/r6.jpg" alt="">
							</a>
						</div>
						<div class="col-md-8 single-right-grid-right">
							<a href="single.html" class="title"> Nullam interdum metus</a>
							<p class="author">By
								<a href="#" class="author">John Maniya</a>
							</p>
							<p class="views">2,114,200 views</p>
						</div>
						<div class="clearfix"> </div>
					</div>
				</div>
			</div>
			<div class="clearfix"> </div>
		</div>
		<!-- footer -->
  <jsp:include page="footer.jsp" />
		<!-- //footer -->
	</div>
	<div class="clearfix"> </div>
	
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="js/bootstrap.min.js"></script>
	<!-- Just to make our placeholder images work. Don't actually copy the next line! -->


</body>
</html>