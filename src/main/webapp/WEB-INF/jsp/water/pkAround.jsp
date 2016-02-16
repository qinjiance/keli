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
	href="${ctx}<fmt:message key="static.resources.host"/>/css/pkAround.css" />
</head>
<body>
	<!-- header -->
	<%@include file="../common/header2.jspf"%>
	
	<div class="bgbg">
		<p class="navi"><a class="on" href="${ctx}pkAround">附近</a>|<a href="${ctx}pkAll">全国</a></p>
		<p class="myq"><img src="${avatarImg}" /><span class="loc">${myWaterQ.loca}</span><span class="deg">健康度：</span><span class="degg">${myWaterQ.waterQ}</span></p>
		<c:forEach items="${userPositions}" var="up">
			<div class="uplist">
				<img class="hea" src="${up.headImg}" />
				<div class="des">
					<p>${up.location}</p>
					<p>${up.distance} 米</p>
				</div>
				<img class="op" userId="${up.userId}" src="${ctx}<fmt:message key="static.resources.host"/>/images/dao.png" />
			</div>
		</c:forEach>
	</div>	
	
	<div id="popget" class="popget">
		<div class="pop1">
			<div class="headd">
				<div class="headImg my">
					<img src="" />
					<p></p>
				</div>
				<div class="headVs">
					<p class="vs">PK</p>
					<p class="vsRet"></p>
				</div>
				<div class="headImg other">
					<img src="" />
					<p></p>
				</div>
			</div>
			<div class="pkTiao erci">
				<img src="${ctx}<fmt:message key="static.resources.host"/>/images/erci.png" />
				<div class="tx">二次污染率</div>
				<div class="ti">
					<p class="my"></p>
					<p class="other"></p>
				</div>
			</div>
			<div class="pkTiao jiegou">
				<img src="${ctx}<fmt:message key="static.resources.host"/>/images/jiegou.png" />
				<div class="tx">结垢率</div>
				<div class="ti">
					<p class="my"></p>
					<p class="other"></p>
				</div>
			</div>
			<div class="pkTiao xijun">
				<img src="${ctx}<fmt:message key="static.resources.host"/>/images/xijun.png" />
				<div class="tx">变异细菌率</div>
				<div class="ti">
					<p class="my"></p>
					<p class="other"></p>
				</div>
			</div>
			<div class="okbtn">再来一局</div>
		</div>
	</div>
	
	<!-- footer -->
	<%@include file="../common/footer.jspf"%>

	<!-- js scripts -->
	<%@include file="../common/scripts.jspf"%>
	
	<script type="text/javascript">
		function pop1(pkRet){
			var pop = $("#popget").clone(true).removeAttr("id").show();
			pop.find(".headImg.my img").attr("src",pkRet.myHeadImg);
			pop.find(".headImg.my p").html(pkRet.myZongheTds);
			pop.find(".headImg.other img").attr("src",pkRet.otherHeadImg);
			pop.find(".headImg.other p").html(pkRet.otherZongheTds);
			pop.find(".vsRet").html(pkRet.pkRet);
			
			pop.find(".pkTiao.erci .my").css("width",pkRet.myErciLv);
			pop.find(".pkTiao.jiegou .my").css("width",pkRet.myJiegouLv);
			pop.find(".pkTiao.xijun .my").css("width",pkRet.myXijunLv);
			pop.find(".pkTiao.erci .other").css("width",pkRet.otherErciLv);
			pop.find(".pkTiao.jiegou .other").css("width",pkRet.otherJiegouLv);
			pop.find(".pkTiao.xijun .other").css("width",pkRet.otherXijunLv);
			
			$(".bgbg").append(pop);
		} 
		$(document).ready(function(){
			$(".op").click(function(){
				var userId = $(this).attr("userId");
				$.getJSON("${ctx}pk",{pkUserId:userId},function(ret){
				    if(ret.code==0){
						pop1(ret.result);
				    }
				 });
			});
			$("#popget .okbtn").click(function(){
				$(this).closest(".popget").remove();
			});
		});
	</script>
</body>
</html>

