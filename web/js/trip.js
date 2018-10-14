var geocoder;
var map;
var autocompleteFrom;
var autocompleteTo;
      
// Initialize the map (called when the Google Maps API loads)
var infoWindow;

function initMap() {
	// Create the map, sets the default center to the middle of the world
	map = new google.maps.Map(document.getElementById('map'), {
    	 center: {lat: 0, lng: 0},
      zoom: 2
    });

    console.log("Desktop file");

	// Get all the trip items on the page
	// var tripItems = document.getElementsByClassName('trip-item');
	
	// infoWindow = new google.maps.InfoWindow();
	
	// // If we have trip items, create markers and center map on markers
	// if (tripItems.length > 0) {
	// 	var bounds = new google.maps.LatLngBounds();
		
	// 	for(var i=0;i<tripItems.length;i++) {
	// 		var tripItem = tripItems[i];
	// 		var latitude = tripItem.dataset.latitude;
	// 		var longitude = tripItem.dataset.longitude;

	// 		let newInfoWindow = new google.maps.InfoWindow({
	// 			content: tripItem.innerHTML
	// 		});
			
	// 		let newMarker = new google.maps.Marker({
	// 			position: { lat: parseFloat(latitude), lng: parseFloat(longitude) },
	// 			icon: './img/' + tripItem.dataset.type + '_icon.png',
	// 			map: map
	// 		});
			
	// 		newMarker.addListener('click', function() {
	// 			newInfoWindow.open(map, newMarker);
	// 		});
			
	// 		bounds.extend(newMarker.getPosition());
	// 	}
		
	// 	map.fitBounds(bounds);
	// }

	console.log("here");
	console.log(document.getElementById('search-fromAirport'));

	// Enable autocomplete
	autocompleteFrom = new google.maps.places.Autocomplete((document.getElementById('search-fromCity')), {types: ['geocode']});
	autocompleteTo = new google.maps.places.Autocomplete((document.getElementById('search-toCity')), {types: ['geocode']});
}

// Show the item modal when the add item button is clicked
$("#addItemModalShow").click(function() {
	$("#addItemModal").modal("show");
});

function form_submit() {
	console.log("Form Submitted??????????????");
 //    var flightFrom, flightTo;
  
	// flightFrom = autocompleteFrom.getPlace().name;
	// flightTo = autocompleteTo.getPlace().name;
	
	// var fromAirport = flightFrom.substr(0,3);
	// var toAirport = flightTo.substr(0,3);
	
	var dateInput = document.getElementById("flightDate").value;

	console.log(dateInput); //e.g. 2015-11-13

	var monthVar = dateInput.substr(0, 7);
	console.log(monthVar);

	// console.log("From: " + fromAirport);
	// console.log("To: " + toAirport);
    console.log("Form Submitted+++++++++++++++++");

}  

// Handle submit
$("#addFlightForm").submit(function(event) {
	console.log("Form Submitted")
	// Get the coordinates from the autocomplete box
	var itemLat, itemLng;
	
	if (autocomplete.getPlace() !== undefined) {
		itemLat = autocomplete.getPlace().geometry.location.lat();
		itemLng = autocomplete.getPlace().geometry.location.lng();
	} else {
		$('.item-form-alert').html('Please pick a valid location');
		$('.item-form-alert').fadeIn('fast');
		event.preventDefault();
		return;
	}
	
	
	// Set data in forms
	$("#itemLatitude").val(itemLat);
	$("#itemLongitude").val(itemLng);
	
	// Prevent from actually submitting form
	event.preventDefault();
	
	// Send data to Servlet via AJAX and respond to whether or not we succesfully add info
	$.ajax({
		type: "POST",
		url: $(this).attr('action'),
		data: $("#addItemForm").serialize(),
		success: function(data) {
			if (data.trim() == 'SUCCESS') {
				// Item succesfully posted
				$('.item-form-alert').fadeOut('fast');
				$('#addItemModal').modal("hide");
				
				// Add marker to map
				var newMarker = new google.maps.Marker({
					position: { lat: itemLat, lng: itemLng },
					map: map
				});
				
				location.reload();
				
//				var time = $("#itemTime").val();
//				var type = $("#itemType").val();
//				var title = $("#itemTitle").val();
//				var address = $("#itemAddress").val();
//				var description = $("#itemDescription").val();
//				
//				var icon = "";
//				
//				if (type === "hotel") {
//	          		  icon = "<i class='fas fa-h-square icon'></i> ";
//	          	  } else if (type === "place") {
//	          		  icon = "<i class='fas fa-map-pin icon'></i> ";
//	          	  } else {
//	          		  icon = "<i class='fas fa-calendar icon'></i> ";
//	          	  }
//				
//				
//				// Add item to timeline
//				var $newItem = $(`
//				<div class="card trip-item" data-longitude="${itemLng}" data-latitude="{itemLat}" data-time="${time}">
//	              <div class="card-body item-${type}">
//	                <h5 class="card-title">${icon}${title}</h5>
//	                <h6 class="card-subtitle address">${address}</h6>
//	                <p class="card-text">${description}</p>
//	                
//	                <hr> 
//	                
//	                <h6 class="card-subtitle time"></h6>
//	              </div>
//				`);
			} else {
				// Show error message
				$('.item-form-alert').html(data.trim());
				$('.item-form-alert').fadeIn('fast');
			}
		}
	});
	
	return;
});

// Support date time input
$(function(){
    $('#itemTime').combodate(); 
    $('.item-form-alert').hide();
});