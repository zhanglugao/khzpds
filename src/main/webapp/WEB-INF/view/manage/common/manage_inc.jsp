<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- AdminLTE -->
<script src="/js/AdminLTE/app.js" type="text/javascript"></script>
<!-- bootstrap -->
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<!-- 解决jquery1.9以上不支持$.broswer的问题 -->
<script src="/js/jquery-browser-support-byzlg.js"></script>
<!-- bootstrap-select  -->
<script src="/js/bootstrap-select.min.js"></script>
<!-- layer -->
<script src="/js/layer/layer.js"></script>
<!-- laydate -->
<script src="/js/laydate/laydate.js"></script>
<!-- 工具类js common.js 提供对js字符串的操作startWith endWith trim等js方法  -->
<script type="text/javascript" src='/js/common.js'></script>
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
