package com.khzpds.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.UserApplyVoteDao;
import com.khzpds.dao.UserCompletionItemApplyDao;
import com.khzpds.vo.UserApplyVoteInfo;
import com.khzpds.vo.UserCompletionItemApplyInfo;
@Service
public class UserApplyVoteService extends IBaseService<UserApplyVoteInfo> {
    private UserApplyVoteDao userApplyVoteDao;
    
    @Autowired  
    public void setUserApplyVoteRepository(UserApplyVoteDao repository) {  
        setRepository(repository);  
        userApplyVoteDao=repository;
    }

	
    
    //--CustomBegin***///
    @Autowired
    private UserCompletionItemApplyDao applyDao;
    public int findByParamCount(UserApplyVoteInfo voteFind) {
		return userApplyVoteDao.findByParamCount(voteFind);
	}  
    public List<UserApplyVoteInfo> findByParamOr(UserApplyVoteInfo voteFind) {
		return userApplyVoteDao.findByParamOr(voteFind);
	}
    public void addVoteInfo(UserApplyVoteInfo voteInfo,
			UserCompletionItemApplyInfo applyInfo) {
    	userApplyVoteDao.insert(voteInfo);
    	applyDao.update(applyInfo, null);
    	
	}
//--CustomEnd*****///



	
}

