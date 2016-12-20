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
</style>
<script src="/js/pageset.js" type="text/javascript"></script>
<script type="text/javascript">
	function getData(currentPage){
		var pageSize=10;
		var name=$("#name").val();
		var url=$("#url").val();
		$.ajax({
			url:"/role/getData",
			type:"post",
			data:"current_page="+currentPage+"&page_size="+pageSize+"&name="+name+"&url="+url,
			dataType:"json",
			success:function(data){
				$(".datatr").remove();
				for(var i=0;i<data.rows.length;i++){
					var obj=data.rows[i];
					var html="<tr class='datatr'><td>"+(i+1)+"</td><td>"+obj.name+"</td><td>"
						+"<button class='btn btn-primary' type='button' onclick='toAdd(\""+obj.id+"\")'>编辑</button>&nbsp;<button class='btn btn-primary' onclick='deleteRole(\""+obj.id+"\")' type='button'>删除</button></td></tr>";
					$("#dataTable").append(html);
				}
				setPageHtml(data.total_page, "next", "getData", currentPage);
			},error:function(){
				layer.alert(errorText);
			}
		});
	}
	
	function toAdd(id){
		if(typeof(id)!='undefined'){
			window.location.href="/role/toAdd?id="+id;
		}else{
			window.location.href="/role/toAdd";
		}
		
	}
	
	$(document).ready(function(){
		getData(1);
	});
	
	function deleteRole(id){
		layer.confirm('确定要删除么', {
		    btn: ['确定','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(index){
		    layer.close(index);
			$.ajax({
				url:"/role/delete",
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
				<h1>角色管理</h1>
				<!-- 首页链接 -->
				<!-- <ol class="breadcrumb">
					<li><a href="../index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
                </ol> -->
			</section>
			<section class="content">
				<!-- <div class="selectbox">
					<div class="form-group">
                           <div class="col-sm-1">
                                <select id="status" class="selectpicker show-tick form-control" data-live-search="false">
                                	<option value=''>课程状态</option>
                                </select>
                            </div>
                     </div>
				</div> -->
				<div class="box-body">
					<div class="col-lg-2 col-xs-5" style="width:13%">
						<input id='name' type="text" class="form-control" placeholder="菜单名称">
					</div>
					<div class="col-lg-2 col-xs-5" style="width:13%">
						<input id='url' type="text" class="form-control" placeholder="菜单链接">
					</div>
					<div class="box-footer col-lg-2 col-xs-3">
						<button onclick='getData(1)' type="button" class="btn btn-primary">搜索</button>
						&nbsp;&nbsp;
						<button onclick='toAdd()' type='button' class='btn btn-primary'>添加角色</button>
					</div>
				</div>
				
				<div class="row" >
					<div class="col-xs-12" >
						<div class="box">
							<div class="box-body table-responsive">
								<table class="table table-hover table-bordered" id='dataTable'>
									<tr>
										<th>序号</th>
										<th>角色名称</th>
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