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
<header>
    <h3>  ${sessionScope.user.surname} <fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
    <h2>${sessionScope.message}</h2><br>
</header>
choose Receipt number<br>

<div class="container">
    <form action="${pageContext.request.contextPath}/chief/delProdFromReceipt" method="post">
        <select name="number">
            <c:forEach items="${sessionScope.receipts}" var="receipt">
                <option value="${receipt.number}">${receipt.number}</option>
            </c:forEach>
        </select>

        <fmt:message key="label.name"/>: <br>
        <select name="productNA">
            <c:forEach items="${sessionScope.products}" var="pName1">
                <option value="${pName1.name}">${pName1.name}</option>
            </c:forEach>
        </select>

        <fmt:message key="label.code"/> <br>
        <select name="productCA">
            <c:forEach items="${sessionScope.products}" var="pCode1">
                <option value="${pCode1.code}">${pCode1.code}</option>
            </c:forEach>
        </select>
        <input type="submit" class="btn btn-warning" value="Submit">
    </form>
</div>

</body>
</html>
