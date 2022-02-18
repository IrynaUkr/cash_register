<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Print</title>
</head>
<body>
<h3> Hello, ${sessionScope.user.surname} !</h3>
you are logged in as ${sessionScope.user.role}<br>
<br>
<h4> the check number is: ${sessionScope.receipt.number} </h4>

${sessionScope.receipt.date} -date<br>
${sessionScope.receipt.status}- status<br>
${sessionScope.receipt.operationType} -operationType<br>
<c:forEach var="bean" items="${sessionScope.receipt.receiptProducts}">
    ......................................<br>
    Code.....  ${bean.code};<br>
    Name..... ${bean.name};<br>
    Price....  ${bean.price};<br>
    Amount...  ${bean.amount};${bean.uom};<br>
    Sum .... ${bean.amount*bean.price};<br>
    ......................................<br>
    <br>
</c:forEach>
<h4>${sessionScope.receipt.amount}  amount of Receipt</h4>
<h4>${sessionScope.receipt.sum}  sum of Receipt </h4>
${sessionScope.user.surname} author <br>

<form action="/logout" target="_blank" method="post">
    <button>logout</button>
</form>
<form action="/ServletBack" target="_blank">
    <button>back to start ${sessionScope.user.role} page</button>
</form>
</body>
</html>
