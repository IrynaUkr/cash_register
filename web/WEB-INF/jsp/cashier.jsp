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
    <title>Cashier</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<header>
    <h3>  ${sessionScope.user.surname} , <fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
</header>
<div class="container">
    <h2>${sessionScope.message}</h2><br>
    <br>
    <form action="${pageContext.request.contextPath}/cashier/openReceipt" target="_blank">
        <button><fmt:message key="label.open_receipt"/></button>
    </form>
    <br>

    <form action="${pageContext.request.contextPath}/cashier/printReceipt" target="_blank">
        <button><fmt:message key="label.print_receipt"/></button>
    </form>


    <fmt:message key="label.update_receipt_m"/><br>
    <form action="${pageContext.request.contextPath}/cashier/updateAmountReceipt" target="_blank">
        <button><fmt:message key="label.update_receipt"/></button>
    </form>

    <br>
    <fmt:message key="label.close_receipt_m"/>
    <form action="${pageContext.request.contextPath}/cashier/closeReceipt" target="_blank">
        <button><fmt:message key="label.close_receipt"/></button>
    </form>


    <form action="${pageContext.request.contextPath}/logout" target="_blank" method="post">
        <button><fmt:message key="label.logout"/></button>
    </form>
</div>
</body>
</html>
