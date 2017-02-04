package com.khzpds.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.khzpds.base.BaseController;
import com.khzpds.base.DictionaryConst;
import com.khzpds.base.Docx4jUtil;
import com.khzpds.base.SystemConfig;
import com.khzpds.service.CompetitionItemService;
import com.khzpds.service.ContentCategoryService;
import com.khzpds.service.DictionaryService;
import com.khzpds.service.UserCompletionItemApplyService;
import com.khzpds.util.DateUtil;
import com.khzpds.util.UUIDUtil;
import com.khzpds.vo.CompetitionItemInfo;
import com.khzpds.vo.ContentCategoryInfo;
import com.khzpds.vo.UserCompletionItemApplyInfo;

/***
 * 用户报名
 * @author zhanglugao 
 *
 */
@RequestMapping("/userApply")
@Controller
public class UserApplyCompetitionController extends BaseController{
	@Autowired
	private UserCompletionItemApplyService userCompetitionItemApplyService;
	@Autowired
	private CompetitionItemService competitionItemService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	
	@RequestMapping("/showFile")
	public ModelAndView showFile(String id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		UserCompletionItemApplyInfo apply=userCompetitionItemApplyService.findById(id);
		apply.setFilePath(apply.getFilePath().replace("\\", "/"));
		if(!apply.getFilePath().endsWith(".mp4")){
			return new ModelAndView("redirect:/filePath"+apply.getFilePath());
		}else{
			request.setAttribute("filePath", apply.getFilePath());
			return new ModelAndView(getRootPath(request)+"/manage/play/video_preview");
		}
	}
	
	public byte[] readStream(InputStream inStream) {  
        ByteArrayOutputStream bops = new ByteArrayOutputStream();  
        int data = -1;  
        try {  
            while((data = inStream.read()) != -1){  
                bops.write(data);  
            }  
            return bops.toByteArray();  
        }catch(Exception e){  
            return null;  
        }  
    } 
	
	/***
	 * 撤销报名
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/cancelApply")
	public void cancelApply(String id,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String,Object>();
		UserCompletionItemApplyInfo apply=userCompetitionItemApplyService.findById(id);
		if(apply==null||!DictionaryConst.BI_SAI_BAO_MING_ZHUANG_TAI_YI_BAO_MING.equals(apply.getApplyStatus())){
			result.put("status", "1");
			result.put("error_desc", "只有已报名状态的报名记录可以撤销");
			this.writeJson(response, result);
			return;
		}
		CompetitionItemInfo item=competitionItemService.findById(apply.getCompetitionItemId());
		if(item==null||new Date().after(item.getUserApplyEndtime())){
			result.put("status", "1");
			result.put("error_desc", "报名已经截至，无法撤销");
			this.writeJson(response, result);
			return;
		}
		apply.setApplyStatus(DictionaryConst.BI_SAI_BAO_MING_ZHUANG_TAI_YI_QU_XIAO);
		userCompetitionItemApplyService.update(apply);
		result.put("status", "0");
		this.writeJson(response, result);
	}
	/**
	 * 查询用户报名记录列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getData")
	public void getData(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result=new HashMap<String,Object>();
		UserCompletionItemApplyInfo applyfind=new UserCompletionItemApplyInfo();
		applyfind.setUserId(getCurrentSessionInfo(request).getUserId());
		List<UserCompletionItemApplyInfo> applys=userCompetitionItemApplyService.findByParamSort(applyfind," create_time desc");
		Map<String,String> typeMap=dictionaryService.getDicMapByParentId(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING);
		Map<String,String> statusMap=dictionaryService.getDicMapByParentId(DictionaryConst.BI_SAI_BAO_MING_ZHUANG_TAI);
		for(UserCompletionItemApplyInfo apply:applys){
			apply.setApplyStatus(statusMap.get(apply.getApplyStatus()));
			apply.setArtist(typeMap.get(apply.getCompetitionType()));
		}
		result.put("applyList", applys);
		this.writeJson(response, result);
	}
	
	/***
	 * 跳转报名页  需要根据type判断是否存在正在运行中的活动下的已发布状态的比赛项目
	 * @param type
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toApply")
	public ModelAndView toApply(String type,String id,String flag,HttpServletRequest request,HttpServletResponse response){
		String dest=null;
		//查询已发布状态的活动下的对应type的比赛项目
		UserCompletionItemApplyInfo applyInfo=null;
		CompetitionItemInfo item=null;
		String itemId=null;
		if(StringUtils.isBlank(id)){
			List<CompetitionItemInfo> items=competitionItemService.findPublishedCompetitionItem(type);
			if(items!=null&&items.size()==1){
				item=items.get(0);
				itemId=item.getId();
				//判断用户是否已经报名
				if(StringUtils.isBlank(flag)){
					UserCompletionItemApplyInfo applyFind=new UserCompletionItemApplyInfo();
					applyFind.setUserId(getCurrentSessionInfo(request).getUserId());
					applyFind.setCompetitionItemId(items.get(0).getId());
					List<UserCompletionItemApplyInfo> applys=userCompetitionItemApplyService.findByParam(applyFind);
					if(applys!=null&&applys.size()>0){
						applyInfo=applys.get(0);
					}else{
						request.setAttribute("item", items.get(0));
					}
				}else{
					request.setAttribute("item", items.get(0));
				}
			}
		}else{
			applyInfo=userCompetitionItemApplyService.findById(id);
			type=applyInfo.getCompetitionType();
			itemId=applyInfo.getCompetitionItemId();
		}
		if(applyInfo!=null){
			Date birthday=applyInfo.getBirthday();
			if(StringUtils.isNotBlank(applyInfo.getRecommenedCompany())){
				ContentCategoryInfo category=contentCategoryService.findById(applyInfo.getRecommenedCompany());
				if(category!=null){
					applyInfo.setRecommenedCompanyName(category.getName());
				}
			}
			if(birthday!=null){
				request.setAttribute("birthday", DateUtil.formatDate2String(birthday, "yyyyMM"));
			}
			type=applyInfo.getCompetitionType();
			request.setAttribute("applyInfo", applyInfo);
			//状态=已报名 或者 时间>报名截止时间  不可修改
			if(item==null){
				item=competitionItemService.findById(applyInfo.getCompetitionItemId());
			}
			if(DictionaryConst.BI_SAI_BAO_MING_ZHUANG_TAI_YI_BAO_MING.equals(applyInfo.getApplyStatus())||new Date().after(item.getUserApplyEndtime())){
				request.setAttribute("ifReadonly", true);
			}
		}
		if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_XIAO_SHUO.equals(type)){
			dest="novel-sign";
		}else if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_HUA.equals(type)){
			dest="draw-sign";
		}else if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_WEI_SHI_PIN.equals(type)){
			dest="video-sign";
		}
		request.setAttribute("type", type);
		request.setAttribute("itemId", itemId);
		return new ModelAndView(getRootPath(request)+"/open/competition/"+dest);
	}
	
	/***
	 * 比赛项目报名
	 * @param applyInfo
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/userApplyCompetitionItem")
	public void userApplyCompetitionItem(UserCompletionItemApplyInfo applyInfo,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		Map<String,Object> result=new HashMap<String, Object>();
		String onlySave=request.getParameter("onlySave");
		if(StringUtils.isNotBlank(onlySave)){
			//仅仅保存
			if(StringUtils.isBlank(applyInfo.getId())){
				//添加
				applyInfo.setApplyStatus(DictionaryConst.BI_SAI_BAO_MING_ZHUANG_TAI_XIN_JIAN);
			}
			else{
				//编辑 状态不变
			}
		}else{
			applyInfo.setApplyStatus(DictionaryConst.BI_SAI_BAO_MING_ZHUANG_TAI_YI_BAO_MING);
		}
		applyInfo.setUserName(getCurrentSessionInfo(request).getUserName());
		applyInfo.setUserId(getCurrentSessionInfo(request).getUserId());
		String birthday1=request.getParameter("birthday1");
		Date date=DateUtil.getDate(birthday1, "yyyyMM");
		String filePathHidden=request.getParameter("filePathHidden");
		if(StringUtils.isNotBlank(filePathHidden)){
			String fileNameHidden=request.getParameter("fileNameHidden");
			applyInfo.setFilePath(URLDecoder.decode(filePathHidden, "utf-8").replace(SystemConfig.getUploadDir(), ""));
			applyInfo.setFileName(fileNameHidden);
		}
		
		applyInfo.setBirthday(date);
		
		if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_WEI_SHI_PIN.equals(applyInfo.getCompetitionType())){
			applyInfo.setApplyGroup("");
		}
		
		if(StringUtils.isBlank(applyInfo.getId())){
			applyInfo.setId(UUIDUtil.getUUID());
			applyInfo.setCreateTime(new Date());
			userCompetitionItemApplyService.add(applyInfo);
		}else{
			userCompetitionItemApplyService.update(applyInfo);
		}
		result.put("status", "0");
		result.put("id", applyInfo.getId());
		this.writeJson(response, result);
	}
	private String[] cardArr=new String[]{
		"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r"
	};
	 @RequestMapping("download")    
    public void download(HttpServletRequest request,String type,String applyId,HttpServletResponse response) throws IOException, Docx4JException { 
		UserCompletionItemApplyInfo applyInfo=null;
		if(StringUtils.isNotBlank(applyId)){
			applyInfo=userCompetitionItemApplyService.findById(applyId);
		}
    	File file=null;
    	String name="novel-sign-up.docx";
    	if(applyInfo!=null){
    		if(StringUtils.isNotBlank(applyInfo.getRecommenedCompany())){
    			ContentCategoryInfo category=contentCategoryService.findById(applyInfo.getRecommenedCompany());
				if(category!=null){
					applyInfo.setRecommenedCompanyName(category.getName());
				}else{
					applyInfo.setRecommenedCompanyName("");
				}
    		}
    		if(StringUtils.isNotBlank(applyInfo.getCardType())){
    			applyInfo.setCardType(dictionaryService.findById(applyInfo.getCardType()).getName());
    		}
    		String suffix="novel-template.docx";
    		if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_HUA.equals(type)){
    			suffix="paint-template.docx";
    			name="paint-sign-up.docx";
	    	}else if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_WEI_SHI_PIN.equals(type)){
	    		suffix="video-template.docx";
	    		name="video-sign-up.docx";
	    	}
    		WordprocessingMLPackage word=Docx4jUtil.getTemplate(request.getSession().getServletContext().getRealPath("")+File.separator+"/file/"+suffix);
    		HashMap<String,String> map=new HashMap<String, String>();
    		//一般属性
    		map.put("$productName", applyInfo.getProductionName()==null?"":applyInfo.getProductionName());
    		map.put("$realName", applyInfo.getRealName()==null?"":applyInfo.getRealName());
    		map.put("$sex", applyInfo.getSex()==null?"":applyInfo.getSex());
    		map.put("$birthday", applyInfo.getBirthday()==null?"":DateUtil.date2String(applyInfo.getBirthday(), "yyyyMM"));
    		map.put("$schoolName", applyInfo.getSchoolName()==null?"":applyInfo.getSchoolName());
    		map.put("$recommenedCompanyName", applyInfo.getRecommenedCompany()==null?"":applyInfo.getRecommenedCompanyName());
    		map.put("$cardType", applyInfo.getCardType()==null?"":applyInfo.getCardType());
    		map.put("$telephone", applyInfo.getTelephone()==null?"":applyInfo.getTelephone());
    		map.put("$mobilePhone", applyInfo.getMobilePhone()==null?"":applyInfo.getMobilePhone());
    		map.put("$email", applyInfo.getEmail()==null?"":applyInfo.getEmail());
    		map.put("$postcode", applyInfo.getPostcode()==null?"":applyInfo.getPostcode());
    		map.put("$address", applyInfo.getAddress()==null?"":applyInfo.getAddress());
    		map.put("$ideaDesc", applyInfo.getIdeaDesc()==null?"":applyInfo.getIdeaDesc());
    		
    		//微视频附加属性
    		if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_WEI_SHI_PIN.equals(type)){
    			map.put("$scriptwriter", applyInfo.getScriptwriter()==null?"":applyInfo.getScriptwriter());
    			map.put("$director", applyInfo.getDirector()==null?"":applyInfo.getDirector());
    			map.put("$camerist", applyInfo.getCamerist()==null?"":applyInfo.getCamerist());
    			map.put("$editor", applyInfo.getEditor()==null?"":applyInfo.getEditor());
    			map.put("$musician", applyInfo.getMusician()==null?"":applyInfo.getMusician());
    			map.put("$artist", applyInfo.getArtist()==null?"":applyInfo.getArtist());
    			map.put("$mainStuff", applyInfo.getMainStuff()==null?"":applyInfo.getMainStuff());
    			map.put("$teamDesc", applyInfo.getTeamDesc()==null?"":applyInfo.getTeamDesc());
    		}
    		
    		//证件号码的处理
    		if(StringUtils.isBlank(applyInfo.getCardNumber())){
    			for(int i=0;i<cardArr.length;i++){
    				map.put(cardArr[i], "");
    			}
    		}else{
    			//得到证件号码长度 
    			int cardLength=applyInfo.getCardNumber().length();
    			for(int i=0;i<cardArr.length;i++){
    				if(i<cardLength){
    					map.put(cardArr[i],applyInfo.getCardNumber().substring(i, i+1));
    				}else{
    					map.put(cardArr[i], "");
    				}
    			}
    		}
    		//组别打勾的处理
    		String gouStr="☑";
    		if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_XIAO_SHUO.equals(type)){
    			if(StringUtils.isNotBlank(applyInfo.getApplyGroup())){
    				if(DictionaryConst.KE_HUAN_XIAO_SHUO_CAN_SAI_ZU_WEI_XING_XIAO_SHUO.equals(applyInfo.getApplyGroup())){
    					map.put("□微型", gouStr+"微型");
    				}else if(DictionaryConst.KE_HUAN_XIAO_SHUO_CAN_SAI_ZU_ZHONG_PIAN_XIAO_SHUO.equals(applyInfo.getApplyGroup())){
    					map.put("        □中篇","        "+gouStr+"中篇");
    				}
    			}
    			if(StringUtils.isNotBlank(applyInfo.getApplyYearGroup())){
    				if(DictionaryConst.KE_HUAN_XIAO_SHUO_NIAN_LING_ZU_DA_XUE.equals(applyInfo.getApplyYearGroup())){
    					map.put("□小学    □中学     □大学    □社会人士（35岁以下）", "□小学    □中学     "+gouStr+"大学    □社会人士（35岁以下）");
    				}
    				else if(DictionaryConst.KE_HUAN_XIAO_SHUO_NIAN_LING_ZU_SHE_HUI_REN_SHI.equals(applyInfo.getApplyYearGroup())){
    					map.put("□小学    □中学     □大学    □社会人士（35岁以下）", "□小学    □中学     □大学   "+gouStr+" 社会人士（35岁以下）");
    				}
    				else if(DictionaryConst.KE_HUAN_XIAO_SHUO_NIAN_LING_ZU_XIAO_XUE.equals(applyInfo.getApplyYearGroup())){
    					map.put("□小学    □中学     □大学    □社会人士（35岁以下）", gouStr+"小学    □中学     □大学    □社会人士（35岁以下）");
    				}
    				else if(DictionaryConst.KE_HUAN_XIAO_SHUO_NIAN_LING_ZU_ZHONG_XUE.equals(applyInfo.getApplyYearGroup())){
    					map.put("□小学    □中学     □大学    □社会人士（35岁以下）", "□小学   "+gouStr+"中学    □大学    □社会人士（35岁以下）");
    				}
    			}
    		}
    		else if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_HUA.equals(type)){
    			if(StringUtils.isNotBlank(applyInfo.getApplyGroup())){
    				if(DictionaryConst.KE_HUAN_HUA_CAN_SAI_ZU_SHOU_HUI_ZU.equals(applyInfo.getApplyGroup())){
    					map.put("□", gouStr);
    				}else if(DictionaryConst.KE_HUAN_HUA_CAN_SAI_ZU_DIAN_NAO_HUI_TU_ZU.equals(applyInfo.getApplyGroup())){
    					map.put("        □电脑","        "+gouStr+"电脑");
    				}
    			}
    			if(StringUtils.isNotBlank(applyInfo.getApplyYearGroup())){
    				if(DictionaryConst.KE_HUAN_HUA_NIAN_LING_ZU_DA_XUE.equals(applyInfo.getApplyYearGroup())){
    					map.put("□小学   □中学    □大学", "□小学   □中学    "+gouStr+"大学");
    				}
    				else if(DictionaryConst.KE_HUAN_HUA_NIAN_LING_ZU_XIAO_XUE.equals(applyInfo.getApplyYearGroup())){
    					map.put("□小学   □中学    □大学", gouStr+"小学   □中学    □大学");
    				}
    				else if(DictionaryConst.KE_HUAN_HUA_NIAN_LING_ZU_ZHONG_XUE.equals(applyInfo.getApplyYearGroup())){
    					map.put("□小学   □中学    □大学", "□小学   "+gouStr+"中学    □大学");
    				}
    			}
    		}
    		else if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_WEI_SHI_PIN.equals(type)){
    			if(StringUtils.isNotBlank(applyInfo.getApplyYearGroup())){
    				if(DictionaryConst.KE_HUAN_WEI_SHI_PIN_NIAN_LING_ZU_DA_XUE.equals(applyInfo.getApplyYearGroup())){
    					map.put("□大学段    □社会人士",gouStr+"大学段    □社会人士");
    				}
    				else if(DictionaryConst.KE_HUAN_WEI_SHI_PIN_NIAN_LING_ZU_SHE_HUI_REN_SHI.equals(applyInfo.getApplyYearGroup())){
    					map.put("□大学段    □社会人士"," □大学段    "+gouStr+"社会人士");
    				}
    			}
    		}
    		System.out.println(map);
    		Docx4jUtil.replacePlaceholder(word, map);
    		String uuid=UUIDUtil.getUUID();
    		String path=request.getSession().getServletContext().getRealPath("")+File.separator+"/file/downloadTemp/"+uuid+".docx";
    		Docx4jUtil.writeDocxToStream(word, path);
    		file=new File(path);
    	}else{
	    	if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_HUA.equals(type)){
	    		name="paint-sign-up.docx";
	    	}else if(DictionaryConst.BI_SAI_XIANG_MU_LEI_XING_KE_HUAN_WEI_SHI_PIN.equals(type)){
	    		name="video-sign-up.docx";
	    	}
	    	String path=request.getSession().getServletContext().getRealPath("")+File.separator+"/file/"+name;
	        file=new File(path);  
    	}
    	 //获取网站部署路径(通过ServletContext对象)，用于确定下载文件位置，从而实现下载  
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型  
        response.setContentType("multipart/form-data");  
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)  
        response.setHeader("Content-Disposition", "attachment;fileName="+name);  
        ServletOutputStream out;  
        //通过文件路径获得File对象(假如此路径中有一个download.pdf文件)  
        try {  
            FileInputStream inputStream = new FileInputStream(file);  
  
            //3.通过response获取ServletOutputStream对象(out)  
            out = response.getOutputStream();  
  
            byte[] b = new byte[512];
            int len;
            while ((len = inputStream.read(b)) > 0)
            out.write(b, 0, len);
            inputStream.close();  
            out.close();  
            out.flush();  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    } 
	 
    
    public static void main(String[] args) {
		String number="12121";
		System.out.println(number.substring(0,1));
		System.out.println(number.substring(4,5));
	}
}
