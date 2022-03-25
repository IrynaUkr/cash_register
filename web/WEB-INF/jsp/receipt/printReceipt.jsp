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
    <title>print Receipt</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <br>
    <h3>  ${sessionScope.user.surname} <fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
    <br><br>
    <form action="${pageContext.request.contextPath}/cashier/printReceipt" method="post">
        <select name="number">
            <c:forEach items="${receipts}" var="receipt">
                <option value="${receipt.number}">${receipt.number}</option>
            </c:forEach>
        </select>
        <input type="submit" class="btn btn-warning border-radius: 10px;"
               value="<fmt:message key="label.print_receipt" />">
    </form>
    <br> <br>
    <form action="${pageContext.request.contextPath}/cashier/servletSaveToWordDocReceipt" method="post">
        <select name="number">
            <c:forEach items="${receipts}" var="receipt">
                <option value="${receipt.number}">${receipt.number}</option>
            </c:forEach>
        </select>
        <input type="submit" class="btn btn-warning border-radius: 10px;"
               value="<fmt:message key="label.save_receipt_to_word"/>">
    </form>
    <br>
    <form action="${pageContext.request.contextPath}/ServletBack" target="_blank">
        <button><fmt:message key="label.back_to_start"/> ${sessionScope.user.role} </button>
    </form>

</div>
</body>
</html>
