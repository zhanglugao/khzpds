package com.khzpds.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.UserCompletionItemApplyDao;
import com.khzpds.vo.UserCompletionItemApplyInfo;
@Service
public class UserCompletionItemApplyService extends IBaseService<UserCompletionItemApplyInfo> {
    private UserCompletionItemApplyDao userCompletionItemApplyDao;
    
    @Autowired  
    public void setUserCompletionItemApplyRepository(UserCompletionItemApplyDao repository) {  
        setRepository(repository);  
        userCompletionItemApplyDao=repository;
    }  
    
    //--CustomBegin***///
//--CustomEnd*****///
}

