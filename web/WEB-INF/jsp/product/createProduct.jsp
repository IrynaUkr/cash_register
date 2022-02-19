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
    <title>createProduct</title>
</head>
<body>
<h3>  ${sessionScope.user.surname}  <fmt:message key="label.welcome" /> ${sessionScope.user.role} </h3>
</h3>
<h2>${sessionScope.message}</h2><br>
Create new Product:<br>

<form action="/merch/createProduct" method="post">
    <input type="text" name="code"> code<br><br>
    <input type="text" name="name"> name<br> <br>
    <input type="text" name="description"> description<br> <br>
    <input type="text" name="price"> price <br> <br>
    <input type="text" name="amount"> amount <br> <br>
    <select name="uom">
        <option value="unit">unit</option>
        <option value="kg">kg</option>
    </select> units of measure  <br> <br>
    <input type="submit" value="Submit">
</form>

<form action="/ServletBack" target="_blank">
    <button>back to start ${sessionScope.user.role} page</button>
</form>


</body>
</html>
