<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../common/manage_inc.jsp" %>
<!DOCTYPE html>
<html>
<link type="text/css" href="/js/easyui/themes/default/easyui.css?${initParam.buildVersion}" rel="stylesheet"/>
<script type="text/javascript" src="/js/easyui/jquery.easyui.min.js?${initParam.buildVersion}"></script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>标签添加</title>
	<script type="text/javascript">
		jQuery(function($){
			$("#name").focus();
		});
		
		//验证标签名是否相同
		function ajaxLabelName() {
			var flag = false;
			$.ajax({
				url:"/contentCategory/ifRepeatName",
				type:"post",
				data:{"name":$("#name").val(),parentId:$("#parentId").val()},
				dataType:"json",
				async: false,
				success:function(data){
					if(data == "success"){
						flag = true;
					}
					else if(data == "exist"){
						$.messager.alert('提示信息','该分类名称已被使用,请更换分类名称!','info');
					}
				}
			});
			return flag;
		}
		
		//保存
		function save() {
			var name = $("#name").val();
			var description = $("#description").val();
			var parentId = $("#parentId").val();
			$.ajax({
			    url:"/contentCategory/add",
			    data:{name:name,description:description,parentId:parentId},
			    onSubmit: function(){
			    	var valid = $("#addLabelForm").form('validate');
			    	var flag = ajaxLabelName();
			    	return valid&&flag;
			    },
			    success:function(data){
			    	if(data != "error"){
						parent.insertTreegrid(parentId,data);
						parent.layer.closeAll();
			    	}
			    }
			});
		}
		
		//关闭窗口 
		function close_dialog(){
			parent.layer.closeAll();
		}
		
	</script>
</head>
<body>
        <form id="addLabelForm" name="addLabelForm" action=""  method="post">
        <input type="hidden" id="parentId" name="parentId" value="${parentId }"/>
        <br/>
        <div class="inputdq">
	        <table class="form">
	        	<tr>
	        		<td class="form-label"><p>名称&nbsp;&nbsp;&nbsp;</p></td>
	        		<td class="form-editor">
	        			<input name="name" id="name" type="text"  class="easyui-validatebox" data-options="required:true"  missingMessage="请填写标签名称">
	        		</td>
	        	</tr>
	        	<tr><td>&nbsp;</td></tr>
	        	<tr>
	        		<td class="form-label"><p>备注</p></td>
	        		<td class="form-editor">
	        			<textarea name="description" id="description" cols="17" rows="5"  class="easyui-validatebox" data-options="required:true"  missingMessage="请填写备注"></textarea>
	        		</td>
	        	</tr>
	        	<tr>
	        		<td class="form-label"></td>
	        		<td class="form-editor">
	        		 <div class="btncenter">
	        		  	<br/>
	        		 	<button type='button' class='btn btn-primary' onclick="save();">确定</button>
	        		 	<button type='button' class='btn btn-primary' onclick="close_dialog();">返回</button>
	        		  </div>
	        		</td>
	        	</tr>
	        </table>
        </div>
        </form>
</body>
</html>