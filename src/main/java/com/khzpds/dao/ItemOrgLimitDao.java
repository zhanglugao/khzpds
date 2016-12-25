package com.khzpds.dao;
import org.apache.ibatis.annotations.Param;

import com.khzpds.base.IBaseDao;
import com.khzpds.vo.ItemOrgLimitInfo;
public interface ItemOrgLimitDao extends IBaseDao<ItemOrgLimitInfo> {

	
    //--CustomBegin***///
	void deleteByItemId(@Param("itemId")String itemId);
	//--CustomEnd*****///
}

