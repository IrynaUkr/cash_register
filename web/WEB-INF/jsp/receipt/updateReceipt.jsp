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
    <title>Update Receipt</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h3>  ${sessionScope.user.surname} <fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
    <h2>${sessionScope.message}</h2><br>
    <br>
    <h4><fmt:message key="label.update_receipt"/></h4>
    N ${sessionScope.receipt.number} <br>
    ${sessionScope.receipt.date} <br>
    ${sessionScope.receipt.status}<br>
    ${sessionScope.receipt.operationType}<br>

    <c:forEach var="bean" items="${sessionScope.receipt.receiptProducts}">
        <fmt:message key="label.code"/>- ${bean.code};
        <fmt:message key="label.name"/>- ${bean.name};
        <fmt:message key="label.price"/>- ${bean.price};
        <fmt:message key="label.amount"/>- ${bean.amount};
        total - ${bean.amount*bean.price};
        <br>
    </c:forEach>

    <h4>${sessionScope.receipt.amount} -<fmt:message key="label.total_amount"/><br>
        ${sessionScope.receipt.sum} -<fmt:message key="label.sum"/> <br>
        ${sessionScope.user.surname} </h4>
    <h3> ${sessionScope.message} </h3>

    <h4><fmt:message key="label.add_product_byName"/></h4>

    <form action="/cashier/servletSaveUpdateReceipt" method="post">
        <select name="productNA">
            <c:forEach items="${products}" var="productName">
                <option value="${productName.name}">${productName.name}</option>
                <option value="${productName.amount}">${productName.amount}</option>
                <option value="---------">---------------</option>
            </c:forEach>
        </select>
        <input type="text"  name="amountNA" placeholder="0"> <fmt:message key="label.amount"/>
        <input type="submit" class="btn-warning" value="<fmt:message key="label.send"/>">
    </form>

    <h4><fmt:message key="label.add_product_byCode"/></h4>
    <form action="/cashier/servletSaveUpdateReceipt" method="post">
        <select name="productCA">
            <c:forEach items="${products}" var="productCode">
                <option value="${productCode.code}">${productCode.code}</option>
                <option value="${productCode.amount}">${productCode.amount}</option>
                <option value="---------">---------------</option>
            </c:forEach>
        </select>
        <input type="text" name="amountCA" placeholder="0"> <fmt:message key="label.amount"/>
        <input type="submit" class="btn-warning" value="<fmt:message key="label.send"/>">
    </form>


    <form action="/cashier/createReceipt" method="post">
        <button><fmt:message key="label.update_receipt"/></button>
    </form>
</div>
</body>
</html>
