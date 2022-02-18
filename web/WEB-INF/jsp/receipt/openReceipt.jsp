<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>open Receipt</title>
    <h3> Hello, ${sessionScope.user.surname} !</h3>
    ${sessionScope.user.role}<br>

</head>
<body>
<form action="/cashier/openReceipt" method="post">
    enter number of check: <input type="text" name="number"><br>
    select status of check:
    <select name="status">
        <option value="CREATED" selected>CREATED</option>
        <option value="FINISHED"> FINISHED</option>
    </select> <br><br>
    select type of check:
    <select name="type">
        <option value="SALE" selected>SALE</option>
        <option value="RETURN"> RETURN</option>
    </select> <br> <br>
    <input type="submit" value="Submit">

</form>

<form action="/ServletBack" target="_blank">
    <button>back to start ${sessionScope.user.role} page</button>
</form>
</body>
</html>
