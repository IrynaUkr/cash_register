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
    <title>Products</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<div class="container">
    <h3>  ${sessionScope.user.surname} <fmt:message key="label.welcome"/> ${sessionScope.user.role} </h3>
    <h2>${sessionScope.message}</h2><br>

    <c:forEach var="bean" items="${sessionScope.delCode}">
        ${bean.number} ${bean.date}
    </c:forEach>


    <div class=row-cols-8>
        <h3>choose kind of sorting</h3>
        <form action="${pageContext.request.contextPath}/merch/ServletProductPages">
            <table class="table table-bordered">
                <td><input type="radio" id="1" name="ProductCode" value="ProductCode"><label for="1"><fmt:message
                        key="label.code"/></label></td>
                <td><input type="radio" id="2" name="ProductName" value="ProductName"><label for="2"><fmt:message
                        key="label.name"/></label></td>
                <td><input type="radio" id="3" name="ProductPrice" value="ProductPrice"><label for="3"><fmt:message
                        key="label.price"/></label></td>
                <td><input type="radio" id="4" name="ProductAmount" value="ProductAmount"><label for="4"><fmt:message
                        key="label.amount"/></label></td>
                <td><input type="radio" id="5" name="ProductUOM" value="UOM"><label for="5"><fmt:message
                        key="label.UOM"/></label></td>
            </table>
            <button class="btn-default" type="submit"><fmt:message key="label.send"/></button>
        </form>
    </div>

    <form action="${pageContext.request.contextPath}/merch/ServletProductPages" method="post">
        <table class="table table-striped table-bordered table-hover">
            <thread>
                <tr class="active">
                    <td> N</td>
                    <td><fmt:message key="label.code"/></td>
                    <td><fmt:message key="label.name"/></td>
                    <td><fmt:message key="label.price"/></td>
                    <td><fmt:message key="label.amount"/></td>
                    <td><fmt:message key="label.UOM"/></td>
                    <td><fmt:message key="label.des"/></td>
                </tr>
            </thread>
            <c:forEach var="bean" items="${sessionScope.products}">
                <tr>
                    <td><input type="checkbox" name="selected" value="${bean.code}"></td>
                    <td> ${bean.code}</td>
                    <td> ${bean.name}</td>
                    <td> ${bean.price}</td>
                    <td> ${bean.amount}</td>
                    <td> ${bean.uom}</td>
                    <td> ${bean.description}</td>
                </tr>
            </c:forEach>
        </table>
        <td><input class="btn btn-warning" type="submit" value="<fmt:message key="label.del_product"/>"></td>

    </form>
    операция выполнена не будет, если для удаляемой строки <br>
    существуют связанные строки в подчиненной таблице. Если связанных строк нет, то удаление будет выполнено..<br>
    <div class="container">
        <div class="row-cols-md-4">
            <table class="table">
                <tr>
                    <c:forEach var="i" begin="1" end="${sessionScope.totalAmPages}">
                        <form action="${pageContext.request.contextPath}/merch/ServletProductPages">
                            <td><input type="submit" class="btn-default btn-lg" name="page" value="${i}"></td>
                        </form>
                    </c:forEach>
                </tr>
            </table>
        </div>
    </div>
    <br>
</div>
<form action="${pageContext.request.contextPath}/ServletBack" target="_blank">
    <button><fmt:message key="label.back_to_start"/>${sessionScope.user.role} </button>
</form>
</div>
<form action="${pageContext.request.contextPath}/logout" target="_blank" method="post">
    <button class="btn-default"><fmt:message key="label.logout"/></button>
</form>

</div>
</body>
</html>
