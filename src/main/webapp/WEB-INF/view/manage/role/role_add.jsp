<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科幻作品大赛</title>
<jsp:include page="../common/manage_inc.jsp"></jsp:include>
<meta content='width=device-width,initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
<script type="text/javascript">
$(document).ready(function(){
	var menus=${menus};
	for(var i=0;i<menus.length;i++){
		var obj=menus[i];
		var html="<tr><td>";
		if(typeof(obj.roleId)!='undefined'){
			html+="<input name='menuCheck' type='checkbox' value='"+obj.id+"' checked='checked'/>";
		}else{
			html+="<input name='menuCheck' type='checkbox' value='"+obj.id+"' />";
		}
		html+="</td><td>"+obj.name+"</td></tr>";
		$("#chooseMenuTable").append(html);
	}
});

function menuChoose(){
	layer.open({
		type: 1,
		content:$("#chooseMenuDiv"),
		shadeClose: true,//开启遮罩关闭
		title:false,
		area: ['400px', '300px']
	});
}

function save(){
	var name=$("#name").val();
	if($.trim(name)==''){
		layer.tips("请填写角色名称","#name",{tips:[2,tipsColor]});return;
	}
	var menuIds="";
	$('input[type=checkbox][name=menuCheck]:checked').each(function() {
		if(menuIds==''){
			menuIds=$(this).val();
		}else{
			menuIds+=","+$(this).val();
		}
	});
	if(menuIds==''){
		layer.tips("请为该角色分配菜单","#chooseBtn",{tips:[2,tipsColor]});return;
	}
	var name=$("#name").val();
	var id=$("#id").val();
	$.ajax({
		url:"/role/add",
		data:{name:name,id:id,menuIds:menuIds},
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.status=='0'){
				layer.closeAll();
				window.location.href="/role/index";
			}else{
				layer.alert(data.error_desc);
			}
		},error:function(){
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
				<h1>角色添加</h1>
				<!-- 首页链接 -->
				<!-- <ol class="breadcrumb">
					<li><a href="../index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
                </ol> -->
			</section>
			<form id="saveActivity" class="form-horizontal">
				<input id="id" name="id" type="hidden" value="${role.id }">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-4" style='margin-top:20px;'>
							<div class="form-group mt10">
								<label class="control-label col-xs-3">角色名称：</label>
								<div class="col-xs-9">
								<input id='name' type="text" class="form-control" name="name" value="${role.name }">
								</div>
							</div>
							
							<div class="form-group mt10">
								<label class="control-label col-xs-3">菜单分配：</label>
								<div class="col-xs-9">
									<button id='chooseBtn' type='button' class='btn btn-primary' onclick='menuChoose()'>菜单选择</button>
								</div>
							</div>
						</div>
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
	
	<div id='chooseMenuDiv' style='display:none'>
		<table id='chooseMenuTable' style='margin-top:15px;margin-left:20px;'>
		</table>
		<button style='margin-top:15px;margin-left:20px;' type='button' class='btn btn-primary' onclick='javascript:layer.closeAll();'>确定</button>
	</div>
</body>
</html>