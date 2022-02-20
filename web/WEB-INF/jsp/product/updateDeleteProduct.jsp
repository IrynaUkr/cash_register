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
    <title>add product quantity</title>
</head>
<body>
<h3>  ${sessionScope.user.surname}  <fmt:message key="label.welcome" /> ${sessionScope.user.role} </h3>
</h3>
<h2>${sessionScope.message}</h2><br>
<h4> add product by name: </h4>
<form action="/merch/setAmountProduct" method="post">
    <select name="productNA">
        <c:forEach items="${products}" var="productName">
            <option value="${productName.name}">${productName.name}</option>
            <option value="${productName.amount}">${productName.amount}</option>
            <option value="---------">---------------</option>
        </c:forEach>
    </select>
    <input type="text" name="amountNA" placeholder="amount"> amount
    <input type="submit" value="add product by name">
</form>

<h4> add product by code: </h4>
<form action="/merch/setAmountProduct" method="post">
    <select name="productND">
        <c:forEach items="${products}" var="productName">
            <option value="${productName.name}">${productName.name}</option>
            <option value="${productName.amount}">${productName.amount}</option>
            <option value="---------">---------------</option>
        </c:forEach>
    </select>
    <input type="text" name="amountND" placeholder="amount"> amount
    <input type="submit" value="delete product by name">
</form>



<h4>delete product by code:</h4>
<br>
<form action="/cashier/deleteProductFromProductList" method="post">
    <select name="productCD">
        <c:forEach items="${products}" var="productCode">
            <option value="${productCode.code}">${productCode.code}</option>
            <option value="${productCode.amount}">${productCode.amount}</option>
            <option value="---------">---------------</option>
        </c:forEach>
    </select>
    <input type="text" name="amountCD" placeholder="amount"> amount

    <input type="submit" value="delete product by code">
</form>

<form action="/ServletBack" target="_blank">
    <button>back to start ${sessionScope.user.role} page</button>
</form>


</body>
</html>