<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Z-report</title>
</head>
<body>

<h3> Hello, ${sessionScope.user.surname} !</h3>
you are logged in as ${sessionScope.user.role}<br>
<h3>  ${sessionScope.message1} !</h3><h3>  ${sessionScope.message2} !</h3>

<h4> Returns list </h4>
<c:forEach var="bean" items="${sessionScope.returnList}">
    id.......${bean.id}; number...${bean.number} ; date..... ${bean.date} ;
    status...${bean.status} ; type.....${bean.operationType}  ; sum......${bean.sum}  <br>
</c:forEach><br>

<h4> return total ${sessionScope.returnSum} </h4>

<h4> Sales list </h4>
<c:forEach var="bean" items="${sessionScope.saleList}">
    id.......${bean.id}; number...${bean.number} ; date..... ${bean.date} ;
    status...${bean.status} ; type.....${bean.operationType} ; sum......${bean.sum} <br>
</c:forEach><br>
<h4> sales total  ${sessionScope.saleSum} </h4>


<h4> CASH_OUTFLOW list </h4>
<c:forEach var="bean" items="${sessionScope.payOut}">
    id.......${bean.id}; date..... ${bean.date} ;
    type.....${bean.type} ; sum......${bean.value} <br>
</c:forEach><br>
<h4> CASH_OUTFLOW- total  ${sessionScope.payOutSum} </h4>


<h4> CASH_INFLOW list </h4>
<c:forEach var="bean" items="${sessionScope.payIn}">
    id.......${bean.id}; date..... ${bean.date} ;
    type.....${bean.type} ; sum......${bean.value} <br>
</c:forEach><br>
<h4> CASH_INFLOW total  ${sessionScope.payInSum} </h4>

<h2> CASH TOTAL  ${sessionScope.result} </h2>

<form action="/ServletBack" target="_blank">

    <button>back to start ${sessionScope.user.role} page</button>
</form>

</body>
</html>
