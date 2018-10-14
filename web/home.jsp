<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<title>Trip Plan</title>

		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<!-- Font style -->
		<link href="https://fonts.googleapis.com/css?family=Lato:100,300,400" rel="stylesheet">
		
		 <!--- Semantics UI Component CSS -->
		<link rel="stylesheet" type="text/css" href="css/dist/components/icon.css">
		<link rel="stylesheet" type="text/css" href="css/dist/components/button.css">
		<link rel="stylesheet" type="text/css" href="css/dist/components/card.css">
		<link rel="stylesheet" type="text/css" href="css/dist/components/label.css">
		<link rel="stylesheet" type="text/css" href="css/dist/components/image.css">
		<link rel="stylesheet" type="text/css" href="css/dist/components/reveal.css">
		<link rel="stylesheet" type="text/css" href="css/dist/components/dimmer.css">
		<link rel="stylesheet" type="text/css" href="css/dist/components/rating.css">
		<link rel="stylesheet" type="text/css" href="css/dist/components/transition.css">
		<link rel="stylesheet" type="text/css" href="css/dist/components/popup.css">

		<link rel="stylesheet" href="css/home.css">
		<!-- Cross file header style -->
		<link rel="stylesheet" href="css/header.css"> 

		<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>


	
	    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script>
		<script src="http://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.2/modernizr.js"></script>

		<!--- Semantic UI Component JS -->
		<script src="css/assets/library/jquery.min.js"></script>
		<script src="css/assets/library/iframe-content.js"></script>
		<script type="text/javascript" src="css/dist/components/popup.js"></script>
		<script type="text/javascript" src="css/dist/components/dimmer.js"></script>
		<script type="text/javascript" src="css/dist/components/rating.js"></script>
		<script type="text/javascript" src="css/dist/components/transition.js"></script>

	</head> 
	
	<body>

		<!-- Nav Bar -->
		<nav id="navigation" class="navbar navbar-expand-lg navbar-light bg-light">
		  <div class="container">
	        <!-- <a class="navbar-brand" href="./home.jsp">Tripi</a> -->
	        <a href = "home.jsp"><img class="nav-img" src="./img/logo-nav.png" alt="" width="auto" height="300"></a>

			  <ul class="navbar-nav ml-auto">
				  <%
					  if (request.getSession().getAttribute("user_id") == null) {
				  %>
				  <li class="nav-item">
					  <a class="nav-link" href="./login.jsp">Log In</a>
				  </li>
				  <li class="nav-item">
					  <a class="nav-link" href="./signup.jsp">Sign Up</a>
				  </li>
				  <li class="nav-item">
					  <a class="nav-link" href="./login.jsp">Create Trip</a>
				  </li>
				  <% } else {  %>
				  <li class="nav-item">
					  <a class="nav-link" href="./tripRegister.jsp">Hello <%= request.getSession().getAttribute("displayname") %></a>
				  </li>

				  <li class="nav-item">
					  <a class="nav-link" href="./logout.jsp">Logout</a>
				  </li>


				  <% } %>

			  </ul>
		  </div>
    	</nav>

	    <!-- Carousel  -->
	    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
	        <ol class="carousel-indicators">
	          <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
	          <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
	          <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
	        </ol>
	        <div class="carousel-inner" role="listbox">
	          <!-- Slide One - Set the background image for this slide in the line below -->
	          <div class="carousel-item active" style="background-image: url('img/home-bg.jpg')">
				<!--  <div class="carousel-caption d-none d-md-block">
	              <h3>First Slide</h3>
	              <p>This is a description for the first slide.</p>
	            </div> -->
	          </div>
	          <!-- Slide Two - Set the background image for this slide in the line below -->
	          <div class="carousel-item" style="background-image: url('img/home-bg2.jpg')">
	          </div>
	          <!-- Slide Three - Set the background image for this slide in the line below -->
	          <div class="carousel-item" style="background-image: url('img/home-bg3.jpg')">
	          </div>
	        </div>
	        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
	          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
	          <span class="sr-only">Previous</span>
	        </a>
	        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
	          <span class="carousel-control-next-icon" aria-hidden="true"></span>
	          <span class="sr-only">Next</span>
	        </a>
	    </div>


	     <!-- Search Box -->
		<nav id="search-nav" class="navbar navbar-light bg-light">
		  	<h2>Plan Your Trip with your Friends! </h2> <br/>
		  	<div class="search-field">
			    <input class="form-control mr-sm-2 search-input" type="search" 
			    	placeholder="Enter a trip start location..." aria-label="Search" id="search-term">
			    <button class="btn btn-outline-success my-2 my-sm-0" onclick="create();">Create Now</button>
			</div>
		</nav>
			
		<br style="clear: both;">
		<div class="search-place">
			<h2><span style="color: black;"> Choose Your Continent Destination </span></h2>
		</div>

		<div id="semantic-results">
			<!-- Semantic Cards Results -->
			<div class="ui card">
			    <div class="image">
			      	<img class="result-img" src="img/paris.jpg">
			        <div class="content">
			          <div class="center"  onclick="clickContent('Europe');">
			          	<a href="#">
				            <div class="overlay">Europe</div>
			        	</a>
			          </div>
			        </div>
			    </div>

			    <div class="extra content">
			      <a class="friends">
			      	<i class="fa fa-map-marker"></i>
			        Paris</a>
			    </div>
			</div><!--End Semantic Card-->

			<div class="ui card">
			    <div class="image">
			      	<img class="result-img" src="img/peru.jpg">
			        <div class="content">
			          <div class="center" onclick="clickContent('South America');">
			          	<a href="#">
				            <div class="overlay">South America</div>
			        	</a>
			          </div>
			        </div>
			    </div>

			    <div class="extra content">
			      <a class="friends">
			      	<i class="fa fa-map-marker"></i>
			        Machu Picchu</a>
			    </div>
			</div><!--End Semantic Card-->

			<div class="ui card">
			    <div class="image">
			      	<img class="result-img" src="img/tokyo.jpg">
			        <div class="content">
			          <div class="center" onclick="clickContent('Asia');">
			          	<a href="#">
				            <div class="overlay">Asia</div>
			        	</a>
			          </div>
			        </div>
			    </div>

			    <div class="extra content">
			      <a class="friends">
			      	<i class="fa fa-map-marker"></i>
			        Tokyo</a>
			    </div>
			</div><!--End Semantic Card-->

			<div class="ui card">
			    <div class="image">
			      	<img class="result-img" src="img/newyork.jpg">
			        <div class="content">
			          <div class="center" onclick="clickContent('North America');">
			          	<a href="#">
				            <div class="overlay">North America</div>
			        	</a>
			          </div>
			        </div>
			    </div>

			    <div class="extra content">
			      <a class="friends">
			      	<i class="fa fa-map-marker"></i>
			        New York</a>
			    </div>
			</div><!--End Semantic Card-->

			<div class="ui card">
			    <div class="image">
			      	<img class="result-img" src="img/cape.jpg">
			        <div class="content">
			          <div class="center" onclick="clickContent('Africa');">
			          	<a href="#">
				            <div class="overlay">Africa</div>
			        	</a>
			          </div>
			        </div>
			    </div>

			    <div class="extra content">
			      <a class="friends">
			      	<i class="fa fa-map-marker"></i>
			        Cape Town</a>
			    </div>
			</div><!--End Semantic Card-->

			<div class="ui card">
			    <div class="image">
			      	<img class="result-img" src="img/sydney.jpg">
			        <div class="content">
			          <div class="center" onclick="clickContent('Australia');">
			          	<a href="#">
				            <div class="overlay">
				            	Australia
				            </div>
			        	</a>
			          </div>
			        </div>
			    </div>

			    <div class="extra content">
			      <a class="friends">
			      	<i class="fa fa-map-marker"></i>
			        Sydney</a>
			    </div>
			</div><!--End Semantic Card-->

			
			
		</div> <!--End Semantic-Result-->
		<br style="clear: both;">
		<br><br>
		<!-- <footer>
			@2018 SD Hacks
		</footer> -->
		<script>

			// Make the search field auto-complete place names for user
			var autocomplete;

			var tripName = ${param.tripName}

			function initAutocomplete() {
		        // Create the autocomplete object, restricting the search to geographical
		        // location types.
		        autocomplete = new google.maps.places.Autocomplete(
		            /** @type {!HTMLInputElement} */(document.getElementById('search-term')),
		            {types: ['geocode']});
		    }

		    function create(){
		    	 // Search Function
				var searchTerm = document.querySelector('#search-term').value;
				console.log(searchTerm);
				searchTerm = searchTerm.trim();
				// searchTerm = encodeURI(searchTerm);
				console.log(searchTerm);
				if(searchTerm == ""){
					// If user not search a place - display all places
						console.log("No places");
						window.location.href = "home.jsp";
					
				}
				var place = autocomplete.getPlace();
				var short_name = place.address_components[0]["short_name"]	
				window.location.href = "create.jsp?place=" + encodeURI(short_name) + "&tripName=" + encodeURI(tripName);

		    }// End function create

		    function clickContent(continent){
		    	window.location.href = "create.jsp?place=" + encodeURI(continent)+ "&tripName=" + encodeURI(tripName);
		    }

		</script>

         
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAxWpIu1ru_6fCnuXcGzUn_aPqrVPRJzuM&libraries=places&callback=initAutocomplete"
        async defer></script>
	</body>
</html>