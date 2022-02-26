<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css"
      integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g=="
      crossorigin="anonymous" referrerpolicy="no-referrer">
<!-- jQuery (Cloudflare CDN) -->
<script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"
        integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<!-- Bootstrap Bundle JS (Cloudflare CDN) -->
<script defer src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/js/bootstrap.min.js"
        integrity="sha512-UR25UO94eTnCVwjbXozyeVd6ZqpaAE9naiEUBK/A+QDbfSTQFhPGj5lOR6d8tsgbBk84Ggb5A3EkjsOgPRPcKA=="
        crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="languages"/>

<html lang="${sessionScope.lang}">
<html>
<head>
    <title>createProduct</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<header>
    <h3>  ${sessionScope.user.surname} ,<fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
    <h2>${sessionScope.message}</h2><br>
</header>
<div class="container">
    <h2><fmt:message key="label.create_product"/></h2><br>
    <form action="/merch/createProduct" class="was-validated" method="post">
        <input type="text" name="code"> <fmt:message key="label.code"/><br><br>
        <input type="text" name="price">
        <fmt:message key="label.price"/> <br> <br>
        <input type="text" name="amount">
        <fmt:message key="label.amount"/> <br> <br>
        <select name="uom"> <fmt:message key="label.UOM"/>
            <option value="unit"><fmt:message key="label.kg"/></option>
            <option value="kg"><fmt:message key="label.unit"/></option>
        </select>
        <fmt:message key="label.amount"/>
        <br> <br>
        <input type="text" name="nameEn">
        <fmt:message key="label.nameEn"/>
        <input type="text" name="descriptionEn">
        <fmt:message key="label.desEn"/>
        <br><br>
        <input type="text" name="nameUa">
        <fmt:message key="label.nameUa"/>
        <input type="text" name="descriptionUa">
        <fmt:message key="label.desUa"/>
        <br><br>
        <input type="text" name="nameRu"><fmt:message key="label.nameRu"/>
        <input type="text" name="descriptionRu">
        <fmt:message key="label.desRu"/>
        <br><br>
         <input class="btn btn-warning" type="submit" value="<fmt:message key="label.send"/>">
    </form>
    <form action="/ServletBack" target="_blank">
        <button><fmt:message key="label.back_to_start"/>${sessionScope.user.role} </button>
    </form>
</div>

</body>
</html>
