<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<style type="text/css">
	.v_clarity{z-index:5;position:absolute; left:30px; top:30px;}
</style>
<script type="text/javascript" src="/js/jquery-1.9.1.min.js?${initParam.buildVersion}" charset="utf-8"></script>
<script type="text/javascript" src="/player/ckplayer/ckplayer.js?${initParam.buildVersion}" charset="utf-8"></script>
<script src="/js/layer/layer.js?${initParam.buildVersion}"></script>
<script type="text/javascript">
	var claritys=eval(${claritys});
	$(document).ready(function(){
		playVideo();
	});
	function getPath(obj){
		var path="";
		if(typeof(obj.splitInfos)!='undefined'){
			for(var j=0;j<obj.splitInfos.length;j++){
				if(j!=0){
					path=path+","+obj.splitInfos[j].filePath;
				}else{
					path=path+obj.splitInfos[j].filePath;
				}
			}
		}else{
			path=obj.videoTransUrl;
		}
		return path;
	}
	
	function getVideoTime(obj){
		var time="";
		if(typeof(obj.splitInfos)!='undefined'){
			for(var j=0;j<obj.splitInfos.length;j++){
				if(j!=0){
					time=time+","+obj.splitInfos[j].totalLength;
				}else{
					time=time+obj.splitInfos[j].totalLength;
				}
			}
		}else{
			time=obj.videoTime;
		}
		return time;
	}
	
	function getVideoSize(obj){
		var size="";
		if(typeof(obj.splitInfos)!='undefined'){
			for(var j=0;j<obj.splitInfos.length;j++){
				if(j!=0){
					size=size+","+obj.splitInfos[j].fileSize;
				}else{
					size=size+obj.splitInfos[j].fileSize;
				}
			}
		}else{
			size=obj.videoTransSize;
		}
		return size;
	}
	
	function getIndexByF(f){
		var index=0;
		for(var i=0;i<claritys.length;i++){
			var obj=claritys[i];
			if(typeof(obj.splitInfos)!='undefined'){
				//有分段
				if(f.split(",")[0]==obj.splitInfos[0].filePath){
					index=i;
					break;
				}
			}else{
				//并没有
				if(f==obj.videoTransUrl){
					index=i;
					break;
				}
			}
		}
		return index;
	}
	
	function bufferHandler(progress){
		//console.log(progress);
		//这里的progress加起来就可以统计出流量
	}
	
	function loadedHandler(){
		//注册buffer改变监听事件
		CKobject.getObjectById('ckplayer_a1').addListener('speed','bufferHandler');
		CKobject.getObjectById('ckplayer_a1').addListener('videoClear','videoClearHandler');
		CKobject.getObjectById('ckplayer_a1').addListener('videoLoad','videoLoadHandler');
		CKobject.getObjectById('ckplayer_a1').addListener('sendNetStream','sendNetStreamHandler');
		CKobject.getObjectById('ckplayer_a1').addListener('loadedmetadata','loadedmetadataHandler');
		CKobject.getObjectById('ckplayer_a1').addListener('loadComplete','loadCompleteHandler');
	}
	
	function videoClearHandler(){
		var obj=CKobject.getObjectById('ckplayer_a1').getStatus();
		var currentF=obj.myflashvars.f;
		var currentIndex=getIndexByF(currentF);
		var size=getVideoSize(claritys[currentIndex]);
		CKobject.getObjectById('ckplayer_a1').changeFlashvars('{w->'+size+'}');
	}
	
	function videoLoadHandler(){
		console.log("加载视频,这个方法里后期需要加等待读取的显示");
	}
	
	function sendNetStreamHandler(){
		console.log("播放器接受到视频流");
	}
	
	function loadedmetadataHandler(){
		console.log("读取到视频的元数据");
	}
	
	function loadCompleteHandler(){
		console.log("加载完成");
	}
	
	function playVideo(){
		if(typeof(claritys)!='undefined'){
			var deft="";
			var deff="";
			var firstPath=getPath(claritys[0]);
			var firstTime=getVideoTime(claritys[0]);
			var firstSize=getVideoSize(claritys[0]);
			
			for(var i=0;i<claritys.length;i++){
				if(i==0){
					deft=claritys[i].videoClarity+claritys[i].videoFormat;
					deff=firstPath;
				}else{
					deft=deft+","+claritys[i].videoClarity+claritys[i].videoFormat;
					deff=deff+"|"+getPath(claritys[i]);
				}
			}
			
			var flashvars={
				f:firstPath,
				//f:"http://look.yun.chinahrt.com/courseyun/tv/subVideo/dce1ce045f1d4583ae868669544ec5fe/1.mp4,http://look.yun.chinahrt.com/courseyun/tv/subVideo/dce1ce045f1d4583ae868669544ec5fe/2.mp4,http://look.yun.chinahrt.com/courseyun/tv/subVideo/dce1ce045f1d4583ae868669544ec5fe/3.mp4,http://look.yun.chinahrt.com/courseyun/tv/subVideo/dce1ce045f1d4583ae868669544ec5fe/4.mp4",
				p:1,
				o:firstTime,
				w:firstSize,
				c:0,
				m:1,
				b:0,
				h:3,
				deft:deft,
				deff:deff,
				loaded:'loadedHandler'
			};
			//只走flash方式
			var params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always',wmode:'transparent'};
			CKobject.embedSWF('/player/ckplayer/ckplayer.swf','a1','ckplayer_a1','1024','768',flashvars,params);
		}else{
			if("${filePath}"!=''){
				var flashvars={
						f:"/filePath/${filePath}",
						//f:"http://look.yun.chinahrt.com/courseyun/tv/subVideo/dce1ce045f1d4583ae868669544ec5fe/1.mp4,http://look.yun.chinahrt.com/courseyun/tv/subVideo/dce1ce045f1d4583ae868669544ec5fe/2.mp4,http://look.yun.chinahrt.com/courseyun/tv/subVideo/dce1ce045f1d4583ae868669544ec5fe/3.mp4,http://look.yun.chinahrt.com/courseyun/tv/subVideo/dce1ce045f1d4583ae868669544ec5fe/4.mp4",
						p:1,
						c:0,
						m:1,
						b:0,
						h:3,
						loaded:'loadedHandler'
					};
					//只走flash方式
					var params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always',wmode:'transparent'};
					CKobject.embedSWF('/player/ckplayer/ckplayer.swf','a1','ckplayer_a1','1024','768',flashvars,params);
			}
		}
	}
	function fileDownload(){
		window.location.href="/userApply/fileDownload?filePath=${filePath}&fileName=${fileName}&fileType=${fileType}";
	}
</script>
</head>
<body>

	<!-- 播放区 -->
	<div style="margin-left:25%;margin-top:20px;margin-bottom:20px;">
		<c:if test="${ empty notShowDown }">
			<button type="button" onclick="fileDownload()" class="btn-primary" >
				下载
			</button>
		</c:if>
	</div>
	<div style='TEXT-ALIGN: center;'>
		<div id="a1" ></div>
	</div>
	<!-- <div id='playerDiv' style="position:relative;left:20px;top:50px;">
		<div onclick='chooseClarity()' class='v_clarity'>
			<img style='width:25px;height:25px;' src='/images/find1-ac.png'/>
		</div>
		<div>
			<input onclick='fullPlay()' type='button' value='全屏'/>
			<input onclick='seek2time("295")' type='button' value='跳转295'/></div>
			<input onclick='requestFullScreen("playerDiv")' type='button' value='全屏2'/>
		<div id='player1div' style='position: absolute;z-index:2'>
			<div id="player1" ></div>
		</div>
		<div id='player2div' style='position: absolute;z-index:1'>
			<div id="player2" ></div>
		</div>
	</div>
	
	<div style='display:none' id='clarityDiv' class='c_clarity'>
		<div id='clarityDiv1'>
		</div>
		<div id='clarityDiv2'>
			<input onclick='queryCheck()' type='button' value="确认" />
		</div>
	</div> -->
	
</body>
</html>