package com.khzpds.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.ItemOrgLimitDao;
import com.khzpds.vo.ItemOrgLimitInfo;
@Service
public class ItemOrgLimitService extends IBaseService<ItemOrgLimitInfo> {
    private ItemOrgLimitDao itemOrgLimitDao;
    
    @Autowired  
    public void setItemOrgLimitRepository(ItemOrgLimitDao repository) {  
        setRepository(repository);  
        itemOrgLimitDao=repository;
    }  
    
    //--CustomBegin***///
//--CustomEnd*****///
}

