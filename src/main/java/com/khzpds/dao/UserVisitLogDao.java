package com.khzpds.dao;
import java.util.List;
import java.util.Map;

import com.khzpds.base.IBaseDao;
import com.khzpds.vo.UserVisitLogInfo;
public interface UserVisitLogDao extends IBaseDao<UserVisitLogInfo> {

	
    //--CustomBegin***///
	List<UserVisitLogInfo> findBySearchMap(Map<String, String> searchMap);
	int findVisitNumBySearchMap(Map<String, String> searchMap);
	List<Map<String, String>> findRefNumBySearchMap(
			Map<String, String> searchMap);
	//--CustomEnd*****///
	

}

