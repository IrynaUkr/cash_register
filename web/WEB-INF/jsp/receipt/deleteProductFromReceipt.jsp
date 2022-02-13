<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>delete product from receipt</title>
</head>
<body>
<h3> Hello, ${sessionScope.user.surname} !</h3>
${sessionScope.user.role}<br>
choose Receipt number<br>

<form action="/delProdFromReceipt" method="post">
    <select name="number">
        <c:forEach items="${receipts}" var="receipt">
            <option value="${receipt.number}">${receipt.number}</option>
        </c:forEach>
    </select>
    <br>
    select product by name: <br>
    <ul>
        <li>
            <select name="products1">
                <c:forEach items="${products}" var="pName1">
                    <option value="${pName1.name}">${pName1.name}</option>
                </c:forEach>
            </select>
        </li>
        <li>
            <select name="products2">
                <c:forEach items="${products}" var="pName2">
                    <option value="${pName2.name}">${pName2.name}</option>
                </c:forEach>
            </select>
        </li>
        <br>
    </ul>
    select product by code: <br>
    <ul>
        <li>
            <select name="products3">
                <c:forEach items="${products}" var="pCode1">
                    <option value="${pCode1.code}">${pCode1.code}</option>
                </c:forEach>
            </select>
        </li>
        <li>
            <select name="products4">
                <c:forEach items="${products}" var="pCode2">
                    <option value="${pCode2.code}">${pCode2.code}</option>
                </c:forEach>
            </select>
            <br>
        </li>
    </ul>

    <input type="submit" value="Submit">
</form>

<form action="/ServletBack" target="_blank">
    <button>back to start ${sessionScope.user.role} page</button>
</form>


</body>
</html>
