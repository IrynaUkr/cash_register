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
    <title>open Receipt</title>
    <h3>  ${sessionScope.user.surname}  <fmt:message key="label.welcome" /> ${sessionScope.user.role} </h3>
    </h3>
    <h2>${sessionScope.message}</h2><br>

</head>
<body>
<form action="/cashier/openReceipt" method="post">
    enter number of check: <input type="text" name="number"><br>
    select status of check:
    <select name="status">
        <option value="CREATED" selected>CREATED</option>
        <option value="CLOSED"> CLOSED</option>
    </select> <br><br>
    select type of check:
    <select name="type">
        <option value="SALE" selected>SALE</option>
        <option value="RETURN"> RETURN</option>
    </select> <br> <br>
    <input type="submit" value="Submit">

</form>

<form action="/ServletBack" target="_blank">
    <button>back to start ${sessionScope.user.role} page</button>
</form>
</body>
</html>
