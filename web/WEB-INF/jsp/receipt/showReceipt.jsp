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
    <title>Print</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <article>
        <h4> N: ${sessionScope.receipt.number} </h4>

        ${sessionScope.receipt.date} -<br>
        ${sessionScope.receipt.status}- <fmt:message key="label.status"/><br>
        ${sessionScope.receipt.operationType} -<fmt:message key="label.type"/><br>
        <c:forEach var="bean" items="${sessionScope.receipt.receiptProducts}">
            ......................................<br>
            <fmt:message key="label.code"/>.....  ${bean.code};<br>
            <fmt:message key="label.name"/>..... ${bean.name};<br>
            <fmt:message key="label.price"/>....  ${bean.price};<br>
            <fmt:message key="label.amount"/>...  ${bean.amount};${bean.uom};<br>
            <fmt:message key="label.sum"/> .... ${bean.amount*bean.price};<br>
            ......................................<br><br>

        </c:forEach>
        <h4>${sessionScope.receipt.amount} <fmt:message key="label.total_amount"/></h4>
        <h4>${sessionScope.receipt.sum} <fmt:message key="label.sum"/></h4>
        ${sessionScope.user.surname} <br>
    </article>
    <form action="${pageContext.request.contextPath}/logout" target="_blank" method="post">
        <button><fmt:message key="label.logout"/></button>
    </form>
    <form action="${pageContext.request.contextPath}/ServletBack" target="_blank">
        <button><fmt:message key="label.back_to_start"/> ${sessionScope.user.role} </button>
    </form>
</div>
</body>
</html>
