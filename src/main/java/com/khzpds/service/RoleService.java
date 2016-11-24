package com.khzpds.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.RoleDao;
import com.khzpds.vo.RoleInfo;
@Service
public class RoleService extends IBaseService<RoleInfo> {
    private RoleDao roleDao;
    
    @Autowired  
    public void setRoleRepository(RoleDao repository) {  
        setRepository(repository);  
        roleDao=repository;
    }  
    
    //--CustomBegin***///
//--CustomEnd*****///
}

