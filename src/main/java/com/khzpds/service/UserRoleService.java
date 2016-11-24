package com.khzpds.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.UserRoleDao;
import com.khzpds.vo.UserRoleInfo;
@Service
public class UserRoleService extends IBaseService<UserRoleInfo> {
    private UserRoleDao userRoleDao;
    
    @Autowired  
    public void setUserRoleRepository(UserRoleDao repository) {  
        setRepository(repository);  
        userRoleDao=repository;
    }  
    
    //--CustomBegin***///
//--CustomEnd*****///
}

