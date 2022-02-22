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
<h4> <fmt:message key="label.add_product_byName"/> </h4>
<form action="/merch/setAmountProduct" method="post">
    <select name="productNA">
        <c:forEach items="${products}" var="productName">
            <option value="${productName.name}">${productName.name}</option>
            <option value="${productName.amount}">${productName.amount}</option>
            <option value="---------">---------------</option>
        </c:forEach>
    </select>
    <input type="text" name="amountNA" placeholder="amount"> amount
    <input type="submit" value=<fmt:message key="label.add_product"/>>
</form>

<h4> <fmt:message key="label.add_product_byCode"/> </h4>
<form action="/merch/setAmountProduct" method="post">
    <select name="productCA">
        <c:forEach items="${products}" var="bean">
            <option value="${bean.code}">${bean.code}</option>
            <option value="${bean.amount}">${bean.amount}</option>
            <option value="---------">---------------</option>
        </c:forEach>
    </select>
    <input type="text" name="amountCA" >
    <input type="submit" value=<fmt:message key="label.add_product"/>>
</form>



<form action="/ServletBack" target="_blank">
    <button>back to start ${sessionScope.user.role} page</button>
</form>


</body>
</html>
