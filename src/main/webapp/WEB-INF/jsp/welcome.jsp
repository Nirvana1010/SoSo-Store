<%--
  Created by IntelliJ IDEA.
  User: 11874
  Date: 2019/12/6
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Soso Mobile</title>
</head>
<body>
*********************欢迎使用嗖嗖移动业务大厅***********************
<br/>
请选择你想要的服务类型:
<script language="JavaScript">
    function loginForm() {
        document.form1.action = "login";
        document.form1.submit();
    }
    function regForm() {
        document.form1.action = "register";
        document.form1.submit();
    }
    function useForm() {
        document.form1.action = "using";
        document.form1.submit();
    }
    function chargeForm() {
        document.form1.action = "charge";
        document.form1.submit();
    }
    function desForm() {
        document.form1.action = "description";
        document.form1.submit();
    }
    function quitForm() {
        document.form1.action = "quit";
        document.form1.submit();
    }
</script>

<form name="form1">
    <br/><br/>
    <input type="submit" name="login" value="用户登录" onclick="loginForm()">
    <br/><br/>
    <input type="submit" name="reg" value="用户注册" onclick="regForm()">
    <br/><br/>
    <input type="submit" name="useSoso" value="使用嗖嗖" onclick="useForm()">
    <br/><br/>
    <input type="submit" name="charge" value="话费充值" onclick="chargeForm()">
    <br/><br/>
    <input type="submit" name="description" value="资费说明" onclick="desForm()">
    <br/><br/>
    <input type="submit" name="quit" value="退出系统" onclick="quitForm()">
</form>
</body>
</html>
