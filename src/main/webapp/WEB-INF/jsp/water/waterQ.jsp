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
	<div class="mainBg">
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
				<span class="selYinshui" val="1">饮水机</span><span class="selYinshui" val="2">瓶装水</span><span class="selYinshui" val="3">自己茶壶烧</span>
			</p>
			<p class="btn">请选择</p>
		</div>	
		
		<div class="tongzhuangshui" style="display:none;">
			<p class="pt"><span class="lab">桶装水健康度</span><span class="color">颜色条</span></p>
			<p class="num">请完成下方题目</p>
			<p class="wq"></p>
			<p class="pos">说说老板，一般买的都是什么桶装水？</p>
			<div class="inp">
				<p class="selTZS" val="1">老板有钱，喝的都是（屈臣氏，恒大）</p>
				<p class="selTZS" val="2">一般般啦，中档品牌（雀巢，娃哈哈，乐百氏）</p>
				<p class="selTZS" val="3">公司小气，都是山寨（国信，景甜）</p>
				<p class="selTZS" val="4">从来没听说过</p>
			</div>
			<p class="btn">请选择</p>
		</div>	
		
		<div class="baojie" style="display:none;">
			<p class="pt"><span class="lab">饮水机健康度</span><span class="color">颜色条</span></p>
			<p class="num">请完成下方题目</p>
			<p class="wq"></p>
			<p class="pos">说说保洁阿姨，多久护理一次饮水机？</p>
			<div class="inp">
				<p class="selBJ" val="1">三个月！阿姨特勤快</p>
				<p class="selBJ" val="2">-_-，半年吧！</p>
				<p class="selBJ" val="3">保洁阿姨是什么？</p>
				<p class="selBJ" val="4">自己洗</p>
			</div>
			<p class="btn">请选择</p>
		</div>
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
			$(".yinshui .btn").click(function(){
				if(!$(this).hasClass("next")){
					return;
				}
				$(".yinshui").fadeOut(function(){
					$(".tongzhuangshui").fadeIn();
				});
			});

			$(".inp .selTZS").click(function(){
				var that = this;
				$.getJSON("${ctx}json/getWaterQ",{lati:'${lati}',longi:'${longi}',yinshui:$(".inp .selYinshui.on").attr('val'),
					tongzhuangshui:$(this).attr('val')},function(ret){
				    if(ret.code==0){
				    	var data = ret.result;
				    	$(that).closest(".inp").siblings(".num").html(data.waterQ);
				    	$(that).closest(".inp").siblings(".wq").html(data.desc);
				    }
				 });
				$(".inp .selTZS").removeClass("on");
				$(this).addClass("on");
				$(this).closest(".inp").siblings(".btn").html("下一步").addClass("next");
			});
			$(".tongzhuangshui .btn").click(function(){
				if(!$(this).hasClass("next")){
					return;
				}
				$(".tongzhuangshui").fadeOut(function(){
					$(".baojie").fadeIn();
				});
			});

			$(".inp .selBJ").click(function(){
				var that = this;
				$.getJSON("${ctx}json/getWaterQ",{lati:'${lati}',longi:'${longi}',
					yinshui:$(".inp .selYinshui.on").attr('val'),
					tongzhuangshui:$(".inp .selTZS.on").attr('val'),
					baojie:$(this).attr('val')},function(ret){
				    if(ret.code==0){
				    	var data = ret.result;
				    	$(that).closest(".inp").siblings(".num").html(data.waterQ);
				    	$(that).closest(".inp").siblings(".wq").html(data.desc);
				    }
				 });
				$(".inp .selBJ").removeClass("on");
				$(this).addClass("on");
				$(this).closest(".inp").siblings(".btn").html("完成").addClass("next");
			});
			$(".baojie .btn").click(function(){
				if(!$(this).hasClass("next")){
					return;
				}
				$(".baojie").fadeOut(function(){
					window.location.href="${ctx}myWaterQ?lati=${lati}&longi=${longi}&location="
							+$(".inp input[name=company]").val()+"&yinshui=" 
							+$(".inp .selYinshui.on").attr('val')+"&tongzhuangshui="
							+$(".inp .selTZS.on").attr('val')+"&baojie="+$(".inp .selBJ.on").attr('val');
				});
			});
		});
	</script>
</body>
</html>

