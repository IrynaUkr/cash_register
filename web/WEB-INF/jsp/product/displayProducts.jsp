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


<c:forEach var="bean" items="${sessionScope.products}">
    ......................................<br>
    Code.....  ${bean.code};<br>
    Name..... ${bean.name};<br>
    Price....  ${bean.price};<br>
    Amount...  ${bean.amount};${bean.uom};<br>
    Sum .... ${bean.amount*bean.price};<br>
    ......................................<br>
</c:forEach>
<br>


<c:forEach var="i" begin="1" end="${sessionScope.totalAmPages}">

    <form action="/merch/ServletProductPages">
    <input type="submit" name="page" value="${i}"><p>
    </form>

    </c:forEach>




</body>
</html>
