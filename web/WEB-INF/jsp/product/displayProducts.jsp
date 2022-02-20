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
<h2>choose kind of sorting</h2>
<form action="/merch/ServletProductPages">
    <table>
        <td><input type="radio" id="1" name="ProductCode" value="ProductCode"><label for="1">Code</label></td>
        <td><input type="radio" id="2" name="ProductName" value="ProductName"><label for="2">Name</label></td>
        <td><input type="radio" id="3" name="ProductPrice" value="ProductPrice"><label for="3">Price</label></td></td>
        <td><input type="radio" id="4" name="ProductAmount" value="ProductAmount"><label for="4">Amount</label></td></td>
        <td><input type="radio" id="5" name="ProductUOM" value="UOM"><label for="5">Units of measure</label></td></td>
    </table>
    <button type="submit">Submit</button>
</form>


<form action="/merch/ServletProductPages" method="post">
    <table>
        <thread>
            <tr>
                <td> N</td>
                <td>Code</td>
                <td>Name</td>
                <td>Price</td>
                <td>Amount</td>
                <td>Units of measure></td>
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
    <td><input type="submit" value="Delete"></td>

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
