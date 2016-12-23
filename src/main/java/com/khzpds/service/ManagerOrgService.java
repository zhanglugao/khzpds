package com.khzpds.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.ManagerOrgDao;
import com.khzpds.vo.ManagerOrgInfo;
@Service
public class ManagerOrgService extends IBaseService<ManagerOrgInfo> {
    private ManagerOrgDao managerOrgDao;
    
    @Autowired  
    public void setManagerOrgRepository(ManagerOrgDao repository) {  
        setRepository(repository);  
        managerOrgDao=repository;
    }  
    
    //--CustomBegin***///
//--CustomEnd*****///
}

