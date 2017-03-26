<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科幻作品大赛--科幻画投票</title>
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
	getData(303001,304001);
	getData(303002,304002);
});
function getData(applyGroup,applyYearGroup){
	if($("#"+applyGroup+"-"+applyYearGroup).text()==''){
		$.ajax({
			url:"/vote/getVoteData",
			data:{page_size:1000,applyGroup:applyGroup,applyYearGroup:applyYearGroup,itemId:itemId,itemType:itemType},
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
					var html="<dl><dt><a href='/userApply/showFile?id="+obj.id+"' target='_blank'><img src='${lookdir}"+obj.filePath+"' width='429' height='322'></a></dt>"
						+"<dd><span class='number'>NO."+obj.vdef1+"&nbsp;未来中国</span><span class='auther'>作者："+obj.realName+"</span>"
						+"<p><span class='fl'><img onclick='vote(\""+obj.id+"\",\""+applyGroup+"\",\""+applyYearGroup+"\")' src='/img/vote1.png' width='76' height='36' class='mt10'></span>"
                        +"<span class='fl mt10 ml10'>票数：<i id='"+obj.id+"voteNum'>"+obj.voteNum+"</i></span></p></dd></dl>";
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
                        <li><a id='indexa' href="/vote/voteIndex">首页</a></li>
                        <li><a href="/vote/votePage?itemType=301001">科幻小说投票</a></li>
                        <li><a href="javascript:;" class="cur">科幻画投票</a></li>
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
     <div class="banner draw">
         <div class="banner-i  w1348 draw1  m0">
              <i><img src="/img/vote-banner1.png" width="373" height="243" alt="科幻画投票"></i>
         </div>
     </div>
     <!--大赛简介 -->
     <div class="introduction draw2">
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
                      <li><img src="/img/tip1-1.png" width="250" height="285" alt="作品创作" ></li>
                      <li><img src="/img/tip2-1.png" width="250" height="285" alt="作品创作" ></li>
                      <li><img src="/img/tip3-1.png" width="250" height="285" alt="作品创作" ></li>
                      <li><img src="/img/tip4-1.png" width="250" height="285" alt="作品创作" ></li>
                      <li><img src="/img/tip5-1.png" width="250" height="285" alt="作品创作" ></li>
                </ul>
          </div>
     </div>
     <div class="wx-novel" id="dataDiv">
              <div class="wt1700 m0">
                  <h2 class="title mb60">手绘组</h2>
                  <!-- 小学  中学 大学 -->
                    <ul class="novel-tile wx">
                        <li class="xx" onclick="getData(303001,304001)">小学组</li>
                        <li class="zx" onclick="getData(303001,304002)">中学组</li>
                        <li class="dx" onclick="getData(303001,304003)">大学组</li>
                    </ul>
                    <!-- 手绘组 小学 -->
                    <div class="main xx on " id="303001-304001"></div>
                    <!-- 手绘组 中学学 -->
                    <div class="main  " id="303001-304002"></div>
                       <!-- 手绘组 大学 -->
                    <div class="main   " id="303001-304003"></div>

                    <!-- 电脑绘图组 -->
                    <h2 class="title mb60">电脑绘图组</h2>
                  <!-- 小学  中学 大学 -->
                    <ul class="novel-tile wx dn">
<!--                         <li class="xx" onclick="getData(303002,304001)">小学组</li>
 -->                        <li class="xx" onclick="getData(303002,304002)">中学组</li>
                        <li class="zx" onclick="getData(303002,304003)">大学组</li>
                    </ul>
                    <!-- 电脑绘图组 小学 -->
                   <!--  <div class="main1 xx on " id="303002-304001"></div> -->
                    <!-- 电脑绘图组 中学 -->
                    <div class="main1 xx on" id="303002-304002"></div>
                       <!-- 手绘组 大学 -->
                    <div class="main1   " id="303002-304003"></div>
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
           // 手绘组
           $('.wx-novel ul.novel-tile.wx li').click(function(){
                var index=$('.wx-novel ul.novel-tile.wx li').index(this);
                var num= $('.wx-novel ul.novel-tile.wx li').index(this)
                 if (num%3==0) {$(this).addClass('xx');$('.main').eq(index) .addClass('xx')}
                 if (num%3==1) {$(this).addClass('zx');$('.main').eq(index) .addClass('zx')}
                 if (num%3==2) {$(this).addClass('dx');$('.main').eq(index) .addClass('dx')}
               
         $('.main').eq(index) .addClass('on').siblings('.main').removeClass('on');    
      }); 
           // 手绘组
           $('.wx-novel ul.novel-tile.dn li').click(function(){
                var index=$('.wx-novel ul.novel-tile.wx.dn li').index(this);
                var num= $('.wx-novel ul.novel-tile.wx.dn li').index(this)
                 if (num%3==0) {$(this).addClass('xx');$('.main1').eq(index) .addClass('xx')}
                 if (num%3==1) {$(this).addClass('zx');$('.main1').eq(index) .addClass('zx')}
                 if (num%3==2) {$(this).addClass('dx');$('.main1').eq(index) .addClass('dx')}
               
         $('.main1').eq(index) .addClass('on').siblings('.main1').removeClass('on');    
      }); 
        </script>
  
  
  

</body>
  </html>