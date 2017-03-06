package com.khzpds.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.khzpds.base.IBaseDao;
import com.khzpds.vo.UserApplyMarkingResultInfo;
public interface UserApplyMarkingResultDao extends IBaseDao<UserApplyMarkingResultInfo> {

	
    //--CustomBegin***///
	List<UserApplyMarkingResultInfo> findByParamSort(
			@Param("obj")UserApplyMarkingResultInfo findInfo);
	//--CustomEnd*****///
}

