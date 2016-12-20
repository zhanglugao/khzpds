package com.khzpds.dao;
import java.util.List;

import com.khzpds.base.IBaseDao;
import com.khzpds.base.PageParameter;
import com.khzpds.vo.RoleInfo;
public interface RoleDao extends IBaseDao<RoleInfo> {

	
    //--CustomBegin***///
	List<RoleInfo> findByIndexPage(PageParameter page);
	//--CustomEnd*****///
}

