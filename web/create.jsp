<%--
  Created by IntelliJ IDEA.
  User: yanxili
  Date: 10/13/18
  Time: 4:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!doctype html>

<html>
<head>
    <!-- <title>Tripi - <%= request.getAttribute("title") %></title> -->

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="./css/trip.css">
    <link rel="stylesheet" href="./css/chat.css">

</head>

<body>
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
                <!-- Image?????? -->
                <div class="trip-header-overlay"></div>

                <div class="trip-details">
                    <h2> Trip to ${param.place} </h2>
                </div>
            </div>

            <div class="trip-items">
                Adding ...
                <!-- <button onclick="createHotel();">Hotel</button> -->
                <br/>


                <!-- Trigger the modal with a button -->
                <button type="button" class="btn btn-info" data-toggle="modal" data-target="#flightModal">Add Flights</button>

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
                                <button type="submit" onclick="form_submit()" class="btn btn-success" data-dismiss="modal">Save</button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            </div>
                        </div>

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
                        <div class="msg_history">
                            <div class="incoming_msg">
                                <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                                <div class="received_msg">
                                    <div class="received_withd_msg">
                                        <p>Test which is a new approach to have all
                                            solutions</p>
                                        <span class="time_date"> 11:01 AM    |    June 9</span></div>
                                </div>
                            </div>
                            <div class="outgoing_msg">
                                <div class="sent_msg">
                                    <p>Test which is a new approach to have all
                                        solutions</p>
                                    <span class="time_date"> 11:01 AM    |    June 9</span> </div>
                            </div>
                            <div class="incoming_msg">
                                <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                                <div class="received_msg">
                                    <div class="received_withd_msg">
                                        <p>Test, which is a new approach to have</p>
                                        <span class="time_date"> 11:01 AM    |    Yesterday</span></div>
                                </div>
                            </div>
                            <div class="outgoing_msg">
                                <div class="sent_msg">
                                    <p>Apollo University, Delhi, India Test</p>
                                    <span class="time_date"> 11:01 AM    |    Today</span> </div>
                            </div>
                            <div class="incoming_msg">
                                <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
                                <div class="received_msg">
                                    <div class="received_withd_msg">
                                        <p>We work directly with our designers and suppliers,
                                            and sell direct to you, which means quality, exclusive
                                            products, at a price anyone can afford.</p>
                                        <span class="time_date"> 11:01 AM    |    Today</span></div>
                                </div>
                            </div>
                        </div>
                        <div class="type_msg">
                            <div class="input_msg_write">
                                <input type="text" class="write_msg" placeholder="Type a message" />
                                <button class="msg_send_btn" type="button"><i class="fa fa-paper-plane" aria-hidden="true"></i></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div> <!-- Close chatroom -->
    </div> <!-- Close Row -->
</div> <!-- Close container -->

<script>

</script>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>




<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.8.4/moment.js"></script>
<script src="./js/combodate.js"></script>
<script src="./js/trip.js"></script>

<script defer src="https://use.fontawesome.com/releases/v5.0.9/js/all.js" integrity="sha384-8iPTk2s/jMVj81dnzb/iFR2sdA7u06vHJyyLlAd4snFpCl/SnyUjRrbdJsw1pGIl" crossorigin="anonymous"></script>
<!--
   <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBNaOYQQnHWBFLos6heIboivme-CaDJ8C0&callback=initMap">
    </script> -->
<!-- <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBNaOYQQnHWBFLos6heIboivme-CaDJ8C0&libraries=places&callback=initMap" async defer></script> -->

</body>
</html>