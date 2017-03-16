package com.khzpds.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.UserApplyVoteDao;
import com.khzpds.vo.UserApplyVoteInfo;
@Service
public class UserApplyVoteService extends IBaseService<UserApplyVoteInfo> {
    private UserApplyVoteDao userApplyVoteDao;
    
    @Autowired  
    public void setUserApplyVoteRepository(UserApplyVoteDao repository) {  
        setRepository(repository);  
        userApplyVoteDao=repository;
    }  
    
    //--CustomBegin***///
//--CustomEnd*****///
}

