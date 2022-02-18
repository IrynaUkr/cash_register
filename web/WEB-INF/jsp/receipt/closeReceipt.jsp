<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Status Receipt</title>
</head>
<body>
<h3> Hello, ${sessionScope.user.surname} !</h3>
${sessionScope.user.role}
<h4>Change Status Receipt:</h4> <br>
<br>choose Receipt number<br>
<form action="/cashier/closeReceipt" method="post">
    <select name="number">
        <c:forEach items="${receipts}" var="receipt">
            <option value="${receipt.number}">${receipt.number}</option>
        </c:forEach>
    </select>
    <input type="submit" value="CLOSE receipt">

    <form action="/ServletBack" target="_blank">
        <button>back to start ${sessionScope.user.role} page</button>
    </form>

</form>
</body>
</html>
