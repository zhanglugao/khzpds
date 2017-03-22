<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>科幻大赛-投票页</title>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta name="viewport" content="initial-scale=1,maximum-scale=1,user-scalable=no">
	<link rel="stylesheet" href="/css/vote-moible.css" />
</head>

<body>
      <div class="top">
           <img src="/phoneimg/vote-banner.png" width="100%" height="100%">
      </div>
      <div class="vote-nav">
            <a href="javascript:;"> <img src="/phoneimg/vote-nav.png" width="100%" height="100%"  alt="投票导航"></a>
      </div>
      <div class="vote-main">
          <div class="vote-novel"> 
                <a href="/vote/votePage?itemType=301001&phone=1" > <img src="/phoneimg/vote-novel.png"   alt="科幻小说-投票"></a>
          </div>
          <div class="vote-draw"> 
                <a href="/vote/votePage?itemType=301002&phone=1" > <img src="/phoneimg/vote-draw.png"   alt="科幻画-投票"></a>
          </div>
          <div class="vote-video"> 
                <a href="/vote/votePage?itemType=301003&phone=1" > <img src="/phoneimg/vote-video.png"  alt="科幻视频-投票"></a>
          </div>
        
      </div>
		
</body>


</html>