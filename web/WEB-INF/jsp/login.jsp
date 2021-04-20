<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.URLDecoder" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css"/>
    <title>EasyMall欢迎您登陆</title>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.js"></script>
    <script type="text/javascript">
        //文档就绪事件
        $(function(){
            $("input[name='username']").val(decodeURI('${cookie.remname.value}'))
        });
    </script>
</head>
<body>
<h1>欢迎登陆EasyMall</h1>
<form action="${pageContext.request.contextPath}/user/login.action" method="POST">
    <table>
        <tr >
            <td class="tdx" style="color:red;text-align: center" colspan="2">
                ${msg}
            </td>
        </tr>
        <tr>
            <td class="tdx">用户名：</td>
            <td><input type="text" name="username" /></td>
        </tr>
        <tr>
            <td class="tdx">密&nbsp;&nbsp; 码：</td>
            <td><input type="password" name="password"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="checkbox" name="remname" value="true"
                        ${not empty cookie.remname?"checked='checked'":""}
                       />记住用户名
                <input type="checkbox" name="autologin" value="true"/>30天内自动登陆
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align:center">
                <input type="submit" value="登 陆"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
