<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ru"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title>Cash Register</title>
</head>
<body>
<h1>Cash Register</h1>
<h2> ${message}</h2>

<h2>Please, enter your login and password to start working</h2>
<form action="/login" method="post">
    <input type="text" name="user" value="login"> <fmt:message key="label.login" /> <br> <br>
    <input type="password" name="password" value="password"><fmt:message key="label.password" /> <br> <br>
    <input type="submit" value="Submit">
</form>

</body>
</html>
