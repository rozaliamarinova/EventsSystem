<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Event categories</title>
<link href="static/nav.css" rel="stylesheet">
<link href="static/nav2.css" rel="stylesheet">
<link href="static/style.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>

<body background="static/popup_linen_gray.jpg" />
<div id="slide-menu">
	<ul class="navigation">
		<li><a href="user.jsp">Profile</a></li>
		<li><a href="genres.jsp">Browse</a></li>
		<li><a href="#">My Playlists</a></li>
		<li><a href="#">Users</a></li>
	</ul>
</div>

<ul class="nav">
	<li id="settings"><a href="#"><img class="logo"
			src="static/logogo.gif" /></a></li>
	<li id="search">
		<form action="search.jsp" method="get">
			<input type="text" name="search_text" id="search_text"
				placeholder="Search" /> <input type="button" name="search_button"
				id="search_button"></a>
		</form>
	</li>
	<li id="options"><a href="#">Hello, Tom</a>
	<li id="options"><a href="#">Log out</a>
</ul>

<div class="row">
	<p></p>
</div>

<h2>Categories</h2>
<br />
<h4 style = "margin-left: 50px;">What are your interests?</h4>

<ul class="nav2">
	<li></li>
</ul>

<div class="spinner"></div>
<div
	style="background: rgba(0, 0, 0, 0.4); height: 600px; padding: 20px;">
	<div class="bg-content">
		<div id="content">
			<div class="row">
				<div class="clear"></div>
				<ul class="portfolio clearfix">
					<li class="box"><h5></h5><img src="static/add.png"
						class="plus" /> <img
						style="box-shadow: 10px 10px 5px rgba(0, 0, 0, 0.3);" alt=""
						width=22%; height=28%; src="static/Event.jpg"><a></a></li>
				</ul>
			</div>
		</div>
	</div>
</div>

<script src="js/bootstrap.js"></script>
<script src="prefixfree-1.0.7.js" type="text/javascript"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
	$(document).ready(
			function() {
				var $navigacia = $('body, #slide-menu'), val = $navigacia
						.css('left') === '250px' ? '0px' : '250px';
				$navigacia.animate({
					left : val
				}, 300)

			});
</script>
</body>

</html>