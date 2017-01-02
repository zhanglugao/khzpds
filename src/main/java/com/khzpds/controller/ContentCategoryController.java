package com.khzpds.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.khzpds.base.BaseController;
import com.khzpds.service.ContentCategoryService;
import com.khzpds.service.ManagerOrgService;
import com.khzpds.util.ArrayUtils;
import com.khzpds.vo.ContentCategoryInfo;
import com.khzpds.vo.ContentCategoryTreeGridVo;
import com.khzpds.vo.ManagerOrgInfo;
import com.khzpds.vo.TreeGridJsonVo;

/***
 * 内容分类
 * @author zlg
 *
 */
@Controller
@RequestMapping("/contentCategory")
public class ContentCategoryController extends BaseController{
	private static final Log log = LogFactory.getLog(ContentCategoryController.class);
	@Autowired
	private ContentCategoryService contentCategoryService;
	@Autowired
	private ManagerOrgService managerOrgService;
	
	/**
	 * 获取分类数据 for ztree
	 * @param name 查询名称
	 * @param platformId 平台id
	 * @param rootId 根节点id
	 * @param request
	 * @param response
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("/getDataForZtree")
	public void getDataForZtree(String name,String rootId,String parentId,HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		 List<ContentCategoryInfo> lst=contentCategoryService.getZtreeGridData(null,name,parentId,rootId);
		 List<ContentCategoryTreeGridVo> treelst = new ArrayList<ContentCategoryTreeGridVo>();
		 ContentCategoryInfo l=new ContentCategoryInfo();
			for (ContentCategoryInfo m : lst) {
				ContentCategoryTreeGridVo v = new ContentCategoryTreeGridVo();
				PropertyUtils.copyProperties(v, m);
				v.set_parentId(m.getParentId());
				if(m.getIfLeaf()==null){
					//判断有没有这个节点的子节点 
					 l.setParentId(m.getId());
					 //l.setPlatformId(m.getPlatformId());
					 ContentCategoryInfo la=contentCategoryService.findByParentIdReturnOne(l);
					 if(la==null){
						 //没有子 所以setfalse
						 v.setIsParent("false") ;
					 }else{
						 v.setIsParent("true") ;
					 }
				}else{
					if(!m.getIfLeaf()) v.setIsParent("true") ;
					else v.setIsParent("false") ;
				}
				treelst.add(v);
			}
			writeJson(response, treelst);
	}
	
	@RequestMapping("/getRootId")
	public void getRootId(String platformId,String type,String ifOrgManager,HttpServletRequest request,HttpServletResponse response){
		if(StringUtils.isNotBlank(ifOrgManager)){
			ManagerOrgInfo managerOrg=new ManagerOrgInfo();
			managerOrg.setManagerId(getCurrentSessionInfo(request).getUserId());
			List<ManagerOrgInfo> managerOrgs=managerOrgService.findByParam(managerOrg);
			if(managerOrgs.size()==1){
				ContentCategoryInfo org=contentCategoryService.findById(managerOrgs.get(0).getOrgId());
				this.writeString(response, org.getName());
			}
		}else{
			ContentCategoryInfo info=contentCategoryService.getRootCategory(getCurrentSessionInfo(request).getUserName(),null);
			this.writeString(response, info.getId().toString());
		}
	}
	
	/**
	 * 查询标签列表
	 * @param model
	 * @param request
	 * @return 字符串
	 * @throws Exception 
	 */
	@RequestMapping(value="/list")
	public ModelAndView labelList(HttpServletResponse response,HttpServletRequest request,ContentCategoryInfo categoryInfo,ModelMap model)  {
		return new ModelAndView(getRootPath(request)+"/manage/category/category_list");
	}
	
	/**
	 * 添加页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/addUI")
	public String addUI(HttpServletRequest request,HttpServletResponse response,ModelMap model)  {
		String parentId = request.getParameter("parentId");
		model.addAttribute("parentId", parentId);
		return getRootPath(request) +"/manage/category/category_add";
	}
	
	/**
	 * 
	* @Title: addLabel 
	* @Description: 添加标签
	* @param @param request
	* @param @param response
	* @param @return  参数说明 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value="/add")
	public void addLabel(HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			ContentCategoryInfo ContentCategoryInfo = new ContentCategoryInfo();
			String platformId =null;
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			String parentId = request.getParameter("parentId");
			if(StringUtils.isBlank(parentId)) {
				ContentCategoryInfo.setName(name);
				ContentCategoryInfo.setParentId(0L);
				ContentCategoryInfo.setDescription(description);
				ContentCategoryInfo.setPlatformId(platformId);
				ContentCategoryInfo.setIfLeaf(true);
			} else {
				ContentCategoryInfo.setName(name);
				ContentCategoryInfo.setParentId(Long.parseLong(parentId));
				ContentCategoryInfo.setDescription(description);
				ContentCategoryInfo.setPlatformId(platformId);
				ContentCategoryInfo.setIfLeaf(true);
			}
			ContentCategoryInfo.setCreateUser(getCurrentSessionInfo(request).getUserName());
			//查出parentId下 sort最大的子节点
			Integer sort=contentCategoryService.getMaxSortByParentId(parentId,platformId);
			if(sort==null){
				sort=0;
			}
			ContentCategoryInfo.setSort(sort+1);
			contentCategoryService.addLabelInfo(ContentCategoryInfo,parentId,platformId);
			Map<String,Object> resMap = new HashMap<String,Object>();
			resMap.put("id", ContentCategoryInfo.getId());
			resMap.put("name", ContentCategoryInfo.getName());
			resMap.put("description", ContentCategoryInfo.getDescription());
			resMap.put("_parentId", ContentCategoryInfo.getParentId());
			writeJson(response, resMap);
		} catch(Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 修改页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/initEdit")
	public String initEditLable(HttpServletRequest request,HttpServletResponse response,ModelMap model) {
		String id = request.getParameter("id");
		ContentCategoryInfo ContentCategoryInfo = contentCategoryService.findById(Long.parseLong(id));
		model.addAttribute("ContentCategoryInfo", ContentCategoryInfo);
		return getRootPath(request) + "/manage/category/category_edit";
	}
	
	/**
	 * 修改
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/update")
	public void editLable(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result = "";
		try {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String parentId = request.getParameter("parentId");
			String description = request.getParameter("description");
			ContentCategoryInfo ContentCategoryInfo = contentCategoryService.findById(Long.parseLong(id));
			ContentCategoryInfo.setName(name);
			ContentCategoryInfo.setParentId(Long.parseLong(parentId));
			ContentCategoryInfo.setDescription(description);
			contentCategoryService.update(ContentCategoryInfo);
			result="success";
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result="error";
		}
		writeString(response, result);
	}
	
	/**
	 * 删除
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException {
		String result="";
		String ids = request.getParameter("ids");
		String id [] = ids.split(",");
		if(id.length>0){
			for(int i=0;i<id.length;i++){
				ContentCategoryInfo contentCategory=contentCategoryService.findById(Long.valueOf(id[i]));
				contentCategoryService.deleteLabel(contentCategory);
				result="success";
			}
			
		}
		writeJson(response, result);
	    return null;
	} 
	
	/***
	 * 只适用于分类管理  异步加载 
	 * @param ContentCategoryInfo
	 * @param clickPid
	 * @param parentId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/labelData")
	public String labelData(ContentCategoryInfo ContentCategoryInfo,String clickPid,String parentId,HttpServletRequest request,HttpServletResponse response) {
			try {
				List<ContentCategoryInfo> lst=null;
				List<ContentCategoryInfo> lst1 =null;
				if(!StringUtils.isBlank(clickPid)) {
					ContentCategoryInfo.setParentId(Long.parseLong(parentId));
				} 
				if("".equals(ContentCategoryInfo.getName())||null==ContentCategoryInfo.getName()){
					ContentCategoryInfo.setName(null);
					if(ContentCategoryInfo.getParentId()==null){
						lst1=new ArrayList<ContentCategoryInfo>();
						ContentCategoryInfo info=contentCategoryService.getRootCategory(getCurrentSessionInfo(request).getUserName(), null);
						lst1.add(info);
					}else
					lst1 = contentCategoryService.findByName(ContentCategoryInfo,null);
				} else {
					lst = contentCategoryService.findByName(ContentCategoryInfo,null);
					if(lst.size()>0){
						lst1 = new ArrayList<ContentCategoryInfo>();
						for(int i = 0; i < lst.size(); i++){
							ContentCategoryInfo labell = lst.get(i);
							labell.setParentId(0L);
							lst1.add(labell);
						}
						
					}
				}
		
			List<ContentCategoryTreeGridVo> labelTreeGridVoList = new ArrayList<ContentCategoryTreeGridVo>();
			if(ArrayUtils.isNotNull(lst1)) {
				for(int i=0;i<lst1.size();i++) {
					ContentCategoryInfo label =lst1.get(i);
					ContentCategoryTreeGridVo labelTreeGridVo = new ContentCategoryTreeGridVo();
					PropertyUtils.copyProperties(labelTreeGridVo,label);
					if(label.getIfLeaf()) {
						labelTreeGridVo.setState("open");
					} else {
						labelTreeGridVo.setState("closed");
					}
					labelTreeGridVo.set_parentId(label.getParentId());
					if(labelTreeGridVo.get_parentId() != null && labelTreeGridVo.get_parentId()==0) {
						labelTreeGridVo.set_parentId(null);
					} 
					labelTreeGridVo.setSort(i+1);
					if(i==lst1.size()-1){
						labelTreeGridVo.setSort(-1);
					}
					if(lst1.size()==1){
						labelTreeGridVo.setSort(-2);
					}
					labelTreeGridVoList.add(labelTreeGridVo);
				}
			}
			TreeGridJsonVo<ContentCategoryTreeGridVo> res = new TreeGridJsonVo<ContentCategoryTreeGridVo>();
			res.setTotal(labelTreeGridVoList.size());
			res.setRows(labelTreeGridVoList);
			writeJson(response, res);
		} catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	@RequestMapping(value="/labelData2")
	public String labelData(ContentCategoryInfo ContentCategoryInfo,HttpServletRequest request,HttpServletResponse response) {
			try {
				List<ContentCategoryInfo> lst=null;
				List<ContentCategoryInfo> lst1=null;
				if("".equals(ContentCategoryInfo.getName())||null==ContentCategoryInfo.getName()){
					ContentCategoryInfo.setName(null);
					lst1 = contentCategoryService.findByName(ContentCategoryInfo,null);
				} else {
					lst = contentCategoryService.findByName(ContentCategoryInfo,null);
					if(lst.size()>0){
						lst1 = new ArrayList<ContentCategoryInfo>();
						for(int i = 0; i < lst.size(); i++){
							ContentCategoryInfo labell = lst.get(i);
							boolean flag = true;
							for (int j = (lst.size() - 1); j > i; j--) {
								ContentCategoryInfo ogInfo = lst.get(j);
								if (ogInfo.getPath().split("-")[1].equals(labell.getPath().split("-")[1])) {
									flag = false;
									break;
								}
							}
							if (flag) {
								String likeStr = "-" + labell.getPath().split("-")[1] + "-";
								ContentCategoryInfo label = new ContentCategoryInfo();
								label.setPath(likeStr);
								lst1.addAll(contentCategoryService.findByName(label,null));
							}	
						}
						
					}
				}
		
			List<ContentCategoryTreeGridVo> labelTreeGridVoList = new ArrayList<ContentCategoryTreeGridVo>();
			for(int i=0;i<lst1.size();i++) {
				ContentCategoryInfo label =lst1.get(i);
				ContentCategoryTreeGridVo labelTreeGridVo = new ContentCategoryTreeGridVo();
				PropertyUtils.copyProperties(labelTreeGridVo,label);
				labelTreeGridVo.set_parentId(label.getParentId());
				if(labelTreeGridVo.get_parentId()==0) {
					labelTreeGridVo.set_parentId(null);
				}
				labelTreeGridVo.setSort(i+1);
				labelTreeGridVoList.add(labelTreeGridVo);
			}
			TreeGridJsonVo<ContentCategoryTreeGridVo> res = new TreeGridJsonVo<ContentCategoryTreeGridVo>();
			res.setTotal(labelTreeGridVoList.size());
			res.setRows(labelTreeGridVoList);
			writeJson(response, res);
		} catch(Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	@RequestMapping("/getLabelByName")
	public void getLabelByName(String q,HttpServletRequest request,HttpServletResponse response) throws IOException {
		//autocomplete采用get方式传递 必须手动去乱码
		q=new String(q.getBytes("iso8859-1"),"utf-8");
		List<Map<String, String>> result=null;
        if(q!=null&&!"".equals(q)){
            result=contentCategoryService.selectLabelByName(q,getCurrentSessionInfo(request).getUserName(),null);
        }
        if(result==null ){
            result=new ArrayList<Map<String,String>>();
        }
	    writeJson(response, result);
	}
	
	@RequestMapping("/ifRepeatName")
	public String ifRepeatName(HttpServletRequest request,HttpServletResponse response,String name,String parentId) throws IOException {
		String result = "";
		ContentCategoryInfo ContentCategoryInfo = new ContentCategoryInfo();
		ContentCategoryInfo.setName(name);
		ContentCategoryInfo.setParentId(Long.parseLong(parentId));
		List<ContentCategoryInfo> ContentCategoryInfoList = contentCategoryService.findByParam(ContentCategoryInfo);
		if(ArrayUtils.isNotNull(ContentCategoryInfoList)) {
			result = "exist";
		} else {
			result = "success";
		}
		writeJson(response, result);
		return null;
	}
	
	/**
	 * 标签排序	
	 * @param request
	 * @param ContentCategoryInfo
	 * @return
	 */
	@RequestMapping(value="/updateSort")
	@ResponseBody
	public String updateSort(HttpServletRequest request,ContentCategoryInfo ContentCategoryInfo){
		String result="success";
		long id = ContentCategoryInfo.getId(); 
		List<ContentCategoryInfo> lst=null;
		ContentCategoryInfo label= null;
		ContentCategoryInfo label1= null;
		ContentCategoryInfo.setId(null);
	    lst=contentCategoryService.findByName(ContentCategoryInfo,null);
	    if(lst.size()>0){
	    	for(int i= 0;i<lst.size();i++){
	    		if(id==lst.get(i).getId()){
	    			if(i==0){
	    				
	    			}else{
	    				if(1==ContentCategoryInfo.getSort()){
	    					label=lst.get(i);
	    					int s = label.getSort();
	    					label.setSort(s-1);
	    					contentCategoryService.update(label);
	    					label1=lst.get(i-1);
	    					label1.setSort(label.getSort()+1);
	    					contentCategoryService.update(label);
		    				break;
	    				}else{
	    					label=lst.get(i);
	    					label.setSort(label.getSort()+1);
	    					contentCategoryService.update(label);
	    					label=lst.get(i-1);
	    					label.setSort(label.getSort()-1);
	    					contentCategoryService.update(label);
		    				break;
	    				}
	    			}
	    		}
	    	}
	    }
	    return result;
	}
	/**
	 * 标签排序	
	 * @param request
	 * @param ContentCategoryInfo
	 * @return
	 */
	@RequestMapping(value="/userLabel")
	@ResponseBody
	public String userLabel(HttpServletRequest request){
		List<ContentCategoryInfo>list=contentCategoryService.findAllByCode("user_base");
		for (ContentCategoryInfo ContentCategoryInfo : list) {
			ContentCategoryInfo._parentId=ContentCategoryInfo.getParentId();
		}
		return "{\"rows\":"+JSONArray.toJSONString(list)+",\"total\":"+list.size()+"}";
	}

	
	/***
	 * 交换
	 * @param request
	 * @param response
	 */
	@RequestMapping("/labelChange")
	public void labelChange(String id,String type,HttpServletRequest request,HttpServletResponse response){
		Long lid=Long.parseLong(id);
		ContentCategoryInfo contentCategory=contentCategoryService.findById(lid);
		//得到交换对象
		ContentCategoryInfo changeObj=null;
		if("up".equals(type)){
			//根据sort得到上一个同级节点
			changeObj=contentCategoryService.findLastByParentId(contentCategory.getSort(),contentCategory.getParentId(),null);
		}else if("down".equals(type)){
			//根据sort得到下一个同级节点
			changeObj=contentCategoryService.findNextByParentId(contentCategory.getSort(),contentCategory.getParentId(),null);
		}
		if(changeObj==null){
			this.writeString(response, "noobj");
			return;
		}
		int temp=contentCategory.getSort();
		contentCategory.setSort(changeObj.getSort());
		changeObj.setSort(temp);
		//执行
		contentCategoryService.updateSort(contentCategory,changeObj);
		this.writeString(response, "success");
	}
}
