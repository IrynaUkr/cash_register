<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="languages"/>

<html lang="${sessionScope.lang}">


<html>
<head>
    <title>Cash Register</title>
</head>
<body>

<h1><fmt:message key="label.cash" /></h1>
<h2> ${message}</h2>
<ul>
    <li><a href="?sessionLocale=en"><fmt:message key="label.lang.en" /></a></li>
    <li><a href="?sessionLocale=ua"><fmt:message key="label.lang.ua" /></a></li>
    <li><a href="?sessionLocale=ru"><fmt:message key="label.lang.ru" /></a></li>
</ul>
<h2><fmt:message key="label.enter" /></h2>

<form action="/login" method="post">
    <input type="text" name="user" value="login"> <fmt:message key="label.login" /> <br> <br>
    <input type="password" name="password" value="password"><fmt:message key="label.password" /> <br> <br>
    <input type="submit" value=<fmt:message key="label.send" />>
</form>


</body>
</html>
