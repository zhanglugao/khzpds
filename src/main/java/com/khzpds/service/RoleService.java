package com.khzpds.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.base.PageParameter;
import com.khzpds.dao.RoleDao;
import com.khzpds.dao.RoleMenuDao;
import com.khzpds.vo.RoleInfo;
import com.khzpds.vo.RoleMenuInfo;
@Service
public class RoleService extends IBaseService<RoleInfo> {
    private RoleDao roleDao;
    
    @Autowired  
    public void setRoleRepository(RoleDao repository) {  
        setRepository(repository);  
        roleDao=repository;
    }

	  
    
    //--CustomBegin***///
    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private UserLoginOperateLogService userLoginOperateLogService;
    
    public List<RoleInfo> findByIndexPage(PageParameter page) {
		return roleDao.findByIndexPage(page);
	}
	public void addRole(RoleInfo role, List<RoleMenuInfo> roleMenus,
			boolean ifAdd,String userId) {
		if(ifAdd){
			roleDao.insert(role);
			userLoginOperateLogService.addLog(role.getId(), "角色", "添加", userId);
		}else{
			roleDao.update(role,null);
			//删除之前的菜单
			roleMenuDao.deleteByRoleId(role.getId());
			userLoginOperateLogService.addLog(role.getId(), "角色", "修改", userId);
		}
		for(RoleMenuInfo roleMenu:roleMenus){
			roleMenuDao.insert(roleMenu);
		}
		
	}
//--CustomEnd*****///



}

