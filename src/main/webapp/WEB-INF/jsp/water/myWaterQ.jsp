<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="../common/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:wb="http://open.weibo.com/wb">
<head>
<title><fmt:message key="webapp.title" /> - 首页</title>
<!-- meta -->
<%@include file="../common/meta.jspf"%>
<!-- css links -->
<%@include file="../common/links.jspf"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}<fmt:message key="static.resources.host"/>/css/myWaterQ.css" />
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jspf"%>
	
	<div>
		<p class="num">${myWaterQ.waterQ}</p>
		<p class="wq">${myWaterQ.waterQDesc}</p>
		<p class="sz">
			<span class="wrap"><em class="name">本地水质</em><em class="desc">${myWaterQ.localWaterQDesc}</em></span>
			<span class="wrap"><em class="name">饮水习惯</em><em class="desc">${myWaterQ.yinshuiWaterQDesc}</em></span>
			<span class="wrap"><em class="name">桶装水</em><em class="desc">${myWaterQ.tongWaterQDesc}</em></span>
			<span class="wrap"><em class="name">饮水机</em><em class="desc">${myWaterQ.yinshuijiWaterQDesc}</em></span>
		</p>
		<p class="loca">${myWaterQ.loca}</p>
		<p class="op">
			<span class="wp"><img src /><em class="de">大点</em></span>
			<span class="wp"><img src /><em class="de">PK</em></span>
		</p>
		<p class="op">
			<span class="wp"><img src /><em class="de">实验室</em></span>
			<span class="wp"><img src /><em class="de">水质地图</em></span>
		</p>
	</div>	
	
	<!-- footer -->
	<%@include file="../common/footer.jspf"%>

	<!-- js scripts -->
	<%@include file="../common/scripts.jspf"%>
	
	<script type="text/javascript">
		$(document).ready(function(){
			
		});
	</script>
</body>
</html>

