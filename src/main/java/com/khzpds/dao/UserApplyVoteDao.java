package com.khzpds.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.khzpds.base.IBaseDao;
import com.khzpds.vo.UserApplyVoteInfo;
public interface UserApplyVoteDao extends IBaseDao<UserApplyVoteInfo> {

	
    //--CustomBegin***///
	int findByParamCount(@Param("obj")UserApplyVoteInfo voteFind);
	List<UserApplyVoteInfo> findByParamOr(@Param("obj")UserApplyVoteInfo voteFind);
	//--CustomEnd*****///

}

