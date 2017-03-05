package com.khzpds.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.UserApplyMarkingResultDao;
import com.khzpds.vo.UserApplyMarkingResultInfo;
@Service
public class UserApplyMarkingResultService extends IBaseService<UserApplyMarkingResultInfo> {
    private UserApplyMarkingResultDao userApplyMarkingResultDao;
    
    @Autowired  
    public void setUserApplyMarkingResultRepository(UserApplyMarkingResultDao repository) {  
        setRepository(repository);  
        userApplyMarkingResultDao=repository;
    }  
    
    //--CustomBegin***///
    //--CustomEnd*****///
}

