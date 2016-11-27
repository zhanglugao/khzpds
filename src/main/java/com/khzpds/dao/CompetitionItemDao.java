package com.khzpds.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.khzpds.base.IBaseDao;
import com.khzpds.vo.CompetitionItemInfo;
public interface CompetitionItemDao extends IBaseDao<CompetitionItemInfo> {

	
    //--CustomBegin***///
	List<CompetitionItemInfo> findPublishedCompetitionItem(@Param("type")String type);
	//--CustomEnd*****///
}

