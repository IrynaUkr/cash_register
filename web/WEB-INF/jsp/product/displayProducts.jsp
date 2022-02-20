<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="languages"/>

<html lang="${sessionScope.lang}">

<html>
<head>
    <title>Products</title>

</head>
<body>

<h3>  ${sessionScope.user.surname} <fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
<h2>${sessionScope.message}</h2><br>


<form action="/merch/ServletProductPages">
    <table>
        <thread>
            <tr>
                <td> N </td>
                <td><input type="submit" name="ProductCode" value="ProductCode"></td>
                <td><input type="submit" name="ProductName" value="ProductName"></td>
                <td><input type="submit" name="ProductPrice" value="ProductPrice"></td>
                <td> <input type="submit" name="ProductAmount" value="ProductAmount"></td>
                <td> <input type="submit" name="ProductUOM" value="Units of measure"></td>
            </tr>
        </thread>
        <c:forEach var="bean" items="${sessionScope.products}">
            <tr>
                <td><input type="checkbox" name="selected" value="${bean.code}"></td>
                <td> ${bean.code}</td>
                <td> ${bean.name}</td>
                <td>${bean.price}</td>
                <td> ${bean.amount}</td>
                <td> ${bean.uom}</td>

            </tr>
        </c:forEach>
    </table>
</form>

<table>
    <tr>
        <c:forEach var="i" begin="1" end="${sessionScope.totalAmPages}">
            <form action="/merch/ServletProductPages">
                <td><input type="submit" name="page" value="${i}"></td>
            </form>
        </c:forEach>
    </tr>
</table>

</body>
</html>
