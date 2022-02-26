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
    <title>delete product from receipt</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h3>  ${sessionScope.user.surname} <fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
    </h3>
    <h2>${sessionScope.message}</h2><br>
    <br>

    <form action="${pageContext.request.contextPath}/chief/delProdFromReceipt" method="post">
        <select name="number">
            <c:forEach items="${receipts}" var="receipt">
                <option value="${receipt.number}">${receipt.number}</option>
            </c:forEach>
        </select>
        <br>
        <fmt:message key="label.name"/>: <br>
        <select name="products1">
            <c:forEach items="${products}" var="pName1">
                <option value="${pName1.name}">${pName1.name}</option>
            </c:forEach>
        </select>
        <fmt:message key="label.code"/>: <br>
        <select name="products3">
            <c:forEach items="${products}" var="pCode1">
                <option value="${pCode1.code}">${pCode1.code}</option>
            </c:forEach>
        </select>
        <input type="submit" class="btn-warning" value="<fmt:message key="label.send"/>">
    </form>

    <form action="/ServletBack" target="_blank">
        <button><fmt:message key="label.back_to_start"/> ${sessionScope.user.role} </button>
    </form>

</div>
</body>
</html>
