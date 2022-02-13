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
<form action="/updateAmountReceipt" method="post">
    <select name="number">
        <c:forEach items="${receipts}" var="receipt">
            <option value="${receipt.number}">${receipt.number}</option>
        </c:forEach>
    </select>

    select product by name: <br>
    <ul>
        <li>
            <select name="products1">
                <c:forEach items="${products}" var="pName1">
                    <option value="${pName1.name}">${pName1.name}</option>
                    <option value="${pName1.amount}">${pName1.amount}</option>
                    <option value="---------">---------------</option>
                </c:forEach>
            </select>
            <input type="text" name="amount1" placeholder="amount"> new amount
            <br>
        </li>
        <li>
            <select name="products2">
                <c:forEach items="${products}" var="pName2">
                    <option value="${pName2.name}">${pName2.name}</option>
                    <option value="${pName2.amount}">${pName2.amount}</option>
                    <option value="---------">---------------</option>
                </c:forEach>
            </select>
            <input type="text" name="amount2" placeholder="amount"> new amount
        </li>
        <br>
    </ul>
    select product by code: <br>
    <ul>
        <li>
            <select name="products3">
                <c:forEach items="${products}" var="pCode1">
                    <option value="${pCode1.code}">${pCode1.code}</option>
                    <option value="${pCode1.amount}">${pCode1.amount}</option>
                    <option value="---------">---------------</option>
                </c:forEach>
            </select>
            <input type="text" name="amount3" placeholder="amount">  new amount
        </li>
        <li>
            <select name="products4">
                <c:forEach items="${products}" var="pCode2">
                    <option value="${pCode2.code}">${pCode2.code}</option>
                    <option value="${pCode2.amount}">${pCode2.amount}</option>
                    <option value="---------">---------------</option>
                </c:forEach>
            </select>
            <input type="text" name="amount4" placeholder="amount">  new amount
            <br>
        </li>
    </ul>

        <input type="submit" value="Submit">
</form>


</body>
</html>
