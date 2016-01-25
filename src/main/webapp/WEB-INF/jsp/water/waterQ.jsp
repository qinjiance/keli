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
	href="${ctx}<fmt:message key="static.resources.host"/>/css/waterQ.css" />
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jspf"%>
	
	<div class="comp">
		<p class="pt"><span class="lab">本地水质健康度</span><span class="color">颜色条</span></p>
		<p class="num">${waterQPos.waterQ}</p>
		<p class="wq">${waterQPos.desc}</p>
		<p class="pos">位置：${waterQPos.posName}</p>
		<p class="inp"><input type="text" name="company" placeholder="请补充公司名称" /></p>
		<p class="btn">请填写</p>
	</div>	
	
	<div class="yinshui" style="display:none;">
		<p class="pt"><span class="lab">饮水习惯健康度</span><span class="color">颜色条</span></p>
		<p class="num">请完成下方题目</p>
		<p class="wq"></p>
		<p class="pos">说说自己，办公室喝什么水？</p>
		<p class="inp">
			<span class="selYinshui" val="1">饮水机</span><span class="selYinshui" val="2">瓶装水</span><span class="selYinshui" val="2">自己茶壶烧</span>
		</p>
		<p class="btn">请选择</p>
	</div>	
	
	<!-- footer -->
	<%@include file="../common/footer.jspf"%>

	<!-- js scripts -->
	<%@include file="../common/scripts.jspf"%>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$(".inp input").keyup(function(){
				if($.trim($(this).val())){
					$(this).closest(".inp").siblings(".btn").html("下一步").addClass("next");
				}else{
					$(this).closest(".inp").siblings(".btn").html("请填写").removeClass("next");
				}
			});
			$(".comp .btn").click(function(){
				if(!$(this).hasClass("next")){
					return;
				}
				$(".comp").fadeOut(function(){
					$(".yinshui").fadeIn();
				});
			});

			$(".inp .selYinshui").click(function(){
				var that = this;
				$.getJSON("${ctx}json/getWaterQ",{lati:'${lati}',longi:'${longi}',yinshui:$(this).attr('val')},function(ret){
				    if(ret.code==0){
				    	var data = ret.result;
				    	$(that).closest(".inp").siblings(".num").html(data.waterQ);
				    	$(that).closest(".inp").siblings(".wq").html(data.desc);
				    }
				 });
				$(".inp .selYinshui").removeClass("on");
				$(this).addClass("on");
				$(this).closest(".inp").siblings(".btn").html("下一步").addClass("next");
			});
		});
	</script>
</body>
</html>

