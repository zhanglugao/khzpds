<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="/img/logo.png" />
<title>分类管理</title>
<%@ include file="../common/manage_inc.jsp" %>
<link rel="icon" href="/img/logo.png" />
<link type="text/css" href="/js/easyui/themes/default/easyui.css?${initParam.buildVersion}" rel="stylesheet"/>
<link type="text/css" href="/js/easyui/themes/icon.css?${initParam.buildVersion}" rel="stylesheet"/>	
<script type="text/javascript" src="/js/easyui/jquery.easyui.min.js?${initParam.buildVersion}"></script>
<script type="text/javascript" src="/js/jquery.autocomplete.js?${initParam.buildVersion}"></script>
<link rel="Stylesheet" href="/css/jquery.autocomplete.css?${initParam.buildVersion}" />
	<script type="text/javascript">
		jQuery(function($){
			var name = $('#name').val();
			var url="labelData";
			if(name!=""){
				url=url+"?name="+name;
			}
	    	$('#tt').treegrid({
		        url:url,
		        idField:'id',
		        height:580,
		        treeField:'name',
		        rownumbers:true,
		        method:"post",
		        onBeforeExpand:function(row,param){
		            if(row){
		            	$(this).treegrid('options').url='/contentCategory/labelData?clickPid=1&parentId='+row.id;
		            }
		        }, 
		        columns:[[
		            {field:'id',title:'标签id',width:220,hidden:true},
		            {field:'_parentId',title:'标签parentId',width:220,hidden:true},
		            {field:'name',title:'标签名称',width:220},
		            {field:'description',title:'标签描述',width:220},
		            {field:'caozuo',title:'操作',width:1000,formatter:function(val,rowData) {
		            	/* return "<img src='/images/up_10.jpg' alt='上移' onclick='moveUp("+rowData.id +","+rowData._parentId+","+"1);'/>"
		            	+"&nbsp;&nbsp;&nbsp;&nbsp;"+"<img src='/images/down.jpg' alt='下移' onclick='moveDown("+rowData.id +","+rowData._parentId+","+"0);'/>"+"&nbsp;&nbsp;&nbsp;&nbsp;" */
		            	var html="<img src='/images/contentCategory/bj.jpg' alt='编辑' onclick='bj("+rowData.id+","+rowData._parentId+");'/>"
		            	+"&nbsp;&nbsp;&nbsp;&nbsp;"+"<img src='/images/contentCategory/add_14.jpg' alt='添加' onclick='add("+rowData.id+");'/>"
		            	+"&nbsp;&nbsp;&nbsp;&nbsp;"+"<img src='/images/contentCategory/sc.jpg' alt='删除' onclick='del("+rowData.id+","+rowData._parentId+");'/>";
		            	//不是最顶级 and 不是第一个   展示上移 
		            	if(typeof(rowData._parentId)!='undefined'&&rowData.sort!='1'&&rowData.sort!='-2'){
		            		html+="&nbsp;&nbsp;&nbsp;&nbsp;"+"<img src='/images/contentCategory/up.jpg' alt='上移' onclick='labelUp("+rowData.id+",\"up\",\""+rowData._parentId+"\");'/>";
		            	}
		            	//不是最顶级 and 不是最后一个 展示下移
		            	if(typeof(rowData._parentId)!='undefined'&&rowData.sort!='-1'&&rowData.sort!='-2'){
		            		html+="&nbsp;&nbsp;&nbsp;&nbsp;"+"<img src='/images/contentCategory/down.jpg' alt='下移' onclick='labelUp("+rowData.id+",\"down\",\""+rowData._parentId+"\");'/>";
		            	}
		            	return html;
		            }}
		        ]],
		        onLoadSuccess:function(data){
		        	$("#tt").treegrid("expand",$("#tt").treegrid("getRoot").id);
		        }
		    });
	    	
	    	$("#name").autocomplete('/contentCategory/getLabelByName', {
	    		minChars: 1,
	            multiple : false,
	    		matchSubset : false,
	            parse : function(data) {
	            	if(data=="[]" || data=="No Records." || data==null || typeof(data) == "undefined") {
	            		return {data :'',  value :'', result :''};
	            	}
	            	else{
	                	return $.map(eval(data), function(row) {  
	                            return {  
	                                data : row,  
	                                value : row.name,  
	                                result : row.name  
	                            }  
	                        });  
	            	}
	            },  
	            formatItem : function(row, i, max) {  
	            	if(row==null||typeof(row) == "undefined") 
	            		return "";
	            	else{
	            		return row.name;
	            	}
	                	
	            }  
	        }); 
	     });
		//上移  跟上一个节点交换
		function labelUp(id,type,parentId){
			//交换
			$.ajax({
				url:'/contentCategory/labelChange',
				data:"id="+id+"&type="+type,
				success:function(data){
					if(data=='noobj'){
						layer.msg("已不存在要交换顺序的对象，请刷新后重新操作",{icon:4});
					}else if(data=='success'){
						var parentArray=new Array();
						setParentArray(parentArray,parentId);
						submitLabelForm();
						for(var i=parentArray.length-1;i>=0;i--){
				    		$("#tt").treegrid("expand",parentArray[i]);
				    	}
						//层层展开
					}else{
						layer.msg("系统错误",{icon:2});
					}
				}
			});
		}

		function setParentArray(parentArray,id){
			parentArray.push(id);
			var parentNode=$("#tt").treegrid("getParent",id);
			if(parentNode!=null){
				setParentArray(parentArray,parentNode.id);
			}
		}
		
		function submitLabelForm() {
			$.ajax({
				url:'/contentCategory/labelData',
				data:{name:$("#name").val()},
				dataType:"json",
				type:"post",
				async:false,
				success:function(data){
					$('#tt').treegrid('loadData',data);
				}
			});
		}
		
		//添加
		function add(obj) {
			layer.open({
				type: 2,
				content:'/contentCategory/addUI?parentId=' + obj,
 				title:false,
 				shadeClose: true,
				area: ['400px', '300px']
			}); 
		}
		
		//不刷新treegrid添加数据
		 function insertTreegrid(parentId,data){
			 var label = eval("("+data+")");
			 $('#tt').treegrid('append',{
					parent: parentId, // 节点有一个“id”定义的值通过“idField”属性
					data: [{
						_parentId:parentId,
						id:label.id,
						name: label.name,
						description:label.description
					}]
				}); 
		 }
		
		//编辑
		function bj(id,pId) {
			if(typeof(pId)=='undefined'){
				layer.msg("顶级无法编辑",{icon:4});
				return;
			}
			layer.open({
				type: 2,
				content:'/contentCategory/initEdit?id='+id,
				title:false,
 				shadeClose: true,
				area: ['400px', '300px']
			}); 
		}
		
		//删除
		function del(obj,pId) {
			if(typeof(pId)=='undefined'){
				layer.msg("顶级无法删除",{icon:4});
				return;
			}
			//alert(obj);
			var children=$('#tt').treegrid("getChildren",obj);
			var ids = obj;
			for(var i=0; i<children.length;i++) {
				ids = ids+","+children[i].id;
			}
			layer.confirm('确定删除？', {icon: 3}, function(index){
			  	//进入考试
			  	layer.close(index);
			  	var index2=layer.load(1);
				$.ajax({
					   type: "post",
					   url: "delete",    
					   data: {'ids' : ids},
					   success: function(data, textStatus){
						    layer.close(index2);
							if(data == "\"success\"") {
								//$.messager.alert('提示信息','成功删除数据!','info');
								layer.msg("删除成功",{icon:1});
								submitLabelForm();
							} else if(data == "\"used\"") {
								//$.messager.alert('提示信息','该标签已被使用,不能删除该条数据!','info');
								layer.msg("该标签已被使用,不能删除该条数据!",{icon:4});
								submitLabelForm();
							} 
					   },error:function(){
						   layer.close(index2);
						   layer.msg("系统错误，请重试或联系管理员",{icon:2});
					   }
				});
			});
		}
		//上移
		function moveUp(id,parentId,flag) {
			//alert(parentId)
			var node = $('#tt').treegrid('find',id);
			$.ajax({
				   type: "post",
				   url: "updateSort?id="+id+"&parentId="+parentId+"&sort="+flag,    
				   success: function(data, textStatus){
						if(data=="success") {
							$('#tt').treegrid("reload");
						} 
						$("#tt").datagrid("clearSelections");
				   },
				   error:function(){
					   $("#tt").datagrid("clearSelections");
					   //$.messager.alert('提示信息','操作失败!','error');
					   layer.msg("操作失败",{icon:2});
				   }
				});	
		}
		//上移
		function moveDown(id,parentId,flag) {
			//alert(parentId)
			var node = $('#tt').treegrid('find',id);
			$.ajax({
				   type: "post",
				   url: "updateSort?id="+id+"&parentId="+parentId+"&sort="+flag,    
				   success: function(data, textStatus){
						if(data=="success") {
							$('#tt').treegrid("reload");
						} 
						$("#tt").datagrid("clearSelections");
				   },
				   error:function(){
					   $("#tt").datagrid("clearSelections");
					   layer.msg("操作失败",{icon:2});
				   }
				});	
		}
		function collapseAll(){
			$('#tt').treegrid('collapseAll');
		}
		function expandAll(){
			$('#tt').treegrid('expandAll');
		}
	</script>
</head>
<body class="skin-blue">
	<%@include file="../common/manage_header.jsp"%>
	<div class="wrapper row-offcanvas row-offcanvas-left">
		<!-- courseyun 左部菜单 -->
		<div>
			<%@include file="../common/manage_left.jsp"%>
		</div>
		<aside class="right-side">
			<div class="easyright" id="coneay">
		          <div class="easyr-from">
		          <br/>
		             <form action="" method="post">
		             	<div class="col-lg-2 col-xs-5">
							<input id='name' value='${label.name }' name='name' type="text" class="form-control" placeholder="输入分类名称">
						</div>
		               <button type='button' class='btn btn-primary' onclick="submitLabelForm();">查询</button>
		             </form>
		          </div>
		          <div class="esybtn cb" style='margin-top:5px;margin-left:5px;margin-bottom:5px;'>
		          		<button type='button' class='btn btn-primary' onclick="expandAll();">展开</button>
		          		<button type='button' class='btn btn-primary' onclick="collapseAll();">折叠</button>
		             	<c:if test="${roleType == '161001' }">
			             	<a href="javascript:add('');"><input class="basebtn" type="button" value="添加"/></a>
		             	</c:if>
		          </div>
		          <table id="tt" class="easyui-layout"></table>
		    </div> 
	    </aside>
    </div>
    <script>
        jQuery(function($){
			var easywh = $('#coneay .datagrid-wrap').width();
			$('#coneay .datagrid-wrap').attr('data-width', easywh);
			$('#add').click(function(){
				//alert('ddd');
				var easywh = $('#coneay .datagrid-wrap').attr('data-width');
				$('#coneay .datagrid-wrap').css('width', (easywh - 420) + 'px');
				$('.new-tag').css({'display':'block'});
			});
			$('.new-tag .close').click(function(){
				var easywh = $('#coneay .datagrid-wrap').attr('data-width');
				$('#coneay .datagrid-wrap').css('width', easywh + 'px');
				$('.new-tag').css({'display':'none'});
			});
		});	
    </script>
</body>
</html>