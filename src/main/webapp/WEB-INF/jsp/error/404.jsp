<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="../common/taglibs.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
<title><fmt:message key="webapp.title" /> - 出错啦</title>
<!-- meta -->
<%@include file="../common/meta.jspf"%>
<!-- css links -->
<%@include file="../common/links.jspf"%>
</head>
<body>
	
	<img src="${ctx}<fmt:message key="static.resources.host"/>/images/404.png" style="width:400px;margin-top:300px;">
	<p style="font-size: 30px;color:#ccc;margin-top:20px;">网络暂时无法链接，点击屏幕重试</p>
	
	<script type="text/javascript">
		$(document).ready(function(){
		});
	</script>
</body>
</html>

