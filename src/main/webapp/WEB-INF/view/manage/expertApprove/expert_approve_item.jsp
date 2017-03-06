<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科幻作品大赛</title>
<jsp:include page="../common/manage_inc.jsp"></jsp:include>
<!-- ztree -->
<link type="text/css" rel="stylesheet" href="/js/ztree/css/zTreeStyle/zTreeStyle.css"/>
<script src="/js/ztree/js/jquery.ztree.core-3.4.js"></script>
<script src="/js/ztree/js/jquery.ztree.excheck-3.4.js"></script>
<meta content='width=device-width,initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
<script src="/js/pageset.js" type="text/javascript"></script>
<script type="text/javascript">
	var tabId;
	$(document).ready(function(){
		$('#myTab a').click(function (e) {
			e.preventDefault();
			tabId=$(this).context.hash.split("#")[1];
			getApplyData(1);
			$(this).tab('show');
		});
		$($("#myTab a")[0]).click();
		
	});
	var pageSize=10;	
	function getApplyData(currentPage,ifSearch){
		$("#"+tabId+"current_page").val(currentPage);
		if(typeof(ifSearch)=='undefined'){
			ifSearch=true;
		}
		if($("#"+tabId+"t tr").length<=1||ifSearch){
			$.ajax({
				url:"/expertApprove/getApplyData?itemId="+tabId,
				data:$("#"+tabId+"form").serialize(),
				dataType:"json",
				type:"post",
				success:function(data){
					var applyGroup=data.applyGroupMap;
					var applyYearGroup=data.applyYearGroupMap;
					if($("#"+tabId+"group").children().length<=1){
						loadDictionarySelect(tabId+"group",applyGroup);
					}
					if($("#"+tabId+"year").children().length<=1){
						loadDictionarySelect(tabId+"year",applyYearGroup);
					}
					var dataList=data.datas;
					$("."+tabId+"class").remove();
					for(var i=0;i<dataList.length;i++){
						var obj=dataList[i];
						var option="";
						if(typeof(obj.orgName)=='undefined'){
							obj.orgName='';
						}
						if(typeof(obj.realName)=='undefined'){
							obj.realName='';
						}
						if(typeof(obj.proName)=='undefined'){
							obj.proName='';
						}
						if(typeof(obj.applyGroup)=='undefined'){
							obj.applyGroup='';
						}
						if(typeof(obj.applyYearGroup)=='undefined'){
							obj.applyYearGroup='';
						}
						if(typeof(obj.approveUserName)=='undefined'){
							obj.approveUserName='';
						}
						var checkHtml="";
						if(typeof(obj.approveStatus)=='undefined'||obj.approveStatus=="-1"){
							obj.approveStatus='未审核';
							obj.approveUserName="";
							obj.approveTime="";
							option+="&nbsp;&nbsp;<button onclick='approve1(1,0,\""+obj.id+"\")' type='button' class='btn btn-primary'>通过审核</button>";
							option+="&nbsp;<button onclick='approve1(0,0,\""+obj.id+"\")' type='button' class='btn btn-primary'>不通过</button>";
							checkHtml="<input type='checkbox' name='"+tabId+"sel' value='"+obj.id+"'/>";
						}else{
							if(obj.approveStatus=='0'){
								obj.approveStatus="审核不通过";
								if(obj.approveType!='1'){
									option+="&nbsp;<button onclick='approve1(1,0,\""+obj.id+"\")' type='button' class='btn btn-primary'>通过审核</button>&nbsp;<button onclick='cancelApprove(\""+obj.id+"\")' type='button' class='btn btn-primary'>撤销审核</button>";
									checkHtml="<input type='checkbox' name='"+tabId+"sel' value='"+obj.id+"'/>";
								}
							}else if(obj.approveStatus=='1'){
								obj.approveStatus="审核通过";
								if(obj.approveType!='1'){
									option+="&nbsp;<button onclick='approve1(0,0,\""+obj.id+"\")' type='button' class='btn btn-primary'>不通过</button>&nbsp;<button onclick='cancelApprove(\""+obj.id+"\")' type='button' class='btn btn-primary'>撤销审核</button>";
									checkHtml="<input type='checkbox' name='"+tabId+"sel' value='"+obj.id+"'/>";
								}
							}else if(obj.approveStatus=='-1'){
								obj.approveStatus="未审核";
								obj.approveUserName="";
								obj.approveTime="";
								option+="&nbsp;<button onclick='approve1(1,0,\""+obj.id+"\")' type='button' class='btn btn-primary'>通过审核</button>";
								option+="&nbsp;<button onclick='approve1(0,0,\""+obj.id+"\")' type='button' class='btn btn-primary'>不通过</button>";
								checkHtml="<input type='checkbox' name='"+tabId+"sel' value='"+obj.id+"'/>";
							}
						}
						if(typeof(obj.approveTime)=='undefined'){
							obj.approveTime='';
						}
						if(typeof(obj.reviewPoint)=='undefined'){
							obj.reviewPoint="";
						}
						if(typeof(obj.filePath)!='undefined'&&obj.filePath!=''){
							option+="&nbsp;<button type='button' onclick='viewProInfo(\""+obj.id+"\")' class='btn btn-primary'>查看作品详情</button>";
						}
						option+="&nbsp;<button type='button' onclick='viewDesc(\""+obj.id+"\",\""+obj.ideaDesc+"\")' class='btn btn-primary'>说明</button>";
						var html="<tr class='"+tabId+"class'><td>"+checkHtml+"</td><td>"+obj.userName+"</td><td>"+obj.realName+"</td><td>"+obj.orgName+"</td>"
							+"<td>"+obj.proName+"</td><td>"+obj.itemStatus+"</td><td>"+obj.applyGroup+"</td><td>"+obj.applyYearGroup+"</td><td>"+obj.approveStatus+"</td><td>"+obj.approveUserName+"</td><td>"+obj.approveTime+"</td><td>"+obj.reviewPoint+"</td><td>"+option+"</td></tr>";
						$("#"+tabId+"t").append(html);
					}
					setPageHtml(data.total_count, "pageDiv", "getApplyData", currentPage,pageSize);
				},error:function(){
					layer.alert(errorText);
				}
			});
		}
	}
	
	function cancelApprove(id){
		$.ajax({
			url:"/orgApprove/cancelApprove",
			data:{id:id},
			type:"post",
			dataType:"json",
			success:function(data){
				if(data.status=='0'){
					layer.msg("操作成功",{icon:1});
					getApplyData($("#"+tabId+"current_page").val(),true);
				}
				if(data.status=='1'){
					layer.alert(data.error_desc);
				}
			},error:function(){
				layer.alert(errorText);
			}
		});
	}
	
	function viewDesc(id,desc){
		//desc=html_encode(desc);
		layer.closeAll();
		if("${ifAdmin}"=='true'){
			layer.open({
				type: 2,
				content:"/userApply/toApply?id="+id+"&notEdit=1",
				shadeClose: true,//开启遮罩关闭
				title:false,
				area: ['80%', '80%']
			});
		}else{
			layer.open({
				type: 1,
				content:desc,
				shadeClose: true,//开启遮罩关闭
				title:false,
				area: ['400px', '400px']
			});
		}
	}
	
	function viewProInfo(id){
		window.open("/userApply/showFile?id="+id,"_target");
	}
	
	function approveMuti(result){
		var type=0;
		var id="";
		var checks=$("input[name="+tabId+"sel]:checked");
		for(var i=0;i<checks.length;i++){
			if(id==''){
				id=$(checks[i]).val();
			}else{
				id+=","+$(checks[i]).val();
			}
		}
		if(id==''){
			layer.msg("请选择记录",{icon:4});
			return;
		}
		approve1(result,type,id);
	}
	
	function approve1(result,type,id){
		$.ajax({
			url:"/expertApprove/approve",
			data:{result:result,type:type,id:id},
			type:"post",
			dataType:"json",
			success:function(data){
				if(data.status=='0'){
					layer.msg("操作成功",{icon:1});
					getApplyData($("#"+tabId+"current_page").val(),true);
				}
				if(data.status=='1'){
					layer.alert(data.error_desc);
				}
			},error:function(){
				layer.alert(errorText);
			}
		});
	}
	
	function returnIndex(){
		window.location.href="/expertApprove/index";
	}
	function loadCategoryTree(){
		if($("#tree").html()==''){
			createTree();
		}
		treeIndex=layer.open({
			type: 1,
			content:$("#treeDiv"),
			shadeClose: true,//开启遮罩关闭
			title:false,
			/* offset: [ //为了演示，随机坐标
						75,$(window).width()*0.65
					], */
			area: ['400px', '300px']
		}); 
	}
    
	function getParentsName(array,treeNode){
		if (treeNode.getParentNode()==null) {
			return;
		}else{
			array.push(treeNode.getParentNode().name);
			getParentsName(array,treeNode.getParentNode());
		}
	}
	
    function zTreeOnClick(event, treeId, treeNode) {
    	var treeObj = $.fn.zTree.getZTreeObj("tree");
    	var nodes = treeObj.getSelectedNodes();
    	var NameArray=new Array();
    	var str = "";	
    	//getParentsName(NameArray,treeNode);
    	for(var i=NameArray.length-1;i>=0;i--){
    		//str+=NameArray[i]+"-";
    		str+=NameArray[i];
    	}
    	var array = new Array();
    	str+=treeNode.name;
    	var n = "";			//id
    	for (var i = 0, l = nodes.length; i < l; i++) {
            n += nodes[i].id + ",";  
        }  
        if (n.length > 0) {
        	n = n.substring(0, n.length - 1);  
        }
        $("#"+tabId+"org").val(str);
        $("#"+tabId+"orgId").val(n);
        layer.closeAll();
    }
    function clearClass(){
    	$("#"+tabId+"org").val("");
        $("#"+tabId+"orgId").val("");
    }
    
    function fuckyou(){
    	
    }
</script>
<script src="/js/categoryTree.js"></script>
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
				<h1>专家首轮审批管理-查询报名列表</h1>
			</section>
			<section class="content">
				<div class="form-group mt10" style='margin-top:5px;'>
					<label class="control-label  col-xs-1"><button onclick='returnIndex()' type='button' class='btn btn-primary'>返回</button></label>
					<div class="col-sm-4">
	                </div>
				</div>
				<div style='margin-top:50px;'>
					<ul id="myTab" class="nav nav-tabs">
						<c:forEach items="${items}" var="item">
							<li><a href="#${item.id }" data-toggle="tab">${item.name}</a></li>
						</c:forEach>
						<!-- <li class="active"><a href="#pptTab" data-toggle="tab">PPT</a></li>
						<li><a href="#pointTab" data-toggle="tab">关键点</a></li> -->
					</ul>
				</div>
				<div id="myTabContent" class="tab-content">
					<c:forEach items="${items}" var="item">
						<div class="tab-pane fade" id="${item.id}">
							<form id='${item.id }form'>
								<div class="selectbox">
									<div class="form-group">
			                           	<div class="col-sm-1">
			                           		<!--  class="selectpicker show-tick form-control"  -->
			                                <select name="applyGroup" id="${item.id}group"  data-live-search="false">
			                                	<option value=''>参赛组别</option>
			                                </select>
			                            </div>
				                     </div>
				                     <div class="form-group">
			                           	<div class="col-sm-1">
			                                <select name="applyYearGroup" id="${item.id}year" data-live-search="false">
			                                	<option value=''>参赛年龄组</option>
			                                </select>
			                            </div>
				                     </div>
				                     <div class="form-group">
			                           	<div class="col-sm-1">
			                                <select name="applyStatus" id="applyStatus" data-live-search="false">
			                                	<option value=''>报名状态</option>
			                                	<option value='302003'>新建</option>
			                                	<option value='302001'>已报名</option>
			                                	<option value='302002'>已取消</option>
			                                </select>
			                            </div>
				                     </div>
				                     <div class="form-group">
			                           	<div class="col-sm-1">
			                                <select name="approveResult" id="approveResult" data-live-search="false">
			                                	<option value=''>初审结果</option>
			                                	<option value='-1'>未审核</option>
			                                	<option value='1'>审核通过</option>
			                                	<option value='0'>审核不通过</option>
			                                </select>
			                            </div>
				                     </div>
								</div>
								
								<div class="box-body" style="margin-top:10px;">
									<div class="col-lg-2 col-xs-5" style="width:13%">
										<input onclick='loadCategoryTree()' id='${item.id }org' type="text" class="form-control" placeholder="选择组织机构">
										<input type='hidden' id='${item.id }orgId' name='orgId' />
									</div>
									<div style="float:left">
										<input type='hidden' /><a href="##" onclick='clearClass()'><img src="/images/cuo.png"></a>
									</div>
									<div class="col-lg-2 col-xs-5" style="width:13%">
										<input name='userName' type="text" class="form-control" placeholder="用户名">
									</div>
									<div class="col-lg-2 col-xs-5" style="width:13%">
										<input name='realName' type="text" class="form-control" placeholder="真实姓名">
									</div>
									<input name='current_page' id="${item.id }current_page" type="hidden" value="10">
									<input name='page_size' type="hidden" value="10">
									<div class="box-footer col-lg-2 col-xs-3">
										<button onclick='getApplyData(1,true)' type="button" class="btn btn-primary">搜索</button>
									</div>
								</div>
							</form>
							<div class="row" >
								<div class="col-xs-12" >
									<div class="box">
										<div class="box-body table-responsive" >
											<div style="margin-bottom:8px;">
												<button type="button" onclick="approveMuti(1)" class='btn btn-primary'>审核通过</button>
												<button type="button" onclick='approveMuti(0)'class='btn btn-primary'>审核不通过</button>
											</div>
											<table class="table table-hover table-bordered" id='${item.id }t'>
												<tr>
													<th><span style="font-size:22px;cursor:pointer;" id="${item.id}checkText" onclick="clickCheck('${item.id}sel')">□</span></th>
													<th>用户名</th>
													<th>真实姓名</th>
													<th>组织机构</th>
													<th>作品名称</th>
													<th>报名状态</th>
													<th>参赛组别</th>
													<th>参赛年龄组</th>
													<th>初审结果</th>
													<th>初审人</th>
													<th>初审时间</th>
													<th>复赛评分</th>
													<th>操作</th>
												</tr>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
					<!-- <div class="tab-pane fade in active" id="pptTab"></div> -->
					<div id='pageDiv'>
					</div>
				</div>
			</section>
		</aside>
	</div>
	<div id='treeDiv' style='display:none'>
		<ul id="tree" class="ztree"></ul>
	</div>
	<div id='descDiv' style="display:none;text-align:center;margin-top:20px;" >
		
	</div>
</body>
<script type="text/javascript">
	function clickCheck(name){
		if($("#"+tabId+"checkText").text()=="□"){
			$("#"+tabId+"checkText").text("☑");
			$("input[name="+tabId+"sel]").prop("checked",true);
		}else{
			$("#"+tabId+"checkText").text("□");
			$("input[name="+tabId+"sel]").prop("checked",false);
		}
		
	}
</script>
</html>