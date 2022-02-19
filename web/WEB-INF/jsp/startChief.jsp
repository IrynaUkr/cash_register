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
    <title>Chief-Cashier  start page</title>
</head>
<body>
<h3> Surname  ${sessionScope.user.surname} </h3>
you are logged in  ${sessionScope.user.role}.<br>
<h2>${sessionScope.message}</h2><br>
<h4>Старший касир має змогу:</h4>
<ul>
    <li>- відмінити чек;  </li>
    <li>- відмінити товар в чеку;</li>
    <li>- зробити  X і Z звіти.</li>
</ul>

<form action="/chief/canselReceipt" target="_blank">
    <button><fmt:message key="label.delete_rec" /></button>
</form>
<br>
<form action="/chief/delProdFromReceipt" target="_blank">
    <button><fmt:message key="label.delete_rec_p" /></button>
</form>
<br>
<form action="/chief/servletPayment" target="_blank" >
    <button><fmt:message key="label.create_payment" /></button>
</form>
X-отчет- это отчет на кассовом аппарате, показывающий сколько наличности было пробито на кассе за текущую смену.<br>
X-отчет можно делать в любой момент и в любом количестве, показания с X - отчета нигде не фиксируются.<br>

<form action="/chief/servletXReport"   method="post">
    date <input type="date" name="date">
    <input type="submit" value=<fmt:message key="label.create_X" />>
</form>
Z-отчет – это итоговый кассовый чек, который обязательно должен быть напечатан в течение 24 часов.<br>
Он означает завершение кассовой смены, сдачу выручки и обнуление оперативной памяти кассового аппарата.
Все данные с предыдущего момента гашения остаются в фискальной памяти кассы и на ленте ЭКЛЗ.<br>
По этому документу можно отследить:
данные счетчиков денег на начало и конец смены;
общую сумму выручки до момента гашения;
сумму возвратов денег, если они имели место;
сумму по аннулированным чекам.
Особенности Z-отчета
Каждый отчет с гашением имеет свой порядковый номер, поэтому в журнале кассира не должно быть пропусков.
Повторно снять его или отменить нельзя, так как он фиксируется в фискальной памяти аппарата и на контрольной ленте.
Если операции по кассе не производились в течение 24 часов, можно снять нулевой Z-отчет.
В течение смены или рабочего дня можно снимать любое количество Z-отчетов,
каждый должен быть оформлен отдельно и подшит к справке операциониста.
После распечатки Z-отчета второй раз его снять невозможно, пока не будет проведена любая операция по кассе.
<form action="/chief/servletZReport"  method="post">
    date <input type="date" name="date">
    <input type="submit" value=<fmt:message key="label.create_Z" />>
</form>
<br>
<form action="/logout" target="_blank" method="post">
    <button><fmt:message key="label.logout" /></button>
</form>
</body>
</html>
