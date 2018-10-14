        var example = "https://www.eventbriteapi.com/v3/events/search/?token=LDXPFOKRVV2JPEXJFHTA&q=biking&location.address=LosAngeles&expand=venue&start_date.range_start=2018-04-21T00:00:00";
            
        var example2 = "https://api.foursquare.com/v2/venues/search?near=%22Los+Angeles,+LA%22&intent=browse&query=hotel&client_id=WKF0WE5GR5TB2YYVFJ0RO5NPQLQ2S1GGFPORNDRJP1EDNM5X&client_secret=Q2BX2P2OUCUIDQHQ0IJZYVW0A4GETAORSRPVLNZ5S43G00YY&v=20180410";
        
        var photo; 
        var link;
        var map; //current google map object
        var markerArray = [];
        var infoArray = [];
        var currentObj;
        
        //back to itenary button
        function goBack(){
        	window.history.back();
        }
        
        
        function search(){
            
            //alert("insearch");
            
            var q = document.getElementById('q').value;
            var location = document.getElementById('location').value;
            var date = document.getElementById('date').value;
            var choice = document.getElementById('choice').value;
            var er = document.getElementById('error');
            
            if(date && date.length > 0 && date !== undefined){
                var dateInput = date.split("/");
                date = dateInput[2] + "-" + dateInput[0] + "-" + dateInput[1];
            }
            
            
            if(choice === "1"){
                if(!q){
                    er.innerHTML = "<strong>Search value cannot be empty!</strong>";
                    return false;
                }
                var arg = "https://www.eventbriteapi.com/v3/events/search/?token=LDXPFOKRVV2JPEXJFHTA&q=" + q;
                if(location){
                    arg += "&location.address=" + location;
                }
                if(date && date.length > 0 && date !== undefined){
                    //alert("enter date: " + date);
                    arg += "&start_date.range_start=" + date + "T00:00:00";
                }
                
                arg += "&expand=venue";
                er.innerHTML = "";
                arg = encodeURI(arg);
                Event(arg);
            }
            else{
                if(!location){
                    er.innerHTML = "<strong>Location cannot be empty!</strong>";
                    return false;
                }
                var arg = "https://api.foursquare.com/v2/venues/search?near=" + location + "&intent=browse"; 
                if(!q){
                    arg += "&query=hotel";
                }
                else{
                    arg += "&query=" + q;
                }
                er.innerHTML = "";
                arg = encodeURI(arg + "&client_id=WKF0WE5GR5TB2YYVFJ0RO5NPQLQ2S1GGFPORNDRJP1EDNM5X&client_secret=Q2BX2P2OUCUIDQHQ0IJZYVW0A4GETAORSRPVLNZ5S43G00YY&v=20180410");
                accomodation(arg);
            }
            
            return false;
        }
                      
        function Event(line){ //eventbrite
                        
            var xhttp = new XMLHttpRequest();
            
            //alert(line);
            
            xhttp.onreadystatechange = function(){
                if(this.readyState == 4 && this.status == 200){
                    var result = document.getElementById('result');
                    var obj = JSON.parse(this.responseText);
                    
                    
                    result.innerHTML = "";
                    
                    
                    var list;
                    if(obj.events && obj.events !== null && obj.events.length > 0){
                        list = obj.events;
                    }
                    else{
                        list = obj.top_match_events;
                    }
                    
                    currentObj = list;
                    
                    for(var i = 0; i < Math.min(15, list.length); i++){
                        
                        
                        
                        var desc = "";
                        if(list[i].description.html === null){
                            desc = "No description provided"; 
                        }
                        else{
                            desc = list[i].description.html;
                        }
                        
                        var startDate = list[i].start.local.split("T")[0];
                        var startTime = list[i].start.local.split("T")[1].slice(0, 5);
                        
                        var img, urls;
                        if(list[i].logo !== null){
                            img = list[i].logo.original.url;
                        }else {img = "no img";}
                        if(list[i].url !== null){
                            urls = list[i].url;
                        }else {urls = "no url";}
                        
                        result.innerHTML += "<div class=\"card mb-3\" style=\"max-width: fill; margin-left: 100px; margin-right: 100px; margin-top: 50px;\">"
                            + "<div class=\"card-header bg-transparent text-success\">"
                            + list[i].name.text + "</div>\n"
                            + "\n<div class=\"card-body \">\n"
                            + "<div>\n<img src=\"" 
                            + img + "\" width=\"150\" heigth=\"150\">" 
                            + "\n<div id=\"summary\">"
                            + "\n<h6>Description:</h6>\n"
                            + "<div id=\"demo" + i + "\" class=\"collapse\">" + desc 
                            + "</div>\n"
                            + "<button onclick=\"tog(" + i + ");\" id=\"btn" + i + "\" type=\"button\" class=\"btn btn-primary\" data-toggle=\"collapse\" data-target=\"#demo" + i + "\">Show More</button>\n"
                            + "</div>\n" + "<h6>Location:</h6>\n" + "<p>" 
                        + list[i].venue.address.localized_multi_line_address_display[0] + "<br>" + list[i].venue.address.localized_multi_line_address_display[1] + "</p>\n"
                            + "<h6>website:</h6>\n<a href=\"" + urls + "\">" 
                            + urls + "</a>\n<h6 style=\"margin-top: 20px;\">Start Date/Time(local):</h6>\n<h6>\n" 
                            + startDate + " at " + startTime + "</h6>\n</div>\n</div>"
                            + "<div class=\"card-footer bg-transparent border-success\">" + "\n"
                            + "<button onclick=\"addEventItem(" + i + ")\" class=\"btn btn-primary\" type=\"button\" id=\"add" + i + "\" >Add to your Trip</button"
                            + "\n</div>\n</div>";
                        
                                    
                    }
                    
                    //remove hidden and hide others
                    result.style.display = "block";
                    document.getElementById('result2').style.display = "none";
                    document.getElementById('map').style.display = "none";
                }
            };
            
            
            xhttp.open("GET", line, true);
            xhttp.send();
            
            return false;
        }
        

        //to toggle show more and hide
        function tog(num){
            var txt = document.getElementById('btn' + num).innerHTML;
            if(txt === "Show More"){ document.getElementById('btn' + num).innerHTML = "Hide";}
            else { document.getElementById('btn' + num).innerHTML = "Show More";}

        }
        
        function getPhoto(id){
            
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function(){
                if(this.readyState == 4 && this.status == 200){
                    var obj = JSON.parse(this.responseText);
                    
                    photo = obj.response.photos.items[0].prefix + "200x200" +
                        obj.response.photos.items[0].suffix;
                    }
            }
            xhttp.open("GET","https://api.foursquare.com/v2/venues/"
                       + id + "/photos?client_id=WKF0WE5GR5TB2YYVFJ0RO5NPQLQ2S1GGFPORNDRJP1EDNM5X&client_secret=Q2BX2P2OUCUIDQHQ0IJZYVW0A4GETAORSRPVLNZ5S43G00YY&v=20180410" , false);
            xhttp.send();
             
            
        }
        
        function getLinks(id){
            
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function(){
                if(this.readyState == 4 && this.status == 200){
                    var obj = JSON.parse(this.responseText);
                    link = obj.response.links.items[0].url;
                    if(!link){
                        link = "no links provided";
                    }
                }
            }
            
            //alert(id);
            
            xhttp.open("GET", "https://api.foursquare.com/v2/venues/" +
                       id + "/links?client_id=WKF0WE5GR5TB2YYVFJ0RO5NPQLQ2S1GGFPORNDRJP1EDNM5X&client_secret=Q2BX2P2OUCUIDQHQ0IJZYVW0A4GETAORSRPVLNZ5S43G00YY&v=20180410", false);
            xhttp.send();
            
        }
        
        function accomodation(line){//foursquare
            
            //alert(line);
            
            var near = "";
            
            var url = line;
                 
            var xhttp = new XMLHttpRequest();
        
        //    document.getElementById('loader').style.display = "inherit";
            
            xhttp.onreadystatechange = function(){
                if(this.readyState == 4 && this.status == 200){
                    var result = document.getElementById('result2');
                    var obj = JSON.parse(this.responseText);
                    obj = obj.response.venues;
                    
                    currentObj = obj;
                    
                    result.innerHTML = "";
                    
                    //alert(obj);
                    
                    for(var i = 0; i < Math.min(15, obj.length); i++){
                        
                        getPhoto(obj[i].id);
                        //getLinks(obj[i].id); FIX ME!
                        var myLatLng = {lat: obj[i].location.lat, lng: obj[i].location.lng};
                        
                        if(i === 0){
                            newMap(myLatLng, 15);
                        }
                        
                        result.innerHTML += "<div class=\"card mb-3\" style=\"max-width: fill; margin-right: 80px; margin-top: 50px;\">"
                            + "<div class=\"card-header bg-transparent text-success\">"
                            + obj[i].name + "</div>\n"
                            + "\n<div class=\"card-body \">\n"
                            + "<div>\n<img src=\"" 
                            + photo + "\">" 
                            + "\n<div>\n<text>"
                            + "\n<h6>Location:</h6>\n" + obj[i].location.formattedAddress[0] + "\n"
                            + "<br>" + obj[i].location.formattedAddress[1] + "\n"
                            + "\n</text>\n</div>\n</div>"
                            + "<div class=\"card-footer bg-transparent border-success\">" + "\n<form method=\"POST\" action=\"#\">"
                            + "<button id=\"add" + i + "\" onclick=\"addAccItem(" + i + ");\" class=\"btn btn-primary\" type=\"button\">Add to your Trip</button>"
                            + "<button style=\"margin-left: 10px;\" class=\"btn btn-secondary\" type=\"button\" onclick=\"details(" + i + ")\">Map</button>"
                            + "\n</form>\n</div>\n</div>";
                        
                        
                        //marker in google map
                        var marker = new google.maps.Marker({
                          position: myLatLng,
                          map: map
                        });
                        
                        markerArray.push(marker);
                            
                    }
                    
                    result.style.display = "block";
                    document.getElementById('result').style.display = "none";
                    document.getElementById('map').style.display = "block";
                
                    
                }
            };
            
            
            xhttp.open("GET", url, false);
            xhttp.send();
            //document.getElementById('loader').style.display = "none";
         
        }

        //map button goes to details 
        //create modal -> goes to itenary store lat lng
        //individual info window, create a new map with single marker
        function details(num){
            //alert(" in details " + num);
            var myLatLng = {lat: currentObj[num].location.lat, lng: currentObj[num].location.lng};
            newMap(myLatLng, 16);
            getLinks(currentObj[num].id);

            var txt = "<h5>"+ currentObj[num].name + "</h5>\n"
                      + "<p>link:<br><a href=\"" + link + "\">" + link + "</a></p>";
            
            var info = new google.maps.InfoWindow({
                content: txt
              });
            
              var marker = new google.maps.Marker({
                position: myLatLng,
                map: map
              });
            
            
              marker.addListener('click', function() {
                info.open(map, marker);
              });
              
           
        }


        // for autocomplete places
        var autocomplete;

        function initAutocomplete() {
            // Create the autocomplete object, restricting the search to geographical
            // location types.
            autocomplete = new google.maps.places.Autocomplete(
                /** @type {!HTMLInputElement} */(document.getElementById('location')),
                {types: ['geocode']});
        }
          
        function newMap(LatLng, zm){
            map = new google.maps.Map(document.getElementById('map'), {
                zoom: zm,
                center: LatLng
            });
        }

        //changing add itinerary button
        function changeButton(num){
            
            var btn = document.getElementById('add' + num);
            if(btn.classList.contains('btn-primary')){
                btn.classList.remove('btn-primary');
                btn.classList.add('btn-secondary');
                btn.innerHTML = "Added";
                return true;
            }
            
            return false;
        }
        
       
        var params;

        //adding event to itinerary
        function addEventItem(num){
            //get lat lng
            
            
            if(!changeButton(num)) {return;}
            //alert("adding Event");
             
            var id;
            var tmp = [];
            
            location.search
                    .substr(1)
                    .split("&")
                    .forEach(function (item) {
                        tmp = item.split("=");
                        if (tmp[0] === "id") id = decodeURIComponent(tmp[1]);
                    });
            
            //alert("this is id: " + id);
            var url;
            if(currentObj[num].url !== null){
            	url = currentObj[num].url;
            }else { url = "no link";}
            
            
            
             var form = document.createElement("form");
                form.setAttribute("method", "POST");
                form.setAttribute("name", "adding");
                form.setAttribute("id", "adding");
                form.setAttribute("action", "./edit/additem?id=" + id);
                	
                var desclength = Math.min(140, currentObj[num].description.text.length);
                var titleLength = Math.min(45, currentObj[num].name.text.length);
                
                params = {
                    	title : currentObj[num].name.text.substring(0, titleLength),
                    	description: currentObj[num].description.text.substring(0, desclength),
                        latitude: currentObj[num].venue.address.latitude,
                        longitude: currentObj[num].venue.address.longitude,
                        address: currentObj[num].venue.address.localized_multi_line_address_display[0],
                        time: currentObj[num].start.local,
                        type: "event",
                        link: url
                    };
            	
            	for(var key in params) {
                    if(params.hasOwnProperty(key)) {
                        var hiddenField = document.createElement("input");
                        hiddenField.setAttribute("type", "hidden");
                        hiddenField.setAttribute("name", key);
                        hiddenField.setAttribute("value", params[key]);

                        form.appendChild(hiddenField);
                    }
                }
            	
            	var sub = document.createElement("input");
            	sub.setAttribute("type", "submit");
            	sub.setAttribute("id", "hideButton");
            	form.appendChild(sub);
            	
                document.body.appendChild(form);
                
                form.querySelector("#hideButton").addEventListener("click" , function(event) {
            		//alert("jqueryHello " + id + " " + currentObj[num].venue.address.latitude);
            		//alert(currentObj[num].name.text.substring(0, titleLength));
            		event.preventDefault();
            		$.ajax({
            			type: "POST",
            			url: "./edit/additem?id=" + id,
            			data: params,
            			success: function(data) {}
            		});
            		
            		return;
            	});
                
                
                
                //submitting form
                form.querySelector('#hideButton').click()
             
                //form.submit();
        }


        //addinf accomodation to itinerary
        function addAccItem(num){
            
            if(!changeButton(num)){return;}
            //alert("adding acc");
            
            var id;
            var tmp = [];
            
            location.search
                    .substr(1)
                    .split("&")
                    .forEach(function (item) {
                        tmp = item.split("=");
                        if (tmp[0] === "id") id = decodeURIComponent(tmp[1]);
                    });
            
            //alert("this is id: " + id);
            getLinks(currentObj[num].id);
            var url;
            if(link !== null){
            	url = link;
            }else { url = "no link";}
            
            
            
             var form = document.createElement("form");
                form.setAttribute("method", "POST");
                form.setAttribute("name", "adding");
                form.setAttribute("id", "adding");
                form.setAttribute("action", "./edit/additem?id=" + id);
                
                
            var type = "";
            if(currentObj[num].categories.name === "Hotel"){
            		type = "hotel";
            }
            else{
            		type = "place";
            }
                	
                
                params = {
                    	title : currentObj[num].name,
                    	description: currentObj[num].name,
                        latitude: currentObj[num].location.lat,
                        longitude: currentObj[num].location.lng,
                        address: currentObj[num].location.formattedAddress[0],
                        time: "2018-04-21T00:00:00",
                        type: "hotel",
                        link: url
                    };
            	
            	for(var key in params) {
                    if(params.hasOwnProperty(key)) {
                        var hiddenField = document.createElement("input");
                        hiddenField.setAttribute("type", "hidden");
                        hiddenField.setAttribute("name", key);
                        hiddenField.setAttribute("value", params[key]);

                        form.appendChild(hiddenField);
                    }
                }
            	
            	var sub = document.createElement("input");
            	sub.setAttribute("type", "submit");
            	sub.setAttribute("id", "hideButton");
            	form.appendChild(sub);
            	
                document.body.appendChild(form);
                
                form.querySelector("#hideButton").addEventListener("click" , function(event) {
            		//alert("jqueryHello ");
            		event.preventDefault();
            		$.ajax({
            			type: "POST",
            			url: "./edit/additem?id=" + id,
            			data: params,
            			success: function(data) {}
            		});
            		
            		return;
            	});
                
                //submitting form
                form.querySelector('#hideButton').click()
            
        }
        

            