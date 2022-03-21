<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css"
      integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g=="
      crossorigin="anonymous" referrerpolicy="no-referrer">

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="languages"/>

<html lang="${sessionScope.lang}">

<html>
<head>
    <title>Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<header>
    <h3>  ${sessionScope.user.surname} ,<fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
    <h2>${sessionScope.message}</h2><br>
</header>
<div class="container">
    Create new user:

    <form action="/admin/users" method="post">
        <input type="text" name="login"> <fmt:message key="label.login"/><br><br>
        <input type="password" name="password"> <fmt:message key="label.password"/><br> <br>
        <input type="text" name="surname"> <fmt:message key="label.surname"/><br> <br>

        <select name="role">
            <option value="ADMIN">ADMIN</option>
            <option value="CASHIER">CASHIER</option>
            <option value="MERCHANDISER">MERCHANDISER</option>
            <option value="CHIEF_CASHIER">CHIEF_CASHIER</option>
        </select> role <br> <br>
        <input type="text" name="address"> address <br> <br>
        <input type="tel" name="phone"> phone number <br> <br>
        <input type="email" name="email"> e.mail <br> <br>
        <input type="submit" class="btn-warning" value="<fmt:message key="label.send"/>">
    </form>
    <form action="/logout" target="_blank" method="post">
        <button><fmt:message key="label.logout"/></button>
    </form>
</div>
</body>
</html>
