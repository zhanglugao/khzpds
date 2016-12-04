package com.khzpds.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.khzpds.base.IBaseDao;
import com.khzpds.vo.ContentCategoryInfo;

public interface ContentCategoryDao extends IBaseDao<ContentCategoryInfo> {
    //--CustomBegin***///
	List<ContentCategoryInfo> findbyIds(@Param("idArray")String[] classIds, @Param("platformId")String platformId);
	String selectIdByName(Map<String, String> findInfo);
	ContentCategoryInfo findByParentIdReturnOne(ContentCategoryInfo l);
	List<ContentCategoryInfo> findByName(@Param("obj")ContentCategoryInfo labelInfo,@Param("platformId") String platformId);
	List<Map<String, String>> selectLabelByName(Map<String, String> findInfo);
	List<ContentCategoryInfo> findAllByCode(@Param("code")String code);
	List<ContentCategoryInfo> findByParentIdPath(@Param("parentId")String parentId);
	List<ContentCategoryInfo> findByParamSort(@Param("obj")ContentCategoryInfo categoryFind,@Param("platformId")String platformId);
	Integer getMaxSortByParentId(@Param("parentId")String parentId, @Param("platformId")String platformId);
	ContentCategoryInfo findLastByParentId(@Param("sort")Integer sort,@Param("parentId") Long parentId,@Param("platformId") String platformId);
	ContentCategoryInfo findNextByParentId(@Param("sort")Integer sort,@Param("parentId") Long parentId,@Param("platformId") String platformId);
	//--CustomEnd*****///
}

