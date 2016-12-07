/**
 * 加载树到tree下
 * @param rootId  根节点id
 * @param name	查询名称
 * @Param type  根据type区分显示类型 默认不多选 type=muti时为多选
 */
var rootId="";
function createTree(categoryType,name,type) {
	if(rootId==''){
		$.ajax({
			url:"/contentCategory/getRootId",
			data:{type:categoryType},
			type:"post",
			async:false,
			success:function(data){
				rootId=data;
			}
		});
	}
	var treeNodes;
	$.ajax({
		url:"/contentCategory/getDataForZtree",
		data:{'name':name,'rootId':rootId},
		type:"post",
		dataType:"json",
		ContentType:"application/json; charset=utf-8",
		success:function(data) {
			treeNodes = data;
			if(type=='muti'){
				$.fn.zTree.init($("#tree"), setting2, eval(treeNodes));
			}else{
				$.fn.zTree.init($("#tree"), setting, eval(treeNodes));
			}
		},
		error:function() {
			 alert('提示信息','操作失败!','error');
		}
	}); 
}
/**
 * 正常样式  页面需自己实现zTreeOnClick 方法
 */
var setting={
		check:{enable:false},
		view:{expandSpeed:300},
		data:{simpleData:{enable:true,idKey:"id",pIdKey:"_parentId",rootPId:0}},
		async:{  
	        autoParam:["id=parentId"],  
	        contentType:"application/x-www-form-urlencoded",  
	        enable:true,  
	        type:"post",
	        url:"/contentCategory/getDataForZtree"
	    },
	    callback:{onClick:zTreeOnClick}
};

/***
 * 树前加入了checkbox的样式  没有实现选中后操作，需要自己实现
 */
var setting2={
		check:{enable:true,chkboxType: {"Y":"s", "N": "ps"}},
		view:{expandSpeed:300},
		data:{simpleData:{enable:true,idKey:"id",pIdKey:"_parentId",rootPId:0}},
		async:{  
	        autoParam:["id=parentId"],  
	        contentType:"application/x-www-form-urlencoded",  
	        enable:true,  
	        type:"post",
	        url:"/contentCategory/getDataForZtree"
	    }
};