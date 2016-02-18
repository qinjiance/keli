<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="../common/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:wb="http://open.weibo.com/wb">
<head>
<title><fmt:message key="webapp.title" /> - 寻宝</title>
<!-- meta -->
<%@include file="../common/meta.jspf"%>
<!-- css links -->
<%@include file="../common/links.jspf"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}<fmt:message key="static.resources.host"/>/css/waterMap.css" />
<link rel="stylesheet"
	href="http://cache.amap.com/lbs/static/main1119.css" />
</head>
<body>
	<!-- header -->
	<%@include file="../common/header.jspf"%>

	<div class="mapC">
		<div class="libao">
			<div class="btn">
				<img src="${ctx}<fmt:message key="static.resources.host"/>/images/libao.png" />
				<p>寻宝mini</p>
			</div>
			<div class="libaolist">
				<div class="data">
					<p>剩余次数：<span class="yu">${waterMap.currTimes}</span>/<span class="tot">${waterMap.todayTotalTimes}</span>每天</p>
					<p>小水滴数：<span class="di">${waterMap.shuidi}</span>滴</p>
					<p>获得部件：<span class="buj">${waterMap.currParts}</span>/${waterMap.totalParts}</p>
				</div>
				<div class="lll"></div>
			</div>
		</div>
		<div id="container"></div>
	</div>

	<div class="wen">
		<p class="lv"><img src="${ctx}<fmt:message key="static.resources.host"/>/images/erci.png" /><span class="tx">二次污染率</span><span class="tiao" style="width:${(waterMap.erCiLv)*1.5}px;"></span><span class="perc">${waterMap.erCiLv}%</span></p>
		<p class="lv"><img src="${ctx}<fmt:message key="static.resources.host"/>/images/jiegou.png" /><span class="tx">结垢率</span><span class="tiao jie" style="width:${(waterMap.jieGouLv)*1.5}px;"></span><span class="perc">${waterMap.jieGouLv}%</span></p>
		<p class="lv"><img src="${ctx}<fmt:message key="static.resources.host"/>/images/xijun.png" /><span class="tx">变异细菌率</span><span class="tiao" style="width:${(waterMap.xiJunLv)*1.5}px;"></span><span class="perc">${waterMap.xiJunLv}%</span></p>
	</div>

	<div id="lib" class="lib" style="display: none;">
		<img src="${ctx}<fmt:message key="static.resources.host"/>/images/libao.png" />
	</div>

	<div id="popget" class="popget">
		<div class="pop1">
			<img src="${ctx}<fmt:message key="static.resources.host"/>/images/libao.png" />
			<div class="tx1">获得<span class="tn">配件</span><span class="wn">：水杯</span>！</div>
			<div class="tx2">离大奖还差<span class="tip1"></span></div>
			<div class="okbtn">确认</div>
		</div>
	</div>

	<div id="popc" class="popc">
		<div class="pop2">
			<div class="tx1">次数用完啦！</div>
			<div class="tx2">分享即可多获得一次机会</div>
			<div class="okbtn">确认</div>
		</div>
	</div>

	<!-- footer -->
	<%@include file="../common/footer.jspf"%>

	<!-- js scripts -->
	<%@include file="../common/scripts.jspf"%>
	<script type="text/javascript"
		src="http://webapi.amap.com/maps?v=1.3&key=${ctx}<fmt:message key="gaode.api.key"/>"></script>
	<script type="text/javascript">  
		function pop1(tn,wn,tip1){
			var pop = $("#popget").clone(true).removeAttr("id").show();
			if(tn>0){
				pop.find("img").attr("src","${ctx}<fmt:message key="static.resources.host"/>/images/part"+tn+".png");
				pop.find(".tn").html("配件");
				pop.find(".wn").html("："+wn);
			}else{
				pop.find("img").attr("src","${ctx}<fmt:message key="static.resources.host"/>/images/shuidi.png");
				pop.find(".tn").html("配件");
				pop.find(".tn").html("小水滴");
				pop.find(".wn").empty();
			}
			pop.find(".tip1").html(tip1+"个部件");
			$(".mapC").append(pop);
		} 
		function pop2(tx1,tx2){
			var pop = $("#popc").clone(true).removeAttr("id").show();
			pop.find(".tx1").html(tx1);
			pop.find(".tx2").html(tx2);
			$(".mapC").append(pop);
		}
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
			
			$(".libao .btn").click(function(){
				$(".libao .btn").hide();
				$(".libao .libaolist .lll").empty();
				for(var i=0;i<8;i++){
					var lib = $("#lib").clone(true).removeAttr("id").show();
					var left = Math.floor(Math.random()*500)+100;
					var top = Math.floor(Math.random()*450)+100;
					lib.css({"top":(top+"px"),"left":(left+"px")});
					$(".libao .libaolist .lll").append(lib);
				}
				$(".libao .libaolist").show();
			});
			$("#lib").click(function(){
				$(this).remove();
				$.getJSON("${ctx}xunbao",{},function(ret){
				    if(ret.code==0){
				    	var data = ret.result;
				    	$(".libaolist .yu").html(data.currTimes);
				    	$(".libaolist .tot").html(data.todayTotalTimes);
				    	$(".libaolist .di").html(data.shuidi);
				    	$(".libaolist .buj").html(data.currParts);
						pop1(data.libaoType,data.libaoName,data.totalParts-data.currParts);
				    }else if(ret.code==1){
				    	var data = ret.result;
				    	$(".libaolist .yu").html(data.currTimes);
				    	$(".libaolist .tot").html(data.todayTotalTimes);
				    	$(".libaolist .di").html(data.shuidi);
				    	$(".libaolist .buj").html(data.currParts);
				    	pop2("很抱歉！","本次未寻到宝物，请再接再厉");
				    }else{
				    	pop2("次数用完啦！","分享即可多获得一次机会");
				    }
				 });
			});
			$("#popget .okbtn").click(function(){
				$(this).closest(".popget").remove();
			});
			$("#popc .okbtn").click(function(){
				$(this).closest(".popc").remove();
			});
		});
	</script>
</body>
</html>

