<%--
  Created by IntelliJ IDEA.
  User: Ирина
  Date: 05.02.2022
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cash Register</title>
</head>
<body>
<h1>Cash Register</h1>
<h2> ${message}</h2>

<h2>Please, enter your login and password to start working</h2>
<form action="/login" method="post">
    <input type="text" name="user" value="login"> login <br> <br>
    <input type="password" name="password" value="password">password <br> <br>
    <input type="submit" value="Submit">
</form>
</body>
</html>
