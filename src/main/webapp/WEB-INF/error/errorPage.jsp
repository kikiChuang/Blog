<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap-theme.min.css">
<script src="${pageContext.request.contextPath}/static/bootstrap3/js/bootstrap.min.js"></script>
<title>错误页面_Java博客系统</title>
</head>
<body style="margin: 135px 100px;">
	<div class="jumbotron" style="padding: 50px 20px;">
		<h1>Error</h1>
		<p>您访问的页面不存在！</p>
		<p>或者地址栏请求参数有问题！</p>
		<p>或者网站出现异常错误！请稍后访问</p>
  		<p><a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath }/index.html" role="button">回到主页</a></p>
	</div>
</body>
</html>