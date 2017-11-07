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
<style type="text/css">
	tr td{text-align:center;}
</style>
<script type="text/javascript">
	
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
				<h1>报名查询管理</h1>
				<!-- 首页链接 -->
				<!-- <ol class="breadcrumb">
					<li><a href="../index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
                </ol> -->
			</section>
			<section class="content">
				<div class="row" >
					<div class="col-xs-12" >
						<div class="box">
							<div class="box-body table-responsive">
								<h1 style='text-align:center;'>${activity.name }报名查询统计</h1>
								<table class="table table-hover table-bordered" id='dataTable'>
									<tr>
										<td rowspan="2">年龄段(人数)\比赛类型</td>
										<td colspan="2">科幻小说</td>
										<td colspan="2">科幻画</td>
										<td colspan="2">科幻微视频</td>
									</tr>
									
									<tr>
										<td colspan="2"></td>
										<td>手绘</td>
										<td>电脑绘图</td>
										<td></td>
									</tr>
									
									<tr>
										<td>小学</td>
										<%--<td id='301001305001306001'>
											<c:if test="${empty result.data301001305001306001}">
												0
											</c:if>
											<c:if test="${not empty result.data301001305001306001}">
												${result.data301001305001306001}
											</c:if>
										</td>--%>
										<td id='301001306001' colspan="2">
											<c:if test="${empty result.data301001306001}">
												0
											</c:if>
											<c:if test="${not empty result.data301001306001}">
												${result.data301001306001}
											</c:if>
										</td>
										<td id='301002303001304001'>
											<c:if test="${empty result.data301002303001304001}">
												0
											</c:if>
											<c:if test="${not empty result.data301002303001304001}">
												${result.data301002303001304001}
											</c:if>
										</td>
										<td id='301002303002304001'>
											<c:if test="${empty result.data301002303002304001}">
												0
											</c:if>
											<c:if test="${not empty result.data301002303002304001}">
												${result.data301002303002304001}
											</c:if>
										</td>
										<td></td>
									</tr>
									
									<tr>
										<td>中学</td>
										<%--<td id='301001305001306002'>
											<c:if test="${empty result.data301001305001306002}">
												0
											</c:if>
											<c:if test="${not empty result.data301001305001306002}">
												${result.data301001305001306002}
											</c:if>
										</td>--%>
										<td id='301001306002' colspan="2">
											<c:if test="${empty result.data301001306002}">
												0
											</c:if>
											<c:if test="${not empty result.data301001306002}">
												${result.data301001306002}
											</c:if>
										</td>
										<td id='301002303001304002'>
											<c:if test="${empty result.data301002303001304002}">
												0
											</c:if>
											<c:if test="${not empty result.data301002303001304002}">
												${result.data301002303001304002}
											</c:if>
										</td>
										<td id='301002303002304002'>
											<c:if test="${empty result.data301002303002304002}">
												0
											</c:if>
											<c:if test="${not empty result.data301002303002304002}">
												${result.data301002303002304002}
											</c:if>
										</td>
										<td></td>
									</tr>
									
									<tr>
										<td>大学</td>
										<%--<td id='301001305001306003'>
											<c:if test="${empty result.data301001305001306003}">
												0
											</c:if>
											<c:if test="${not empty result.data301001305001306003}">
												${result.data301001305001306003}
											</c:if>
										</td>--%>
										<td id='301001306003' colspan="2">
											<c:if test="${empty result.data301001306003}">
												0
											</c:if>
											<c:if test="${not empty result.data301001306003}">
												${result.data301001306003}
											</c:if>
										</td>
										<td id='301002303001304003'>
											<c:if test="${empty result.data301002303001304003}">
												0
											</c:if>
											<c:if test="${not empty result.data301002303001304003}">
												${result.data301002303001304003}
											</c:if>
										</td>
										<td id='301002303002304003'>
											<c:if test="${empty result.data301002303002304003}">
												0
											</c:if>
											<c:if test="${not empty result.data301002303002304003}">
												${result.data301002303002304003}
											</c:if>
										</td>
										<td id='301003307001'>
											<c:if test="${empty result.data301003307001}">
												0
											</c:if>
											<c:if test="${not empty result.data301003307001}">
												${result.data301003307001}
											</c:if>
										</td>
									</tr>
									
									<tr>
										<td>社会人士</td>
										<%--<td id='301001305001306004'>
											<c:if test="${empty result.data301001305001306004}">
												0
											</c:if>
											<c:if test="${not empty result.data301001305001306004}">
												${result.data301001305001306004}
											</c:if>
										</td>--%>
										<td id='301001306004' colspan="2">
											<c:if test="${empty result.data301001306004}">
												0
											</c:if>
											<c:if test="${not empty result.data301001306004}">
												${result.data301001306004}
											</c:if>
										</td>
										<td></td>
										<td></td>
										<td id='301003307002'>
											<c:if test="${empty result.data301003307002}">
												0
											</c:if>
											<c:if test="${not empty result.data301003307002}">
												${result.data301003307002}
											</c:if>
										</td>
									</tr>
									
									<tr>
										<td>合计</td>
										<%--<td id='total1'></td>--%>
										<td id='total2' colspan="2"></td>
										<td id='total3'></td>
										<td id='total4'></td>
										<td id='total5'></td>
									</tr>
									
									<tr>
										<td>总计</td>
										<td colspan="5" id='totalall'></td>
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
<script type="text/javascript">
	//$("#total1").text(parseInt($.trim($("#301001305001306001").text()))+parseInt($.trim($("#301001305001306002").text()))+parseInt($.trim($("#301001305001306003").text()))+parseInt($.trim($("#301001305001306004").text())));
	
	$("#total2").text(parseInt($.trim($("#301001306001").text()))+parseInt($.trim($("#301001306002").text()))+parseInt($.trim($("#301001306003").text()))+parseInt($.trim($("#301001306004").text())));
	
	$("#total3").text(parseInt($.trim($("#301002303001304001").text()))+parseInt($.trim($("#301002303001304002").text()))+parseInt($.trim($("#301002303001304003").text())));
	
	$("#total4").text(parseInt($.trim($("#301002303002304001").text()))+parseInt($.trim($("#301002303002304002").text()))+parseInt($.trim($("#301002303002304003").text())));
	
	$("#total5").text(parseInt($.trim($("#301003307001").text()))+parseInt($.trim($("#301003307002").text())));
	
	//$("#totalall").text(parseInt($.trim($("#total1").text()))+parseInt($.trim($("#total2").text()))+parseInt($.trim($("#total3").text()))+parseInt($.trim($("#total4").text()))+parseInt($.trim($("#total5").text())));
	$("#totalall").text(parseInt($.trim($("#total2").text()))+parseInt($.trim($("#total3").text()))+parseInt($.trim($("#total4").text()))+parseInt($.trim($("#total5").text())));
</script>
</html>