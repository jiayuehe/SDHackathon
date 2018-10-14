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
    <title>Trip Login</title>
    <!-- Bootstrap core CSS -->
    <link href="https://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        .auth-form {
            width: 330px;
            margin: 0 auto;
        }

        input[type='text'], input[type='password'], input[type='email'] {
            border-radius: 0;
        }

        .auth-form .first {
            border-radius: 5px 5px 0 0;
        }

        .autauth-bg.jpgh-form .last {
            border-radius: 0 0 5px 5px;
        }
    </style>
</head>
<body class="text-center" style = "background-image: url('auth-bg.jpg'); background-size: cover;">
    <form class="auth-form" method = "POST" action = "Login">
        <a href = "home.jsp"></a>
        <div style = "margin-top: -60px;">
            <h1 class="h3 mb-3 font-weight-normal" style = "color: #f9880e;">Please sign in</h1>

            <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger item-form-alert" role="alert">
                <%= request.getAttribute("error") %>
            </div>
            <% } %>

            <input type="text" id="username" name="username" class="form-control first" placeholder="Username" required autofocus>

            <input type="password" id="password" name="password" class="form-control last" placeholder="Password" required>

            <br>
            <input type="submit" value="Sign In"/>

        </div>

    </form>


</body>
</html>
