<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>REPORT</h1>
<form action="/canselReceipt" target="_blank">
    <button>ku-ku</button>
    <table id="list_order_table">

        <c:forEach var="bean" items="${receipts}">

            <tr>
                <td>${bean.id}</td>
                <td>${bean.number} </td>
                <td>${bean.date}</td>
                <td>${bean.status}</td>

            </tr>

        </c:forEach>
    </table>
</form>
</body>
</html>
