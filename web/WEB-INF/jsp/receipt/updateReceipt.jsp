<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Receipt</title>
</head>
<body>
<h3> Hello, ${sessionScope.user.surname} !</h3>
you are logged in as ${sessionScope.user.role}<br>
<br>
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
</body>
<h4> add product by name: </h4>

<form action="/cashier/servletSaveUpdateReceipt" method="post">
    <select name="productNA">
        <c:forEach items="${products}" var="productName">
            <option value="${productName.name}">${productName.name}</option>
            <option value="${productName.amount}">${productName.amount}</option>
            <option value="---------">---------------</option>
        </c:forEach>
    </select>
    <input type="text" name="amountNA" placeholder="new amount"> amount
    <input type="submit" value="set new amount">
</form>

<h4> add product by code: </h4>
<form action="/cashier/servletSaveUpdateReceipt" method="post">
    <select name="productCA">
        <c:forEach items="${products}" var="productCode">
            <option value="${productCode.code}">${productCode.code}</option>
            <option value="${productCode.amount}">${productCode.amount}</option>
            <option value="---------">---------------</option>
        </c:forEach>
    </select>
    <input type="text" name="amountCA" placeholder=" new amount"> amount

    <input type="submit" value="set new amount">
</form>


<form action="/cashier/createReceipt" method="post">
    <button>UPDATE receipt</button>
</form>


</html>
