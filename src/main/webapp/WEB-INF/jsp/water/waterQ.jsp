<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="../common/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:wb="http://open.weibo.com/wb">
<head>
<title><fmt:message key="webapp.title" /> - 水质调查</title>
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
			<p class="pt"><span class="lab">本地水质健康度</span><img class="color" src="${ctx}<fmt:message key="static.resources.host"/>/images/color.png" /></p>
			<div class="cir ${waterQPos.degree}">
				<p class="num">${waterQPos.waterQ}</p>
				<p class="wq">${waterQPos.desc}</p>
			</div>
			<p class="pos">位置：${waterQPos.posName}</p>
			<p class="inp"><input type="text" name="company" placeholder="请补充公司名称" /></p>
			<p class="btn">请填写</p>
		</div>	
		
		<div class="yinshui" style="display:none;">
			<p class="pt"><span class="lab">饮水习惯健康度</span><img class="color" src="${ctx}<fmt:message key="static.resources.host"/>/images/color.png" /></p>
			<p class="ques"><img src="${ctx}<fmt:message key="static.resources.host"/>/images/ques.gif" /></p>
			<div class="cir ${waterQPos.degree}" style="display:none;">
				<p class="num">请完成下方题目</p>
				<p class="wq"></p>
			</div>
			<p class="pos">说说自己，办公室喝什么水？</p>
			<div class="inp">
				<p class="selYinshui" val="1">饮水机</p>
				<p class="selYinshui" val="2">瓶装水</p>
				<p class="selYinshui" val="3">自己茶壶烧</p>
			</div>
			<p class="btn">请选择</p>
		</div>	
		
		<div class="tongzhuangshui" style="display:none;">
			<p class="pt"><span class="lab">桶装水健康度</span><img class="color" src="${ctx}<fmt:message key="static.resources.host"/>/images/color.png" /></p>
			<p class="ques"><img src="${ctx}<fmt:message key="static.resources.host"/>/images/ques.gif" /></p>
			<div class="cir ${waterQPos.degree}" style="display:none;">
				<p class="num">请完成下方题目</p>
				<p class="wq"></p>
			</div>
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
			<p class="pt"><span class="lab">饮水机健康度</span><img class="color" src="${ctx}<fmt:message key="static.resources.host"/>/images/color.png" /></p>
			<p class="ques"><img src="${ctx}<fmt:message key="static.resources.host"/>/images/ques.gif" /></p>
			<div class="cir ${waterQPos.degree}" style="display:none;">
				<p class="num">请完成下方题目</p>
				<p class="wq"></p>
			</div>
			<p class="pos">说说保洁阿姨，多久护理一次饮水机？</p>
			<div class="inp">
				<p class="selBJ" val="3">三个月！阿姨特勤快</p>
				<p class="selBJ" val="6">-_-，半年吧！</p>
				<p class="selBJ" val="12">保洁阿姨是什么？</p>
				<p class="selBJ" val="0">自己洗</p>
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
				    	$(that).closest(".yinshui").find(".num").html(data.waterQ);
				    	$(that).closest(".yinshui").find(".wq").html(data.desc);
				    	$(that).closest(".yinshui").find(".cir").removeClass("gr").removeClass("blue").removeClass("or").addClass(data.degree);
				    	//$(that).closest(".yinshui").find(".ques").fadeOut(function(){
				    	//	$(that).closest(".yinshui").find(".cir").fadeIn();
				    	//});
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
				$.getJSON("${ctx}json/getWaterQ",{lati:'${lati}',longi:'${longi}',
					tongzhuangshui:$(this).attr('val')},function(ret){
				    if(ret.code==0){
				    	var data = ret.result;
				    	$(that).closest(".tongzhuangshui").find(".num").html(data.waterQ);
				    	$(that).closest(".tongzhuangshui").find(".wq").html(data.desc);
				    	$(that).closest(".tongzhuangshui").find(".cir").removeClass("gr").removeClass("blue").removeClass("or").addClass(data.degree);
				    	//$(that).closest(".tongzhuangshui").find(".ques").fadeOut(function(){
				    	//	$(that).closest(".tongzhuangshui").find(".cir").fadeIn();
				    	//});
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
					baojie:$(this).attr('val')},function(ret){
				    if(ret.code==0){
				    	var data = ret.result;
				    	$(that).closest(".baojie").find(".num").html(data.waterQ);
				    	$(that).closest(".baojie").find(".wq").html(data.desc);
				    	$(that).closest(".baojie").find(".cir").removeClass("gr").removeClass("blue").removeClass("or").addClass(data.degree);
				    	//$(that).closest(".baojie").find(".ques").fadeOut(function(){
				    	//	$(that).closest(".baojie").find(".cir").fadeIn();
				    	//});
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

