package com.khzpds.dao;
import java.util.List;

import com.khzpds.base.IBaseDao;
import com.khzpds.base.PageParameter;
import com.khzpds.vo.UserInfoInfo;
public interface UserInfoDao extends IBaseDao<UserInfoInfo> {

	
    //--CustomBegin***///
	List<UserInfoInfo> findByIndexForPage(PageParameter page);
	//--CustomEnd*****///
}

