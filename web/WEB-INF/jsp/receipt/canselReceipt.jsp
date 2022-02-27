<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css"
      integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g=="
      crossorigin="anonymous" referrerpolicy="no-referrer">
<
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="languages"/>

<html lang="${sessionScope.lang}">


<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<header>
<h3>  ${sessionScope.user.surname} <fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
<h2>${sessionScope.message}</h2><br>
</header>
<div class="container">

    <br>N <br>
    <form action="${pageContext.request.contextPath}/chief/canselReceipt" method="post">
        <select name="number">
            <c:forEach items="${sessionScope.receipts}" var="receipt">
                <option value="${receipt.number}">${receipt.number}</option>
            </c:forEach>
        </select>
        <input type="submit" class="btn btn-warning" value="<fmt:message key="label.send"/>">
    </form>

    <form action="${pageContext.request.contextPath}/ServletBack" target="_blank">
        <button><fmt:message key="label.back_to_start"/> ${sessionScope.user.role} </button>
    </form>


    <form action="${pageContext.request.contextPath}/logout" target="_blank" method="post">
        <button><fmt:message key="label.logout"/></button>
    </form>
</div>
</body>
</html>
