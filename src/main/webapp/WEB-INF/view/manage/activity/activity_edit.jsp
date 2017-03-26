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
	$(document).ready(function(){
		
	});
	function saveActivity(){
		var name=$("#activityName").val();
		if($.trim(name)==''){
			layer.msg("活动名称不能为空",{icon:4});return;
		}
		$.ajax({
			url:"/activity/editName",
			data:{id:"${activity.id}",name:name},
			type:"post",
			dataType:"json",
			success:function(data){
				if(data.status=='0'){
					layer.msg("保存成功",{icon:1});
				}else if(data.status=='1'){
					layer.alert(data.error_desc);
				}
			},error:function(){
				layer.alert(errorText);
			}
		});
	}
	//结束报名
	function changeItemStatus(optionName,id,type){
		layer.confirm('确定要'+optionName+'么', {
		    btn: ['确定','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(index){
		    layer.close(index);
			$.ajax({
				url:"/activity/changeItemStatus",
				data:{id:id,type:type},
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.status=='0'){
						layer.closeAll();
						layer.msg("操作成功",{icon:1});
						location.reload();
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
	var limitIndex;
	function setItemTakeScoreLimit(itemId,vdef1,vdef2){
		$("#itemId").val(itemId);
		$("#vdef1").val(vdef1);
		$("#vdef2").val(vdef2);
		limitIndex=layer.open({
			type: 1,
			content:$("#takeScoreLimitDiv"),
			scrollbar: false,
			shadeClose: true,//开启遮罩关闭
			title:false,
			area: ['400px', '200px']
		});
	}
	function saveLimit(){
		var vdef1=$("#vdef1").val();
		var vdef2=$("#vdef2").val();
		if(isNaN(vdef1)||isNaN(vdef2)){
			layer.msg("请填写数字",{icon:4});
			return;
		}
		$.ajax({
			url:"/activity/setItemTakeScoreLimit",
			type:"post",
			success:function(data){
				if(data.status=='0'){
					layer.msg("设置成功",{icon:1});
					layer.close(limitIndex);
					window.location.reload();
				}
			},
			dataType:"json",
			data:$("#limitForm").serialize()
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
				<h1>活动编辑</h1>
			</section>
			<section class="content">
				<div class="form-group mt10" style='margin-top:5px;'>
							<label class="control-label  col-xs-1">活动名称：</label>
							<div class="col-sm-4">
								<input style='width:300px;' type='text' id='activityName' value="${activity.name}"/>
								<button onclick='saveActivity()' type='button' class='btn btn-primary'>保存</button>
                       		</div>
						</div>
				<div class="row" >
					<div class="col-xs-12" >
						<div class="box">
							<div class="box-body table-responsive">
								<table class="table table-hover table-bordered" id='dataTable'>
									<tr>
										<th>项目名称</th>
										<th>项目类型</th>
										<th>项目状态</th>
										<th>创建人</th>
										<th>发布时间</th>
										<th>报名截止时间</th>
										<th>一轮评审开始时间</th>
										<th>一轮评审结束时间</th>
										<th>二轮评审结束时间</th>
										<th>操作</th>
									</tr>
									<c:forEach items="${items}" var="item">
										<tr>
											<td>${item.name }</td>
											<td>${item.type }</td>
											<td>${item.statusName }</td>
											<td>${item.createUser }</td>
											<td><fmt:formatDate value="${item.publishTime }" pattern="yyyy-MM-dd"/></td>
											<td><fmt:formatDate value="${item.userApplyEndtime }" pattern="yyyy-MM-dd"/></td>
											<td><fmt:formatDate value="${item.firstReviewStarttime }" pattern="yyyy-MM-dd"/></td>
											<td><fmt:formatDate value="${item.firstReviewEndtime }" pattern="yyyy-MM-dd"/></td>
											<td><fmt:formatDate value="${item.secondReviewEndtime }" pattern="yyyy-MM-dd"/></td>
											<c:if test="${item.status=='309002' }">
												<!-- 已发布 -->
												<td><button onclick='changeItemStatus("结束报名","${item.id}","0")' type='button' class='btn btn-primary'>结束报名</button>&nbsp;<button onclick='setItemTakeScoreLimit("${item.id}","${item.vdef1 }","${item.vdef2 }")' type='button' class='btn btn-primary'>设置专家打分限制</button></td>
											</c:if>
											<c:if test="${item.status=='309007' }">
												<!-- 报名结束 -->
												<td><button onclick='changeItemStatus("开始一轮评审","${item.id}","1")' type='button' class='btn btn-primary'>开始一轮评审</button>&nbsp;<button onclick='setItemTakeScoreLimit("${item.id}","${item.vdef1 }","${item.vdef2 }")' type='button' class='btn btn-primary'>设置专家打分限制</button></td>
											</c:if>
											<c:if test="${item.status=='309008' }">
												<!-- 一轮评审中 -->
												<td><button onclick='changeItemStatus("结束一轮评审","${item.id}","2")' type='button' class='btn btn-primary'>结束一轮评审</button>&nbsp;<button onclick='setItemTakeScoreLimit("${item.id}","${item.vdef1 }","${item.vdef2 }")' type='button' class='btn btn-primary'>设置专家打分限制</button></td>
											</c:if>
											<c:if test="${item.status=='309003' }">
												<!-- 一轮评审结束 -->
												<td><button onclick='changeItemStatus("结束二轮评审","${item.id}","3")' type='button' class='btn btn-primary'>结束二轮评审</button>&nbsp;<button onclick='setItemTakeScoreLimit("${item.id}","${item.vdef1 }","${item.vdef2 }")' type='button' class='btn btn-primary'>设置专家打分限制</button></td>
											</c:if>
											<c:if test="${item.status=='309004' }">
												<!-- 二轮评审结束 -->
												<td><button onclick='setItemTakeScoreLimit("${item.id}","${item.vdef1 }","${item.vdef2 }")' type='button' class='btn btn-primary'>设置专家打分限制</button></td>
											</c:if>
											<c:if test="${item.status=='309001' }">
												<!-- 未发布 -->
												<td><button onclick='setItemTakeScoreLimit("${item.id}","${item.vdef1 }","${item.vdef2 }")' type='button' class='btn btn-primary'>设置专家打分限制</button></td>
											</c:if>
											<c:if test="${item.status=='309005' }">
												<!-- 已结束 -->
												<td><button onclick='setItemTakeScoreLimit("${item.id}","${item.vdef1 }","${item.vdef2 }")' type='button' class='btn btn-primary'>设置专家打分限制</button></td>
											</c:if>
											<c:if test="${item.status=='309006' }">
												<!-- 已废弃 -->
												<td><button onclick='setItemTakeScoreLimit("${item.id}","${item.vdef1 }","${item.vdef2 }")' type='button' class='btn btn-primary'>设置专家打分限制</button></td>
											</c:if>
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
	<div id="takeScoreLimitDiv" style="display:none;">
		<form id="limitForm" style="margin-top:20px;margin-left:20px;">
			打分专家人数限制：<input id="vdef1" name="vdef1" />	<br/>	<br/>
			复赛不通过得分阀值：<input id="vdef2" name="vdef2" />
			<input type="hidden" name="itemId" id="itemId"/> 
		</form>
		<br/><br/>
		<button style="margin-left:50px;" onclick="saveLimit()" type='button' class='btn btn-primary'>保存</button>
	</div>
</body>
</html>