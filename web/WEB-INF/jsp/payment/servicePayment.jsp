<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>payment</title>
</head>
<body>
<h3> Hello, ${sessionScope.user.surname} !</h3>
you are logged in as ${sessionScope.user.role}<br>
SERVICE_CASH_INFLOW -принято в кассу для служебных нужд- сдача покупателю<br><br>
SERVICE_CASH_OUTFLOW-выдано из кассы на служебные нужды- выдача дневной выручки<br><br>
<form action="/chief/servletPayment" method="post">
    <select name="type">
        <option value="SERVICE_CASH_INFLOW" selected>CASH_INFLOW</option>
        <option value="SERVICE_CASH_OUTFLOW">CASH_OUTFLOW</option>
    </select><br><br>
    <input type="text" name="value" > payment
    <input type="text" name="description" > description
    <input type="submit" value="Submit">
</form>





</body>
</html>
