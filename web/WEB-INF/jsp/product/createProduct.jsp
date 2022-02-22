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
<h3><fmt:message key="label.create_product"/></h3><br>

<form action="/merch/createProduct" method="post">
    <input type="text" name="code">  <fmt:message key="label.code"/><br><br>
    <input type="text" name="price">  <fmt:message key="label.price"/> <br> <br>
    <input type="text" name="amount">  <fmt:message key="label.amount"/> <br> <br>
    <select name="uom"> <fmt:message key="label.UOM"/>
        <option value="unit"><fmt:message key="label.kg"/></option>
        <option value="kg"><fmt:message key="label.unit"/></option>
    </select>  <fmt:message key="label.amount"/>  <br> <br>
    <input type="text" name="nameEn"> <fmt:message key="label.nameEn"/><br> <br>
    <input type="text" name="descriptionEn"> <fmt:message key="label.desEn"/><br> <br>
    <input type="text" name="nameUa"> <fmt:message key="label.nameUa"/><br> <br>
    <input type="text" name="descriptionUa"> <fmt:message key="label.desUa"/><br> <br>
    <input type="text" name="nameRu"><fmt:message key="label.nameRu"/><br> <br>
    <input type="text" name="descriptionRu"> <fmt:message key="label.desRu"/><br> <br>
    <input type="submit" value="Submit">
</form>

<form action="/ServletBack" target="_blank">
    <button>back to start ${sessionScope.user.role} page</button>
</form>


</body>
</html>
