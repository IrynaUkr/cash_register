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
  <title>Admin</title>
</head>
<body>
<h3> Hello, ${sessionScope.user.surname} </h3>

you are logged in as an administrator.<br>

<h2>${sessionScope.message}</h2><br>

Create new user:

<form action="/admin/users" method="post">
  <input type="text" name="login"> login<br><br>
  <input type="password" name="password"> password<br> <br>
  <input type="text" name="surname"> surname<br> <br>

  <select name="role">
    <option value="ADMIN">ADMIN</option>
    <option value="CASHIER">CASHIER</option>
    <option value="MERCHANDISER">MERCHANDISER</option>
    <option value="CHEIF_CASHIER">CHEIF_CASHIER</option>
  </select> role <br> <br>
  <input type="text" name="adress"> address <br> <br>
  <input type="tel" name="phone number"> phone number <br> <br>
  <input type="email" name="e.mail"> e.mail <br> <br>
  <input type="submit" value="Submit">
</form>
<form action="/logout" target="_blank" method="post">
  <button><fmt:message key="label.logout" /></button>
</form>

</body>
</html>
