<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8" />
  <title>科幻大赛-科幻画投票</title>
  <meta name="keywords" content="" />
  <meta name="description" content="" />
  <meta name="viewport" content="initial-scale=1,maximum-scale=1,user-scalable=no">
  <link rel="stylesheet" href="/css/vote-moible.css" />
  <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
    <script src="/js/layer/layer.js"></script>
    <script type="text/javascript">
  var itemId="${itemId}";
	var itemType="${itemType}";
	$(document).ready(function(){
		<c:if test="${empty itemId}">
			layer.alert("当前没有可供投票的本类型比赛项目");
			$("#dataDiv").css("display","none");
		</c:if>
		getData(303001,304001,1);
		getData(303002,304001,1);
	});
	function getData(applyGroup,applyYearGroup,currentPage){
		if(($("#"+applyGroup+"-"+applyYearGroup).text()==''&&currentPage==1)||currentPage!=1){
			$.ajax({
				url:"/vote/getVoteData",
				data:{current_page:currentPage,page_size:4,applyGroup:applyGroup,applyYearGroup:applyYearGroup,itemId:itemId,itemType:itemType},
				dataType:"json",
				type:"post",
				success:function(data){
					var rows=data.rows;
					if(rows.length==0){
						$("#"+applyGroup+"-"+applyYearGroup+"a").css("display","none");
						if(currentPage!=1){
							layer.msg("没有更多了");
						}
						return;
					}
					$("#"+applyGroup+"-"+applyYearGroup+"page").val(currentPage+1);
					for(var i=0;i<rows.length;i++){
						var obj=rows[i];
						if(typeof(obj.applyYearGroup)=='undefined'){
							obj.applyYearGroup="";
						}
						if(typeof(obj.applyGroup)=='undefined'){
							obj.applyGroup="";
						}
						var html="<ul><li><img src='${lookdir}"+obj.filePath+"' width='100%'></li>"
							+"<li><span>NO."+obj.vdef1+"</span><span>作者："+obj.realName+"</span>"
							+"<p class='vote-button'><span><img onclick='vote(\""+obj.id+"\",\""+applyGroup+"\",\""+applyYearGroup+"\")' src='/img/vote-button.png' width='80%'>"
							+"</span><span>票数：<i id='"+obj.id+"voteNum'>"+obj.voteNum+"</i></span></p></li></ul>";
						$("#"+applyGroup+"-"+applyYearGroup).append(html);
					}
				}
			});
		}
	}
	
	function loadMore(applyGroup,applyYearGroup){
		getData(applyGroup,applyYearGroup,parseInt($("#"+applyGroup+"-"+applyYearGroup+"page").val()));
	}
	
	function vote(applyId,applyGroup,applyYearGroup){
		$.ajax({
			url:"/vote/vote",
			data:{applyId:applyId,applyGroup:applyGroup,applyYearGroup:applyYearGroup},
			dataType:"json",
			type:"post",
			success:function(data){
				if(data.status=="0"){
					layer.msg("投票成功");
					var voteNum=parseInt($("#"+applyId+"voteNum").text());
					$("#"+applyId+"voteNum").text(voteNum+1);
				}else{
					layer.msg(data.error_desc);
				}
			}
		});
	}
  </script>
</head>
<body>
      <div class="top">
           <img src="/img/vote-banner-draw.jpg" width="100%" height="100%"  alt="科幻大赛-科幻小说投票banner">
      </div>
      <div class="vote-nav">
            <a href="/vote/phoneIndex"> <img src="/img/vote-nav.png" width="100%" height="100%"  alt="投票导航"></a>
      </div>
      <!-- 科幻小说  类别    小学  中学   大学 社会组 -->
      <div class="vote-main novel-main" id="dataDiv">
          <h3>手绘组</h3>
          <div class="novel-tab ">
                <ul class="novel-tile novel-ul wx">
                      <li class="xx" onclick="getData(303001,304001,1)">小学组</li>
                      <li class="zx" onclick="getData(303001,304002,1)">中学组</li>
                      <li class="dx" onclick="getData(303001,304003,1)">大学组</li>
                 </ul>
                 <!-- 小学 -->
                 <div class="content xx on ">
                    <div id="303001-304001"></div>
                 	<input type="hidden" id="303001-304001page"/>
                    <a id="303001-304001a" onclick="loadMore(303001,304001)" href="javascript:;" class="moible-more">更多>></a>
                 </div>
                 <!-- 中学 -->
                 <div class="content  ">
                    <div id="303001-304002"></div>
                 	<input type="hidden" id="303001-304002page"/>
                    <a id="303001-304002a" onclick="loadMore(303001,304002)" href="javascript:;" class="moible-more">更多>></a>
                 </div>
                 <!-- 大学 -->
                 <div class="content  ">
                    <div id="303001-304003"></div>
                 	<input type="hidden" id="303001-304003page"/>
                    <a id="303001-304003a" onclick="loadMore(303001,304003)" href="javascript:;" class="moible-more">更多>></a>
                 </div>
          </div>
          <h3>电脑组</h3>
          <div class="novel-tab ">
                <ul class="novel-tile novel-ul dx">
                      <li class="xx" onclick="getData(303002,304001,1)">小学组</li>
                      <li class="zx" onclick="getData(303002,304002,1)">中学组</li>
                      <li class="dx" onclick="getData(303002,304003,1)">大学组</li>
                 </ul>
                <!-- 小学 -->
                 <div class="content1 xx on ">
                    <div id="303002-304001"></div>
                 	<input type="hidden" id="303002-304001page"/>
                    <a id="303002-304001a" onclick="loadMore(303002,304001)" href="javascript:;" class="moible-more">更多>></a>
                 </div>
                 <!-- 中学 -->
                 <div class="content1  ">
                    <div id="303002-304002"></div>
                 	<input type="hidden" id="303002-304002page"/>
                    <a id="303002-304002a" onclick="loadMore(303002,304002)" href="javascript:;" class="moible-more">更多>></a>
                 </div>
                 <!-- 大学 -->
                 <div class="content1  ">
                    <div id="303002-304003"></div>
                 	<input type="hidden" id="303002-304003page"/>
                    <a id="303002-304003a" onclick="loadMore(303002,304003)" href="javascript:;" class="moible-more">更多>></a>
                 </div>
                 <!-- 回到顶部 -->
               <div class="btn-float-wrapper">
               		<a class="btn-float top" id="scrollTop" style=" z-index: 99;"></a>
              </div>
               
          </div>
      </div>    
    <script type="text/javascript">
           // 微型小说
           $('.novel-tab ul.novel-tile.wx li').click(function(){
                var index=$('.novel-tab ul.novel-tile.wx li').index(this);
                var num= $('.novel-tab ul.novel-tile.wx li').index(this)
                 if (num%3==0) {$(this).addClass('xx');$('.content').eq(index) .addClass('xx')}
                 if (num%3==1) {$(this).addClass('zx');$('.content').eq(index) .addClass('zx')}
                 if (num%3==2) {$(this).addClass('dx');$('.content').eq(index) .addClass('dx')}
                
         $('.content').eq(index) .addClass('on').siblings('.content').removeClass('on');    
      }); 
           // 微型小说
           $('.novel-tab ul.novel-tile.dx li').click(function(){
                var index=$('.novel-tab ul.novel-tile.dx li').index(this);
                var num= $('.novel-tab ul.novel-tile.dx li').index(this)
                 if (num%3==0) {$(this).addClass('xx');$('.content1').eq(index) .addClass('xx')}
                 if (num%3==1) {$(this).addClass('zx');$('.content1').eq(index) .addClass('zx')}
                 if (num%3==2) {$(this).addClass('dx');$('.content1').eq(index) .addClass('dx')}
               
         $('.content1').eq(index) .addClass('on').siblings('.content1').removeClass('on');    
      }); 
            // 滚到顶部
      (function() {
        var bb = 60;
        var b = $("#scrollTop");
        if(b.size() == 0) {
          return;
        }
        if($(window).scrollTop() == bb) {
          b.hide()
        }
        b.css("zIndex", "99").click(function() {
          $("body,html").animate({
            scrollTop: 0
          }, 300);
        });
        $(window).scroll(function() {
          if($(this).scrollTop() > bb) {
            b.fadeIn();
          } else {
            b.fadeOut();
          }
        });
        var tipwidth = $('.tips').css('width')
        $('.tips').css('height', tipwidth)
      })();
           
        </script>
  
  
</body>


</html>