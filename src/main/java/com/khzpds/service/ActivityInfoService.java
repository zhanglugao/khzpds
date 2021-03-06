package com.khzpds.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.base.PageParameter;
import com.khzpds.dao.ActivityInfoDao;
import com.khzpds.dao.CompetitionItemDao;
import com.khzpds.vo.ActivityInfoInfo;
import com.khzpds.vo.CompetitionItemInfo;
@Service
public class ActivityInfoService extends IBaseService<ActivityInfoInfo> {
    private ActivityInfoDao activityInfoDao;
    
    @Autowired  
    public void setActivityInfoRepository(ActivityInfoDao repository) {  
        setRepository(repository);  
        activityInfoDao=repository;
    }

    //--CustomBegin***///
    @Autowired
    private CompetitionItemDao competitionItemDao;
    @Autowired
    private UserLoginOperateLogService userLoginOperateLogService;
    //首页查询
	public List<ActivityInfoInfo> findByParamLikeForPage(PageParameter page) {
		return activityInfoDao.findByParamLikeForPage(page);
	}
	//添加活动-关联初始化比赛项目
	public void addActivityAndCompetitionItem(ActivityInfoInfo activity,
			List<CompetitionItemInfo> ciList,String userId) {
		if(activity!=null){
			activityInfoDao.insert(activity);
		}
		if(ciList!=null&&ciList.size()>0){
			for(CompetitionItemInfo ci:ciList){
				competitionItemDao.insert(ci);
			}
		}
		userLoginOperateLogService.addLog(activity.getId(), "活动", "添加", userId);
	}
	
	public void updateActivity(ActivityInfoInfo activity,
			List<CompetitionItemInfo> items,String userId) {
		activityInfoDao.update(activity, null);
		for(CompetitionItemInfo item:items){
			competitionItemDao.update(item, null);
		}
		userLoginOperateLogService.addLog(activity.getId(), "活动", "修改", userId);
	}
	
	public void deleteActivity(String id, List<CompetitionItemInfo> items,String userId) {
		activityInfoDao.deleteById(id, null);
		for(CompetitionItemInfo item:items){
			competitionItemDao.deleteById(item.getId(), null);
		}
		userLoginOperateLogService.addLog(id, "活动", "删除", userId);
	}
	//--CustomEnd*****///


}

