<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科幻作品大赛</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
<!-- bootstrap-select  -->
<link rel="stylesheet" href="/css/bootstrap-select.min.css">
<!-- font Awesome -->
<link href="/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<!-- Ionicons -->
<link href="/css/ionicons.min.css" rel="stylesheet" type="text/css" />
<!-- Theme style -->
<link href="/css/AdminLTE.css" rel="stylesheet" type="text/css" />

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="/js/jquery-1.9.1.min.js"></script>
<!-- bootstrap -->
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<!-- 解决jquery1.9以上不支持$.broswer的问题 -->
<script src="/js/jquery-browser-support-byzlg.js"></script>
<!-- bootstrap-select  -->
<script src="/js/bootstrap-select.min.js"></script>
<!-- layer -->
<script src="/js/layer/layer.js"></script>
<meta content='width=device-width,initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
<script type="text/javascript">
</script>
</head>
<body class="skin-blue">
	<div class="wrapper row-offcanvas row-offcanvas-left">
		<aside class="right-side">
			<section class="content-header">
				<h1>作品打分详情</h1>
			</section>
			<section class="content">
				<div class="row" >
					<div class="col-xs-12" >
						<div class="box">
							<div class="box-body table-responsive">
								<table class="table table-hover table-bordered" id='dataTable'>
									<tr>
										<th>序号</th>
										<th>打分项名称</th>
										<th>总分</th>
										<th>得分</th>
										<c:if test="${ifCanAdd!=1 }">
											<th>打分人</th>
										</c:if>
									</tr>
									
									<c:forEach items="${results }" var="result" varStatus="vs">
										<tr>
											<td>${vs.count }</td>
											<td>${result.setUpName }</td>
											<td>${result.totalScore }</td>
											<td><input <c:if test="${ifCanAdd!=1 }"> readonly="readonly" </c:if> data-id='${result.applyId }_${result.setUpId}' value='${result.getScore }' data-score="${result.totalScore}"/></td>
											<c:if test="${ifCanAdd!=1 }"><td>${result.vdef3 }</td></c:if>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>
				<c:if test="${ifCanAdd==1 }">
					<button type="button" class='btn btn-primary' onclick="saveScore()">保存</button>
				</c:if>
			</section>
		</aside>
	</div>
</body>
<script type="text/javascript">
	function saveScore(){
		var inputs=$("input");
		var datas="";
		for(var i=0;i<inputs.length;i++){
			var $obj=$(inputs[i]);
			var value=$obj.val();
			if(isNaN(value)||value==''){
				layer.msg("请输入合法的数字",{icon:4});
				return;
			}
			if(parseFloat(value)>parseFloat($obj.attr("data-score"))){
				layer.msg("得分不能大于总分",{icon:4});
				return;
			}
			if(datas==''){
				datas=$obj.attr("data-id")+"#"+value;
			}else{
				datas+=","+$obj.attr("data-id")+"#"+value;
			}
		}
		$.ajax({
			url:"/expertTakeScore/takeScore",
			data:{datas:datas},
			type:"post",
			dataType:"json",
			success:function(data){
				if(data.status=='0'){
					parent.takeScoreSuccess();
				}else{
					alert(data.error_desc);
				}
			}
		});
	}
</script>
</html>