package com.khzpds.dao;
import java.util.List;

import com.khzpds.base.IBaseDao;
import com.khzpds.base.PageParameter;
import com.khzpds.vo.ActivityInfoInfo;
public interface ActivityInfoDao extends IBaseDao<ActivityInfoInfo> {

	
    //--CustomBegin***///
	List<ActivityInfoInfo> findByParamLikeForPage(PageParameter page);
	//--CustomEnd*****///
}

