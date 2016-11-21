package com.khzpds.dao;
import org.apache.ibatis.annotations.Param;

import com.khzpds.base.IBaseDao;
import com.khzpds.vo.UserInfoInfo;




public interface UserInfoDao extends IBaseDao<UserInfoInfo> {

	
    //--CustomBegin***///
	UserInfoInfo findByUserName(@Param("userName")String userName);
	//--CustomEnd*****///
}

