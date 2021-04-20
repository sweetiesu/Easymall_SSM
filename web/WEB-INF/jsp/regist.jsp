<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>欢迎注册EasyMall</title>
    <meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/regist.css"/>
    <%--引入jQuery函数库--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.2.js"></script>
    <script type="text/javascript">
        //创建formObj对象
        var formObj = {
            "checkForm":function(){
                var canSub = true;
                //1.获取用户输入的参数
                //2.非空校验   val()是获取当前jQUery对象身上的值
               /*var username =  $.trim($("input[name='username']").val());
               //清空
                $("input[name='username']").nextAll("span").text("");
               if(username == ""){
                    //获取当前input框后的span框，向其中书写错误提示
                   $("input[name='username']").nextAll("span").text("用户名不能为空");
               }*/
               canSub = this.checkNull("username","用户名不能为空") && canSub;
               canSub = this.checkNull("password","密码不能为空") && canSub;
               canSub = this.checkNull("password2","确认密码不能为空") && canSub;
               canSub = this.checkNull("nickname","昵称不能为空") && canSub;
               canSub = this.checkNull("email","邮箱不能为空") && canSub;
               canSub = this.checkNull("valistr","验证码不能为空") && canSub;

                //3.密码一致性校验
                canSub = this.checkPassword() && canSub;
                //4.邮箱格式校验
                canSub = this.checkEmail() && canSub;
                //5.返回校验结果
                return canSub;
            },
            "checkNull":function(name,msg){
                var tag =  $.trim($("input[name="+name+"]").val());
                //清空
                $("input[name="+name+"]").nextAll("span").text("");
                if(tag == ""){
                    //获取当前input框后的span框，向其中书写错误提示
                    $("input[name="+name+"]").nextAll("span").text(msg);
                    return false;
                }
                return true;
            },
            "checkPassword":function(){
                //获取两个密码框的值
                var password = $("input[name='password']").val();
                var password2 = $("input[name='password2']").val();
                if(password != "" && password2 != "" && password != password2){
                    $("input[name='password2']").nextAll("span").text("两次密码不一致");
                    return false;
                }
                return true;
            },
            "checkEmail":function () {
                var reg =/\w+@\w+(\.\w+)+/;
                var email = $("input[name='email']").val();
                if(email != "" && !reg.test(email)){
                    $("input[name='email']").nextAll("span").text("邮箱格式不正确");
                    return false;
                }
                return true;
            }

        };
        //文档就绪事件
        $(function(){
            //鼠标离焦事件
            $("input[name='username']").blur(function (){
                //用户名的非空校验
                formObj.checkNull("username","用户名不能为空");
                //ajax查询用户名是否存在
                //获取用户名输出框中的值
                var username = $("input[name='username']").val();
                if(""!=username){
                    //非空校验只是做出用户名不能为空的显示，而此处的判断才是拦截ajax的操作。
                    $("#username_span").load("${pageContext.request.contextPath}/user/ajaxHasUserName.action",{"username":username});
                }

            });
            $("input[name='password']").blur(function (){
                //用户名的非空校验
                formObj.checkNull("password","密码不能为空");
            });
            $("input[name='password2']").blur(function (){
                //用户名的非空校验
                formObj.checkNull("password2","确认密码不能为空");
                formObj.checkPassword();
            });
            $("input[name='nickname']").blur(function (){
                //用户名的非空校验
                formObj.checkNull("nickname","昵称不能为空");
            });
            $("input[name='email']").blur(function (){
                //用户名的非空校验
                formObj.checkNull("email","邮箱不能为空");
                formObj.checkEmail();
            });
            $("input[name='valistr']").blur(function (){
                //用户名的非空校验
                formObj.checkNull("valistr","验证码不能为空");
            });

            $("#img").click(function(){
                var date = new Date();
                var time = date.getTime();
                //点击图片，更换验证码
                $("#img").attr("src","${pageContext.request.contextPath}/user/valiImg.action?time="+time)
            });
        });
    </script>
</head>
<body>
<form action="${pageContext.request.contextPath}/user/regist.action" method="POST" onsubmit="return formObj.checkForm()">
    <h1>欢迎注册EasyMall</h1>
    <table>
        <tr>
            <%--存储由RegistServlet向当前页面发送的msg域属性--%>
            <%--如果返回null，则用空串显示。--%>
            <%--如果不为null，则证明有错误提示信息，应该将其展示--%>
            <td class="tds" style="color:red;text-align:center" colspan="2">
                <%--<%=request.getAttribute("msg")==null?"":request.getAttribute("msg")%>--%>
                ${msg}
            </td>
        </tr>
        <tr>
            <td class="tds">用户名：</td>
            <td>
                <input type="text" name="username" value="${param.username}"/>
                <span id="username_span"></span>
            </td>
        </tr>
        <tr>
            <td class="tds">密码：</td>
            <td>
                <input type="password" name="password"/>
                <span></span>
            </td>
        </tr>
        <tr>
            <td class="tds">确认密码：</td>
            <td>
                <input type="password" name="password2"/>
                <span></span>
            </td>
        </tr>
        <tr>
            <td class="tds">昵称：</td>
            <td>
                <input type="text" name="nickname" value="${param.nickname}"/>
                <span></span>
            </td>
        </tr>
        <tr>
            <td class="tds">邮箱：</td>
            <td>
                <input type="text" name="email" value="${param.email}"/>
                <span></span>
            </td>
        </tr>
        <tr>
            <td class="tds">验证码：</td>
            <td>
                <input type="text" name="valistr"/>
                <img id="img" src="${pageContext.request.contextPath}/user/valiImg.action" width="" height="" alt="" />
                <span></span>
            </td>
        </tr>
        <tr>
            <td class="sub_td" colspan="2" class="tds">
                <input type="submit" value="注册用户"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
