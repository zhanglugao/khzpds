<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科幻作品大赛</title>
<jsp:include page="../common/manage_inc.jsp"></jsp:include>
<script src="/js/pageset.js" type="text/javascript"></script>
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
</style>
<script type="text/javascript">
	function getData(currentPage){
		var pageSize=10;
		var year=$("#year").val();
		var name=$("#name").val();
		var status=$("#status").val();
		$.ajax({
			url:"/activity/getData",
			type:"post",
			data:"current_page="+currentPage+"&page_size="+pageSize+"&year="+year+"&name="+name+"&status="+status,
			dataType:"json",
			success:function(data){
				$(".datatr").remove();
				for(var i=0;i<data.rows.length;i++){
					var obj=data.rows[i];
					var html="<tr class='datatr'><td>"+obj.name+"</td><td>"+obj.year+"</td><td>"+obj.startTime+"</td><td>"+obj.endTime+"</td>"+
						"<td>"+obj.status+"</td><td>"+obj.createTime+"</td><td>";
					if(obj.status=='未发布'){
						html+="&nbsp;<button onclick='toEdit(\""+obj.id+"\")' type='button' class='btn btn-primary'>编辑</button>";
						html+="&nbsp;<button onclick='publish(\""+obj.id+"\")' type='button' class='btn btn-primary'>发布</button>";
						html+="&nbsp;<button onclick='deleteActivity(\""+obj.id+"\")' type='button' class='btn btn-primary'>删除</button>";
						html+="&nbsp;<button onclick='terminate(\""+obj.id+"\")' type='button' class='btn btn-primary'>废弃</button>";
					}
					if(obj.status=='已发布'){
						html+="&nbsp;<button onclick='toEdit(\""+obj.id+"\")' type='button' class='btn btn-primary'>编辑</button>";
						html+="&nbsp;<button onclick='turnEnd(\""+obj.id+"\")' type='button' class='btn btn-primary'>中止</button>";
					}
					if(obj.status=='已结束'){
						html+="&nbsp;<button onclick='publish(\""+obj.id+"\")' type='button' class='btn btn-primary'>重新打开</button>";
					}
					if(obj.status=='已废弃'){
						html+="&nbsp;<button onclick='toEdit(\""+obj.id+"\")' type='button' class='btn btn-primary'>编辑</button>";
						html+="&nbsp;<button onclick='publish(\""+obj.id+"\")' type='button' class='btn btn-primary'>发布</button>";
					}
					html+="</td></tr>";
					$("#dataTable").append(html);
				}
				setPageHtml(data.total_count, "pageDiv", "getData", currentPage,pageSize);
			},error:function(){
				layer.alert(errorText);
			}
		});
	}
	
	/**
	*	to编辑页面
	*/
	function toEdit(id){
		window.location.href="/activity/toEdit?id="+id;
	}
	
	function turnEnd(id){
		layer.confirm('确定要中止么', {
		    btn: ['确定','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(index){
		    layer.close(index);
			$.ajax({
				url:"/activity/turnEnd",
				data:{id:id},
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.status=='0'){
						layer.closeAll();
						layer.msg("中止成功",{icon:1});
						getData(1);
					}else{
						layer.alert(data.error_desc);
					}
				},
				error:function(){
					layer.alert(errorText);
				}
			});
		});
	}
	
	/***
	*	废弃活动
	*/
	function terminate(id){
		layer.confirm('确定要废弃么', {
		    btn: ['确定','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(index){
		    layer.close(index);
			$.ajax({
				url:"/activity/terminate",
				data:{id:id},
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.status=='0'){
						layer.closeAll();
						layer.msg("废弃成功",{icon:1});
						getData(1);
					}else{
						layer.alert(data.error_desc);
					}
				},
				error:function(){
					layer.alert(errorText);
				}
			});
		});
	}
	
	/***
	*	删除活动
	*/
	function deleteActivity(id){
		layer.confirm('确定要删除么', {
		    btn: ['确定','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(index){
		    layer.close(index);
			$.ajax({
				url:"/activity/delete",
				data:{id:id},
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.status=='0'){
						layer.closeAll();
						layer.msg("删除成功",{icon:1});
						getData(1);
					}else{
						layer.alert(data.error_desc);
					}
				},
				error:function(){
					layer.alert(errorText);
				}
			});
		});
	}
	
	/***
	*	发布活动
	*/
	function publish(id){
		$.ajax({
			url:"/activity/publish",
			data:{id:id},
			type:"post",
			dataType:"json",
			success:function(data){
				if(data.status=='0'){
					layer.msg("发布成功",{icon:1});
					getData(1);
				}else if(data.status=='1'){
					layer.alert(data.error_desc);
				}
			},error:function(){
				layer.alert(errorText);
			}
		});
	}
	$(document).ready(function(){
		loadDictionarySelect("status",${status});
		getData(1);
	});
	
	function toAdd(){
		window.location.href="/activity/toAdd";
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
			<section class="content">
				<div class="selectbox">
					<div class="form-group">
                           <div class="col-sm-1">
                                <select id="status" class="selectpicker show-tick form-control" data-live-search="false">
                                	<option value=''>活动状态</option>
                                </select>
                            </div>
                     </div>
				</div>
				<div class="box-body">
					<div id='createUserDiv' class="col-lg-2 col-xs-5" style="width:13%">
						<input id='year' name='year' id='createUser' type="text" class="form-control" placeholder="活动年份">
					</div>
					<div class="col-lg-2 col-xs-5" style="width:13%">
						<input id='name' type="text" class="form-control" placeholder="活动名称">
					</div>
					<!-- <div class="col-lg-2 col-xs-5" style="width:13%">
						<input id='name' type="text" class="form-control" placeholder="课程名称">
					</div> -->
					<div class="box-footer col-lg-2 col-xs-3">
						<button onclick='getData(1)' type="button" class="btn btn-primary">搜索</button>
						&nbsp;&nbsp;
						<button onclick='toAdd()' type='button' class='btn btn-primary'>创建活动</button>
					</div>
				</div>
				
				<div class="row" >
					<div class="col-xs-12" >
						<div class="box">
							<div class="box-body table-responsive">
								<table class="table table-hover table-bordered" id='dataTable'>
									<tr>
										<th>活动名称</th>
										<th>活动年度</th>
										<th>开始时间</th>
										<th>结束时间</th>
										<th>状态</th>
										<th>创建时间</th>
										<th>操作</th>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div id="pageDiv">
				</div>
			</section>
		</aside>
	</div>
</body>
</html>