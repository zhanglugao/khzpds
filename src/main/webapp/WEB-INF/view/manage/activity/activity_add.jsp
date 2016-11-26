<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科幻作品大赛管理后台</title>
<jsp:include page="../common/manage_inc.jsp"></jsp:include>
<meta content='width=device-width,initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
<script type="text/javascript">
$(document).ready(function(){
	var date=new Date;
	var year=date.getFullYear();
	for(var i=0;i<5;i++){
		$("#year").append("<option value='"+year+"'>"+year+"</option>");
		year++;
	}
});
function save(){
	var year=$("#year").val();
	var name=$("#name").val();
	if($.trim(name)==''){
		layer.tips("请填写活动名称","#name",{tips:[2,'#E3170D']});return;
	}
	var startTime=$("#startTime").val();
	if(startTime==''){
		layer.tips("请选择活动开始时间","#startTime",{tips:[2,'#E3170D']});return;
	}
	var date=new Date();
	var nowDate=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	if(startTime<nowDate){
		layer.tips("活动开始时间必须大于当前时间","#startTime",{tips:[2,'#E3170D']});return;
	}
	var endTime=$("#endTime").val();
	if(endTime==''){
		layer.tips("请选择活动结束时间","#endTime",{tips:[2,'#E3170D']});return;
	}
	var firstEndTime=$("#firstEndTime").val();
	if(firstEndTime==''){
		layer.tips("请选择一轮评审完毕时间","#firstEndTime",{tips:[2,'#E3170D']});return;
	}
	var secondEndTime=$("#secondEndTime").val();
	if(secondEndTime==''){
		layer.tips("请选择二轮评审完毕时间","#secondEndTime",{tips:[2,'#E3170D']});return;
	}
	if(secondEndTime>endTime){
		layer.tips("活动结束时间不能小于二轮评审时间","#endTime",{tips:[2,'#E3170D']});return;
	}
	if(startTime>firstEndTime){
		layer.tips("一轮评审时间不能小于活动开始时间","#firstEndTime",{tips:[2,'#E3170D']});return;
	}
	if(firstEndTime>secondEndTime){
		layer.tips("二轮评审时间不能小于一轮评审时间","#secondEndTime",{tips:[2,'#E3170D']});return;
	}
	$.ajax({
		url:"/activity/addActivity",
		data:$("#saveActivity").serialize(),
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.status=='0'){
				layer.msg("添加成功",{icon:1});
				 setTimeout("jumpList()",1000); 
			}else{
				layer.alert(data.error_desc);
			}
		},
		error:function(){
			layer.alert(errorText);
		}
	});
	
}
function jumpList(){
	window.location.href="/activity/index";
}
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
				<h1>活动管理</h1>
				<!-- 首页链接 -->
				<!-- <ol class="breadcrumb">
					<li><a href="../index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
                </ol> -->
			</section>
			<form id="saveActivity" class="form-horizontal">
				<input id="photo_url" name="photo_url" type="hidden" value="">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-4">
								<div class="form-group mt10" style='margin-top:50px;'>
									<label class="control-label  col-xs-3">活动年度：</label>
									<!-- <div class="col-xs-9">
										<input type="text" class="form-control" name="name" value="">
									</div> -->
									<div class="col-sm-4">
		                                <select name='year' id="year" class="selectpicker show-tick form-control" data-live-search="false">
		                                </select>
                            		</div>
								</div>
								<%-- <div class="mt10 form-group">
									<label class="control-label col-xs-3">级别：</label>
									<div class="col-xs-9">
									 <input id="selectDate" type="hidden">
										<select name='level_id' class="form-control" id="mySelect">
										 <option value=''>---请选择---</option>
										  <c:forEach var="leave" items="${leaves}">
										  	 <option value='${leave.value}'>${leave.name}</option>
										  </c:forEach>
										</select>
									</div>
								</div> --%>
								<div class="form-group mt10">
									<label class="control-label col-xs-3">活动名称：</label>
									<div class="col-xs-9">
									<input id='name' type="text" class="form-control" name="name" value="">
									</div>
								</div>
								<div class="form-group mt10">
									<label class="control-label col-xs-3">开始时间：</label>
									<div class="col-xs-9">
									<input id='startTime' readonly="readonly" onclick='laydate({istime: true, format: "YYYY-MM-DD"})' type="text" class="form-control" name="startTime" value="">
									</div>
								</div>
								<div class="form-group mt10">
									<label class="control-label col-xs-3">结束时间：</label>
									<div class="col-xs-9">
									<input id='endTime' readonly="readonly" onclick='laydate({istime: true, format: "YYYY-MM-DD"})' type="text" class="form-control" name="endTime" value="">
									</div>
								</div>
							</div>
							<div class="col-xs-11">
								<div class="form-group mt10  col-xs-6">
										<label class="control-label col-xs-2">活动介绍：</label>
										<div class="col-xs-10">
											<textarea id='description' class="form-control" rows="5" name="description" 
												placeholder=""></textarea>
										</div>
									</div>
							</div>
							<div class="col-xs-4">
								
								<div class="form-group mt10">
									<label class="control-label col-xs-3">比赛项目：</label>
									<!-- <div class="col-xs-9">
									<input id='startTime' readonly="readonly" onclick='laydate({istime: true, format: "YYYY-MM-DD"})' type="text" class="form-control" name="startTime" value="">
									</div> -->
								</div>
								<div class="form-group mt10">
									<label class="control-label col-xs-3">一轮评审完毕时间：</label>
									<div class="col-xs-9">
									<input id='firstEndTime' readonly="readonly" onclick='laydate({istime: true, format: "YYYY-MM-DD"})' type="text" class="form-control" name="startTime" value="">
									</div>
								</div>
								<div class="form-group mt10">
									<label class="control-label col-xs-3">二轮评审完毕时间：</label>
									<div class="col-xs-9">
									<input id='secondEndTime' readonly="readonly" onclick='laydate({istime: true, format: "YYYY-MM-DD"})' type="text" class="form-control" name="endTime" value="">
									</div>
								</div>
							</div>
							<%-- <div class="form-group mt10 col-xs-8"> 
                                  <label class="control-label col-xs-4">头像图片：</label>
                                  <div class="col-xs-8">
                                     <div id='imgDiv' style='display:none;'><img id='img' src="${videoPackage.showPictureUrl }" width="160" height="90"></div>
                                     <div class="noline"> 
                                         <div class='picker'>选择图片</div>
                                     </div>
                                   </div> 
                            </div> --%>
							<!-- <div class="col-xs-12">
								<div class="form-group mt10 col-xs-6">
									<label class="control-label col-xs-2">电子邮件：</label>
									<div class="col-xs-10">
									<input type="text" class="form-control" name="email" value="">
									</div>
								</div>
								<div class="form-group mt10 col-xs-6">
									<label class="control-label col-xs-2">移动电话：</label>
									<div class="col-xs-10">
									<input type="text" class="form-control" name="mobile" value="">
									</div>
								</div>
								<div class="form-group mt10 col-xs-6">
									<label class="control-label col-xs-2">单位名称：</label>
									<div class="col-xs-10">
									<input type="text" class="form-control" name="company_name" value="">
									</div>
								</div>
								
								<div class="form-group mt10 col-xs-6">
									<label class="control-label col-xs-2">单位地址：</label>
									<div class="col-xs-10">
									<input type="text" class="form-control" name="company_address" value="">
									</div>
								</div>
								<div class="form-group mt10  col-xs-6">
									<label class="control-label col-xs-2">教师特点：</label>
									<div class="col-xs-10">
										<textarea class="form-control" rows="5" name="feature" 
											placeholder=""></textarea>
									</div>
								</div>
								<div class="form-group col-xs-6">
									<label class="control-label col-xs-2">教师简介：</label>
									<div class="col-xs-10">
										<textarea class="form-control" rows="5" name="teacher_desc"
											placeholder=""></textarea>
									</div>
								</div>
						</div> -->
					</div>
				</div>
			</form>
			<div class="box-body">
				<div class="box-footer col-xs-12 " style='margin-left:100px;margin-top:30px;'>
					<button type="button" class="btn btn-primary" onclick="save()">添加</button>
				</div>
			</div>
		</aside>
	</div>
</body>
</html>