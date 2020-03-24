<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>商品图片上传 </title>
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


</head>

<body>
<form action="image/item/upload" method="post" enctype="multipart/form-data">
  ${msg}
  <label>图片</label><input type="file" name="file"/><br/>
  <input type="submit" value="提  交"/>
</form>
</body>
</html>
