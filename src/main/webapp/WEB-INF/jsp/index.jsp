<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="./common/taglibs.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
<title><fmt:message key="webapp.title" /> - 首页</title>
<!-- meta -->
<%@include file="./common/meta.jspf"%>
<!-- css links -->
<%@include file="./common/links.jspf"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}<fmt:message key="static.resources.host"/>/css/index.css" />
</head>
<body>
	<div class="mainBg">
		<div class="landing">
			<p class="landingImg" ><img src="${ctx}<fmt:message key="static.resources.host"/>/images/lbs.jpg" /></p>
		    <p class="landingTit">办公室水质地图</p>
		    <p><a class="landingA" href="javascript:void(0);">开始绘制地图</a></p>
		</div>	
		
		<div class="loading" style="display:none;">
			<p class="loadingTT">办公室水质地图</p>
			<p class="loadingImg"><img src="${ctx}<fmt:message key="static.resources.host"/>/images/loading1.gif" /></p>
			<p class="loadingTT2">抽取样本中...</p>
			<p class="loadingImg"><img src="${ctx}<fmt:message key="static.resources.host"/>/images/loading2.gif" /></p>
			<p class="loadingTT2">定位中...</p>
		</div>
	</div>
		
	<!-- footer -->
	<%@include file="./common/footer.jspf"%>

	<!-- js scripts -->
	<%@include file="./common/scripts.jspf"%>
	
	<script type="text/javascript">
		function getLocation(){
		  if (navigator.geolocation){
		    navigator.geolocation.getCurrentPosition(showPosition,showError);
		  }
		  else{
			  console.log("Geolocation is not supported by this browser.");
			  getWaterQ();
		  }
		}
		function showPosition(position){
		  console.log("Latitude: " + position.coords.latitude + "Longitude: " + position.coords.longitude);	
		  getWaterQ(position.coords.latitude,position.coords.longitude);
		}
		function showError(error){
		  switch(error.code){
		    case error.PERMISSION_DENIED:
			  console.log("User denied the request for Geolocation.");
		      break;
		    case error.POSITION_UNAVAILABLE:
			  console.log("Location information is unavailable.");
		      break;
		    case error.TIMEOUT:
			  console.log("The request to get user location timed out.");
		      break;
		    case error.UNKNOWN_ERROR:
			  console.log("An unknown error occurred.");
		      break;
		    }
		  getWaterQ();
		}
		function getWaterQ(lati,longi){
			if(!lati){
				lati='';
			}
			if(!longi){
				longi='';
			}
			window.location.href='${ctx}getWaterQ?lati='+lati+'&longi='+longi;
		}
		$(document).ready(function(){
			$(".landingA").click(function(){
				getLocation();
				$(".landing").slideUp(function(){
					$(".loading").slideDown();
				});
			});
		});
	</script>
</body>
</html>

