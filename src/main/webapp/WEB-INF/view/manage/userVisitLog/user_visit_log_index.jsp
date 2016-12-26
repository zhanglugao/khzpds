<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科幻作品大赛</title>
<jsp:include page="../common/manage_inc.jsp"></jsp:include>
<meta content='width=device-width,initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
<style>
.jumpPageInput{
	width:30px;
	border-left:0px;
	border-top:0px;
	border-right:0px;
	border-bottom:1px solid red;
	text-align:center;
}

.circliful { 
    position: relative;  
} 
.circle-text, .circle-info, .circle-text-half, .circle-info-half { 
    width: 100%; 
    position: absolute; 
    text-align: center; 
    display: inline-block; 
} 
.circle-info, .circle-info-half { 
    color: #999; 
} 
.circliful .fa { 
    margin: -10px 3px 0 3px; 
    position: relative; 
    bottom: 4px; 
} 
</style>
<script src="/js/jquery.circliful.min.js"></script>
<script type="text/javascript">
	var currentSearchNum=0;
	function getData(searchNum){
		if(currentSearchNum==searchNum)return;
		$.ajax({
			url:"/userVisitLog/getData",
			type:"post",
			data:{num:searchNum},
			dataType:"json",
			success:function(data){
				var visitNum=data.visitNum;
				var pcNum=data.pcNum;
				var phoneNum=parseInt(visitNum)-parseInt(pcNum);
				$("#searchNum").text((searchNum+"").split("-")[1]);
				$("#visitNum").text(visitNum);
				
				var percent=0;
				if(pcNum!='0'&&visitNum!='0'){
					percent=(parseFloat(pcNum)/parseFloat(visitNum))*100;
				}
				$("#myStat").remove();
				var html='<div id="myStat" data-dimension="250" data-text="" data-info=""'
					+'data-width="30" data-fontsize="15" data-percent="0" data-fgcolor="#61a9dc"'  
					+'data-bgcolor="#eee" data-fill="#ddd">'
					+'</div> ';
				$("#myStatParent").append(html);
				$("#myStat").attr("data-percent",percent+"");
				//$("#myStat").attr("data-text","桌面"+percent+"%");
				if(percent!=0){
					$("#myStat").attr("data-text","手机"+(100-percent)+"%");
				}
				$('#myStat').circliful();
				
				var refResult=data.refResult;
				$("#resUl").children().remove();
				for(var i=0;i<refResult.length;i++){
					var obj=refResult[i];
					if(obj.ref==''){
						obj.ref="直接访问";
					}
					$("#resUl").append("<li>"+obj.num+"&nbsp;&nbsp;"+obj.ref+"</li>");
				}
				
			},
			error:function(){
				layer.alert(errorText);
			}
		});
		currentSearchNum=searchNum;
	}
	
	$(document).ready(function(){
		getData(-7);
	});
</script>
</head>
<body class="skin-blue">
	<!-- courseyun 后端模板 header -->
	<%@include file="../common/manage_header.jsp"%>
	<div class="wrapper row-offcanvas row-offcanvas-left">
		<!-- courseyun 左部菜单 -->
		<div>
			<%@include file="../common/manage_left.jsp"%>
		</div>
		<aside class="right-side">
			<section class="content-header">
				<h1>首页查询统计</h1>
			</section>
			<section class="content">
				<p>&nbsp;</p>
				<div class="box-body" style="margin-left:20px;">
					<div class="col-lg-2 col-xs-5" style="width:13%">
						<a href="javascript:;" onclick='getData(-7)'>最近7天</a>
						<!-- <input id='name' type="text" class="form-control" placeholder="用户名"> -->
					</div>
					<div class="col-lg-2 col-xs-5" style="width:13%">
						<a href="javascript:;" onclick='getData(-30)'>最近30天</a>
					</div>
					<div class="col-lg-2 col-xs-5" style="width:13%">
						<a href="javascript:;" onclick='getData(-90)'>最近90天</a>
					</div>
					<!-- <div class="box-footer col-lg-2 col-xs-3">
						<button onclick='getData(1)' type="button" class="btn btn-primary">搜索</button>
						&nbsp;&nbsp;
						<button onclick='addUser()' type='button' class='btn btn-primary'>添加用户</button>
					</div> -->
				</div>
				<p>&nbsp;</p><p>&nbsp;</p>
				<div class="row" style="margin-left:20px;font-size:15px;">
					<div class="col-xs-12" >
						<div>独立访客 - 过去<span id='searchNum'></span>天</div>
						<br/>
						<div style='font-size:30px;'><span id='visitNum'></span>人</div>
					</div>
				</div>
				<p>&nbsp;</p><p>&nbsp;</p>
				<div class="row" style="margin-left:20px;font-size:15px;">
					<div class="col-xs-12" >
						<div style='font-size:20px'>
							访客 ________________________________________________
						</div>
						<div id='myStatParent' class='col-xs-2'>
							
						</div>
						<div class='col-xs-2'>
							<ul id='resUl'>
								
							</ul>
						</div>
					</div>
				</div>
			</section>
		</aside>
	</div>
</body>
</html>