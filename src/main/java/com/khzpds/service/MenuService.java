package com.khzpds.service;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.base.PageParameter;
import com.khzpds.dao.MenuDao;
import com.khzpds.dao.RoleMenuDao;
import com.khzpds.util.UUIDUtil;
import com.khzpds.vo.MenuInfo;

@Service
public class MenuService extends IBaseService<MenuInfo> {
    private MenuDao menuDao;
    
    @Autowired  
    public void setMenuRepository(MenuDao repository) {  
        setRepository(repository);  
        menuDao=repository;
    }  
    
    //--CustomBegin***///
    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private UserLoginOperateLogService userLoginOperateLogService;
    public List<MenuInfo> findMenusByUserId(String id) {
		return menuDao.findMenusByUserId(id);
	}  
	public List<MenuInfo> findByIndexPage(PageParameter page) {
		return menuDao.findByIndexPage(page);
	}
	public List<Map<String, String>> findMenusByRoleId(String roleId) {
		return menuDao.findMenusByRoleId(roleId);
	}
	public void deleteMenu(MenuInfo menu,String userId) {
		//
		MenuInfo findInfo=new MenuInfo();
		findInfo.setParentId(menu.getParentId());
		List<MenuInfo> menus=this.findByParam(findInfo);
		if(menus.size()<=1){
			MenuInfo parent=this.findById(menu.getParentId());
			parent.setVdef1("0");
			this.update(parent);
		}
		
		menuDao.deleteById(menu.getId(), null);
		roleMenuDao.deleteByMenuId(menu.getId());
		userLoginOperateLogService.addLog(menu.getId(), "菜单", "删除", userId);
	}
	
	public List<MenuInfo> findByParamSort(MenuInfo menu) {
		return menuDao.findByParamSort(menu);
	}
	
	public void addOrUpdateMenu(MenuInfo menu,String userId) {
		if(menu.getLevel()==2){
			//查找出父级菜单 如果
			MenuInfo parent=this.findById(menu.getParentId());
			if(!"1".equals(parent.getVdef1())){
				parent.setVdef1("1");
				update(parent);
			}
		}else if(menu.getLevel()==1){
			menu.setParentId("");
		}
		if(StringUtils.isBlank(menu.getId())){
			menu.setId(UUIDUtil.getUUID());
			menu.setSort(20);
			menu.setVdef1("0");
			this.add(menu);
			userLoginOperateLogService.addLog(menu.getId(), "菜单", "添加", userId);
		}else{
			//
			MenuInfo oldMenu=this.findById(menu.getId());
			if(menu.getLevel()==2){
				if(!menu.getParentId().equals(oldMenu.getParentId())){
					//判断oldMenu的parent是否还有子节点 如果没有  则变回一级
					MenuInfo findInfo=new MenuInfo();
					findInfo.setParentId(oldMenu.getParentId());
					List<MenuInfo> menus=this.findByParam(findInfo);
					if(menus.size()<=1){
						MenuInfo parent=this.findById(oldMenu.getParentId());
						parent.setVdef1("0");
						this.update(parent);
					}
				}
			}
			this.update(menu);
			userLoginOperateLogService.addLog(menu.getId(), "菜单", "修改", userId);
		}
		
	}
//--CustomEnd*****///
}

