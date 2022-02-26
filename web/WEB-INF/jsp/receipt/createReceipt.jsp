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
    <title>add Product</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h3>  ${sessionScope.user.surname} <fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
    <h2>${sessionScope.message}</h2><br>
    <fmt:message key="label.open_receipt"/>:
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
        <fmt:message key="label.sum"/> - ${bean.amount*bean.price};
        <br>
    </c:forEach>

    <fmt:message key="label.total_amount"/> .... ${sessionScope.receipt.amount} ;<br>
    <fmt:message key="label.sum"/>.... ${sessionScope.receipt.sum} -;<br>

    <hr>

    <fmt:message key="label.add_product_byName"/>:<br>

    <form action="${pageContext.request.contextPath}/cashier/addProductToReceiptList" method="post">
        <select name="productNA">
            <c:forEach items="${products}" var="productName">
                <option value="${productName.name}">${productName.name}</option>
                <option value="${productName.amount}">${productName.amount}</option>
                <option value="---------">---------------</option>
            </c:forEach>
        </select>
        <input type="text" name="amountNA" placeholder="0">
        <input type="submit" class="btn btn-warning  border-radius: 5px;"
               value="<fmt:message key="label.add_product_byName"/>">
    </form>

    <fmt:message key="label.add_product_byCode"/><br>
    <form action="${pageContext.request.contextPath}/cashier/addProductToReceiptList" method="post">
        <select name="productCA">
            <c:forEach items="${products}" var="productCode">
                <option value="${productCode.code}">${productCode.code}</option>
                <option value="${productCode.amount}">${productCode.amount}</option>
                <option value="---------">---------------</option>
            </c:forEach>
        </select>
        <input type="text" name="amountCA" placeholder="0">
        <input type="submit" class="btn btn-warning border-radius: 5px"
               value="<fmt:message key="label.add_product_byCode"/>">
    </form>
<hr>
    <fmt:message key="label.del_product"/>:<br>
    <form action="${pageContext.request.contextPath}/cashier/deleteProductFromProductList" method="post">
        <select name="productND">
            <c:forEach items="${products}" var="productName">
                <option value="${productName.name}">${productName.name}</option>
                <option value="${productName.amount}">${productName.amount}</option>
                <option value="---------">---------------</option>
            </c:forEach>
        </select>
        <input type="text" name="amountND" >
        <input type="submit" class="btn btn-warning border-radius: 5px" value="<fmt:message key="label.del_product"/>">
    </form>

    <fmt:message key="label.del_product"/><br>
    <form action="${pageContext.request.contextPath}/cashier/deleteProductFromProductList" method="post">
        <select name="productCD">
            <c:forEach items="${products}" var="productCode">
                <option value="${productCode.code}">${productCode.code}</option>
                <option value="${productCode.amount}">${productCode.amount}</option>
                <option value="---------">---------------</option>
            </c:forEach>
        </select>
        <input type="text" name="amountCD">
        <input type="submit" class="btn btn-warning border-radius: 5px" value="<fmt:message key="label.del_product"/>">
    </form>
    <br>
    <form action="${pageContext.request.contextPath}/cashier/createReceipt" method="post">
        <button><fmt:message key="label.save_receipt"/></button>
    </form>
    <br>
    <form action="${pageContext.request.contextPath}/ServletBack" target="_blank">
        <button><fmt:message key="label.back_to_start"/> ${sessionScope.user.role} </button>
    </form>
</div>

</body>
</html>
