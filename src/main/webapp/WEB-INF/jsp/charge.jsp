<%--
  Created by IntelliJ IDEA.
  User: 11874
  Date: 2019/12/7
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Charge Money</title>
</head>
<body>
请输入充值卡号：
<form action="chargeSuccess" method="post">
    <input type="text" name="cardNumber"><br/>
    <br/>
    充值金额(至少为50元):<br/>
    <input type="text" name="money"><br/>
    <br/>
    <input type="submit" value="确认充值">
</form>
</body>
</html>
