<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
   
    <div class="col-sm-3 col-md-2 sidebar">
        <div class="top-navigation">
            <div class="t-menu">MENU</div>
            <div class="t-img">
                <img src="./images/lines.png" alt="" />
            </div>
            <div class="clearfix"> </div>
        </div>
        <div class="drop-navigation drop-navigation">
            <ul class="nav nav-sidebar">
              <!-- TODO CHANGE MENU LINKS -->
              <li class="active">
                    <a href="index.html" class="home-icon">
                        <span class="glyphicon glyphicon-home" aria-hidden="true"></span>Home</a>
                </li>
                <li>
                    <a href="#" class="user-icon">
                        <span class="glyphicon glyphicon-hourglass" aria-hidden="true"></span>Recent</a>
                </li>
                <li>
                    <a href="#" class="user-icon">
                        <span class="glyphicon glyphicon-star" aria-hidden="true"></span>Most popular</a>
                </li>
            </ul>
            <!-- script-for-menu -->
            <script>
                $(".top-navigation").click(function () {
                    $(".drop-navigation").slideToggle(300, function () {
                        // Animation complete.
                    });
                });
            </script>
            <!-- script-for-menu -->
            <script>
                $("li a.menu1").click(function () {
                    $("ul.cl-effect-2").slideToggle(300, function () {
                        // Animation complete.
                    });
                });
            </script>
        </div>
    </div>
    <div class="clearfix"> </div>
    <div class="drop-menu">
        <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu4">
            <li role="presentation">
                <a role="menuitem" tabindex="-1" href="#">Regular link</a>
            </li>
            <li role="presentation" class="disabled">
                <a role="menuitem" tabindex="-1" href="#">Disabled link</a>
            </li>
            <li role="presentation">
                <a role="menuitem" tabindex="-1" href="#">Another link</a>
            </li>
        </ul>
    </div>