package com.khzpds.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.RoleMenuDao;
import com.khzpds.vo.RoleMenuInfo;
@Service
public class RoleMenuService extends IBaseService<RoleMenuInfo> {
    private RoleMenuDao roleMenuDao;
    
    @Autowired  
    public void setRoleMenuRepository(RoleMenuDao repository) {  
        setRepository(repository);  
        roleMenuDao=repository;
    }  
    
    //--CustomBegin***///
//--CustomEnd*****///
}

