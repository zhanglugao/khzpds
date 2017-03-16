package com.khzpds.service;
import java.util.List;

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
    public int findByParamCount(UserApplyVoteInfo voteFind) {
		return userApplyVoteDao.findByParamCount(voteFind);
	}  
    public List<UserApplyVoteInfo> findByParamOr(UserApplyVoteInfo voteFind) {
		return userApplyVoteDao.findByParamOr(voteFind);
	}
//--CustomEnd*****///



	
}

