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
		var pageSize=5;
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
					html+="&nbsp;<button onclick='toShowItem(\""+obj.id+"\")' type='button' class='btn btn-primary'>选择</button>";
					html+="</td></tr>";
					$("#dataTable").append(html);
				}
				setPageHtml(data.total_page, "next", "getData", currentPage);
			},error:function(){
				layer.alert(errorText);
			}
		});
	}
	
	/**
	*	to编辑页面
	*/
	function toShowItem(id){
		//layer.msg("敬请期待");
		window.location.href="/reviewResult/toShowItem?id="+id;
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
				<h1>评审结果管理</h1>
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
				<div class="box-footer clearfix">
					<ul class="pagination pagination-sm no-margin pull-left">
						<li id='previous'><a href="##" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						<li id='next'><a href="##" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
					</ul>
				</div>
			</section>
		</aside>
	</div>
</body>
</html>