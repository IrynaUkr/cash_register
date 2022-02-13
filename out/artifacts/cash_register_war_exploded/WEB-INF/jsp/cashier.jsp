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

<ul> Реализована транзакция:
    <li>1. cоздан счет</li>
    <li>2. добавлены товары</li>
    <li>3. изменился остаток на складе в зависимости от типа операции(возврат или продажа)</li>
</ul>
<ul> Реализовать:
    <li>1. суммирование товаров в чеке с одним ИД</li>
    <li>2. динамическое формирование длины списка товаров и передача в массива</li>
</ul>
<form action="/createReceipt" target="_blank">
    <button>create Receipt</button>
</form>
<br>
_________________________________________________________________
<ul> Реализована транзакция:
    <li>1. для выбранных товаров можно установить новое значение "amount"</li>
    <li>2. изменится остаток на складе в зависимости от типа операции(возврат или продажа)</li>
</ul>
Изменению подлежат только вновь созданные счета со статусом "CREATED"<br>
если счет закрыт- оплата прията от покупателя, чек выдан. <br>
Если удален старшим кассиром-то были на то основания, изменять нельзя.<br>
you can update receipt only if status is "CREATED"<br>
<form action="/updateAmountReceipt" target="_blank">
    <button>update Receipt</button>
</form>

<br>
Статус "CREATED" можно изменить только на "CLOSED"
<form action="/closeReceipt" target="_blank">
    <button>close Receipt</button>
</form>

</body>
</html>
