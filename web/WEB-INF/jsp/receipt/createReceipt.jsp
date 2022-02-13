<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<html>
<head>
    <title>create Receipt</title>
</head>
<body>
<h3> Hello, ${sessionScope.user.surname} !</h3>
${sessionScope.user.role}

<h4>Create new Receipt:</h4> <br>
<form action="/createReceipt" method="post">
    <input type="text" name="number">number<br>
    select status of document:
    <select name="status">
        <option value="CREATED" selected>CREATED</option>
        <option value="FINISHED"> FINISHED</option>
    </select> <br><br>
    select type of document:
    <select name="type">
        <option value="SALE" selected>SALE</option>
        <option value="RETURN"> RETURN</option>
    </select> <br> <br>
    select product by name: <br><br>
    <ul>
        <li>
            <select name="products1">
                <c:forEach items="${products}" var="pName1">
                    <option value="${pName1.name}">${pName1.name}</option>
                    <option value="${pName1.amount}">${pName1.amount}</option>
                    <option value="---------">---------------</option>
                </c:forEach>
            </select>
            <input type="text" name="amount1" placeholder="amount"> amount
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
            <input type="text" name="amount2" placeholder="amount"> amount
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
            <input type="text" name="amount3" placeholder="amount"> amount
        </li>
        <li>
            <select name="products4">
                <c:forEach items="${products}" var="pCode2">
                    <option value="${pCode2.code}">${pCode2.code}</option>
                    <option value="${pCode2.amount}">${pCode2.amount}</option>
                    <option value="---------">---------------</option>
                </c:forEach>
            </select>
            <input type="text" name="amount4" placeholder="amount"> amount
            <br>
        </li>

        <br>
        <input type="submit" value="Submit">
    </ul>
</form>

<form action="/ServletBack" target="_blank">
    <button>back to start ${sessionScope.user.role} page</button>
</form>


</body>
</html>
