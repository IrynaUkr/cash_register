<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib uri="/WEB-INF/currentDateTag.tld" prefix="m" %>--%>

<%@ page isELIgnored="false" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css"
      integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g=="
      crossorigin="anonymous" referrerpolicy="no-referrer">

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="languages"/>

<html lang="${sessionScope.lang}">


<html>
<head>
    <title>Cash Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
<%--    <fmt:message key="label.current_date"/> <m:currentDateTag/>--%>
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
<div class = container>
    <h2> ${sessionScope.message}</h2>
</div>

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
                <input type="text" name="login"> <fmt:message key="label.login"/> <br> <br>
                <input type="password" name="password"><fmt:message key="label.password"/> <br> <br>
                <input type="submit" class="btn btn-warning" value=<fmt:message key="label.send"/>>
            </form>
        </div>
    </div>
</div>
</body>
</html>
