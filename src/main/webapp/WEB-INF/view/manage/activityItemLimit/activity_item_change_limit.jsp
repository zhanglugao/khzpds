<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科幻作品大赛</title>
<jsp:include page="../common/manage_inc.jsp"></jsp:include>
<meta content='width=device-width,initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
<script type="text/javascript">
	var r = /^\+?[1-9][0-9]*$/;
	$(document).ready(function(){
		$("#saveBtn").click(function(){
			var objs=$(".orgClass");
			var jsonData='[';
			for(var i=0;i<objs.length;i++){
				var obj=$(objs[i]);
				var value=obj.val();
				if(value!=""&&!r.test(value)){
					if(value!='0'){
						layer.alert("第"+(i+1)+"行限额数目填写非法。");
						return;
					}
				}
				if(value!=''){
					var d='{"id":"'+obj.attr("id")+'","value":"'+value+'"}';
					if(jsonData=='['){
						jsonData+=d;
					}else{
						jsonData+=","+d;
					}
				}
			}
			jsonData+="]";
			layer.load(1);
			$.ajax({
				url:"/activityItemLimit/changeLimit",
				type:"post",
				data:"data="+jsonData+"&id=${param.id}",
				dataType:"json",
				success:function(data){
					if(data.status=='0'){
						layer.msg("保存成功",{icon:1});
					}else if(data.status=='1'){
						layer.alert(data.error_desc);
					}
				},
				error:function(){
					layer.alert(errorText)
				}
			});
			});
		});
	});
	
	function returnIndex(){
		window.location.href="/activityItemLimit/toShowItem?id=${param.activityId}";
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
				<h1>活动限额设置</h1>
			</section>
			<section class="content">
				<div class="form-group mt10" style='margin-top:5px;'>
							<label class="control-label  col-xs-3"><button id='saveBtn' type='button' class='btn btn-primary'>保存</button>
								&nbsp;
							<button onclick='returnIndex()' type='button' class='btn btn-primary'>返回</button></label>
							<div class="col-sm-4">
								
                       		</div>
						</div>
				<div class="row" >
					<div class="col-xs-12" >
						<div class="box">
							<div class="box-body table-responsive">
								<table class="table table-hover table-bordered" id='dataTable'>
									<tr>
										<th>序号</th>
										<th>项目名称</th>
										<th>组织机构名称</th>
										<th>限额数目</th>
									</tr>
									<c:forEach items="${orgList}" var="org" varStatus="vs">
										<tr>
											<td>${vs.count }</td>
											<td>${item.name }</td>
											<td>${org.name }</td>
											<td><input class='orgClass' id='${org.id }' type='text' value='${org.additional }' /></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>
			</section>
		</aside>
	</div>
</body>
</html>