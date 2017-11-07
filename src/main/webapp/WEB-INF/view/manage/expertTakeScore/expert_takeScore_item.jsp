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
	<style>
		.pic{
			width:454px; height:343px; display:block;border:3px solid #000000;
		}
	</style>
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

            var clientHeight2=document.body.clientHeight;
            $(".itemDiv").css("height",clientHeight2*0.6);
        });
        function takeScoreSuccess(){
            layer.closeAll();
            layer.msg("打分成功",{icon:1});
            getApplyData($("#"+tabId+"current_page").val());
        }
        var pageSize=100;
        function getApplyData(currentPage,ifSearch){
            $("#"+tabId+"current_page").val(currentPage);
            if(typeof(ifSearch)=='undefined'){
                ifSearch=true;
            }
            if($("#"+tabId+"t tr").length<=1||ifSearch){
                $.ajax({
                    url:"/expertApprove/getApplyData?itemId="+tabId+"&type=${type}"+"&page_size="+pageSize,
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
                            var checkHtml="<input type='checkbox' name='"+tabId+"sel' value='"+obj.id+"'/>";
                            if(typeof(obj.approveTime)=='undefined'){
                                obj.approveTime='';
                            }
                            if(typeof(obj.reviewPoint)=='undefined'){
                                obj.reviewPoint="";
                            }
                            /* if(obj.markingUser==''&&obj.markingTime==''){ */
                            option+="&nbsp;<button type='button' onclick='takeScoreDis(\""+obj.id+"\")' class='btn btn-primary'>打分</button>";
                            /* }else{ */
                            option+="&nbsp;<button type='button' onclick='takeScoreDis(\""+obj.id+"\",1)' class='btn btn-primary'>打分详情</button>";
                            /* } */
                            if(typeof(obj.filePath)!='undefined'&&obj.filePath!=''){
                                option+="&nbsp;<button type='button' onclick='viewProInfo(\""+obj.id+"\")' class='btn btn-primary'>查看作品详情</button>";
                            }
                            option+="&nbsp;<button type='button' onclick='viewDesc(\""+obj.id+"\",\""+obj.ideaDesc+"\")' class='btn btn-primary'>说明</button>";
                            var html="<tr class='"+tabId+"class'><td>"+checkHtml+"</td><td>"+obj.userName+"</td><td>"+obj.realName+"</td><td>"+obj.orgName+"</td>"
                                +"<td>"+obj.proName+"</td><td>"+obj.itemStatus+"</td><td>"+obj.applyGroup+"</td><td>"+obj.applyYearGroup+"</td><td>"+obj.aveScore+"</td><td>"+option+"</td></tr>";
                            //$("#"+tabId+"t").append(html);
                            var filePath="";
                            console.log(obj.competitionType);
                            if("301001"==obj.competitionType){
                                filePath="/img/vote-dx.jpg";
                            }
                            if("301002"==obj.competitionType){
                                filePath="${lookdir}"+obj.filePath;
                            }
                            if("301003"==obj.competitionType){
                                filePath="${lookdir}/video/2017print/"+obj.id+".png";
                            }
                            var html2="<div class='"+tabId+"class' style='float:left;'><img src='"+filePath+"' class='pic'>"+obj.proName+"</div>";
                            $("#"+tabId+"div").append(html2);
                        }
                        //setPageHtml(data.total_count, "pageDiv", "getApplyData", currentPage,pageSize);
                    },error:function(){
                        layer.alert(errorText);
                    }
                });
            }
        }
        var takeId="";
        function takeScoreDis(id,flag){
            var ifCanAdd=0;
            if(typeof(flag)=='undefined'){
                ifCanAdd=1;
            }
            takeId=id;
            if(ifCanAdd==1){
                //验证能否添加
                $.ajax({
                    url:"/expertTakeScore/validateTake",
                    data:{id:id,type:'${type}'},
                    type:"post",
                    dataType:"json",
                    success:function(data){
                        if(data.status=='0'){
                            //进入打分详情
                            layer.open({
                                type: 2,
                                content:"/expertTakeScore/getTakeScoreInfo?id="+takeId+"&ifCanAdd="+ifCanAdd+"&type=${type}",
                                shadeClose: true,//开启遮罩关闭
                                title:false,
                                scrollbar: false,
                                area: ['40%', '70%']
                            });
                        }else{
                            layer.alert(data.error_desc);
                        }
                    }
                });
            }else{
                layer.open({
                    type: 2,
                    content:"/expertTakeScore/getTakeScoreInfo?id="+takeId+"&ifCanAdd="+ifCanAdd+"&type=${type}",
                    shadeClose: true,//开启遮罩关闭
                    title:false,
                    scrollbar: false,
                    area: ['40%', '70%']
                });
            }
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
                    scrollbar: false,
                    area: ['80%', '80%']
                });
            }else{
                layer.open({
                    type: 1,
                    content:desc,
                    scrollbar: false,
                    shadeClose: true,//开启遮罩关闭
                    title:false,
                    area: ['400px', '400px']
                });
            }
        }
        var lookIndex;
        function viewProInfo(id){
            layer.open({
                type: 2,
                content:"/userApply/showFile?id="+id,
                scrollbar: false,
                shadeClose: true,//开启遮罩关闭
                title:false,
                area: ['80%', '80%'],
                success: function(layero, index){
                    $(this).click(function(){
                        alert(1);
                    });
                }
            });
        }

        function returnIndex(){
            window.location.href="/expertTakeScore/index?type=${type}";
        }
	</script>
</head>
<body class="skin-blue">
<%@include file="../common/manage_header.jsp"%>
<div class="wrapper row-offcanvas row-offcanvas-left">
	<div>
		<%@include file="../common/manage_left.jsp"%>
	</div>
	<aside class="right-side">
		<section class="content-header">
			<c:if test="${type=='fusai'}"><h1>专家复赛管理</h1></c:if>
			<c:if test="${type=='final'}"><h1>专家决赛管理</h1></c:if>
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
				</ul>
			</div>
			<div id="myTabContent" class="tab-content">
				<c:forEach items="${items}" var="item">
					<div class="tab-pane fade" id="${item.id}">
						<form id='${item.id }form'></form>
						<div id="${item.id}div" class='itemDiv'style="height:500px;overflow:scroll;"></div>
					</div>
				</c:forEach>
				</div>
		</section>
	</aside>
</div>
<div id='descDiv' style="display:none;text-align:center;margin-top:20px;" ></div>
<div id='takeScoreDiv' style="display:none;"></div>
</body>
</html>