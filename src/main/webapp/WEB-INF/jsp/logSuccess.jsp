<%--
  Created by IntelliJ IDEA.
  User: 11874
  Date: 2019/12/7
  Time: 19:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Success</title>
</head>
<body>

<form name="form1" action="select" method="post">
    卡号：${requestScope.number}登录成功！
    <br/><br/>
    请选择你想要的服务类型:
    <br/><br/>
    1.本月账单查询
    <br/><br/>
    2.套餐余量查询
    <br/><br/>
    3.打印消费详单
    <br/><br/>
    4.套餐变更
    <br/><br/>
    5.办理退网
    <br/><br/>
    请输入（1~5），输入其他则退出：
    <input type="number" name="select">

</form>
</body>
</html>
