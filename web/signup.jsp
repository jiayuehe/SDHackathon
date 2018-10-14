<%--
  Created by IntelliJ IDEA.
  User: yanxili
  Date: 10/13/18
  Time: 1:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Trip Signup</title>

        <!-- Bootstrap core CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">


        <style>
            .auth-form {
                margin-top: 100px;
                width: 400px;
                margin-left: auto;
                margin-right: auto;
                background-color: rgb(255,255,255);
                padding: 20px;
            }

            .auth-form .first {
                border-radius: 5px 5px 0 0;
            }

            .autauth-bg.jpgh-form .last {
                border-radius: 0 0 5px 5px;
            }
        </style>
    </head>
    <body class="text-center" style = "background-image: url('./img/auth-bg.jpg'); background-size: cover;">
    <form class="auth-form" method = "POST" action = "Signup">

        <div>
            <h1 class="h3 mb-3 font-weight-normal">Welcome new user! </h1>

            <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger item-form-alert" role="alert">
                <%= request.getAttribute("error") %>
            </div>
            <% } %>
            Please enter your usename: <br/>
            <input type="text" id="username" name="username" class="form-control first" placeholder="Username" required>
            <br/>
            Please enter your password: <br/>
            <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>

            <br>
            <input type="submit" class="btn btn-outline-success my-2 my-sm-0" value="Sign Up"/>
        </div>
    </form>
    </body>
</html>
