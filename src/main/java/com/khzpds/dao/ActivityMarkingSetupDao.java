package com.khzpds.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.khzpds.base.IBaseDao;
import com.khzpds.vo.ActivityMarkingSetupInfo;
public interface ActivityMarkingSetupDao extends IBaseDao<ActivityMarkingSetupInfo> {

	
    //--CustomBegin***///
	List<ActivityMarkingSetupInfo> findByParamSort(
			@Param("obj")ActivityMarkingSetupInfo setUpFind);
	//--CustomEnd*****///
}

