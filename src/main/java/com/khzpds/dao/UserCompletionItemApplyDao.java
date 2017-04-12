package com.khzpds.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.khzpds.base.IBaseDao;
import com.khzpds.base.PageParameter;
import com.khzpds.vo.UserCompletionItemApplyInfo;


public interface UserCompletionItemApplyDao extends IBaseDao<UserCompletionItemApplyInfo> {
    //--CustomBegin***///
	List<UserCompletionItemApplyInfo> findByParamSort(
			@Param("obj")UserCompletionItemApplyInfo applyfind,@Param("sort") String sort);
	List<Map<String, String>> findBySearchMap(Map<String, String> searchMap);
	List<Map<String, Object>> findReportDataByActivityId(@Param("activityId")String id);
	String findMaxApplyNumber(@Param("activityId")String activityId,@Param("itemId") String competitionItemId, @Param("itemType")String itemType, @Param("applyGroup")String applyGroup);
	List<Map<String, String>> findBySearchMapPage(PageParameter page);
	List<UserCompletionItemApplyInfo> findTopVoteApplyInfo(@Param("size")Integer n);
	List<Map<String, String>> findBySearchMapScorePage(PageParameter page);
	//--CustomEnd*****///
	
}

