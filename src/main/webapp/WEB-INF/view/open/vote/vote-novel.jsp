<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科幻作品大赛--科幻小说投票</title>
<link rel="stylesheet" href="/css/vote/style.css"/>
<link rel="stylesheet" href="/css/vote/header.css"/>
<link rel="stylesheet" type="text/css" href="/css/vote/registration.css">
<script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src='/js/common.js'></script>
<script type="text/javascript" src='/js/base64.js'></script>
<script type="text/javascript" src='/js/cookieOperate.js'></script>
<script type="text/javascript" src='/js/autoLogin.js'></script>
<script src="/js/layer/layer.js"></script>
<script type="text/javascript">
	var itemId="${itemId}";
	var itemType="${itemType}";
	$(document).ready(function(){
		<c:if test="${empty itemId}">
			layer.alert("当前没有可供投票的本类型比赛项目");
			$("#dataDiv").css("display","none");
		</c:if>
		getData(305001,306001);
		getData(305002,306001);
	});
	function getData(applyGroup,applyYearGroup){
		if($("#"+applyGroup+"-"+applyYearGroup).text()==''){
			$.ajax({
				url:"/vote/getVoteData",
				data:{applyGroup:applyGroup,applyYearGroup:applyYearGroup,itemId:itemId,itemType:itemType},
				dataType:"json",
				type:"post",
				success:function(data){
					var rows=data.rows;
					for(var i=0;i<rows.length;i++){
						var obj=rows[i];
						if(typeof(obj.applyYearGroup)=='undefined'){
							obj.applyYearGroup="";
						}
						if(typeof(obj.applyGroup)=='undefined'){
							obj.applyGroup="";
						}
						var html="<dl><dt><a href='javascript:;'><img src='/img/vote-dx.jpg' width='237' height='302'></a></dt>"
							+"<dd><span>NO."+obj.vdef1+"</span><span>《"+obj.productionName+"》</span><span>作者："+obj.realName+"</span><span><img onclick='vote(\""+obj.id+"\",\""+applyGroup+"\",\""+applyYearGroup+"\")' style='cursor:pointer' src='/img/vote1.png' class='mt20' ></span>"
							+"<span>票数：<i id='"+obj.id+"voteNum'>"+obj.voteNum+"</i></span></dd></dl>";
						$("#"+applyGroup+"-"+applyYearGroup).append(html);
					}
				}
			});
		}
	}
	
	
	function vote(applyId,applyGroup,applyYearGroup){
		$.ajax({
			url:"/vote/vote",
			data:{applyId:applyId,applyGroup:applyGroup,applyYearGroup:applyYearGroup},
			dataType:"json",
			type:"post",
			success:function(data){
				if(data.status=="0"){
					layer.msg("投票成功",{icon:1});
					var voteNum=parseInt($("#"+applyId+"voteNum").text());
					$("#"+applyId+"voteNum").text(voteNum+1);
				}else{
					layer.alert(data.error_desc);
				}
			}
		});
	}
</script>
</head>
<body>
     <!-- 头部 -->
    <div id="loginDiv2" style="display:none;position:absolute;right:50px;top:25px;"><span id='loginName'></span>|<a href="/user/logout">退出</a>|<a href="/user/openIndex">个人中心</a></div>
     <div class="head">
          <div class="head-i w1348 m0">
               <i class="fl mr10">
                   <img src="/images/logo1.jpg" height="70" width="70" alt="logo">
               </i>
               <i class="logo mt15 fl">
                  <img src="/images/logo.png" alt="logo" width="326" height="37">
               </i>
              <div class="nav fr">
                  <ul class="nav-text fl">
                        <li><a id='indexa' href="/index.html">首页</a></li>
                        <li><a href="javascript:;" class="cur">科幻小说投票</a></li>
                        <li><a href="/vote/votePage?itemType=301002" >科幻画投票</a></li>
                        <li><a href="/vote/votePage?itemType=301003" >科幻微视频投票</a></li>
                  </ul>
                  <p id='loginDiv' class="head-login fl mt15" style="display:none;">
                       <a href="/login.html">登  录</a>
                       <a href="/register.html">注  册</a>
                  </p>
              </div>
              
          </div>
     </div>
     <!--科幻画报报名内容-->
     <div class="banner novel">
         <div class="banner-i  w1348 novel1  m0">
              <i><img src="/img/vote-banner.png" width="1124" height="630" alt="科幻小说报名"></i>
         </div>
     </div>
     <!--大赛简介 -->
     <div class="introduction novel2">
            <div class="introduction-i w1348 m0">
                 <dl>
                      <dt>大赛简介</dt>
                      <dd>为深入贯彻实施创新驱动发展战略，按照科技部、中央宣传部、中国科协《关于举办2016年科技活动周的通知》（国科发政〔2016〕101号）要求，在全国科技活动周组委会的指导下，科技部中国科学技术交流中心、教育部中国教育科学研究院决定联合组织举办科技活动周重大示范活动——“首届全国青少年优秀原创科幻作品大赛”活动。
                      </dd>
                      <dd>活动旨在激发青少年创作活力，培养青少年的创新精神，挖掘青少年的创造潜能，在全社会特别是青少年群体中营造敢于科学幻想，促进未来创想，弘扬科学精神的良好氛围。
                      </dd>
                 </dl>

            </div>
     </div>
     <!-- 大赛流程 -->
     <div class="process">
          <div class="process-i wt1700 m0">
                <p class="lc">大赛流程：</p>
                <ul class="zp">
                      <li><img src="/images/tip-1.png" width="250" height="285" alt="作品创作" ></li>
                      <li><img src="/images/tip-2.png" width="250" height="285" alt="作品创作" ></li>
                      <li><img src="/images/tip-3.png" width="250" height="285" alt="作品创作" ></li>
                      <li><img src="/images/tip-4.png" width="250" height="285" alt="作品创作" ></li>
                      <li><img src="/images/tip-5.png" width="250" height="285" alt="作品创作" ></li>
                   
                </ul>
          </div>
     </div>
     <div class="wx-novel"  id="dataDiv">
              <div class="wt1700 m0">
                  <h2 class="title mb60">微型小说</h2>
                  <!-- 小学  中学 大学  社会人士 -->
                    <ul class="novel-tile wx">
                        <li class="xx" onclick="getData(305001,306001)">小学</li>
                        <li class="zx" onclick="getData(305001,306002)">中学</li>
                        <li class="dx" onclick="getData(305001,306003)">大学</li>
                        <li class="sh" onclick="getData(305001,306004)">社会人士</li>
                    </ul>
                    <!-- 微型小说  小学 -->
                    <div class="content xx on " id="305001-306001"></div>
                    <!-- 微型小说  中学 -->
                    <div class="content xx " id="305001-306002"></div>
                    <!--微型小说  大学 -->
                    <div class="content xx " id="305001-306003"></div>
                    <!-- 微型小说  社会 -->
                    <div class="content xx " id="305001-306004"></div>
                 <h2 class="title mt20">中篇小说</h2>
                 <ul class="novel-tile dx">
                        <li class="xx" onclick="getData(305002,306001)">小学</li>
                        <li class="zx" onclick="getData(305002,306002)">中学</li>
                        <li class="dx" onclick="getData(305002,306003)">大学</li>
                        <li class="sh" onclick="getData(305002,306004)">社会人士</li>
                    </ul>
                  <!-- 短型小说  小学 -->
                    <div class="content1 xx on " id="305002-306001"></div>
                    <!-- 短型小说  中学 -->
                    <div class="content1 xx " id="305002-306002"></div>
                    <!--短型小说  大学 -->
                    <div class="content1 xx " id="305002-306003"></div>
                    <!-- 短型小说  社会 -->
                    <div class="content1 xx " id="305002-306004"></div>
            </div>
     </div>
  
  
    

     <!-- 底部 -->
     <!-- <div class="footer">
        <div class="footer-i w1348 m0">
              Copyright © 2016-2017 Science  contest
        </div>
     </div> -->
    <script type="text/javascript" src="/js/jquery.nicescroll.js"></script>
        <script type="text/javascript">
           // 微型小说
           $('.wx-novel ul.novel-tile.wx li').click(function(){
                var index=$('.wx-novel ul.novel-tile.wx li').index(this);
                var num= $('.wx-novel ul.novel-tile.wx li').index(this)
                 if (num%4==0) {$(this).addClass('xx');$('.content').eq(index) .addClass('xx')}
                 if (num%4==1) {$(this).addClass('zx');$('.content').eq(index) .addClass('zx')}
                 if (num%4==2) {$(this).addClass('dx');$('.content').eq(index) .addClass('dx')}
                 if (num%4==3) {$(this).addClass('sh');$('.content').eq(index) .addClass('sh')}
         $('.content').eq(index) .addClass('on').siblings('.content').removeClass('on');    
      }); 
           // 微型小说
           $('.wx-novel ul.novel-tile.dx li').click(function(){
                var index=$('.wx-novel ul.novel-tile.dx li').index(this);
                var num= $('.wx-novel ul.novel-tile.dx li').index(this)
                 if (num%4==0) {$(this).addClass('xx');$('.content1').eq(index) .addClass('xx')}
                 if (num%4==1) {$(this).addClass('zx');$('.content1').eq(index) .addClass('zx')}
                 if (num%4==2) {$(this).addClass('dx');$('.content1').eq(index) .addClass('dx')}
                 if (num%4==3) {$(this).addClass('sh');$('.content1').eq(index) .addClass('sh')}
         $('.content1').eq(index) .addClass('on').siblings('.content1').removeClass('on');    
      }); 
            $('.content').niceScroll({
                cursorcolor: "#a20fc8",//#CC0071 光标颜色
                cursoropacitymax: 1, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0
                touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备
                cursorwidth: "10px", //像素光标的宽度
                cursorborder: "0", //   游标边框css定义
                cursorborderradius: "5px",//以像素为光标边界半径
                autohidemode: false //是否隐藏滚动条
            });
            $('.content1').niceScroll({
                cursorcolor: "#a20fc8",//#CC0071 光标颜色
                cursoropacitymax: 1, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0
                touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备
                cursorwidth: "10px", //像素光标的宽度
                cursorborder: "0", //   游标边框css定义
                cursorborderradius: "5px",//以像素为光标边界半径
                autohidemode: false //是否隐藏滚动条
            });
        </script>
  
  

</body>
  </html>