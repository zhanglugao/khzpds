<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/manage_inc.jsp" %>
<!DOCTYPE html>
<html>
<link type="text/css" href="/js/easyui/themes/default/easyui.css?${initParam.buildVersion}" rel="stylesheet"/>
<link type="text/css" href="/js/easyui/themes/icon.css?${initParam.buildVersion}" rel="stylesheet"/>	
    <script type="text/javascript" src="/js/easyui/jquery.easyui.min.js?${initParam.buildVersion}"></script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>新建标题</title>
	<script type="text/javascript">
	
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
	
		function update() {
			var name = $("#name").val();
			var description = $("#description").val();
			var id = $("#id").val();
			var parentId = $("#parentId").val();
			$.ajax({
			    url:"/contentCategory/update",
			    data:{name:name,description:description,parentId:parentId,id:id},
			    onSubmit: function(){
			    	var valid = $("#updateLabelForm").form('validate');
			    	var flag = ajaxLabelName();
			    	return valid&&flag;
			    },
			    success:function(data){
			    	if(data=="success"){
						parent.submitLabelForm();
						parent.layer.closeAll();
			    	}
			    }
			});
			/*
			var createName = $("#createName").val();
			var description = $("#description").val();
			var id = $("#id").val();
			var parentId = $("#parentId").val();
			var addLabelForm = document.forms["addLabelForm"];
			addLabelForm.action="/contentCategory/edit?createName=" + createName +"&description=" + description + "&id=" + id + "&parentId=" + parentId;
			addLabelForm.submit();
			window.close();
			*/
		}
		
		//关闭窗口 
		function close_dialog(){
			parent.layer.closeAll();
		}
	</script>
</head>
<body>
        <form name="updateLabelForm" id="updateLabelForm" action="" method="post">
        <div class="inputdq">
        <input type="hidden" name="id" id="id" value="${ContentCategoryInfo.id }"/>
        <input type="hidden" name="parentId" id="parentId" value="${ContentCategoryInfo.parentId }" />
        <br/>
        <table class="form">
        	<tr>
        		<td class="form-label"><p>名称&nbsp;&nbsp;&nbsp;</p></td>
        		<td class="form-editor">
        			<input name="name" id="name" type="text" class="input" value="${ContentCategoryInfo.name }" class="easyui-validatebox" data-options="required:true"  missingMessage="请填写标签名称">
        		</td>
        	</tr>
        	<tr><td>&nbsp;</td></tr>
        	<tr>
        		<td class="form-label"><p>备注</p></td>
        		<td class="form-editor">
        			<textarea name="description" id="description" cols="20" rows="5" class="textarea" class="easyui-validatebox" data-options="required:true"  missingMessage="请填写备注">${ContentCategoryInfo.description }</textarea>
        		</td>
        	</tr>
        	<tr></tr>
        	<tr>
        		<td class="form-label"></td>
        		<td class="form-editor">
        		   <div class="btncenter">
        		   		<br/>
        			 	<button type='button'class='btn btn-primary' onclick="update();">确定</button>
	        		 	<button type='button'class='btn btn-primary' onclick="close_dialog();">返回</button>
        			</div>
        		</td>
        	</tr>
        </table>
        </div>
        </form>
</body>
</html>