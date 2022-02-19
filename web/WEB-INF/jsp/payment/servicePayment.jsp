<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="languages"/>

<html lang="${sessionScope.lang}">

<html>
<head>
    <title>payment</title>
</head>
<body>
<h3>  ${sessionScope.user.surname}  <fmt:message key="label.welcome" /> ${sessionScope.user.role} </h3>
</h3>
<h2>${sessionScope.message}</h2><br>
SERVICE_CASH_INFLOW -принято в кассу для служебных нужд- сдача покупателю<br><br>
SERVICE_CASH_OUTFLOW-выдано из кассы на служебные нужды- выдача дневной выручки<br><br>
<form action="/chief/servletPayment" method="post">
    <select name="type">
        <option value="SERVICE_CASH_INFLOW" selected>CASH_INFLOW</option>
        <option value="SERVICE_CASH_OUTFLOW">CASH_OUTFLOW</option>
    </select><br><br>
    <input type="text" name="value" > payment
    <input type="text" name="description" > description
    <input type="submit" value="Submit">
</form>


</body>
</html>
