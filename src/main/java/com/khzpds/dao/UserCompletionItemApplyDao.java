package com.khzpds.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.khzpds.base.IBaseDao;
import com.khzpds.vo.UserCompletionItemApplyInfo;
public interface UserCompletionItemApplyDao extends IBaseDao<UserCompletionItemApplyInfo> {

	
    //--CustomBegin***///
	List<UserCompletionItemApplyInfo> findByParamSort(
			@Param("obj")UserCompletionItemApplyInfo applyfind,@Param("sort") String sort);
	//--CustomEnd*****///
}

