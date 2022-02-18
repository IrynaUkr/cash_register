<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cashier</title>
</head>
<body>
<h3> Hello, ${sessionScope.user.surname} </h3>
you are logged in as an  ${sessionScope.user.role}.<br>
${message}

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
    <button>open receipt</button>
</form>
<br>

<form action="/cashier/printReceipt" target="_blank">
    <button>print Receipt</button>
</form>
_________________________________________________________________
<ul> Реализована транзакция:
    <li>1. для присутствующих в чеке товаров можно установить новое значение "amount"</li>
    <li>2. изменится остаток на складе в зависимости от типа операции(возврат или продажа)</li>
</ul>
Изменению подлежат только вновь созданные счета со статусом "CREATED"<br>
если счет закрыт- оплата прията от покупателя, чек выдан. <br>
Если удален старшим кассиром-то были на то основания, изменять нельзя.<br>
you can update receipt only if status is "CREATED"<br>
<form action="/cashier/updateAmountReceipt" target="_blank">
    <button>update Receipt</button>
</form>

<br>
Статус "CREATED" можно изменить только на "CLOSED"
<form action="/cashier/closeReceipt" target="_blank">
    <button>close Receipt</button>
</form>


<form action="/logout" target="_blank" method="post">
    <button>logout</button>
</form>
</body>
</html>
