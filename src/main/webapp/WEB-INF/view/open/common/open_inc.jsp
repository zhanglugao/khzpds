<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/js/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" href="/css/style.css"/>
<link rel="stylesheet" href="/css/header.css"/>
<link href="/css/sign.css" rel="stylesheet" type="text/css">

<!-- 解决jquery1.9以上不支持$.broswer的问题 -->
<script src="/js/jquery-browser-support-byzlg.js"></script>
<!-- layer -->
<script src="/js/layer/layer.js"></script>
<!-- laydate -->
<script src="/js/laydate/laydate.js"></script>
<!-- 工具类js common.js 提供对js字符串的操作startWith endWith trim等js方法  -->
<script type="text/javascript" src='/js/common.js'></script>
<script type="text/javascript" src='/js/autoLogin.js'></script>
<script type="text/javascript" src='/js/base64.js'></script>
<script>
	//layer.tips颜色公共提取
	var tipsColor="#ff0000";
	var errorText="系统错误，请重试或联系管理员";
	/**
	* 加载字典表数据到select中
	*/
	function loadDictionarySelect(name,obj){
		if(obj!=''){
			var data=eval(obj);
			for(var o in data){
				console.log(o);
				$("#"+name).append("<option value='"+o+"'>"+data[o]+"</option>");
			}
		}
	}
</script>
