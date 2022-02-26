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
    <title>Chief-Cashier start page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<header>
    <h3>  ${sessionScope.user.surname} <fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
    <h2>${sessionScope.message}</h2><br>
</header>

<div class="container">
    <form action="/chief/canselReceipt" target="_blank">
        <button><fmt:message key="label.delete_rec"/></button>
    </form>
    <br>
    <form action="/chief/delProdFromReceipt" target="_blank">
        <button><fmt:message key="label.delete_rec_p"/></button>
    </form>
    <br>
    <form action="/chief/servletPayment" target="_blank">
        <button><fmt:message key="label.create_payment"/></button>
    </form>
    X-отчет- это отчет на кассовом аппарате, показывающий сколько наличности было пробито на кассе за текущую смену.<br>
    X-отчет можно делать в любой момент и в любом количестве, показания с X - отчета нигде не фиксируются.<br>

    <form action="/chief/servletXReport" method="post">
        date <input type="date" name="date">
        <input type="submit" class="btn-warning" value=<fmt:message key="label.create_X"/>>
    </form>
    Z-отчет – это итоговый кассовый чек, который обязательно должен быть напечатан в течение 24 часов.<br>
    Он означает завершение кассовой смены, сдачу выручки и обнуление оперативной памяти кассового аппарата.
    Все данные с предыдущего момента гашения остаются в фискальной памяти кассы и на ленте ЭКЛЗ.<br>
    По этому документу можно отследить:
    данные счетчиков денег на начало и конец смены;
    общую сумму выручки до момента гашения;
    сумму возвратов денег, если они имели место;

    Особенности Z-отчета
    Каждый отчет с гашением имеет свой порядковый номер, поэтому в журнале кассира не должно быть пропусков.
    Повторно снять его или отменить нельзя, так как он фиксируется в фискальной памяти аппарата и на контрольной ленте.
    Если операции по кассе не производились в течение 24 часов, можно снять нулевой Z-отчет.
    В течение смены или рабочего дня можно снимать любое количество Z-отчетов,
    каждый должен быть оформлен отдельно и подшит к справке операциониста.
    После распечатки Z-отчета второй раз его снять невозможно, пока не будет проведена любая операция по кассе.
    <form action="/chief/servletZReport" method="post">
        date <input type="date" name="date">
        <input type="submit" class="btn-warning" value=<fmt:message key="label.create_Z"/>>
    </form>
    <br>
    <form action="/logout" target="_blank" method="post">
        <button><fmt:message key="label.logout"/></button>
    </form>
</div>
</body>
</html>
