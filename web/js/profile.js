var autocomplete;

function initAutocomplete() {
    // Create the autocomplete object, restricting the search to geographical
    // location types.
    autocomplete = new google.maps.places.Autocomplete(
        /** @type {!HTMLInputElement} */(document.getElementById('placeInput')),
        {types: ['geocode']});
}

function shortenPlace(){
	var searchTerm = document.querySelector('#placeInput').value;
	searchTerm = searchTerm.trim();
	var place = autocomplete.getPlace();
	var short_name = place.address_components[0]["short_name"]	
	document.querySelector('#placeInput').value = short_name;
}

/* to change page without refreshing*/
function tripPage(){
    document.getElementById('tripLink').classList.add('active');
    document.getElementById('updateLink').classList.remove('active');
    document.getElementById('createLink').classList.remove('active');
    document.getElementById('updatePage').style.display = "none";
    document.getElementById('createPage').style.display = "none";
    document.getElementById('tripsPage').style.display = "block";
  } 
  function CreateTrip(){
    document.getElementById('tripLink').classList.remove('active');
    document.getElementById('updateLink').classList.remove('active');
    document.getElementById('createLink').classList.add('active');
    document.getElementById('tripsPage').style.display = "none";
    document.getElementById('updatePage').style.display = "none";
    document.getElementById('createPage').style.display = "block";
  }      
function update(){
    document.getElementById('tripLink').classList.remove('active');
    document.getElementById('updateLink').classList.add('active');
    document.getElementById('createLink').classList.remove('active');
    document.getElementById('tripsPage').style.display = "none";
    document.getElementById('createPage').style.display = "none";
    document.getElementById('updatePage').style.display = "block";
  }
    
function validateCreate(){
	shortenPlace();
	var name = document.getElementById('titleInput').value;
	var link = document.getElementById('linkInput').value;
	var place = document.getElementById('placeInput').value;
	var description = document.getElementById('descriptionInput').value;
	if (name != null && link != null && place != null && description != null) {
		sendMessage(name, place, link, description);
	}
	
	return false;
}

function validateUpdate(){
    var name = document.getElementById('nameInput');
    var image = document.getElementById('imageUrl');
    var password = document.getElementById('password');
    var passwordError = document.getElementById('passwordError');
    var passwordEr = false;
    
    if(name.value.length <= 0 && image.value.length <= 0 && password.value.length <= 0){
        passwordError.innerHTML = "<strong>Please Enter Update</strong>";
        passwordEr = true;
    }
    
    if(passwordEr === true){
        return false;
    }
    
    return true;
}

var socket;
function connectToServer() {
	socket = new WebSocket("ws://localhost:8080/tripi_csci201/feed"); 

	socket.onmessage = function(event) {
		let message = JSON.parse(event.data); // assume event.data = {"message": "login", "user": "natalie"}

		if(message.message == "newI"){
			var htmlString = "<div class = 'result-blocks'><a href = 'view?id=" + message.id + "'><img class = 'result-img' src = '" + message.img;
			htmlString += "'> <div class = 'result-text'><h3>" + message.title + "</h3>" + message.details + "</div></a></div>";
			document.getElementById("results").innerHTML += htmlString;
		}
	}
} 

function sendMessage(name, place, link, description) { //for when you make a newI
	var xhttp = new XMLHttpRequest();
	xhttp.open("GET", "create?coverPhotoLink=" + link + "&description=" + description + "&title=" + name + "&mainPlace=" + place, false)
	xhttp.send();
	var id = 0;
	if(xhttp.responseText.trim().length > 0) { 
		id = xhttp.responseText;
	} 
	else {
		return false;
	}
	var msg = {"message": "newI", "id": id, "place": place, "title": name, "img": link, "details": description};
	socket.send(JSON.stringify(msg));
	
	window.location.href = './edit?id=' + id;
} 

connectToServer();