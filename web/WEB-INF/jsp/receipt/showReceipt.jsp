<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css"
      integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g=="
      crossorigin="anonymous" referrerpolicy="no-referrer">


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="languages"/>

<html lang="${sessionScope.lang}">

<html>
<head>
    <title>Print</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <article>
        <h4> N: ${sessionScope.receipt.number} </h4>

        <table class="table table-striped table-bordered table-hover">
            <thread>
                <td>
                    <fmt:message key="label.code"/>
                </td>
                <td><fmt:message key="label.name"/></td>
                <td><fmt:message key="label.price"/></td>
                <td><fmt:message key="label.amount"/></td>
                <td><fmt:message key="label.UOM"/></td>
                <td><fmt:message key="label.sum"/></td>
            </thread>
            <c:forEach var="bean" items="${sessionScope.receipt.receiptProducts}">
                <tr>
                    <td> ${bean.code} </td>
                    <td> ${bean.name} </td>
                    <td> ${bean.price} </td>
                    <td> ${bean.amount} </td>
                    <td> ${bean.uom} </td>
                    <td> ${bean.amount*bean.price} </td>
                </tr>
            </c:forEach>
        </table>
        <h4> <fmt:message key="label.total_amount"/> ${sessionScope.receipt.amount} </h4>
        <h4> <fmt:message key="label.sum"/> ${sessionScope.receipt.sum}</h4>
        <h4> <fmt:message key="label.cashier"/> ${sessionScope.user.surname}</h4>

    </article>

    <form action="${pageContext.request.contextPath}/logout" target="_blank" method="post">
        <button><fmt:message key="label.logout"/></button>
    </form>
    <form action="${pageContext.request.contextPath}/ServletBack" target="_blank">
        <button><fmt:message key="label.back_to_start"/> ${sessionScope.user.role} </button>
    </form>
</div>
</body>
</html>
</html>
