<%--&lt;%&ndash;--%>
  <%--Created by IntelliJ IDEA.--%>
  <%--User: yanxili--%>
  <%--Date: 10/13/18--%>
  <%--Time: 4:40 PM--%>
  <%--To change this template use File | Settings | File Templates.--%>
<%--&ndash;%&gt;--%>
<%--<%@ page language="java" contentType="text/html; charset=UTF-8"--%>
         <%--pageEncoding="UTF-8"%>--%>
<%--<!doctype html>--%>

<%--<html>--%>
<%--<head>--%>
    <%--<!-- <title>Tripi - <%= request.getAttribute("title") %></title> -->--%>


<%--</head>--%>


<%--</html>--%>

<%--
  Created by IntelliJ IDEA.
  User: zifanshi
  Date: 10/13/18
  Time: 12:21 PM
  To change this template use File | Settings | File Templates.
--%>

<!DOCTYPE html>
<html>
<head>
    <title>Chat Client</title>
    <script>
        var userName = "<%= request.getSession().getAttribute("username") %>";
        var tripName = "<%= request.getSession().getAttribute("tripName")%>";
        var socket;
        function connectToServer() {
            socket = new WebSocket("ws://localhost:4444");
            socket.onopen = function(event) {
                socket.send(JSON.stringify({
                    type : "INITIALIZE",
                    tripName : tripName,
                }))
            };
            socket.onmessage = function(event) {
                parseData(event.data);
            };
            socket.onclose = function(event) {
            };
        }

        function sendMessage() {
            let content = document.getElementById("chatMessage").value;
            var cdate = new Date();
            var newContent = "";
            newContent += '<div class="outgoing_msg">';
            newContent += '<div class="sent_msg">';
            newContent += '<p>';
            newContent += content;
            newContent += '</p>';
            newContent += '<span class="time_date">';
            newContent += cdate.toLocaleTimeString();
            newContent += '</span>';
            newContent += '</div></div>';
            var originalContent = document.getElementById("msgHistory").innerHTML;
            originalContent += newContent;
            document.getElementById("msgHistory").innerHTML = originalContent;



            let queryString = JSON.stringify({
                type: "CHAT",
                userName: userName,
                tripName: tripName,
                content: content
            });
            console.log("query send is:" + queryString);
            socket.send(queryString);

            document.getElementById("chatMessage").value = "";
            return false;
        }

        function parseData(data) {
            console.log(data);
            let msg = JSON.parse(data);
            console.log(msg.type);

            switch (msg.type) {
                case "CHAT":
                    console.log("CHAT HERE ");

                    let fromUser = msg.username;
                    if(fromUser != userName){
                        let content = msg.content;
                        console.log("User name is " + fromUser);
                        console.log("Content is "+ content);
                        // TODO display here
                        console.log("What is happending? --- ");
                        var cdate = new Date();
                        var newContent = "";
                        newContent += '<div class="incoming_msg">';
                        newContent += '<div class="incoming_msg_img">';
                        newContent += fromUser;
                        newContent += '</div>';
                        newContent += '<div class="received_msg">';
                        newContent += '<div class="received_withd_msg">';

                        newContent += '<p>';
                        newContent += content;
                        newContent += '</p>';
                        newContent += '<span class="time_date">';
                        newContent += cdate.toLocaleTimeString();
                        newContent += '</span>';
                        newContent += '</div></div>';
                        console.log(newContent);
                        var originalContent = document.getElementById("msgHistory").innerHTML;
                        originalContent += newContent;
                        document.getElementById("msgHistory").innerHTML = originalContent;

                    }

                    break;
                case "ADD_LOCATION":
                    let currentUser = msg.userName;
                    let dest = msg.destination;
                    let origin = msg.origin;
                    console.log("user is " + currentUser)
                    console.log("dest is " + dest);
                    console.log("origin is " + origin);
                    var cdate = new Date();


                    var newContent = "";
                    newContent += '<ul class="timeline">';
                    newContent += '<li>';
                    newContent += 'New Flight Added';
                    newContent += '<a href="#" class="float-right">'
                    newContent += cdate.toLocaleTimeString();
                    newContent += '</a>';
                    newContent += '<p>';
                    newContent += 'FROM: '
                    newContent += origin.toUpperCase();
                    newContent += '<br/> TO: ';
                    newContent += dest.toUpperCase();
                    newContent += '</li>';
                    newContent += '</ul>';

                    var originalContent = document.getElementById("addTimeLine").innerHTML;
                    originalContent += newContent;

                    document.getElementById("addTimeLine").innerHTML = originalContent;

                    break;
                case "RESULT":

                    console.log("Result here.....");


                    console.log(data);
                    console.log(msg.list);
                    console.log(msg.price);
                    var route = msg.list[0];
                    for(var i = 1; i < msg.list.length ; i++){
                        route +=   " --> " +  msg.list[i];
                    }
                    var price = msg.price.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
                    document.getElementById("totalPrice").innerHTML = price;
                    document.getElementById("route").innerHTML = route;
                    document.getElementById("displayPrice").style.visibility="visible";

            }
        }

        function addLocations() {
            let dest = document.getElementById("search-toCity").value.substr(0,3);
            let origin = document.getElementById("search-fromCity").value.substr(0, 3);
            let startMonth = document.getElementById("flightDate").value;

            let flightQuery = {
                dest: dest,
                origin: origin,
                startMonth: startMonth
            };

            let queryString = JSON.stringify({
                type: "ADD_LOCATION",
                userName: userName,
                tripName: tripName,
                flightQuery: flightQuery
            });
            console.log("Search query: " + queryString);

            socket.send(queryString);
            return false;
        }

        // City name, Dest, price,
        function save() {
            let startMonth = document.getElementById("flightDate").value;
            console.log("We are in save message");
            /* TODO: change the following user and content */
            let queryString = JSON.stringify({
                type: "CALCULATE",
                userName: userName,
                tripName: tripName,
                content: startMonth
            });

            console.log(queryString);

            socket.send(queryString);



            console.log("Saving....");

            return false;
        }

    </script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="./css/trip.css">
    <link rel="stylesheet" href="./css/chat.css">
    <link rel="stylesheet" type="text/css" href="./css/timeline.css">

</head>
<body onload="connectToServer()">
<nav id="navigation" class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <!-- <a class="navbar-brand" href="./home.jsp">Tripi</a> -->
        <a href = "home.jsp"><img class="nav-img" src="./img/logo-nav.png" alt="" width="auto" height="300" style="height: 45px !important;"></a>



    </div>
</nav>

<div class="container-fluid full-screen">
    <div class="row h-100">
        <div class="col padding-0 col-map">
            <!-- Auto focus map -->
            <div id="map">

            </div>
        </div>

        <div class="col timeline">
            <div class="trip-header">
                <img src="img/cape.jpg" class="trip-cover">
                <div class="trip-header-overlay"></div>

                <div class="trip-details">
                    <h2> Your Trip to ${param.place} </h2>
                </div>
            </div>

            <div class="trip-items">

                <!-- <button onclick="createHotel();">Hotel</button> -->
                <br/>

                <!-- Trigger the modal with a button -->
                <button type="button" class="btn btn-info" data-toggle="modal" data-target="#flightModal">Add Flights</button>
                <button type="button" class="btn btn-info" onclick="save()">Calculate Price</button>


                <!-- Modal -->
                <div class="modal fade" id="flightModal" role="dialog">
                    <div class="modal-dialog">

                        <!-- Modal content-->
                        <div class="modal-content" id="addFlightForm">
                            <div class="modal-header">
                                <h4 class="modal-title">Adding a Flight</h4>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>
                            <div class="modal-body">
                                <label>From:</label>
                                <input class="form-control search-input" type="search"
                                       placeholder="From City..." aria-label="Search" id="search-fromCity">
                                <br/>

                                <label>To:</label>
                                <input class="form-control search-input" type="search"
                                       placeholder="To City..." aria-label="Search" id="search-toCity">
                                <br/>

                                <label>Travel Month:</label>
                                <input class="form-control search-input" type="date" name="traveldate" id="flightDate">

                            </div>
                            <div class="modal-footer">
                                <button type="submit" onclick="addLocations()" class="btn btn-success" data-dismiss="modal">Save</button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            </div>
                        </div>

                    </div>
                </div> <!-- Close Modal -->
            </div> <!-- Close Trip Items -->

            <!-- TimeLine of Items Added -->
            <div class="container mt-5 mb-5">
                <div id="displayPrice">
                    Your Pirce is: $<span id="totalPrice"></span>
                    <br/>
                    Your Optimal Route is <span id="route"></span>
                </div>

                <div class="row">

                    <div class="col" id="addTimeLine">
                        <br/>
                        <h4>Group Activities</h4>
                        <ul class="timeline">


                        </ul>
                    </div>
                </div>
            </div>

        </div>  <!-- Close timeline -->



        <!-- Start Chat Room -->
        <div class="col col-lg-4 chatroom padding-0">
            <br/>
            <h4 class=" text-center">Discussion</h4>
            <div class="messaging">
                <div class="inbox_msg">

                    <div class="mesgs">
                        <div class="msg_history" id ="msgHistory">

                        </div>
                        <div class="type_msg">
                            <form class="input_msg_write">

                                <input type="text" class="write_msg" id = "chatMessage" placeholder="Type a message"/>

                                <button class="msg_send_btn" type="button" onclick="sendMessage()"><i class="fa fa-paper-plane" aria-hidden="true"></i></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div> <!-- Close chatroom -->
    </div> <!-- Close Row -->
</div> <!-- Close container -->


<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script defer src="https://use.fontawesome.com/releases/v5.0.9/js/all.js" integrity="sha384-8iPTk2s/jMVj81dnzb/iFR2sdA7u06vHJyyLlAd4snFpCl/SnyUjRrbdJsw1pGIl" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.8.4/moment.js"></script>
<script src="./js/combodate.js"></script>
<script src="./js/trip.js"></script>

<script defer src="https://use.fontawesome.com/releases/v5.0.9/js/all.js" integrity="sha384-8iPTk2s/jMVj81dnzb/iFR2sdA7u06vHJyyLlAd4snFpCl/SnyUjRrbdJsw1pGIl" crossorigin="anonymous"></script>

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBNaOYQQnHWBFLos6heIboivme-CaDJ8C0&libraries=places&callback=initMap" async defer></script>

</body>
</html>