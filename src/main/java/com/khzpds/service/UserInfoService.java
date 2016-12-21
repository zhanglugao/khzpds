package com.khzpds.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.base.PageParameter;
import com.khzpds.base.SessionInfo;
import com.khzpds.dao.MenuDao;
import com.khzpds.dao.UserInfoDao;
import com.khzpds.dao.UserRoleDao;
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
    
    public SessionInfo setSession(UserInfoInfo user){
		SessionInfo session=new SessionInfo();
		session.setIfLogin(true);
		session.setMail(user.getMail());
		session.setPassword(user.getPassword());
		session.setUserId(user.getId());
		session.setUserName(user.getUserName());
		session.setRealName(user.getRealName());
		if("admin".equals(user.getUserName())){
			List<MenuInfo> menus=menuDao.findByParam(new MenuInfo(),null);
			session.setMenus(menus);
		}else{
			List<MenuInfo> menus=menuDao.findMenusByUserId(user.getId());
			session.setMenus(menus);
		}
		return session;
	}
    
	public List<UserInfoInfo> findByIndexForPage(PageParameter page) {
		return userInfoDao.findByIndexForPage(page);
	}
	public void updateManagerInfo(UserInfoInfo user,
			List<UserRoleInfo> userRoleList, boolean ifAdd) {
		if(ifAdd){
			userInfoDao.insert(user);
		}else{
			userInfoDao.update(user,null);
			userRoleDao.deleteByUserId(user.getId());
		}
		for(UserRoleInfo userRole:userRoleList){
			userRoleDao.insert(userRole);
		}
	}
//--CustomEnd*****///

}

