<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page session="true" %>


<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css"
      integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g=="
      crossorigin="anonymous" referrerpolicy="no-referrer">
<!-- jQuery (Cloudflare CDN) -->
<script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"
        integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<!-- Bootstrap Bundle JS (Cloudflare CDN) -->
<script defer src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/js/bootstrap.min.js"
        integrity="sha512-UR25UO94eTnCVwjbXozyeVd6ZqpaAE9naiEUBK/A+QDbfSTQFhPGj5lOR6d8tsgbBk84Ggb5A3EkjsOgPRPcKA=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="languages"/>

<html lang="${sessionScope.lang}">


<html>
<head>
    <title>Cash Register</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<header>
    <nav class="navbar navbar-default">
        <div class="container">
            <div class="navbar-header">
                <h1 class="text-center"><fmt:message key="label.cash"/></h1>
            </div>
        </div>
    </nav>
</header>
<h2> ${message}</h2>
<div class="container">
    <div class="row">
        <div class="col-md-4">
            <ul>
                <fmt:message key="label.choose.lang"/>
                <li><a class="btn btn-warning" href="?sessionLocale=en"><fmt:message key="label.lang.en"/></a></li>
                <br>
                <li><a class="btn btn-warning" href="?sessionLocale=ua"><fmt:message key="label.lang.ua"/></a></li>
                <br>
                <li><a class="btn btn-warning" href="?sessionLocale=ru"><fmt:message key="label.lang.ru"/></a></li>
            </ul>
        </div>
        <div class="col-md-8">
            <h2><fmt:message key="label.enter"/></h2>
            <form role="form" action="/login" method="post">
                <input type="text" name="user" value="login"> <fmt:message key="label.login"/> <br> <br>
                <input type="password" name="password" value="password"><fmt:message key="label.password"/> <br> <br>
                <input type="submit" class="btn btn-warning" value=<fmt:message key="label.send"/>>
            </form>
        </div>
    </div>
</div>
</body>
</html>
