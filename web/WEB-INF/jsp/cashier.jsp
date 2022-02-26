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
    <title>Cashier</title>
    <link rel="stylesheet" href="/style.css">
</head>
<body>
<header>
<h3>  ${sessionScope.user.surname} , <fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
</header>
<div class="container"></div>
<h2>${sessionScope.message}</h2><br>

<ul> Касир має можливість:
    <li>- створити замовлення (чек);</li>
    <li>- додати обрані товари по їх коду або по назві товару у замовлення;</li>
    <li>- вказати / змінити кількість певного товару або вагу;</li>
    <li>- закрити замовлення (чек).</li>
</ul>

<ul>Создание счета
    <li>1. открыть чек</li>
    <li>2. редактировать наполнение(добавлять/удалять товары) до схранения чека</li>
    <li>3. сохранить чек: реализована транзакция в БД будут записаны <br>
        -чек(табл чеки)<br>
        -список продуктов и их количество(табл чек-продукт)<br>
        -изменится остаток на складе в зависимости от типа операции(возврат или продажа)
    </li>
</ul>

<br>
<form action="/cashier/openReceipt" target="_blank">
    <button><fmt:message key="label.open_receipt"/></button>
</form>
<br>

<form action="/cashier/printReceipt" target="_blank">
    <button><fmt:message key="label.print_receipt"/></button>
</form>
_________________________________________________________________
<ul> Реализована транзакция:
    <li>1. для присутствующих в чеке товаров можно установить новое значение "amount"</li>
    <li>2. изменится остаток на складе в зависимости от типа операции(возврат или продажа)</li>
</ul>
Изменению подлежат только вновь созданные счета со статусом "CREATED"<br>
если счет закрыт- оплата прията от покупателя, чек выдан. <br>
Если удален старшим кассиром-то были на то основания, изменять нельзя.<br>
<fmt:message key="label.update_receipt_m"/><br>
<form action="/cashier/updateAmountReceipt" target="_blank">
    <button><fmt:message key="label.update_receipt"/></button>
</form>

<br>
<fmt:message key="label.close_receipt_m"/>
<form action="/cashier/closeReceipt" target="_blank">
    <button><fmt:message key="label.close_receipt"/></button>
</form>


<form action="/logout" target="_blank" method="post">
    <button><fmt:message key="label.logout"/></button>
</form>
</div>
</body>
</html>
