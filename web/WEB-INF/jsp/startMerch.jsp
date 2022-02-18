
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>merch</title>
</head>
<body>
<h3> Hello, ${sessionScope.user.surname} </h3>

you are logged in as a MERCHANDISER.<br>
Товарознавець може створювати товари
та зазначати їх кількість на складі.<br>

<h2>${sessionScope.message}</h2><br>

<form action="/merch/createProduct" target="_blank">
    <button>create Product</button>
</form>
<br>

<br>

<form action="/merch/setAmountProduct" target="_blank">
    <button>add product quantity</button>
</form>
<br>
//to do
<form action="/merch/delete/Product" target="_blank">
    <button>delete product</button>
</form>
<br>
<form action="/logout" target="_blank" method="post">
    <button>logout</button>
</form>

</body>
</html>
