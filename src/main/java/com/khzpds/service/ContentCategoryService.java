package com.khzpds.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.ContentCategoryDao;
import com.khzpds.util.ArrayUtils;
import com.khzpds.vo.ContentCategoryInfo;

@Service
public class ContentCategoryService extends IBaseService<ContentCategoryInfo> {
    private ContentCategoryDao contentCategoryDao;
    
    @Autowired  
    public void setContentCategoryRepository(ContentCategoryDao repository) {  
        setRepository(repository);  
        contentCategoryDao=repository;
    }  
    
    //--CustomBegin***///
    /***
     * 返回分类的完整路径 1级-2级-3级,1级-2级-3级这种格式
     * @param classIds
     * @param platformId
     * @return
     */
	public Map<String,String> getCategoryNamesByIds(String classIds,String platformId,String splitCode) {
		Map<String,String> map=new HashMap<String, String>();
		classIds=classIds.substring(0,classIds.length()-1);
		classIds=classIds.substring(1,classIds.length());
		String[] ids=classIds.split(",");
		StringBuffer sb=new StringBuffer();
		StringBuffer idBuffer=new StringBuffer();
		for(String id:ids){
			//得到除根节点外所有父节点
			ContentCategoryInfo category=this.findById(id);
			if(category==null){
				continue;
			}
			String path=category.getPath();
			String[] paths=path.split("-");
			StringBuffer sb2=new StringBuffer();
			StringBuffer sb3=new StringBuffer();
			for(String str:paths){
				if(StringUtils.isBlank(str)){
					continue;
				}
				ContentCategoryInfo category2=this.findById(str);
				if(category2!=null&&category2.getParentId()-0!=0){
					if(!"".equals(sb2.toString())){
						sb2.append(splitCode+category2.getName());
						sb3.append(splitCode+category2.getId());
					}else{
						sb2.append(category2.getName());
						sb3.append(category2.getId());
					}
				}
			}
			if(!"".equals(sb.toString())){
				sb.append(","+sb2.toString());
				idBuffer.append(","+sb3.toString());
			}else{
				sb.append(sb2.toString());
				idBuffer.append(sb3.toString());
			}
		}
		map.put("ids", idBuffer.toString());
		map.put("names", sb.toString());
		return map;
	}
	/***
	 * 根据id数组平台id得到obj集合
	 */
	public List<ContentCategoryInfo> findbyIds(String[] classIds,
			String platformId) {
		return contentCategoryDao.findbyIds(classIds,platformId);
	}
	
	/**
	 * 获得数据for ztree
	 */
	public List<ContentCategoryInfo> getZtreeGridData(String platformId,
			String name, String parentId, String rootId) {
		//pid优先级最高
        String id =parentId;
        
        //如果pid 为空
        //根据要查询的name先从数据库查出id 
        if(!StringUtils.isBlank(name) && !StringUtils.isBlank(rootId) && StringUtils.isBlank(id)){
            id=this.selectIdByName(name,platformId,rootId);
            if(StringUtils.isBlank(id)) id="-1";
        } 
        
        //如果前面的pid name 都为空,再判断rootId是否为空
        if(StringUtils.isBlank(id) && !StringUtils.isBlank(rootId)){
            id=rootId;
        }
        
        List<ContentCategoryInfo> lst1=new ArrayList<ContentCategoryInfo>();
        ContentCategoryInfo searchResult=this.findById(id);
        if(searchResult!=null){
            //获取id节点的孩子节点
        	ContentCategoryInfo findInfo=new ContentCategoryInfo();
        	findInfo.setParentId(searchResult.getId());
        	findInfo.setPlatformId(platformId);
        	lst1=contentCategoryDao.findByParamSort(findInfo, platformId);
            //如果查出的不是根节点信息修改增加一个父节点
            if(searchResult.getId()!=null&&rootId!=null&&parentId!=null&&rootId.equals(parentId)){
                searchResult.setParentId(null);
                lst1.add(searchResult);
            }else{
            	lst1.add(searchResult);
            }
        }
        return lst1;
	}
	
	public String selectIdByName(String name, String platformId,
			String rootId) {
		Map<String,String> findInfo=new HashMap<String, String>();
		findInfo.put("name", name);
		if(!StringUtils.isBlank(rootId)&&!rootId.equals("0")) {
			findInfo.put("rootId", rootId);
		}
		findInfo.put("platformId", platformId);
		return contentCategoryDao.selectIdByName(findInfo);
	}

	public ContentCategoryInfo findByParentIdReturnOne(ContentCategoryInfo l) {
		return contentCategoryDao.findByParentIdReturnOne(l);
	}

	public Integer addLabelInfo(ContentCategoryInfo labelInfo, String parentId, String platformId) {
		Integer result = 0;
		result = add(labelInfo);
		long id = labelInfo.getId();
		if(StringUtils.isBlank(parentId)) {
			ContentCategoryInfo label = findById(id);
			label.setPath("-" + id + "-");
			update(label);
		} else {
			ContentCategoryInfo info = findById(Long.parseLong(parentId));
			ContentCategoryInfo laInfo = findById(id);
			laInfo.setPath(info.getPath()+id+"-");
			info.setIfLeaf(false);
			update(laInfo);
			update(info);
		}
		return result;
	}

	public List<ContentCategoryInfo> findByName(ContentCategoryInfo labelInfo, String platformId) {
		return contentCategoryDao.findByName(labelInfo,platformId);
	}

	public List<Map<String, String>> selectLabelByName(String name,String userName, String platformId){
	    Map<String,String> findInfo=new HashMap<String, String>();
	    findInfo.put("name", name);
	    findInfo.put("platformId",platformId);
	    findInfo.put("userName", userName);
	    List<Map<String, String>> result= contentCategoryDao.selectLabelByName(findInfo);
	    return result;
	}

	public List<ContentCategoryInfo> findAllByCode(String code) {
		return contentCategoryDao.findAllByCode(code);
	}

	/**
	 * 删除标签
	 * @param platformId 
	 * 
	 */
	public void deleteLabel(ContentCategoryInfo labelInfo) {
		if(labelInfo==null)return;
		boolean ifLeaf = labelInfo.getIfLeaf();
		if(ifLeaf) {
			delete(labelInfo.getId());
			ContentCategoryInfo info = findById(labelInfo.getParentId());
			ContentCategoryInfo childLabel = new ContentCategoryInfo();
			childLabel.setParentId(labelInfo.getParentId());
			List<ContentCategoryInfo> childLabelList = findByName(childLabel,labelInfo.getPlatformId());
			if(ArrayUtils.isNull(childLabelList)) {
				info.setIfLeaf(true);
				update(info);
			}
		} else {
			ContentCategoryInfo label = new ContentCategoryInfo();
			label.setPath(labelInfo.getPath());
			List<ContentCategoryInfo> allLabelInfoList = findByName(label,labelInfo.getPlatformId());
			for (ContentCategoryInfo laInfo : allLabelInfoList) {
				delete(laInfo.getId());
			}
		}
	}

	public List<ContentCategoryInfo> findByParamSort(
			ContentCategoryInfo categoryFind, String platformId) {
		return contentCategoryDao.findByParamSort(categoryFind,platformId);
	}

	public Integer getMaxSortByParentId(String parentId, String platformId) {
		return contentCategoryDao.getMaxSortByParentId(parentId,platformId);
	}

	public ContentCategoryInfo findLastByParentId(Integer sort, Long parentId,
			String platformId) {
		return contentCategoryDao.findLastByParentId(sort,parentId,platformId);
	}

	public ContentCategoryInfo findNextByParentId(Integer sort, Long parentId,
			String platformId) {
		return contentCategoryDao.findNextByParentId(sort,parentId,platformId);
	}

	public void updateSort(ContentCategoryInfo contentCategory,
			ContentCategoryInfo changeObj) {
		contentCategoryDao.update(contentCategory, contentCategory.getPlatformId());
		contentCategoryDao.update(changeObj, changeObj.getPlatformId());
	}

	public List<ContentCategoryInfo> findByParentIdPath(String category_id) {
		// TODO Audto-generated method stub
		return contentCategoryDao.findByParentIdPath(category_id);
	}

	public ContentCategoryInfo getRootCategory(String userName,
			String platformId) {
		ContentCategoryInfo findInfo=new ContentCategoryInfo();
		findInfo.setParentId(0l);
		findInfo.setPlatformId(platformId);
		List<ContentCategoryInfo> infos=this.findByParam(findInfo);
		if(infos.size()==0){
			ContentCategoryInfo info=new ContentCategoryInfo();
			info.setCreateUser(userName);
			info.setIfLeaf(true);
			info.setName("机构");
			info.setDescription("机构根节点");
			info.setParentId(0l);
			info.setPlatformId(platformId);
			this.add(info);
			infos=this.findByParam(findInfo);
			info.setPath("-"+infos.get(0).getId()+"-");
			this.update(info);
		}
		return infos.get(0);
	}
//--CustomEnd*****///
}

