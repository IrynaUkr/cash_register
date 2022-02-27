<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.1/css/bootstrap.min.css"
      integrity="sha512-T584yQ/tdRR5QwOpfvDfVQUidzfgc2339Lc8uBDtcp/wYu80d7jwBgAxbyMh0a9YM9F8N3tdErpFI8iaGx6x5g=="
      crossorigin="anonymous" referrerpolicy="no-referrer">
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="languages"/>
<html>
<head>
    <title>Z-report</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<header>
    <h3>  ${sessionScope.user.surname} , <fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
    <h3>Z-REPORT</h3>
</header>
<div class="container">
    <div class="container">
        <table class="table table-striped table-bordered table-hover">
            <thread>
                <tr class="active">
                    <td><fmt:message key="label.number"/> </td>
                    <td><fmt:message key="label.date"/></td>
                    <td><fmt:message key="label.status"/></td>
                    <td><fmt:message key="label.type"/></td>
                    <td><fmt:message key="label.sum"/></td>
                </tr>
            </thread>
            <c:forEach var="bean" items="${sessionScope.returnList}">
                <tr>
                    <td>${bean.number}</td>
                    <td>${bean.date} </td>
                    <td>${bean.status}</td>
                    <td>${bean.operationType}</td>
                    <td>${bean.sum}</td>
                </tr>
            </c:forEach><br>
            return total ${sessionScope.returnSum}
        </table>
    </div>
    <div class="container">
        <table class="table table-striped table-bordered table-hover">
            <thread>
                <tr class="active">
                    <td><fmt:message key="label.number"/> </td>
                    <td><fmt:message key="label.date"/></td>
                    <td><fmt:message key="label.status"/></td>
                    <td><fmt:message key="label.type"/></td>
                    <td><fmt:message key="label.sum"/></td>
                </tr>
            </thread>
            <c:forEach var="bean" items="${sessionScope.saleList}">
                <tr>
                    <td>${bean.number}</td>
                    <td>${bean.date} </td>
                    <td>${bean.status}</td>
                    <td>${bean.operationType}</td>
                    <td>${bean.sum}</td>
                </tr>
            </c:forEach><br>
            sales total ${sessionScope.saleSum}
        </table>
    </div>
    <div class="container">
        <table class="table table-striped table-bordered table-hover">
            <thread>
                <tr class="active">
                    <td><fmt:message key="label.number"/> </td>
                    <td><fmt:message key="label.date"/></td>
                    <td><fmt:message key="label.type"/></td>
                    <td><fmt:message key="label.sum"/></td>
                </tr>
            </thread>
            <c:forEach var="bean" items="${sessionScope.payOut}">
                <tr>
                    <td>${bean.id}</td>
                    <td>${bean.date} </td>
                    <td>${bean.type}</td>
                    <td>${bean.value}</td>
                </tr>
            </c:forEach><br>
            CASH_OUTFLOW- total ${sessionScope.payOutSum}
        </table>
    </div>
    <div class="container">
        <table class="table table-striped table-bordered table-hover">
            <thread>
                <tr class="active">
                    <td><fmt:message key="label.number"/> </td>
                    <td><fmt:message key="label.date"/></td>
                    <td><fmt:message key="label.type"/></td>
                    <td><fmt:message key="label.sum"/></td>
                </tr>
            </thread>
            <c:forEach var="bean" items="${sessionScope.payIn}">
                <tr>
                    <td>${bean.id}</td>
                    <td>${bean.date} </td>
                    <td>${bean.type}</td>
                    <td>${bean.value}</td>
                </tr>
            </c:forEach><br>
            CASH_INFLOW total ${sessionScope.payInSum}
        </table>
    </div>
    <hr>
    <div class="container">
        <h5> CASH TOTAL ${sessionScope.result} </h5>
    </div>
    <div class="container">
        <form action="${pageContext.request.contextPath}/ServletBack" target="_blank">
            <button><fmt:message key="label.back_to_start"/> ${sessionScope.user.role} </button>
        </form>
    </div>
</div>
</body>
</html>
