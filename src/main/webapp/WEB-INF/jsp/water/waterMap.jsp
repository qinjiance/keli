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
	href="${ctx}<fmt:message key="static.resources.host"/>/css/waterMap.css" />
<link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jspf"%>
	
	<div class="mapC">
		<div id="container"></div>
	</div>
	
	
	<!-- footer -->
	<%@include file="../common/footer.jspf"%>

	<!-- js scripts -->
	<%@include file="../common/scripts.jspf"%>
    <script type="text/javascript" 
    	src="http://webapi.amap.com/maps?v=1.3&key=${ctx}<fmt:message key="gaode.api.key"/>"></script>
	<script type="text/javascript">  
		$(document).ready(function(){
			var lnglatXY = [${waterMap.lng},${waterMap.lat}];
			var map = new AMap.Map("container", {
			        resizeEnable: true,
			        center: lnglatXY,
					zoom: 16
		    });
			//添加点标记，并使用自己的icon
		    new AMap.Marker({
		        map: map,
				position: lnglatXY,
				content: '<img class="marker-cuss" src="${avatarImg}"></img>'          
		    });
		});
	</script>
</body>
</html>

