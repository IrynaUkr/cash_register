
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3> Hello, ${sessionScope.user.surname} </h3>
${message}.<br>
<h4>Старший касир має змогу:</h4>
<ul>
    <li>- відмінити чек;  </li>
    <li>- відмінити товар в чеку;</li>
    <li>- зробити  X і Z звіти.</li>
</ul>

<form action="/canselReceipt" target="_blank">
    <button>delete Receipt</button>
</form>
<br>
<form action="/delProdFromReceipt" target="_blank">
    <button>delete Product from Receipt</button>
</form>
<br>
<form action="/ServletReport" target="_blank" >
    <button>X report</button>
</form>
<br>
<form action="Zreport.jsp" target="_blank">
    <button>z report</button>
</form>
<br>
</body>
</html>
