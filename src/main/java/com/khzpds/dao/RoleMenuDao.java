package com.khzpds.dao;
import org.apache.ibatis.annotations.Param;

import com.khzpds.base.IBaseDao;
import com.khzpds.vo.RoleMenuInfo;
public interface RoleMenuDao extends IBaseDao<RoleMenuInfo> {

	
    //--CustomBegin***///
	void deleteByRoleId(@Param("roleId")String roleId);
	//--CustomEnd*****///
}

