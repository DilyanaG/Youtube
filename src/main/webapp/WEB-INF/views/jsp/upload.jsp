<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="./error"%>





<jsp:include page="header.jsp" />

<!-- upload -->
<div class="upload">
	<!-- container -->
	<div class="container">
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<c:if test="${not empty message}">
		<h1>Upload Status</h1>
		<h2>Message : ${message}</h2>
		</c:if>
		<form action="./upload" method="post" enctype="multipart/form-data">
			<div class="upload-grids">
				<div class="upload-right">
					<div class="upload-file">
						<div class="services-icon">
							<span class="glyphicon glyphicon-open" aria-hidden="true"></span>
						</div>

						<input type="file" value="Choose file" name="file"
							accept="video/*" />
					</div>
					<div class="upload-info">
						<h5>Select files to upload</h5>
						<span>or</span>
						<p>Drag and drop files</p>
					</div>
				</div>
				<div class="upload-right-bottom-grids">

					<div class="col-md-4 upload-right-bottom-left">
						<h4>TITLE</h4>
						<div class="upload-right-top-list">
							<ul>
								<li><input type="text" class="email" name="title"
									placeholder="Title" maxlength="50" title="Enter a valid title" />

								</li>
							</ul>
						</div>
					</div>
					<div class="col-md-4 upload-right-bottom-left">

						<h4>DESCRIPTION</h4>
						<div class="upload-right-top-list">
							<ul>
								<li><input type="text" class="email" name="description"
									placeholder="DESCRIPTION" /></li>

							</ul>
						</div>
					</div>
					<div class="col-md-4 upload-right-bottom-left">

						<input type="submit" value="UPLOAD VIDEO" />


					</div>

					<div class="clearfix"></div>
				</div>
			</div>
		</form>
	</div>
	<!-- //container -->
</div>
<!-- //upload -->
<!-- footer -->
<jsp:include page="footer.jsp" />
<!-- //footer -->
<div class="clearfix"></div>



<script src="js/bootstrap.min.js"></script>


</body>
</html>