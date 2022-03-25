<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="application/msword; charset=UTF-8" %>
<%@ page session="true" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="languages"/>

<html lang="${sessionScope.lang}">
    <head>
        <title>Print</title>

    </head>
    <body>
        <div class="container">
            <h4>
                <fmt:message key="label.number"/>
                    ${sessionScope.receipt.number} </h4>
            <h5>
                <fmt:message key="label.date"/>
                    ${sessionScope.receipt.date} </h5>
            <h5>
                <fmt:message key="label.status"/>
                    ${sessionScope.receipt.status}</h5>

            <h5>
                <fmt:message key="label.type"/>
                    ${sessionScope.receipt.operationType} </h5>

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
            <h4>
                <fmt:message key="label.total_amount"/>
                    ${sessionScope.receipt.amount}</h4>
            <h4>
                <fmt:message key="label.sum"/>
                    ${sessionScope.receipt.sum}</h4>
            <h4>
                <fmt:message key="label.cashier"/>
                    ${sessionScope.user.surname}</h4>
        </div>
    </body>
</html>

