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
</head>
<body>
     <!-- 头部 -->
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
                       <li class="bm cur"><a   href="javascript:;" >更换会员信息</a></li>
                        <li class="cur11" ><a  href="javascript:;" class="color" >报名情况</a></li>
                        <li class="cur22"><a  href="/user/logout" class="color">退出</a></li>
                    </ul>
               </div>
               <div class="content fl">
                     <div class="form-left fl">
                        <form class="maintain on">
                            <ul  class="left-form margin">
                               <!-- 作品名称 -->
                                <li>
                                    <span>更改会员名</span>
                                    <input id='userName' type="text" style="width:260px;" />
                                </li>
                                <li>
                                    <span>输入密码</span>
                                    <input id='password' type="text" style="width:260px;" />
                                </li>
                                 <li>
                                    <span>再次输入密码</span>
                                    <input id='password2' type="text" style="width:260px;" />
                                </li>
                                 <li class="gg">
                                   <p >
                                       <a onclick="changeUserName()" href="javascript:;" >确认更改</a>
                                       <a onclick='cancelInput()' href="javascript:;">放弃更改</a>
                                  </p>
                                </li>
                              
                             </ul>   
                          
                         
                        </form>
                         <table class="maintain">
                          <thead>
                             <th class="zp">作品名称</th>
                              <th class="ss">所属类别</th>
                               <th class="ts">提交时间</th>
                                <th class="zt">报名状态</th>
                          </thead>
                      <tbody style="height: 690px;overflow-y: auto;">
                           <tr>
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
                            </tr>
                        
                 
  
                      </tbody>    


                     </table>
                    <dl class="m0 btn-bm">
                      <dd class="margin-left:0">
                          <a href="javascript:;" class="enter"></a>
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
     <div class="footer">
        <div class="footer-i w1348 m0">
              Copyright © 2016-2017 Science  contest
        </div>
     </div>
  
	  <script type="text/javascript" src="/js/index.js"></script>
    <script type="text/javascript">
</script>
	
</body>
</html>