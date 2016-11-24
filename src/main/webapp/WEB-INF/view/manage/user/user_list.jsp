<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>内容云平台</title>
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
				<div class="selectbox">
					<div class="form-group">
                           <div class="col-sm-1">
                                <select id="status" class="selectpicker show-tick form-control" data-live-search="false">
                                	<option value=''>课程状态</option>
                                </select>
                            </div>
                     </div>
				</div>
				<div class="box-body">
					<div id='createUserDiv' class="col-lg-2 col-xs-5" style="width:13%">
						<input name='createUser' id='createUser' type="text" class="form-control" placeholder="课程分类">
					</div>
					<div class="col-lg-2 col-xs-5" style="width:13%">
						<input id='code' type="text" class="form-control" placeholder="课程编号">
					</div>
					<div class="col-lg-2 col-xs-5" style="width:13%">
						<input id='name' type="text" class="form-control" placeholder="课程名称">
					</div>
					<div class="col-lg-2 col-xs-5" style="width:13%">
						<input readonly="readonly" onclick="loadCategoryTree()" id='class_name' type="text" class="form-control" placeholder="课程分类">
					</div>
					<div class="box-footer col-lg-2 col-xs-3">
						<button onclick='getData(1,true)' type="button" class="btn btn-primary">搜索</button>
						&nbsp;&nbsp;
						<button onclick='addChoose()' type='button' class='btn btn-primary'>添加课程</button>
					</div>
				</div>
				
				<div class="row" >
					<div class="col-xs-12" >
						<div class="box">
							<div class="box-body table-responsive">
								<table class="table table-hover table-bordered" id='dataTable'>
									<tr>
										<th>编号</th>
										<th>课程名称</th>
										<th>价格</th>
										<th>累计播放次数</th>
										<th>创建时间</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="box-footer clearfix">
					<ul class="pagination pagination-sm no-margin pull-right">
						<li id='previous'><a href="##" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						<li id='next'><a href="##" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
						<li>&nbsp;&nbsp;&nbsp;<input onchange="jumpPageMethod()" id='jumpPageInput' class='jumpPageInput'/></li>
					</ul>
				</div>
			</section>
		</aside>
	</div>
</body>
</html>