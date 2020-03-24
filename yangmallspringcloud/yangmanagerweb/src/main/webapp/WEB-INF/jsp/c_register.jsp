<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>登录 — 商城后台管理系统 </title>
    <%
        String path = request.getContextPath();

        String host = request.getHeader("x-forwarded-host");

        String basePath;
        if(host!=null&&host.length()>0){
            String prefix=request.getHeader("x-forwarded-prefix");
            if(prefix==null){
                prefix="";
            }
            basePath= request.getHeader("x-forwarded-proto")+"://"+host+prefix+path+"/";
        }else {
            basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        }
    %>

    <base href="<%=basePath%>">

    <link rel="icon" href="favicon.ico">
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/app.css" rel="stylesheet">
  </head>

  <body class="login">
    <div>
      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form action="register.html" method="POST" role="form">
              <h1>管理员注册</h1>
              <div>
                <input type="text" name="userName" class="form-control" placeholder="管理员登录名" required="">
              </div>
              <div>
                <input type="password" name="password" class="form-control" placeholder="管理员登录密码" required="">
              </div>
                ${errorMsg}
                <div>
                    <input type="password" name="passwordagain" class="form-control" placeholder="再次输入登录密码" required="">
                </div>

                <!--
                <div>
                <a class="btn btn-default submit">&nbsp;登&nbsp;录&nbsp;</a>
              </div>
              -->

                <!--
                <input type="submit" value="登录">
                -->

                <div>
                <button type="submit" class="btn btn-default">注册</button>
                </div>

              <div class="clearfix"></div>

              <div class="separator">
                <div class="clearfix"></div>
                <br>

              </div>
            </form>
          </section>
        </div>
      </div>
    </div>

    <script type="text/javascript">
        function captcha(e) {
            e.src="captcha?ss="+ Math.random();
        }
    </script>


  </body></html>