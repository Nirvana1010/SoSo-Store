<%--
  Created by IntelliJ IDEA.
  User: 11874
  Date: 2019/12/7
  Time: 9:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
请输入手机卡号：<br/>
<form action="logSuccess" method="post">
    <input type="text" name="number">
    <br/>
    <br/>
    请输入密码：<br/>
    <input type="text" name="password">
    <br/><br/>
    <input type="submit" value="登录">
</form>
</body>
</html>
