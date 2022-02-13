
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>createProduct</title>
</head>
<body>
Surname: <h3> ${sessionScope.user.surname} </h3>
role: ${sessionScope.user.role}<br>
Create new Product:<br>

<form action="/createProduct" method="post">
    <input type="text" name="code"> code<br><br>
    <input type="text" name="name"> name<br> <br>
    <input type="text" name="description"> description<br> <br>
    <input type="text" name="price"> price <br> <br>
    <input type="text" name="amount"> amount <br> <br>
    <select name="uom">
        <option value="unit">unit</option>
        <option value="kg">kg</option>
    </select> units of measure  <br> <br>
    <input type="submit" value="Submit">
</form>

<form action="/ServletBack" target="_blank">
    <button>back to start ${sessionScope.user.role} page</button>
</form>


</body>
</html>
