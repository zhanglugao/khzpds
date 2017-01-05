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
			url:"/menu/getData",
			type:"post",
			data:"current_page="+currentPage+"&page_size="+pageSize+"&name="+name+"&url="+url,
			dataType:"json",
			success:function(data){
				$(".datatr").remove();
				for(var i=0;i<data.rows.length;i++){
					var obj=data.rows[i];
					var html="<tr class='datatr'><td>"+(i+1)+"</td><td>"+obj.name+"</td><td>"+obj.url+"</td><td>启用</td><td>"
						+"<button class='btn btn-primary' type='button' onclick='addMenu(\""+obj.id+"\",\""+obj.name+"\",\""+obj.url+"\")'>编辑</button>&nbsp;<button class='btn btn-primary' onclick='deleteMenu(\""+obj.id+"\")' type='button'>删除</button></td></tr>";
					$("#dataTable").append(html);
				}
				setPageHtml(data.total_count, "pageDiv", "getData", currentPage,pageSize);
			},error:function(){
				layer.alert(errorText);
			}
		});
	}
	$(document).ready(function(){
		getData(1);
	});
	function addMenu(id,name,url){
		if(typeof(id)!='undefined'){
			$("#menuId").val(id);
		}else{
			$("#menuId").val("");
		}
		if(typeof(name)!='undefined'){
			$("#menuName").val(name);
		}else{
			$("#menuName").val("");
		}
		if(typeof(url)!='undefined'){
			$("#menuUrl").val(url);
		}else{
			$("#menuUrl").val("");
		}
		layer.open({
			type: 1,
			content:$("#menuDiv"),
			shadeClose: true,//开启遮罩关闭
			title:false,
			area: ['400px', '300px']
		});
	}
	
	function deleteMenu(id){
		layer.confirm('确定要删除么', {
		    btn: ['确定','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(index){
		    layer.close(index);
			$.ajax({
				url:"/menu/delete",
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
	
	function confirmAdd(){
		var name=$("#menuName").val();
		var url=$("#menuUrl").val();
		if($.trim(name)==''){
			layer.tips("请填写菜单名称","#menuName",{tips:[2,tipsColor]});return;
		}
		if($.trim(name)==''){
			layer.tips("请填写菜单链接","#menuUrl",{tips:[2,tipsColor]});return;
		}
		var id=$("#menuId").val();
		$.ajax({
			url:"/menu/add",
			data:{name:name,url:url,id:id},
			type:"post",
			dataType:"json",
			success:function(data){
				if(data.status=='0'){
					layer.closeAll();
					if(id!=''){
						layer.msg("编辑成功",{icon:1});
					}else{
						layer.msg("添加成功",{icon:1});
					}
					getData(1);
				}else{
					layer.alert(data.error_desc);
				}
			},
			error:function(){
				layer.alert(errorText);
			}
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
				<h1>菜单管理</h1>
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
						<button onclick='addMenu()' type='button' class='btn btn-primary'>添加菜单</button>
					</div>
				</div>
				
				<div class="row" >
					<div class="col-xs-12" >
						<div class="box">
							<div class="box-body table-responsive">
								<table class="table table-hover table-bordered" id='dataTable'>
									<tr>
										<th>序号</th>
										<th>菜单名称</th>
										<th>菜单链接</th>
										<th>状态</th>
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
	<div id='menuDiv' style="display:none;">
		<input type='hidden' id='menuId'/>
		<div style="margin-top:20px;margin-left:20px;">
			<label>菜单名称</label>
			<input type='text' id='menuName' />
		</div>
		<div style="margin-top:20px;margin-left:20px;">
			<label>菜单链接</label>
			<input type='text' id='menuUrl' />
		</div>
		
		<div style="margin-top:20px;margin-left:20px;">
			<button onclick='confirmAdd()' type="button" class="btn btn-primary">确认</button>
		</div>
	</div>
</body>
</html>