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
	function save(){
		var password=$("#password").val();
		var password2=$('#password2').val();
		if(password==''){
			layer.msg("密码不能为空",{icon:4});return;
		}
		if(password.length<4){
			layer.msg("密码过短",{icon:4});return;
		}
		if(password.length>20){
			layer.msg("密码过长",{icon:4});return;
		}
		if(password!=password2){
			layer.msg("两次填写密码必须相等",{icon:4});return;
		}
		$.ajax({
			url:"/user/changeUserInfo",
			type:"post",
			data:{userName:"${editUser.userName}",password:password,realName:$("#realName").val()},
			dataType:"json",
			success:function(data){
				if(data.status=='0'){
					layer.msg("修改成功",{icon:1});	
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
	<%@include file="../common/manage_header.jsp"%>
	<div class="wrapper row-offcanvas row-offcanvas-left">
		<div>
			<%@include file="../common/manage_left.jsp"%>
		</div>
		<aside class="right-side">
			<section class="content-header">
				<h1>个人资料修改</h1>
			</section>
			<form id="saveActivity" class="form-horizontal">
				<input id="id" name="id" type="hidden" value="${role.id }">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-5" style='margin-top:20px;'>
							<div class="form-group mt10">
								<label class="control-label col-xs-4">真实姓名：</label>
								<div class="col-xs-8">
									<input id='realName' type="text" class="form-control" name="realName" value="${editUser.realName }">
								</div>
							</div>
							<div class="form-group mt10">
								<label class="control-label col-xs-4">请输入新密码：</label>
								<div class="col-xs-8">
									<input id='password' type="password" class="form-control" name="password" value="${editUser.password }">
								</div>
							</div>
							<div class="form-group mt10">
								<label class="control-label col-xs-4">请再次输入新密码：</label>
								<div class="col-xs-8">
									<input id='password2' type="password" class="form-control" name="password2" value="${editUser.password }">
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			<div class="box-body">
				<div class="box-footer col-xs-12 " style='margin-left:300px;margin-top:30px;'>
					<button type="button" class="btn btn-primary" onclick="save()">确定</button>
				</div>
			</div>
		</aside>
	</div>
</body>
</html>