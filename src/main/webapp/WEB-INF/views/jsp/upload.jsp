<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="./error"%>


<style>
#progress {
    width: 100%;
    background-color: grey;
}
#bar {
    width: 1%;
    height: 30px;
    background-color: green;
 
}
#percent {
    width: 100%;
    background-color: grey;
}
</style>




<jsp:include page="header.jsp" />

<!-- upload -->
<div class="upload">
	<!-- container -->
	<div class="container">
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<c:if test="${not empty errorMessage}">
			<h2 style="color: red">${errorMessage}</h2>
		</c:if>

		<form action="./upload" method="post" enctype="multipart/form-data">
			<div class="upload-grids">
				<div class="upload-right">
					<div class="upload-file">
						<div class="services-icon">
							<span class="glyphicon glyphicon-open" aria-hidden="true"></span>
						</div>

						<input type="file" value="Choose file" name="file"
							accept="video/*" required="" capture="" />

                <script>
                $(function() {

                    var bar = $('.bar');
                    var percent = $('.percent');
                    var status = $('#status');

                    $('form').ajaxForm({
                        beforeSend: function() {
                            status.empty();
                            var percentVal = '0%';
                            bar.width(percentVal);
                            percent.html(percentVal);
                        },
                        uploadProgress: function(event, position, total, percentComplete) {
                            var percentVal = percentComplete + '%';
                            bar.width(percentVal);
                            percent.html(percentVal);
                        },
                        complete: function(xhr) {
                            status.html(xhr.responseText);
                        }
                    });
                });
                </script>
					</div>
					<div class="upload-info">
						<h5>Select files to upload</h5>
						<span>or</span>
						<p>Drag and drop files</p>
					</div>
					<div class="progress">
						<div class="bar"></div>
						<div class="percent">0%</div>
					</div>

					<div id="status"></div>
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



<script src="FinalProject/js/bootstrap.min.js"></script>


</body>
</html>