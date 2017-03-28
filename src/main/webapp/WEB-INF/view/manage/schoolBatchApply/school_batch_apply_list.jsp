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
<script type="text/javascript">
	function getData(currentPage){
		var pageSize=10;
		var year=$("#year").val();
		var name=$("#name").val();
		var status=$("#status").val();
		$.ajax({
			url:"/schoolBatchApply/getData",
			type:"post",
			data:"current_page="+currentPage+"&page_size="+pageSize+"&year="+year+"&name="+name+"&status="+status,
			dataType:"json",
			success:function(data){
				$(".datatr").remove();
				for(var i=0;i<data.rows.length;i++){
					var obj=data.rows[i];
					if(typeof(obj.reviewPoint)=='undefined'){
						obj.reviewPoint="";
					}
					if(typeof(obj.approveStatus)=='undefined'||obj.approveStatus=='-1'){
						obj.approveStatus="未审核";
					}else if(obj.approveStatus=='0'){
						obj.approveStatus="初赛不通过";
					}else if(obj.approveStatus=='1'){
						obj.approveStatus="初赛通过";
					}
					if(typeof(obj.vdef1)=='undefined'){
						obj.vdef1="";
					}
					var html="<tr class='datatr'><td>"+obj.productionName+"</td><td>"+obj.artist+"</td><td>"+obj.createTime+"</td><td>"+obj.applyStatus+"</td>"+
						"<td>"+obj.approveStatus+"</td><td>"+obj.reviewPoint+"</td>";
					if(obj.applyStatus=='已报名'){
						html+="<td class='cz1'><button type='button' onclick='toedit(\""+obj.id+"\")' class='btn btn-primary'>查看</button>&nbsp;";
						//暂时设置审核通过了就不能改了 没通过还能改
						if(obj.approveStatus!='初赛通过'){
							html+="<button type='button' onclick='cancelApply(\""+obj.id+"\")'  class='btn btn-primary'>撤销</button>&nbsp;"
						}
						if(obj.approveStatus=='初赛通过'){
							html+="<button onclick='fusai(\""+obj.id+"\")' type='button' class='btn btn-primary'>完善复赛报名表</button>&nbsp;"
						}
						html+="<button type='button' onclick='downloadApplyTable(\""+obj.competitionType+"\",\""+obj.id+"\")'  class='btn btn-primary'>下载报名表</button>";
					}else if(obj.applyStatus=='已取消'){
						html+="<td class='cz1'><button type='button' onclick='toedit(\""+obj.id+"\")' class='btn btn-primary'>编辑</button>&nbsp;<button type='button' onclick='downloadApplyTable(\""+obj.competitionType+"\",\""+obj.id+"\")' class='btn btn-primary'>下载报名表</button>";
					}else if (obj.applyStatus=='新建'){
						html+="<td class='cz1'><button type='button' onclick='toedit(\""+obj.id+"\")' class='btn btn-primary'>编辑</button>&nbsp;<button type='button' onclick='downloadApplyTable(\""+obj.competitionType+"\",\""+obj.id+"\")' class='btn btn-primary'>下载报名表</button>";
					}
					html+="</td></tr>";
					$("#dataTable").append(html);
				}
				setPageHtml(data.total_count, "pageDiv", "getData", currentPage,pageSize);
			},error:function(){
				layer.alert(errorText);
			}
		});
	}
	
	function fusai(id){
		layer.open({
			type: 2,
			content:"/userApply/toFusaiApply?id="+id,
			shadeClose: true,//开启遮罩关闭
			title:false,
			area: ['80%', '80%']
		});
	}
	
	$(document).ready(function(){
		getData(1);
	});
	
	function downloadApplyTable(type,applyId){
		window.open("/userApply/download?type="+type+"&applyId="+applyId,"_blank");
	}
	
	function toedit(id){
		layer.open({
			type: 2,
			content:"/userApply/toApply?id="+id+"&notShowExplain=1&ifSchool=1",
			shadeClose: true,//开启遮罩关闭
			title:false,
			area: ['80%', '80%']
		});
	}
	
	function cancelApply(id){
		$.ajax({
			url:"/userApply/cancelApply",
			data:{id:id},
			dataType:"json",
			success:function(data){
				if(data.status=='0'){
					layer.msg("撤销成功",{icon:1});
					getData(1);
				}else{
					layer.alert(data.error_desc);
				}
			},error:function(){
				layer.alert(errorText);
			}
		});
	}
	
	function applyItem(type){
		layer.closeAll();
		layer.open({
			type: 2,
			content:"/userApply/toApply?flag=1&type="+type+"&notShowExplain=1&ifSchool=1",
			shadeClose: true,//开启遮罩关闭
			title:false,
			area: ['80%', '80%']
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
				<h1>学校批量报名管理</h1>
				<!-- 首页链接 -->
				<!-- <ol class="breadcrumb">
					<li><a href="../index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
                </ol> -->
			</section>
			<section class="content">
				<c:if test="${not empty ifCanAdd }">
					<button class="btn btn-primary" onclick="applyItem(301001)" type="button">科幻小说报名</button>
					<button class="btn btn-primary" onclick="applyItem(301002)" type="button">科幻画报名</button>
					<button class="btn btn-primary" onclick="applyItem(301003)" type="button">科幻微视频报名</button>
				</c:if>
				<!-- <div class="selectbox">
					<div class="form-group">
                           <div class="col-sm-1">
                                <select id="status" class="selectpicker show-tick form-control" data-live-search="false">
                                	<option value=''>活动状态</option>
                                </select>
                            </div>
                     </div>
				</div>
				<div class="box-body">
					<div id='createUserDiv' class="col-lg-2 col-xs-5" style="width:13%">
						<input id='year' name='year' id='createUser' type="text" class="form-control" placeholder="活动年份">
					</div>
					<div class="col-lg-2 col-xs-5" style="width:13%">
						<input id='name' type="text" class="form-control" placeholder="活动名称">
					</div>
					<div class="col-lg-2 col-xs-5" style="width:13%">
						<input id='name' type="text" class="form-control" placeholder="课程名称">
					</div>
					<div class="box-footer col-lg-2 col-xs-3">
						<button onclick='getData(1)' type="button" class="btn btn-primary">搜索</button>
					</div>
				</div> -->
				
				<div class="row" >
					<div class="col-xs-12" >
						<div class="box">
							<div class="box-body table-responsive">
								<table class="table table-hover table-bordered" id='dataTable'>
									<tr>
										<th>作品名称</th>
										<th>所属类别</th>
										<th>创建时间</th>
										<th>报名状态</th>
										<th>审核状态</th>
										<th>评分</th>
										<th>操作</th>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div id='pageDiv'>
				</div>
			</section>
		</aside>
	</div>
</body>
</html>