<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css"
      integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g=="
      crossorigin="anonymous" referrerpolicy="no-referrer">


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="languages"/>

<html lang="${sessionScope.lang}">

<html>
<head>
    <title>payment</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<header>
    <h3>  ${sessionScope.user.surname} , <fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
</header>
<body>

<h2>${sessionScope.message}</h2><br>
SERVICE_CASH_INFLOW -принято в кассу для служебных нужд- сдача покупателю<br><br>
SERVICE_CASH_OUTFLOW-выдано из кассы на служебные нужды- выдача дневной выручки<br><br>
<div class="container">
<form action="${pageContext.request.contextPath}/chief/servletPayment" method="post">
    <select name="type">
        <option value="SERVICE_CASH_INFLOW" selected>CASH_INFLOW</option>
        <option value="SERVICE_CASH_OUTFLOW">CASH_OUTFLOW</option>
    </select><br><br>
    <input type="text" name="value" >
    <input type="text" name="description" > <fmt:message key="label.des" />
    <input type="submit"class="btn btn-warning" value=" <fmt:message key="label.send" />">
</form>

</div>
</body>
</html>
