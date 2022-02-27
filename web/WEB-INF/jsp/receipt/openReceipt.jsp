<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css"
integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g=="
crossorigin="anonymous" referrerpolicy="no-referrer">

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="languages"/>

<html lang="${sessionScope.lang}">

<html>
<head>
    <title>open Receipt</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>

<body>
<div class="container">
    <h3>  ${sessionScope.user.surname} <fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
    <h2>${sessionScope.message}</h2><br>
    <form action="${pageContext.request.contextPath}/cashier/openReceipt" method="post">
        N: <input type="text" name="number"><br>
        <fmt:message key="label.status"/>:
        <select name="status">
            <option value="CREATED" selected>CREATED</option>
            <option value="CLOSED"> CLOSED</option>
        </select> <br><br>
        <fmt:message key="label.type"/>
        <select name="type">
            <option value="SALE" selected>SALE</option>
            <option value="RETURN"> RETURN</option>
        </select> <br> <br>
        <input type="submit" class="btn btn-warning border-radius: 10px;" value="<fmt:message key="label.open_receipt" />">
    </form>

    <form action="${pageContext.request.contextPath}/ServletBack" target="_blank">
        <button><fmt:message key="label.back_to_start"/> ${sessionScope.user.role} </button>
    </form>
</div>
</body>
</html>
