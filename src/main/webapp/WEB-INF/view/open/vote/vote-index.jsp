<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科幻作品大赛投票页--引导页</title>
<link rel="stylesheet" href="/css/vote/style.css"/>
<link rel="stylesheet" href="/css/vote/header.css"/>
<link href="/css/vote/vote.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src='/js/common.js'></script>
<script type="text/javascript" src='/js/base64.js'></script>
<script type="text/javascript" src='/js/cookieOperate.js'></script>
<script type="text/javascript" src='/js/autoLogin.js'></script>
<script src="/js/layer/layer.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	getData(305001,306001,301001,"${novelId}");
	getData(305002,306001,301001,"${novelId}");
	getData("",304001,301002,"${paintId}");
	getData("",307001,301003,"${videoId}");
	getRankingData();
});
function getRankingData(){
	$.ajax({
		url:"/vote/getRankingData",
		data:{num:10},
		dataType:"json",
		type:"post",
		success:function(data){
			var rows=data.rows;
			for(var i=0;i<rows.length;i++){
				var obj=rows[i];
				if(obj.productionName.length>6){
					obj.productionName=obj.productionName.substring(0,5)+"...";
				}
				var html="<tr><td><em>"+(i+1)+"</em></td><td>"+obj.realName+"</td><td>"+obj.productionName+"</td><td><span class='vote-number'>"+obj.voteNum+"</span></td></tr>";
				$("#rankingData").append(html);
			}
		}
	});
	
    
    
}
function getData(applyGroup,applyYearGroup,itemType,itemId){
	if($.trim($("#"+applyGroup+"-"+applyYearGroup).text())==''){
		var page_size;
		if(itemType=='301001'){
			page_size=10;
		}else{
			page_size=20;
		}
		$.ajax({
			url:"/vote/getVoteData",
			data:{page_size:page_size,applyGroup:applyGroup,applyYearGroup:applyYearGroup,itemId:itemId,itemType:itemType},
			dataType:"json",
			type:"post",
			success:function(data){
				var rows=data.rows;
				if(itemType=='301001'){
					for(var i=0;i<rows.length;i++){
						var obj=rows[i];
						if(typeof(obj.applyYearGroup)=='undefined'){
							obj.applyYearGroup="";
						}
						if(typeof(obj.applyGroup)=='undefined'){
							obj.applyGroup="";
						}
					//小说
					var html="<dl><dt><a href='/userApply/showFile?id="+obj.id+"' target='_blank'><img src='/img/novel-pic.png' width='97' height='116'></a></dt>"
						+"<dd><span>NO."+obj.vdef1+"</span><span>《"+obj.productionName+"》</span><span>作者："+obj.realName+"</span><span><img onclick='vote(\""+obj.id+"\",\""+obj.applyGroup+"\",\""+obj.applyYearGroup+"\")' style='cursor:pointer' src='/img/vote-button.png' class='mt5' width='55' height='25'></span>"
						+"<span>票数：<i id='"+obj.id+"voteNum'>"+obj.voteNum+"</i></span></dd></dl>";
					$("#"+applyGroup+"-"+applyYearGroup).append(html);
					}
				}
				else if(itemType=='301002'){
					//科幻画
					var html="";
					for(var i=0;i<rows.length;i++){
						var obj=rows[i];
						if(typeof(obj.applyYearGroup)=='undefined'){
							obj.applyYearGroup="";
						}
						if(typeof(obj.applyGroup)=='undefined'){
							obj.applyGroup="";
						}
						if(i%4==0){
							//添加头
							html+="<ul>";
							html+="<li class='pic-one fl'>";
						}
						if(i%4==1){
							html+="<li class='pic-two'>";
						}
						if(i%4==2){
							html+="<li class='pic-three fl'>";
						}
						if(i%4==3){
							html+="<li class='pic-thour'>";
						}
						var zu="";
						if(obj.applyGroup=='303001'){
							zu="手绘组";
						}
						else if(obj.applyGroup=='303002'){
							zu="电脑绘图组";
						}
						html=html+"<div class='pic'><a href='/userApply/showFile?id="+obj.id+"' target='_blank'><img src='${lookdir}"+obj.filePath+"' /></a></div>"
							+"<div class='title mt20'><span>"+zu+"NO."+obj.vdef1+"&nbsp;"+obj.productionName+"</span><div class='cb fl db'><span>作者:"+obj.realName+"</span>"
							+"<p class='fr'><span class='fl'>票数:<i id='"+obj.id+"voteNum'>"+obj.voteNum+"</i></span><img style='cursor:pointer' onclick='vote(\""+obj.id+"\",\""+obj.applyGroup+"\",\""+obj.applyYearGroup+"\")' src='/img/vote-button1.png' width='76' height='35' class='vote-btn fl'>"
							+"</p></div></div></li>";
						if((i%4==3)||i==rows.length-1){
							html+="</ul>";
						}
					}
					$("#"+applyGroup+"-"+applyYearGroup).append(html);
					if(applyYearGroup=='304001'){
						//小学
						jQuery(".multipleColumn.khh-xx").slide({titCell:".hd ul",mainCell:".bd .ulWrap",autoPage:true,effect:"leftLoop",autoPlay:true,vis:1});
					}
					if(applyYearGroup=='304002'){
						//中学
						jQuery(".multipleColumn.khh-zx").slide({titCell:".hd ul",mainCell:".bd .ulWrap",autoPage:true,effect:"leftLoop",autoPlay:true,vis:1});
					}
					if(applyYearGroup=='304003'){
						//大学
						jQuery(".multipleColumn.khh-dx").slidde({titCell:".hd ul",mainCell:".bd .ulWrap",autoPage:true,effect:"leftLoop",autoPlay:true,vis:1});
					}
				}
				else if(itemType=='301003'){
					//科幻视频
					var html="";
					for(var i=0;i<rows.length;i++){
						var obj=rows[i];
						if(typeof(obj.applyYearGroup)=='undefined'){
							obj.applyYearGroup="";
						}
						if(typeof(obj.applyGroup)=='undefined'){
							obj.applyGroup="";
						}
						if(i%4==0){
							//添加头
							html+="<ul>";
							html+="<li class='pic-one fl'>";
						}
						if(i%4==1){
							html+="<li class='pic-two'>";
						}
						if(i%4==2){
							html+="<li class='pic-three fl'>";
						}
						if(i%4==3){
							html+="<li class='pic-thour'>";
						}
						html=html+"<div class='pic'><a href='/userApply/showFile?id="+obj.id+"' target='_blank'><img src='${lookdir}/video/2017print/"+obj.id+".png'  /></a></div>"
							+"<div class='title mt20'><span>NO."+obj.vdef1+"&nbsp;"+obj.productionName+"</span><div class='cb fl db'><span>作者:"+obj.realName+"</span>"
							+"<p class='fr'><span class='fl'>票数:<i id='"+obj.id+"voteNum'>"+obj.voteNum+"</i></span><img style='cursor:pointer' onclick='vote(\""+obj.id+"\",\""+obj.applyGroup+"\",\""+obj.applyYearGroup+"\")' src='/img/vote-button1.png' width='76' height='35' class='vote-btn fl'>"
							+"</p></div></div></li>";
						if((i%4==3&&i!=0)||i==rows.length-1){
							html+="</ul>";
						}
					}
					$("#"+applyGroup+"-"+applyYearGroup).append(html);
					if(applyYearGroup=='307001'){
						jQuery(".multipleColumn.khsp-dx").slide({titCell:".hd ul",mainCell:".bd .ulWrap",autoPage:true,effect:"leftLoop",autoPlay:true,vis:1});
					}
					if(applyYearGroup=='307002'){
						jQuery(".multipleColumn.khsp-sh").slide({titCell:".hd ul",mainCell:".bd .ulWrap",autoPage:true,effect:"leftLoop",autoPlay:true,vis:1});
					}
				}
			}
		});
	}
}

function vote(applyId,applyGroup,applyYearGroup){
	$.ajax({
		url:"/vote/vote",
		data:{applyId:applyId,applyYearGroup:applyYearGroup,applyGroup:applyGroup},
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

function searchNovel(applyGroup1,applyGroup2,applyYearGroup){
	getData(applyGroup1,applyYearGroup,301001,"${novelId}");
	getData(applyGroup2,applyYearGroup,301001,"${novelId}");
}
</script>
</head>
<body>
<!-- 头部 -->
	<div id="loginDiv2" style="display:none;position:absolute;right:50px;top:25px;"><span id='loginName'></span>|<a href="/user/logout">退出</a>|<a href="/user/openIndex">个人中心</a></div>
     <div class="head">
          <div class="head-i w1348 m0">
              <div class="nav fr mt15">
                  <p id='loginDiv' class="head-login fl" style="display:none">
                       <a href="/login.html">登   录</a>
                       <a href="/register.html">注  册</a>
                  </p>
              </div>
          </div>
     </div>
<!--内容-->
<div class="banner-box">
  <div class="bd">
    <ul>
      <li style="background:url(/images/banner1.png) no-repeat;">
      <div class="m-width">
        <a href="javascript:void(0);"><img src="/images/banner1-1.png" class="m0" style="margin-left: 276px;margin-top:0px"/></a>
      </div>
      </li>

    </ul>
    <!-- <div class="share"> 
              
              <div class="b-share fl">
                   <span  class="shareTo fl"><img src="/img/add.png"> 分享到: </span>
                       <div class="bshare fl ml10" >
                        <a title="分享到QQ空间" class="bshare-qzone"></a>
                        <a title="分享到新浪微博" class="bshare-sinaminiblog"></a>
                        <a title="分享到微信" class="bshare-weixin"></a>
                       <a title="分享到腾讯微博" class="bshare-qqmb"></a>
                      <a title="分享到人人网" class="bshare-renren"></a>
                   </div>
               </div>
               <div class="login-style fr">
                     <a href="javascript:;"><img src="/img/weixin-login.png" alt="微信登陆"></a>
                     <a href="javascript:;"><img src="/img/qq-style.png" alt="qq登陆"></a>
               </div>
       </div> -->
     </div>
  </div>
</div>
<!--  -->
<div class="vote">
  <div class="wt1746 vote-title m0">
    <dl class="fl m0">
      <dd><a href="/index.html" class="vote-enter"><img src="/img/vote-enter.png"></a></dd>
      <dd><a href="/vote/votePage?itemType=301001" class="vote-novel"><img src="/img/vote-novel.png"></a></dd>
      <dd><a href="/vote/votePage?itemType=301002" class="vote-draw"><img src="/img/vote-draw.png"></a></dd>
      <dd><a href="/vote/votePage?itemType=301003" class="vote-video"><img src="/img/vote-video.png"></a></dd>
    </dl>
  </div>
  <div class="vote-content m0">
    <div class="vote-fl fl">
      <!-- 科幻小说投票 -->
      <div class="vote-kh">
        <!-- 科幻小说题目 -->
        <p class="vote-tm fl">
          <span class="mt20 ml30"><img src="/img/novel-title.png"></span>
          <span class="title-line"><img src="/img/line-vote.png"></span>
          <span class="title-line1"><img src="/img/line1-vote.png"></span>
          <a href="/vote/votePage?itemType=301001" class="title-more"><img src="/img/title-more.png"></a>
        </p>
        <ul class="novel-ul fl ml40 mt50">
          <li class="xx"  onclick="searchNovel(305001,305002,306001)">小学组</li>
          <li class="zx"  onclick="searchNovel(305001,305002,306002)">中学组</li>
          <li class="dx"  onclick="searchNovel(305001,305002,306003)">大学组</li>
          <li class="sh"  onclick="searchNovel(305001,305002,306004)">社会人士组</li>
        </ul>
        <ol class="tit-novel">
          <li class="wx-title"><span>微型小说</span></li>
          <li class="dp-title"><span>短篇小说</span></li>
        </ol>
        <!-- 小学组 -->
        <div class="novel-content xx on fl ml40 ">
          <!-- 微型小说 -->
          <div class="fl mt20 novel-wx">
          	<div id="305001-306001"></div>
            <a href="/vote/votePage?itemType=301001" class="more-novel">更多......</a>
          </div>
          <!-- 短形小说 -->
          <div class="fl mt20 novel-wx novel-dx">
          	<div id="305002-306001"></div>
            <a href="/vote/votePage?itemType=301001" class="more-novel">更多......</a>
          </div>
        </div>
        <!-- 中学组 -->
        <div class="novel-content fl ml40 ">
          <!-- 微型小说 -->
          <div class="fl mt20 novel-wx">
          	<div id="305001-306002"></div>
            <a href="/vote/votePage?itemType=301001" class="more-novel">更多......</a>
          </div>
          <!-- 短形小说 -->
          <div class="fl mt20 novel-wx novel-dx">
          	<div id="305002-306002"></div>
            <a href="/vote/votePage?itemType=301001" class="more-novel">更多......</a>
          </div>
        </div>
        <!-- 大学组 -->
        <div class="novel-content fl ml40 ">
          <!-- 微型小说 -->
          <div class="fl mt20 novel-wx">
          	<div id="305001-306003"></div>
            <a href="/vote/votePage?itemType=301001" class="more-novel">更多......</a>
          </div>
          <!-- 短形小说 -->
          <div class="fl mt20 novel-wx novel-dx">
          	<div id="305002-306003"></div>
            <a href="/vote/votePage?itemType=301001" class="more-novel">更多......</a>
          </div>
        </div>
        <!-- 社会人士组 -->
        <div class="novel-content fl ml40 ">
          <!-- 微型小说 -->
          <div class="fl mt20 novel-wx">
          	<div id="305001-306004"></div>
            <a href="/vote/votePage?itemType=301001" class="more-novel">更多......</a>
          </div>
          <!-- 短形小说 -->
          <div class="fl mt20 novel-wx novel-dx">
          	<div id="305002-306004"></div>
            <a href="/vote/votePage?itemType=301001" class="more-novel">更多......</a>
          </div>
        </div>
      </div>
      <!-- 科幻画投票 -->
      <div class="vote-kh">
            <p class="vote-tm  fl ">
              <span class="mt20 ml30"><img src="/img/draw-title.png" alt="科幻画"></span>
              <span class="title-line"><img src="/img/line-vote.png"></span>
              <span class="title-line1"><img src="/img/line1-vote.png"></span>
              <a href="/vote/votePage?itemType=301002" class="title-more"><img src="/img/title-more.png"></a>
            </p>
            <ul class="novel-ul khh fl ml40 mt50">
              <li class="xx">小学组</li>
               <li class="zx" onclick='getData("",304002,301002,"${paintId}")' >中学组</li>
              <li class="dx" onclick='getData("",304003,301002,"${paintId}")' >大学组</li>
            </ul>
            <!--小学 -->
            <div class="novel-content1 xx on fl ml40 ">
              <div class="multipleColumn khh-xx">
                        <div class="bd">
                         <div class="ulWrap" id='-304001'></div>
                         <!-- ulWrap End -->
                        </div>
                        <div class="hd">
                          <ul>
                          </ul>
                        </div>
                        <!-- bd End -->
                      </div>
            </div>
            <!-- 中学组 -->
            <div class="novel-content1  fl ml40 ">
              <div class="multipleColumn khh-zx">
                        <div class="bd">
                         <div class="ulWrap" id='-304002'></div>
                         <!-- ulWrap End -->
                        </div>
                        <div class="hd">
                          <ul>
                          </ul>
                        </div>
                        <!-- bd End -->
                      </div>
            </div>
            <!-- 大学组 -->
            <div class="novel-content1  fl ml40 ">
              <div class="multipleColumn khh-dx">
                        <div class="bd">
                         <div class="ulWrap" id='-304003'></div>
                         <!-- ulWrap End -->
                        </div>
                        <div class="hd">
                          <ul>
                          </ul>
                        </div>
                        <!-- bd End -->
                      </div>
            </div>
      </div>
      <!-- 科幻视频投票 -->
       <div class="vote-kh">
             <p class="vote-tm fl">
                 <span class="mt40 ml30"><img src="/img/video-title.png" alt="科幻微视频"></span>
                 <span class="title-line"><img src="/img/line-vote.png"></span>
                 <span class="title-line1"><img src="/img/line1-vote.png"></span>
                 <a href="/vote/votePage?itemType=301003" class="title-more"><img src="/img/title-more.png"></a>
            </p>
             <ul class="novel-ul kh fl ml40 mt50">
                <li class="sh" onclick='getData("",307001,301003,"${videoId}")'>大学组</li>
                <li class="dx" onclick='getData("",307002,301003,"${videoId}")'>社会人士组</li>
             </ul>
           <!--   大学组 -->
             <div class="novel-content2 sh on fl ml40 ">
                      <div class="multipleColumn khsp-dx">
                        <div class="bd">
                          <div class="ulWrap" id='-307001'></div>
                          <!-- ulWrap End -->
                        </div>
                        <div class="hd">
                          <ul>
                          </ul>
                        </div>
                        <!-- bd End -->
                      </div>
             </div>
              <!--  社会人士组 -->
               <div class="novel-content2 fl ml40 ">
                  <div class="multipleColumn khsp-sh">
                          <div class="bd">
                            <div class="ulWrap" id='-307002'></div>
                            <!-- ulWrap End -->
                          </div>
                          <div class="hd">
                            <ul>
                            </ul>
                          </div>
                          <!-- bd End -->
                        </div>
               </div>
       </div>
    </div>
    <!-- 作品排行 -->
    <div class="vote-fr fr ">
      <!-- 作品排行 -->
      <div class="work-top fl">
        <div>
          <span class="top-title"><img src="/img/top-ph.png"></span>
          <img src="/img/top-line.png" width="478" height="5" class="fl mt10">
        </div>
        <table class="pm">
            <thead>
                <tr>
                  <td>排名 </td>
                  <td>作者</td>
                  <td>作品名称</td>
                  <td>票数</td>
                </tr>
            </thead>
                 <tbody id='rankingData'>
                    
                 </tbody>
        </table>
      </div>
      <!--  大赛评委 -->
      <div class="pw fl">
            <div class="pw-title fl">
              <span class="top-title"><img src="/img/ds-pw.png" width="188" height="103"></span>
              <img src="/img/top-line.png" width="478" height="5" class="fl mt10">
            </div>
        <div class="fl">
            <div class="zj-scrool">
                <dl>
                  <dt class="fl"><a href="javascript:;"><img src="/img/zj-oy.jpg" width="221" height="248" alt="欧阳奋远"></a></dt>
                  <dd>
                        <span>欧阳自远</span>
                        <p> 中国科学院院士、第三世界科学院院士和国际宇航科学院院士。</p>
                        <p class="sec-p">从事天体化学、地球化学、月球与行星科学研究。</p>
                  </dd>
                </dl>
                <dl>
                  <dt class="fl"><a href="javascript:;"><img src="/img/zj-jl.jpg" width="221" height="248" alt="金力"></a></dt>
                  <dd>
                        <span>金力</span>
                        <p class="sec-p"> 中科院院士，主要研究方向为进化遗传学、人类群体遗传学和基因组学。发表SCI论文500多篇，被引2万多次。二次获国家自然科学二等奖、何梁何利奖科技进步奖等。</p>
                  </dd>
                </dl>
                <dl>
                  <dt class="fl"><a href="javascript:;"><img src="/img/zj-xy.jpg" width="221" height="248" alt="刘晓群"></a></dt>
                  <dd>
                      <span>刘晓群</span>
                        <p class="sec-p"> 中科院院士，主要研究方向为进化遗传学、人类群体遗传学和基因组学。发表SCI论文500多篇，被引2万多次。二次获国家自然科学二等奖、何梁何利奖科技进步奖等。</p>
                  </dd>
                </dl>
                 <dl>
                  <dt class="fl"><a href="javascript:;"><img src="/img/zj-yc.jpg" width="221" height="248" alt="郑永春"></a></dt>
                  <dd>
                      <span>郑永春</span>
                        <p class="sec-p">     中国科学院国家天文台科学传播中心主任、博士、研究员。中国科普作家协会副理事长、2016年获美国天文学会卡尔萨根奖。</p>
                  </dd>
                </dl>
                  <dl>
                  <dt class="fl"><a href="javascript:;"><img src="/img/zj-wy.jpg" width="221" height="248" alt="吴岩"></a></dt>
                  <dd>
                      <span>吴岩</span>
                        <p class="sec-p">科幻作家，北师大教授，科幻创意研究中心主任，博士生导师。中国科普作家协会副理事长。</p>
                  </dd>
                </dl>
                <dl>
                  <dt class="fl"><a href="javascript:;"><img src="/img/zj-km.jpg" width="221" height="248" alt="侯克明"></a></dt>
                  <dd>
                      <span style="top:10px">侯克明</span>
                        <p class="sec-p"> 北京电影学院导演系教授、 硕士研究生导师，从事电影导演、表演、管理、技术、理论研究、教育专业。国家广电总局科技委常委、国家电影审查委员委员、第九届中国电影华表奖评委、第十一届中国电影铜牛奖评委、第十六届中国电视金鹰奖评委、北京市"五个一工程"奖评委
                      </p>
                  </dd>
                </dl>
                <dl>
                  <dt class="fl"><a href="javascript:;"><img src="/img/zj-hx.jpg" width="221" height="248" alt="专家"></a></dt>
                  <dd>
                      <span style="top:10px">黄序</span>
                        <p class="sec-p"> 博士, 北京市“海聚工程”青年项目特聘专家，在中美创业生物科技企业并成功退出，并有丰富的生物医药投融资经验。，科幻作家，曾出版科幻小说《智星》、《一瞬》、《墨镜》。2012年11月出版科幻悬疑小说《沉默基因》，2015年出版沉默科幻系列第二部《沉默细胞》。
                      </p>
                  </dd>
                </dl>
                <dl>
                  <dt class="fl"><a href="javascript:;"><img src="/img/zj-ns.jpg" width="221" height="248" alt="祖乃甡"></a></dt>
                  <dd>
                        <span>祖乃甡</span>
                        <p class="sec-p"> 博士, 清华大学美术学院视觉传达设计系副教授、硕士生导师</p>
                  </dd>
                </dl>
                <dl>
                  <dt class="fl"><a href="javascript:;"><img src="/img/zj-jd.jpg" width="221" height="248" alt="叶建东"></a></dt>
                  <dd>
                      <span>叶建东</span>
                      <p class="sec-p">
                                 北京市可持续发展促进会会长，北京科技视频网总编辑，从2013年起持续组织北京科技微视频大赛工作。
                      </p>
                  </dd>
                </dl>
                <dl>
                  <dt class="fl"><a href="javascript:;"><img src="/img/zj-mn.jpg" width="221" height="248" alt="汪耆年"></a></dt>
                  <dd>
                      <span style="bottom:160px">汪耆年</span>
                       <p class="sec-p">
                        著名的航空科普教育专家。原首都师范大学科技教育研究中心任研究员，现任北京市青少年科学技术馆航空室特级教师；北京航空航天学会常务理事兼航空科普教育委员会主任
                      </p>
                  </dd>
                </dl>
             </div>
         </div>   
      </div>
    </div>
  </div>
</div>
<div class="footer">
        <div class="footer-i wt1348 m0">
             <p> 全国青少年优秀原创科幻作品大赛活动组委会 </p> 
             <p> 联系电话：010-62003328，68511864，68598019  </p> 
        </div>
       
  </div>
<script type="text/javascript">
           // 微型小说
           $('.vote-content ul.novel-ul li').click(function(){
                var index=$('.vote-content ul.novel-ul li').index(this);
                var num= $('.vote-content ul.novel-ul li').index(this)
                 if (num%4==0) {$(this).addClass('xx');$('.novel-content').eq(index) .addClass('xx')}
                 if (num%4==1) {$(this).addClass('zx');$('.novel-content').eq(index) .addClass('zx')}
                 if (num%4==2) {$(this).addClass('dx');$('.novel-content').eq(index) .addClass('dx')}
                 if (num%4==3) {$(this).addClass('sh');$('.novel-content').eq(index) .addClass('sh')}
         $('.novel-content').eq(index) .addClass('on').siblings('.novel-content').removeClass('on');    
      }); 
           // 科幻话
           $('.vote-content ul.novel-ul.khh li').click(function(){
                var index=$('.vote-content ul.novel-ul.khh li').index(this);
                var num= $('.vote-content ul.novel-ul.khh li').index(this)
                 if (num%3==0) {$(this).addClass('xx');$('.novel-content1').eq(index) .addClass('xx')}
                 if (num%3==1) {$(this).addClass('zx');$('.novel-content1').eq(index) .addClass('zx')}
                 if (num%3==2) {$(this).addClass('dx');$('.novel-content1').eq(index) .addClass('dx')}
         $('.novel-content1').eq(index) .addClass('on').siblings('.novel-content1').removeClass('on');    
      }); 
             // 科幻话
           $('.vote-content ul.novel-ul.kh li').click(function(){
                var index=$('.vote-content ul.novel-ul.kh li').index(this);
                var num1= $('.vote-content ul.novel-ul.kh li').index(this)
                
                 if (num1%2==0) {$(this).addClass('sh');$('.novel-content2').eq(index) .addClass('sh')}
                 if (num1%2==1) {$(this).addClass('dx');$('.novel-content2').eq(index) .addClass('dx')}
         $('.novel-content2').eq(index) .addClass('on').siblings('.novel-content2').removeClass('on');    
      }); 
        </script>
</body>
</html>