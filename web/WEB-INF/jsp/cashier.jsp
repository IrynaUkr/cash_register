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
    <title>Cashier</title>
</head>
<body>
<h3>  ${sessionScope.user.surname} , <fmt:message key="label.welcome" /> ${sessionScope.user.role} </h3>
</h3>
<h2>${sessionScope.message}</h2><br>

<ul> Касир має можливість:
    <li>- створити замовлення (чек);</li>
    <li>- додати обрані товари по їх коду або по назві товару у замовлення;</li>
    <li>- вказати / змінити кількість певного товару або вагу;</li>
    <li>- закрити замовлення (чек).</li>
</ul>

<ul>Создание счета
    <li>1. открыть чек</li>
    <li>2. редактировать наполнение(добавлять/удалять товары) до схранения чека </li>
    <li>3. сохранить чек: реализована транзакция  в БД будут записаны  <br>
        -чек(табл чеки)<br>
        -список продуктов и их количество(табл чек-продукт)<br>
        -изменится остаток на складе в зависимости от типа операции(возврат или продажа)</li>
</ul>

<br>
<form action="/cashier/openReceipt" target="_blank">
    <button><fmt:message key="label.open_receipt" /></button>
</form>
<br>

<form action="/cashier/printReceipt" target="_blank">
    <button><fmt:message key="label.print_receipt" /></button>
</form>
_________________________________________________________________
<ul> Реализована транзакция:
    <li>1. для присутствующих в чеке товаров можно установить новое значение "amount"</li>
    <li>2. изменится остаток на складе в зависимости от типа операции(возврат или продажа)</li>
</ul>
Изменению подлежат только вновь созданные счета со статусом "CREATED"<br>
если счет закрыт- оплата прията от покупателя, чек выдан. <br>
Если удален старшим кассиром-то были на то основания, изменять нельзя.<br>
<fmt:message key="label.update_receipt_m" /><br>
<form action="/cashier/updateAmountReceipt" target="_blank">
    <button><fmt:message key="label.update_receipt" /></button>
</form>

<br>
<fmt:message key="label.close_receipt_m" />
<form action="/cashier/closeReceipt" target="_blank">
    <button><fmt:message key="label.close_receipt" /></button>
</form>


<form action="/logout" target="_blank" method="post">
    <button><fmt:message key="label.logout" /></button>
</form>

</body>
</html>
