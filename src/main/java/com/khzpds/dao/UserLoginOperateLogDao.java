package com.khzpds.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.khzpds.base.IBaseDao;
import com.khzpds.base.PageParameter;
import com.khzpds.vo.UserLoginOperateLogInfo;
public interface UserLoginOperateLogDao extends IBaseDao<UserLoginOperateLogInfo> {

	
    //--CustomBegin***///
	List<Map<String, String>> findIndexPage(@Param("obj")PageParameter page);
	//--CustomEnd*****///
}

