package com.khzpds.dao;
import org.apache.ibatis.annotations.Param;

import com.khzpds.base.IBaseDao;
import com.khzpds.vo.UserRoleInfo;
public interface UserRoleDao extends IBaseDao<UserRoleInfo> {

	
    //--CustomBegin***///
	void deleteByUserId(@Param("userId")String userId);
	//--CustomEnd*****///
}

