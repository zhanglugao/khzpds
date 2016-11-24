<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body class='body-blue'>
	<!-- 头部 -->
	<header class="header">
		<a href="javascript:;" class="logo">科幻作品大赛管理后台</a>
		<nav class="navbar navbar-static-top" role="navigation">
			<!-- Sidebar toggle button-->
			<a href="javascript:;" class="navbar-btn sidebar-toggle" data-toggle="offcanvas" role="button"> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
			</a> 
			<a href="javascript:;" class="shouye-style-test"> <span><i
					class="glyphicon glyphicon-home"></i></span>
			</a>
			<div class="navbar-right">
				<ul class="nav navbar-nav">
					<li class="dropdown user user-menu">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
							<i class="glyphicon glyphicon-user"></i> 
							<span>${User_session_key.userName}<i class="caret"></i></span>
						</a>
						<ul class="dropdown-menu">
							<li class="user-header bg-light-blue">
								<img src="/images/find3-ac.png" class="img-circle" alt="User Image" />
								<p>${User_session_key.userName}</p>
								<p><a href="/loginSys/exit"> 退出登录 <i class="glyphicon glyphicon-off"></i></a></p>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</nav>
	</header>
</body>
</html>