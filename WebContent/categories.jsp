<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
<%@ page import="server.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Event categories</title>
<link href="static/nav.css" rel="stylesheet">
<link href="static/nav2.css" rel="stylesheet">
<link href="static/style.css" rel="stylesheet">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
  <style type="text/css">
  .btn.btn-outline-info.clicked {
  	background: #17a2b8;
  	color: white;
  }
  </style>
</head>

<body background="static/popup_linen_gray.jpg">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <a class="navbar-brand" href="#">EVENTO</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="#">Events <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Categories</a>
      </li>
       <li class="nav-item">
        <a class="nav-link" href="#">Log out</a>
      </li>
      <li class="nav-item">
        <a class="nav-link disabled" href="#">Choose category(register only)</a>
      </li>
    </ul>
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
  </div>
</nav>

<div class="row">
	<p></p>
</div>

<h2>Events</h2>
<br />
<h4 style = "margin-left: 50px;">Choose whatever you like to attend!</h4>
<br />
<div
	style=" height: 600px; padding: 20px;">
	<div class="bg-content">
		<div id="content" style="width:100%;">
			<div class="card-group">
			<% ArrayList<Event> events = (ArrayList)session.getAttribute("events");%>
			<% for(Event event : events) { %>
			  <div class="card bg-light text-dark" style="margin-right:20px;">
			    <img class="card-img-top" src="<%=event.getLinkEvent() %>" alt="Card image cap">
			    <div class="card-block" style="padding:12px;">
			      <h4 class="card-title"><%=event.getName() %></h4>
			      <p class="card-text">	<p class="text-muted" style="display:inline-block;">Venue : </p>
			      <%=event.getPlace() %>
			       <p class="text-muted" style="display:inline-block;"> on </p>
			       <%=event.getDate() %>
			      <p class="text-muted" style="display:inline-block;width:80%;;"> For more information : 			      
			      <a style="text-decoration: underline; cursor:pointer;color:#17a2b8;"><%=event.getLinkEvent() %></a>
			       </p>
			      </p>
			      <p class="card-text" style="bottom:0%;">
					<button type="button" id="going" target="<%=event.getId() %>" style="bottom:0%;" class="btn btn-outline-info" value="Going">Going</button>
					<button type="button" id="interested" target="<%=event.getId() %>" class="btn btn-outline-info">Interested</button>
			    </p>
			      <small class="text-muted" style="margin-bottom:2px;"><%=event.getNumGoing() %> going</small><br/>
			    </div>
			  </div>
			<% } %>
			</div>
		</div>
	</div>
</div>

<script src="js/bootstrap.js"></script>
<script src="prefixfree-1.0.7.js" type="text/javascript"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript">

$('#going').on('click', function(e){

$button = $(this);
var x = $(this).attr("target");

if($button.hasClass('clicked')){
	 $.post("removeGoing", 
				{ 
					eventId: x,
					
				});
    $button.removeClass('clicked');
    $button.text('Not Going');
} else {
	  $.post("going", 
				{ 
		  	eventId: x,
					
				});
    $button.addClass('clicked');          
    $button.text('Going');
}
});
</script>
</body>

</html>