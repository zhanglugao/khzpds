package com.khzpds.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.khzpds.base.IBaseDao;
import com.khzpds.vo.MenuInfo;
public interface MenuDao extends IBaseDao<MenuInfo> {

	
    //--CustomBegin***///
	List<MenuInfo> findMenusByUserId(@Param("userId")String id);
	//--CustomEnd*****///
}

