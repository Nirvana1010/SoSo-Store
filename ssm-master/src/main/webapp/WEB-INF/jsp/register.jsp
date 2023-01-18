<%--
  Created by IntelliJ IDEA.
  User: 11874
  Date: 2019/12/7
  Time: 11:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Register</title>
</head>
<body>
<br/>
<form action="/regSuccess" method="post">
    选择卡号：<br/>
    <select name="number">
        <option>---请选择---</option>
        <option >${requestScope.num1}</option>
        <option >${requestScope.num2}</option>
        <option >${requestScope.num3}</option>
        <option >${requestScope.num4}</option>
        <option >${requestScope.num5}</option>
        <option >${requestScope.num6}</option>
        <option >${requestScope.num7}</option>
        <option >${requestScope.num8}</option>
        <option >${requestScope.num9}</option>
    </select>
    <br/><br/>
    选择套餐类型：<br/>
    <select name="package">
        <option>---请选择---</option>
        <option value="Talk">话痨套餐</option>
        <option value="Net">网虫套餐</option>
        <option value="Super">超人套餐</option>
    </select>
    <br/><br/>
    请输入姓名：<br/>
    <input type="text" name="name">
    <br/><br/>
    请输入密码：<br/>
    <input type="text" name="password">
    <br/><br/>
    请输入预存话费金额：<br/>
    <input type="number" name="money">
    <br/><br/>
    <input type="submit" value="确认注册">
</form>
</body>
</html>
