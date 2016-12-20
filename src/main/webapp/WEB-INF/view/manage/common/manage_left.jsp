<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="left-side sidebar-offcanvas" style='margin-top:70px;'>
	<section class="sidebar">
		<ul class="sidebar-menu">
			<li class="active">
				<a href="/user/index" > 
					<i class="fa fa-dashboard"></i> <span>用户管理</span> 
				</a>
			</li>
			
			<li>
				<a href="/menu/index" > 
					<i class="fa fa-th-list"></i> <span>菜单管理</span> 
				</a>
			</li>
			
			<li>
				<a href="/role/index" > 
					<i class="fa fa-th-list"></i> <span>角色管理</span> 
				</a>
			</li>
			
			<li>
				<a href="/activity/index" > 
					<i class="fa fa-th-list"></i> <span>活动管理</span> 
				</a>
			</li>
			
			<!-- 教师管理 -->
			<li>
				<a href="javascript:;" > <i class="fa fa-laptop"></i> <span>教师管理</span></a>
			</li>
			
			<!-- 分类管理 -->
			<li>
				<a href="/contentCategory/list" > <i class="fa fa-align-justify"></i> <span>机构管理</span></a>
			</li>
			
			<!-- 报表统计 -->
			<li class="treeview">
				<a href="javascript:;"> <i class="fa fa-edit"></i>
					<span>报表统计</span> <i class="fa fa-angle-left pull-right"></i>
				</a>
				<ul class="treeview-menu">
					<li><a href="javascript:;"><i
							class="fa fa-angle-double-right"></i>按平台</a></li>
					<li><a href="javascript:;"><i
							class="fa fa-angle-double-right"></i>按供商</a></li>
					<li><a href="javascript:;"><i
							class="fa fa-angle-double-right"></i>CDN</a></li>
				</ul>
			</li>
			
			<!-- 平台授权 -->	
			<li>
				<a href="javascript:;"> <i class="fa fa-table"></i> <span>平台授权</span></a>
			</li>
			
			<!-- 系统管理 -->
			<li class="treeview">
				<a href="javascript:;"> <i class="fa fa-edit"></i>
					<span>系统管理</span> <i class="fa fa-angle-left pull-right"></i>
				</a>
				<ul class="treeview-menu">
					<li><a href="javascript:;" ><i
							class="fa fa-angle-double-right"></i>课程自定义项管理</a></li>
					<li><a href="javascript:;" ><i
							class="fa fa-angle-double-right"></i>列表自定义项管理</a></li>
					<li><a href="javascript:;" ><i
							class="fa fa-angle-double-right"></i>视频来源管理</a></li>
					<li><a href="javascript:;" ><i
							class="fa fa-angle-double-right"></i>自定义标签管理</a></li>
					<li><a href="javascript:;" ><i
							class="fa fa-angle-double-right"></i>自定义转码设置</a></li>
				</ul>
			</li>
			
		</ul>
	</section>
</aside>
