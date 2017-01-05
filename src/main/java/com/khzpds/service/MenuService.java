package com.khzpds.service;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.base.PageParameter;
import com.khzpds.dao.MenuDao;
import com.khzpds.dao.RoleMenuDao;
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
    public List<MenuInfo> findMenusByUserId(String id) {
		return menuDao.findMenusByUserId(id);
	}  
	public List<MenuInfo> findByIndexPage(PageParameter page) {
		return menuDao.findByIndexPage(page);
	}
	public List<Map<String, String>> findMenusByRoleId(String roleId) {
		return menuDao.findMenusByRoleId(roleId);
	}
	public void deleteMenu(String id) {
		menuDao.deleteById(id, null);
		roleMenuDao.deleteByMenuId(id);
	}
//--CustomEnd*****///
}

