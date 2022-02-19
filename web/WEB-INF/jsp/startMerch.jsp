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
    <title>merch</title>
</head>
<body>
<h3>  ${sessionScope.user.surname}  <fmt:message key="label.welcome" /> ${sessionScope.user.role} </h3>
</h3>
<h2>${sessionScope.message}</h2><br>

Товарознавець може створювати товари
та зазначати їх кількість на складі.<br>login



<form action="/merch/createProduct" target="_blank">
    <button> <fmt:message key="label.create_product" /> </button>
</form>
<br>

<br>

<form action="/merch/setAmountProduct" target="_blank">
    <button> <fmt:message key="label.add_product" /></button>
</form>
<br>
//to do
<form action="/merch/delete/Product" target="_blank">
    <button> <fmt:message key="label.del_product" /></button>
</form>
<br>
<form action="/logout" target="_blank" method="post">
    <button><fmt:message key="label.logout" /></button>
</form>

</body>
</html>
