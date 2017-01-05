package com.khzpds.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.khzpds.base.IBaseDao;
import com.khzpds.base.PageParameter;
import com.khzpds.vo.MenuInfo;

public interface MenuDao extends IBaseDao<MenuInfo> {
    //--CustomBegin***///
	List<MenuInfo> findMenusByUserId(@Param("userId")String id);
	List<MenuInfo> findByIndexPage(PageParameter page);
	List<Map<String, String>> findMenusByRoleId(@Param("roleId")String roleId);
	List<MenuInfo> findByParamSort(@Param("obj") MenuInfo menuInfo);
	//--CustomEnd*****///
}

