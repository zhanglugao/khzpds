<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户首页</title>
<jsp:include page="../common/open_inc.jsp"></jsp:include>
<link href="/css/user-login.css" rel="stylesheet" type="text/css">
<link href="/css/guide.css" rel="stylesheet" type="text/css">
<!-- <script type="text/javascript" src="js/Validform.js"></script> -->
<style type="text/css">
.form-left table thead th.cz{background:#fa8564; width:250px;}
.form-left table tr td.cz1{background:#fa8564; width:250px;}
</style>
<script type="text/javascript">
	function getData(){
		$.ajax({
			url:"/userApply/getData",
			dataType:"json",
			success:function(data){
				var list=data.applyList;
				$(".trclass").remove();
				for(var i=0;i<list.length;i++){
					var obj=list[i];
					var html="<tr class='trclass'><td class='zp1'>"+obj.productionName+"</td><td class='ss1'>"+obj.artist+"</td><td class='ts1'>"+obj.createTime+
						"</td><td class='zt1'>"+obj.applyStatus+"</td>";
					if(obj.applyStatus=='已报名'){
						html+="<td class='cz1'><a onclick='toedit(\""+obj.id+"\")' href='javascript:;'>查看</a>&nbsp;<a onclick='cancelApply(\""+obj.id+"\")' href='javascript:;'>撤销</a>&nbsp;<a onclick='downloadApplyTable(\""+obj.competitionType+"\",\""+obj.id+"\")' href='javascript:;'>下载报名表</a></td></tr>";
					}else if(obj.applyStatus=='已取消'){
						html+="<td class='cz1'><a onclick='toedit(\""+obj.id+"\")' href='javascript:;'>编辑</a>&nbsp;<a onclick='downloadApplyTable(\""+obj.competitionType+"\",\""+obj.id+"\")' href='javascript:;'>下载报名表</a></td></tr>";
					}else if (obj.applyStatus=='新建'){
						html+="<td class='cz1'><a onclick='toedit(\""+obj.id+"\")' href='javascript:;'>编辑</a>&nbsp;<a onclick='downloadApplyTable(\""+obj.competitionType+"\",\""+obj.id+"\")' href='javascript:;'>下载报名表</a></td></tr>";
					}
					$("#dataDiv").append(html);
				}
			}
		});
	}
	function downloadApplyTable(type,applyId){
		window.open("/userApply/download?type="+type+"&applyId="+applyId,"_blank");
	}
	
	function toedit(id){
		window.location.href="/userApply/toApply?id="+id;
	}
	
	function cancelApply(id){
		$.ajax({
			url:"/userApply/cancelApply",
			data:{id:id},
			dataType:"json",
			success:function(data){
				if(data.status=='0'){
					layer.msg("撤销成功",{icon:1});
					getData();
				}else{
					layer.alert(data.error_desc);
				}
			},error:function(){
				layer.alert(errorText);
			}
		});
	}
	$(document).ready(function(){
		getData();
	});
	
	function cancelInput(){
		$("#userName").val("${sessionScope.User_session_key.userName }");
		$("#realName").val("${sessionScope.User_session_key.realName }");
		$("#password").val("${sessionScope.User_session_key.password }");
		$("#password2").val("${sessionScope.User_session_key.password }");
	}
	
	function changeUserInfo(){
		var userName=$("#userName").val();
		if($.trim(userName)==''){
			layer.msg("会员名称不能为空",{icon:4});return;
		}
		if(userName.length<3||userName.length>10){
			layer.msg('会员名称长度必须介于3~15位之间',{icon:4});return;
		}
		var realName=$("#realName").val();
		if($.trim(realName)==''){
			layer.msg("真实姓名不能为空",{icon:4});return;
		}
		var password=$("#password").val();
		if(trim(password)==''){
			layer.msg("密码不能为空",{icon:4});return;
		}
		if(password.length<6||password.length>20){
			layer.msg('密码长度必须介于6~20位之间',{icon:4});return;
		}
		var password2=$("#password2").val();
		if(password2!=password){
			layer.msg('两次填写密码必须一致',{icon:4});return;
		}
		
		$.ajax({
			url:"/user/changeUserInfo",
			data:{userName:userName,password:password,realName:realName},
			dataType:"json",
			success:function(data){
				if(data.status=='0'){
					layer.msg("更改成功",{icon:1});
				}else{
					layer.alert(data.error_desc);
				}
			},error:function(){
				layer.alert(errorText);
			}
		});
	}
</script>
</head>
<body>
     <!-- 头部 -->
     <div style="position:absolute;right:50px;top:25px;z-index:2;"><span>${sessionScope.User_session_key.userName }</span>|<a href="/user/logout">退出</a>|<a href="/user/openIndex">个人中心</a></div>
     <div class="head">
          <img src="/images/sj-top1.png" class="sj-top">
          <div class="head-i w1348 m0">
              
              <div class="nav fr mt15">
                    <p id='loginDiv' class="head-login fl" style="visibility:hidden">
                       <a href="login.html">登   录</a>
                       <a href="register.html">注  册</a>
                  </p>

              </div>
              
          </div>
     </div>
     <!--头部banner-->
       <div class="headbanner">
          <div class="headbanner-i w1348 m0">
          </div>
       </div> 
       <!-- 中间内容 -->
      <div class="main">
               <!-- 报名表左侧 -->
               <div class="sidebar fl">
                    <ul>
                       <li class="bm cur"><a   href="javascript:;" >报名情况</a></li>
                        <li class="cur11" ><a  href="javascript:;" class="color" >更换会员信息</a></li>
                        <li class="cur22"><a  href="/user/logout" class="color">退出</a></li>
                    </ul>
               </div>
               <div class="content fl">
                     <div class="form-left fl">
                     	<table class="maintain on">
                          <thead>
                          <tr>
                             <th class="zp">作品名称</th>
                              <th class="ss">所属类别</th>
                               <th class="ts">提交时间</th>
                                <th class="zt">报名状态</th>
                                <th class="cz">操作</th>
                                </tr>
                          </thead>
                      <tbody id='dataDiv'>
                           <!-- <tr>
                                <td class="zp1">作品名称</td>
                              <td class="ss1">所属类别</td>
                               <td class="ts1">提交时间</td>
                                <td class="zt1">已上传  提交报名</td>
                            </tr>
                            <tr>
                                <td class="zp1">作品名称</td>
                              <td class="ss1">所属类别</td>
                               <td class="ts1">提交时间</td>
                                <td class="zt1">已报名</td>
                            </tr> -->
                      </tbody>    
                     </table>
                        <form class="maintain">
                            <ul  class="left-form margin">
                               <!-- 作品名称 -->
                                <li>
                                    <span>会员名</span>
                                    <input value="${sessionScope.User_session_key.userName }" id='userName' type="text" style="width:260px;" />
                                </li>
                                <li>
                                    <span>真实姓名</span>
                                    <input value="${sessionScope.User_session_key.realName }" id='realName' type="text" style="width:260px;" />
                                </li>
                                <li>
                                    <span>输入新密码</span>
                                    <input value="${sessionScope.User_session_key.password }" id='password' type="password" style="width:260px;" />
                                </li>
                                 <li>
                                    <span>再次输入新密码</span>
                                    <input value="${sessionScope.User_session_key.password }" id='password2' type="password" style="width:260px;" />
                                </li>
                                 <li class="gg">
                                   <p >
                                       <a onclick="changeUserInfo()" href="javascript:;" >确认更改</a>
                                       <a onclick='cancelInput()' href="javascript:;">放弃更改</a>
                                  </p>
                                </li>
                              
                             </ul>   
                          
                         
                        </form>
                         
                    <dl class="m0 btn-bm">
                      <dd class="margin-left:0">
                          <a href="http://khds.sxl.cn" target="_blank" class="enter"></a>
                          <span><img src="/images/g1.png" width="120" height="28" alt="进入大赛"></span>
                      </dd>
                      <dd>
                          <a href="/novel.html" class="novel" ></a>
                          <span><img src="/images/g2.png" width="120" height="30" alt="科幻小说报名"></span>
                      </dd>
                      <dd>
                          <a href="/draw.html" class="draw" ></a>
                          <span><img src="/images/g3.png" width="120" height="30" alt="科幻画报名"></span>
                      </dd>
                      <dd>
                          <a href="/video.html" class="video" ></a>
                          <span><img src="/images/g4.png" width="120" height="30" alt="科幻视频报名"></span>
                      </dd>
                  </dl> 
               </div>
               </div>
      </div>
       <!-- 导航部分 -->
      
     <!-- 底部 -->
    <!--  <div class="footer">
        <div class="footer-i w1348 m0">
              Copyright © 2016-2017 Science  contest
        </div>
     </div> -->
  
	  <script type="text/javascript" src="/js/index.js"></script>
    <script type="text/javascript">
</script>
	
</body>
</html>