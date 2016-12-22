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
	
	function returnIndex(){
		window.location.href="/activityItemLimit/index";
	}
	
	function toSetItemLimit(id){
		window.location.href="/activityItemLimit/toSetItemLimit?id="+id+"&activityId=${param.id}";
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
							<label class="control-label  col-xs-1"><button onclick='returnIndex()' type='button' class='btn btn-primary'>返回</button></label>
							<div class="col-sm-4">
								
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
											<td><button onclick='toSetItemLimit("${item.id}")' type='button' class='btn btn-primary'>设置项目限额</button></td>
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