<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1"><link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

<title>Trip Selection</title>
    <style>
        body{
            width: 100%;
            height: 100%;
            background-image: url("img/auth-bg.jpg");
            background-size: cover;
        }

        .container{
            margin-top: 100px;
            width: 400px;
            margin-left: auto;
            margin-right: auto;
            background-color: rgb(255,255,255);
            padding: 20px;
        }


        .form-request-help{
            margin-top: 50px;
            padding: 10px;
            width: 300px;
            margin-left: auto;
            margin-right: auto;
            text-align: center;
        }
    </style>
</head>
<body>
 <div class="container">

      <form class="form-request-help" method="POST" action="CreateTrip">
        <h2 class="form-request-help-heading text-center">Planning Trips</h2>
          <br/>
          <h4 class="form-request-help-heading text-left">Create a new trip:</h4>
        <label for="tripHandle" class="sr-only">New Group Name </label>
        <input id = "tripName" .type="text" name="tripHandle" class="form-control" placeholder="trojanTrip" required autofocus>
        <input type="hidden" name="command" value="creaettripHandle" >
          <br/>
          <input type="submit" class="btn btn-outline-success my-5 my-sm-0" value="Submit"/>

     </form>
   

    <form class="form-request-help" method="POST" action="JoinTrip">
        <h4 class="form-request-help-heading text-left">Join a Trip:</h4>
        <label for="tripHandle" class="sr-only"> Join Trip Name </label>
        <input type="text" name="tripHandle" class="form-control" placeholder="tommyTrip" required autofocus>
        <input type="hidden" name= "command" value="jointripHandle">
        <br/>
        <input type="submit" class="btn btn-outline-success my-5 my-sm-0" value="Submit"/>
    </form>
      
      
      <!--
  private String address;

  private String venmoHandle;
  private int groupId;
  private String checkedInStatus;
  
        -->

  </div> <!-- /container -->


</body>
</html>