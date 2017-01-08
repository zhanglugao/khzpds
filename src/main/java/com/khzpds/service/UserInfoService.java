package com.khzpds.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.base.PageParameter;
import com.khzpds.base.SessionInfo;
import com.khzpds.dao.ManagerOrgDao;
import com.khzpds.dao.MenuDao;
import com.khzpds.dao.UserInfoDao;
import com.khzpds.dao.UserRoleDao;
import com.khzpds.vo.ManagerOrgInfo;
import com.khzpds.vo.MenuInfo;
import com.khzpds.vo.UserInfoInfo;
import com.khzpds.vo.UserRoleInfo;
@Service
public class UserInfoService extends IBaseService<UserInfoInfo> {
    private UserInfoDao userInfoDao;
    
    @Autowired  
    public void setUserInfoRepository(UserInfoDao repository) {  
        setRepository(repository);  
        userInfoDao=repository;
    }  
    
    //--CustomBegin***///
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private ManagerOrgDao managerOrgDao;
    
    @Autowired
    private UserLoginOperateLogService userLoginOperateLogService;
    
    public SessionInfo setSession(UserInfoInfo user){
		SessionInfo session=new SessionInfo();
		session.setIfLogin(true);
		session.setMail(user.getMail());
		session.setPassword(user.getPassword());
		session.setUserId(user.getId());
		session.setUserName(user.getUserName());
		session.setRealName(user.getRealName());
		List<MenuInfo> menus=null;
		if("admin".equals(user.getUserName())){
			MenuInfo menuFind=new MenuInfo();
			menuFind.setLevel(1);
			menus=menuDao.findByParamSort(menuFind);
		}else{
			menus=menuDao.findMenusByUserId(user.getId());
		}
		for(MenuInfo menu:menus){
			if("1".equals(menu.getVdef1())){
				//查找子级菜单
				MenuInfo menuFind=new MenuInfo();
				menuFind.setParentId(menu.getId());
				List<MenuInfo> childMenus=menuDao.findByParamSort(menuFind);
				menu.setChildMenus(childMenus);
			}
		}
		session.setMenus(menus);
		return session;
	}
    
	public List<UserInfoInfo> findByIndexForPage(PageParameter page) {
		return userInfoDao.findByIndexForPage(page);
	}
	public void updateManagerInfo(UserInfoInfo user,
			List<UserRoleInfo> userRoleList, List<ManagerOrgInfo> managerOrgList, boolean ifAdd,String userId) {
		if(ifAdd){
			userInfoDao.insert(user);
			userLoginOperateLogService.addLog(user.getId(), "管理员", "添加", userId);
		}else{
			userInfoDao.update(user,null);
			userRoleDao.deleteByUserId(user.getId());
			managerOrgDao.deleteByUserId(user.getId());
			userLoginOperateLogService.addLog(user.getId(), "管理员", "修改", userId);
		}
		for(UserRoleInfo userRole:userRoleList){
			userRoleDao.insert(userRole);
		}
		for(ManagerOrgInfo managerOrg:managerOrgList){
			managerOrgDao.insert(managerOrg);
		}
	}
//--CustomEnd*****///

}

