<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<html>
<head>
    <title>Update Amount Receipt</title>
</head>
<body>
<h3> Hello, ${sessionScope.user.surname} !</h3>
${sessionScope.user.role}

має можливість:
- вказати / змінити кількість певного товару або вагу;<br>
choose Receipt number<br>

<form action="/cashier/updateAmountReceipt" method="post">
    <select name="number">
        <c:forEach items="${receipts}" var="receipt">
            <option value="${receipt.number}">${receipt.number}</option>
        </c:forEach>
    </select>
    <input type="submit" value="Submit">
</form>


<form action="/ServletBack" target="_blank">
    <button>back to start ${sessionScope.user.role} page</button>
</form>


</body>
</html>
