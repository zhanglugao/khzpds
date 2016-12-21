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
		var mail=$("#mail").val();
		var realName=$("#realName").val();
		$.ajax({
			url:"/user/getData",
			type:"post",
			data:"current_page="+currentPage+"&page_size="+pageSize+"&name="+name+"&mail="+mail+"&realName="+realName+"&type=1",
			dataType:"json",
			success:function(data){
				$(".datatr").remove();
				for(var i=0;i<data.rows.length;i++){
					var obj=data.rows[i];
					if(typeof(obj.mail)=='undefined'){
						obj.mail='';
					}
					if(typeof(obj.realName)=='undefined'){
						obj.realName='';
					}
					var html="<tr class='datatr'><td>"+(i+1)+"</td><td>"+obj.userName+"</td><td>"+obj.mail+"</td><td>"+obj.realName+"</td><td>"+obj.roleNames+"</td><td>"
						+"<button class='btn btn-primary' type='button' onclick='addUser(\""+obj.id+"\",\""+obj.userName+"\",\""+obj.mail+"\",\""+obj.realName+"\",\""+obj.roleIds+"\")'>编辑</button>&nbsp;<button class='btn btn-primary' onclick='resetpwd(\""+obj.id+"\")' type='button'>重置密码</button></td></tr>";
					$("#dataTable").append(html);
				}
				setPageHtml(data.total_page, "next", "getData", currentPage);
			},error:function(){
				layer.alert(errorText);
			}
		});
	}
	$(document).ready(function(){
		getData(1);
		var roles=${roles};
		for(var i=0;i<roles.length;i++){
			var obj=roles[i];
			if(i==0){
				var html="&nbsp;<input name='roleChecks' type='checkbox' value='"+obj.id+"' />"+obj.name+"<br/>";
				$("#roleDiv").append(html);
			}else{
				var html="&nbsp;<input name='roleChecks' style='margin-left:30px;' type='checkbox' value='"+obj.id+"' />"+obj.name+"<br/>";
				$("#roleDiv").append(html);
			}
		}
	});
	function addUser(id,userName,mail,realName,roleIds){
		if(typeof(id)!='undefined'){
			$("#userId").val(id);
		}else{
			$("#userId").val("");
		}
		if(typeof(userName)!='undefined'){
			$("#userName").val(userName);
		}else{
			$("#userName").val("");
		}
		if(typeof(mail)!='undefined'){
			$("#userMail").val(mail);
		}else{
			$("#userMail").val("");
		}
		if(typeof(realName)!='undefined'){
			$("#userRealName").val(realName);
		}else{
			$("#userRealName").val("");
		}
		if(typeof(roleIds)!='undefined'){
			var roles=roleIds.split(",");
			$("input[type=checkbox][name=roleChecks]").each(function(){
				var f=false;
				for(var i=0;i<roles.length;i++){
					if($(this).val()==roles[i]){
						f=true;
						$(this).attr("checked",true);
						break;
					}
				}
				if(!f){
					$(this).attr("checked",false);
				}
			});
		}else{
			$("input[type=checkbox][name=roleChecks]:checked").each(function(){
				$(this).attr("checked",false);
			});
		}
		layer.open({
			type: 1,
			content:$("#userDiv"),
			shadeClose: true,//开启遮罩关闭
			title:false,
			area: ['500px', '500px']
		});
	}
	
	function resetpwd(id){
		layer.confirm('确定要重置密码为111111么', {
		    btn: ['确定','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(index){
		    layer.close(index);
			$.ajax({
				url:"/user/resetpwd",
				data:{id:id,type:"1"},
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.status=='0'){
						layer.closeAll();
						layer.msg("重置密码成功",{icon:1});
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
		var userName=$("#userName").val();
		var mail=$("#userMail").val();
		var realName=$("#userRealName").val();
		if($.trim(userName)==''){
			layer.tips("请填写用户名","#userName",{tips:[2,tipsColor]});return;
		}
		/* if($.trim(mail)==''){
			layer.tips("请填写邮箱","#userMail",{tips:[2,tipsColor]});return;
		}
		if($.trim(realName)==''){
			layer.tips("请填写真实姓名","#userRealName",{tips:[2,tipsColor]});return;
		} */
		var roleIds="";
		$("input[name=roleChecks][type=checkbox]:checked").each(function(){
			if(roleIds==''){
				roleIds=$(this).val();
			}else{
				roleIds+=","+$(this).val();
			}
		});
		if(roleIds==''){
			layer.msg("请选择角色",{icon:4});return;
		}
		var id=$("#userId").val();
		$.ajax({
			url:"/user/add",
			data:{userName:userName,mail:mail,realName:realName,id:id,roleIds:roleIds,type:"1"},
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
				<h1>用户管理</h1>
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
						<input id='name' type="text" class="form-control" placeholder="用户名">
					</div>
					<div class="col-lg-2 col-xs-5" style="width:13%">
						<input id='mail' type="text" class="form-control" placeholder="邮箱">
					</div>
					<div class="col-lg-2 col-xs-5" style="width:13%">
						<input id='realName' type="text" class="form-control" placeholder="真实姓名">
					</div>
					<div class="box-footer col-lg-2 col-xs-3">
						<button onclick='getData(1)' type="button" class="btn btn-primary">搜索</button>
						&nbsp;&nbsp;
						<button onclick='addUser()' type='button' class='btn btn-primary'>添加管理员</button>
					</div>
				</div>
				
				<div class="row" >
					<div class="col-xs-12" >
						<div class="box">
							<div class="box-body table-responsive">
								<table class="table table-hover table-bordered" id='dataTable'>
									<tr>
										<th>序号</th>
										<th>用户名</th>
										<th>邮箱</th>
										<th>真实姓名</th>
										<th>角色</th>
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
	<div id='userDiv' style="display:none;">
		<input type='hidden' id='userId'/>
		<div style="margin-top:20px;margin-left:20px;">
			<span style='color:red;'>*</span><label>用户名</label>
			<input type='text' id='userName' />
		</div>
		<div style="margin-top:20px;margin-left:20px;">
			<label>邮箱</label>
			<input type='text' id='userMail' />
		</div>
		<div style="margin-top:20px;margin-left:20px;">
			<label>真实姓名</label>
			<input type='text' id='userRealName' />
		</div>
		<div id='roleDiv' style="margin-left:20px;margin-top:20px;">
			<label>角色</label>
		</div>
		<div style="margin-top:20px;margin-left:20px;">
			<span style='color:red'>注:新添加的管理员密码默认为111111</span>
		</div>
		<div style="margin-top:20px;margin-left:20px;">
			<button onclick='confirmAdd()' type="button" class="btn btn-primary">确认</button>
		</div>
	</div>
</body>
</html>