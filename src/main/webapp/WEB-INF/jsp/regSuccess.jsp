<%--
  Created by IntelliJ IDEA.
  User: 11874
  Date: 2019/12/7
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register Success</title>
</head>
<body>
注册成功！
<br/>
卡号：${requestScope.number}
<br/>
用户名：${requestScope.name}
<br/>
当前余额：${requestScope.money}
<br/>
${requestScope.msg}
</body>
</html>
