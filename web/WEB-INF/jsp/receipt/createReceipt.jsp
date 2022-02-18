<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add Product</title>

</head>
<body>
you are logged in as ${sessionScope.user.role}<br>
<h4> the check is open:</h4>
${sessionScope.receipt.number} -number<br>
${sessionScope.receipt.date} -date<br>
${sessionScope.receipt.status}- status<br>
${sessionScope.receipt.operationType} -operationType<br>

<c:forEach var="bean" items="${sessionScope.receipt.receiptProducts}">
    code- ${bean.code};
    name- ${bean.name};
    price- ${bean.price};
    amount- ${bean.amount};
    total - ${bean.amount*bean.price};
    <br>
</c:forEach>

<h5>${sessionScope.receipt.amount} -total amount
    ${sessionScope.receipt.sum} -total sum <br>
    ${sessionScope.user.surname} -surname</h5>
<h3> ${sessionScope.message} </h3>
<h4> add product by name: </h4>

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

<h4> add product by code: </h4>
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

<h4>delete product by name:</h4>
enter amount of product, that had been added in check before<br>
<form action="/cashier/deleteProductFromProductList" method="post">
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
enter amount of product, that had been added in check before<br>
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
<br>
<form action="/cashier/createReceipt" method="post">
    <button>save receipt</button>
</form>


<br>
<form action="/ServletBack" target="_blank">
    <button>back to start ${sessionScope.user.role} page</button>
</form>



</body>
</html>
