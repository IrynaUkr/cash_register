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
    <title>add Product</title>

</head>
<body>
<h3>  ${sessionScope.user.surname}  <fmt:message key="label.welcome" /> ${sessionScope.user.role} </h3>
</h3>
<h2>${sessionScope.message}</h2><br>
<h4> <fmt:message key="label.open_receipt" />:</h4>
${sessionScope.receipt.number} -N<br>
${sessionScope.receipt.date} -<br>
${sessionScope.receipt.status}- <fmt:message key="label.status" /><br>
${sessionScope.receipt.operationType} <fmt:message key="label.type" /><br>

<c:forEach var="bean" items="${sessionScope.receipt.receiptProducts}">
    <fmt:message key="label.code" />- ${bean.code};
    <fmt:message key="label.name" />- ${bean.name};
    <fmt:message key="label.price" />- ${bean.price};
    <fmt:message key="label.amount" />- ${bean.amount};
    <fmt:message key="label.sum" /> - ${bean.amount*bean.price};
    <br>
</c:forEach>

<h5>${sessionScope.receipt.amount} -<fmt:message key="label.total_amount" />
    ${sessionScope.receipt.sum} - <fmt:message key="label.sum" /> <br>
    ${sessionScope.user.surname} </h5>
<h3> ${sessionScope.message} </h3>
<h4>  <fmt:message key="label.add_product_byName" />: </h4>

<form action="/cashier/addProductToReceiptList" method="post">
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

<h4>  <fmt:message key="label.add_product_byCode" /> </h4>
<form action="/cashier/addProductToReceiptList" method="post">
    <select name="productCA">
        <c:forEach items="${products}" var="productCode">
            <option value="${productCode.code}">${productCode.code}</option>
            <option value="${productCode.amount}">${productCode.amount}</option>
            <option value="---------">---------------</option>
        </c:forEach>
    </select>
    <input type="text" name="amountCA" placeholder="amount"> amount

    <input type="submit" value="add product by code">
</form>

<h4> <fmt:message key="label.del_product" />:</h4>
enter amount of product, that had been added in check before<br>
<form action="/cashier/deleteProductFromProductList" method="post">
    <select name="productND">
        <c:forEach items="${products}" var="productName">
            <option value="${productName.name}">${productName.name}</option>
            <option value="${productName.amount}">${productName.amount}</option>
            <option value="---------">---------------</option>
        </c:forEach>
    </select>
    <input type="text" name="amountND" placeholder="0"><fmt:message key="label.amount" />
    <input type="submit" value="delete product by name">
</form>

<h4>delete product by code:</h4>
enter amount of product, that had been added in check before<br>
<form action="/cashier/deleteProductFromProductList" method="post">
    <select name="productCD">
        <c:forEach items="${products}" var="productCode">
            <option value="${productCode.code}">${productCode.code}</option>
            <option value="${productCode.amount}">${productCode.amount}</option>
            <option value="---------">---------------</option>
        </c:forEach>
    </select>
    <input type="text" name="amountCD" placeholder="0">  <fmt:message key="label.amount" />

    <input type="submit" value="delete product by code">
</form>
<br>
<form action="/cashier/createReceipt" method="post">
    <button>save receipt</button>
</form>


<br>
<form action="/ServletBack" target="_blank">
    <button><fmt:message key="label.back_to_start" />  ${sessionScope.user.role} </button>
</form>



</body>
</html>
