package com.khzpds.service;

import com.khzpds.base.IBaseService;
import com.khzpds.base.PageParameter;
import com.khzpds.base.SystemConfig;
import com.khzpds.dao.UserCompletionItemApplyDao;
import com.khzpds.util.ChangeSuffixUtil;
import com.khzpds.util.Ooo32pdf;
import com.khzpds.util.TestPdf2Swf;
import com.khzpds.vo.UserCompletionItemApplyInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
public class UserCompletionItemApplyService extends IBaseService<UserCompletionItemApplyInfo> {
    private UserCompletionItemApplyDao userCompletionItemApplyDao;
    
    @Autowired  
    public void setUserCompletionItemApplyRepository(UserCompletionItemApplyDao repository) {  
        setRepository(repository);  
        userCompletionItemApplyDao=repository;
    }  
    
    //--CustomBegin***///
    public List<UserCompletionItemApplyInfo> findByParamSort(
			UserCompletionItemApplyInfo applyfind, String sort) {
		return userCompletionItemApplyDao.findByParamSort(applyfind,sort);
	} 
	public List<Map<String, String>> findBySearchMap(
			Map<String, String> searchMap) {
		return userCompletionItemApplyDao.findBySearchMap(searchMap);
	}
	public void updateMuti(List<UserCompletionItemApplyInfo> list) {
		// TODO Auto-generated method stub
		for(UserCompletionItemApplyInfo info:list){
			userCompletionItemApplyDao.update(info, null);
		}
	}
	
	public List<Map<String, Object>> findReportDataByActivityId(
			String id) {
		return userCompletionItemApplyDao.findReportDataByActivityId(id);
	}

	public String findMaxApplyNumber(String activityId, String competitionItemId, String itemType, String applyGroup) {
		return userCompletionItemApplyDao.findMaxApplyNumber(activityId,competitionItemId,itemType,applyGroup);
	}
	public List<Map<String, String>> findBySearchMapPage(
			PageParameter page) {
		return userCompletionItemApplyDao.findBySearchMapPage(page);
	}
	public List<UserCompletionItemApplyInfo> findTopVoteApplyInfo(Integer n) {
		return userCompletionItemApplyDao.findTopVoteApplyInfo(n);
	}

	public List<Map<String, String>> findBySearchMapScorePage(PageParameter page) {
		return userCompletionItemApplyDao.findBySearchMapScorePage(page);
	}

    public void convertDocFile(UserCompletionItemApplyInfo applyInfo) {
    	if(StringUtils.isNotBlank(applyInfo.getFilePath())){
    		if(applyInfo.getFilePath().endsWith("doc")||applyInfo.getFilePath().endsWith("docx")){
				String targetPath= ChangeSuffixUtil.change(SystemConfig.getUploadDir()+applyInfo.getFilePath(),"pdf");
				Ooo32pdf convertPdf=new Ooo32pdf();
				convertPdf.convert2PDF(SystemConfig.getUploadDir()+applyInfo.getFilePath(),targetPath);
				//转换成pdf后 需要再转成swf格式 再在前台去展示使用！
				File pdfFile=new File(targetPath);
				if(pdfFile.exists()){
					String targetPath2=ChangeSuffixUtil.change(SystemConfig.getUploadDir()+applyInfo.getFilePath(),"swf");
					try {
						TestPdf2Swf.convertPDF2SWF(targetPath,  targetPath2);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else if(applyInfo.getFilePath().endsWith("pdf")){
				File pdfFile=new File(SystemConfig.getUploadDir()+applyInfo.getFilePath());
				if(pdfFile.exists()){
					String targetPath2=ChangeSuffixUtil.change(SystemConfig.getUploadDir()+applyInfo.getFilePath(),"swf");
					try {
						TestPdf2Swf.convertPDF2SWF(SystemConfig.getUploadDir()+applyInfo.getFilePath(),  targetPath2);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
    }
//--CustomEnd*****///

	public static void main(String[] args) {
		String filePath="E:\\1.doc";
		System.out.println(filePath.substring(0,filePath.lastIndexOf(".")));
	}
	

}

