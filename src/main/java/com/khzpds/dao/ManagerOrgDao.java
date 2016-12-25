package com.khzpds.dao;
import org.apache.ibatis.annotations.Param;

import com.khzpds.base.IBaseDao;
import com.khzpds.vo.ManagerOrgInfo;
public interface ManagerOrgDao extends IBaseDao<ManagerOrgInfo> {

	
    //--CustomBegin***///
	void deleteByUserId(@Param("userId")String id);
	//--CustomEnd*****///
}

