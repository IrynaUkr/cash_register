<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css"
      integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g=="
      crossorigin="anonymous" referrerpolicy="no-referrer">

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="languages"/>

<html lang="${sessionScope.lang}">

<html>
<head>
    <title>Update Receipt</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<header>
    <h3>  ${sessionScope.user.surname} , <fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
</header>
<div class="container">
    <h2>${sessionScope.message}</h2><br>
    <br>
    <fmt:message key="label.update_receipt"/>
    <div class="col-md-6">
        <table class="table table-striped table-bordered table-hover">
            <thread>
                <tr class="active">
                    <td>N</td>
                    <td><fmt:message key="label.status"/></td>
                    <td><fmt:message key="label.type"/></td>
                    <td><fmt:message key="label.surname"/></td>
                </tr>
            </thread>
            <tr>
                <td>${sessionScope.receipt.number} </td>
                <td>${sessionScope.receipt.status}</td>
                <td> ${sessionScope.receipt.operationType}</td>
                <td>${sessionScope.user.surname}</td>
            </tr>
        </table>
    </div>

    <c:forEach var="bean" items="${sessionScope.receipt.receiptProducts}">
        <fmt:message key="label.code"/>- ${bean.code};
        <fmt:message key="label.name"/>- ${bean.name};
        <fmt:message key="label.price"/>- ${bean.price};
        <fmt:message key="label.amount"/>- ${bean.amount};
        total - ${bean.amount*bean.price};
        <br>
    </c:forEach>

    <fmt:message key="label.total_amount"/>..........${sessionScope.receipt.amount};<br>
    <fmt:message key="label.sum"/>.........................${sessionScope.receipt.sum};<br>
    <hr>
    <fmt:message key="label.add_product_byName"/>

    <form action="${pageContext.request.contextPath}/cashier/servletSaveUpdateReceipt" method="post">
        <select name="productNA">
            <c:forEach items="${sessionScope.products}" var="productName">
                <option value="${productName.name}">${productName.name}</option>
            </c:forEach>
        </select>
        <input type="text" name="amountNA">
        <input type="submit" class="btn-warning" value="<fmt:message key="label.update_receipt"/>">
    </form>

    <fmt:message key="label.add_product_byCode"/>
    <form action="${pageContext.request.contextPath}/cashier/servletSaveUpdateReceipt" method="post">
        <select name="productCA">
            <c:forEach items="${sessionScope.products}" var="productCode">
                <option value="${productCode.code}">${productCode.code}</option>
            </c:forEach>
        </select>
        <input type="text" name="amountCA">
        <input type="submit" class="btn-warning" value="<fmt:message key="label.update_receipt"/>">
    </form>
</div>
</body>
</html>
