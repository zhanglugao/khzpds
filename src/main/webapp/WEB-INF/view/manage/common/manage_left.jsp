<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<aside class="left-side sidebar-offcanvas" style='margin-top:70px;'>
	<section class="sidebar">
		<ul class="sidebar-menu">
			<c:forEach items="${User_session_key.menus }" var="menu">
				<c:if test="${menu.vdef1=='0'}">
					<li>
						<a href="${menu.url }" > 
							<i class="fa fa-align-justify"></i> <span>${menu.name }</span> 
						</a>
					</li>
				</c:if>
				<c:if test="${menu.vdef1=='1'}">
					<li class="treeview">
						<a href="javascript:;"> 
							<i class="fa fa-th"></i>
							<span>${menu.name}</span> <i class="fa fa-angle-left pull-right"></i>
						</a>
						<ul class="treeview-menu">
							<c:forEach items="${menu.childMenus }" var="childMenu">
								<li><a href="${childMenu.url }" ><i
									class="fa fa-angle-double-right"></i>${childMenu.name }</a></li>
							</c:forEach>
						</ul>
					</li>
				</c:if>
			
			</c:forEach>
			
			<!-- <li>
				<a href="/menu/index" > 
					<i class="fa fa-align-justify"></i>  <span>菜单管理</span> 
				</a>
			</li>
			
			<li>
				<a href="/role/index" > 
					<i class="fa fa-align-justify"></i> <span>角色管理</span> 
				</a>
			</li>
			
			<li>
				<a href="/user/manageIndex" > 
					<i class="fa fa-align-justify"></i> <span>管理员管理</span> 
				</a>
			</li>
			
			<li>
				<a href="/activity/index" > 
					<i class="fa fa-align-justify"></i> <span>活动管理</span> 
				</a>
			</li>
			
			分类管理
			<li>
				<a href="/contentCategory/list" > 
				<i class="fa fa-align-justify"></i> <span>组织机构管理</span></a>
			</li> -->
			
			<!-- 系统管理 -->
			<!-- <li class="treeview">
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
			</li> -->
			
		</ul>
	</section>
</aside>
