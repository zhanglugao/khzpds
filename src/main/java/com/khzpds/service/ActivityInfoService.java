package com.khzpds.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.base.PageParameter;
import com.khzpds.dao.ActivityInfoDao;
import com.khzpds.vo.ActivityInfoInfo;
@Service
public class ActivityInfoService extends IBaseService<ActivityInfoInfo> {
    private ActivityInfoDao activityInfoDao;
    
    @Autowired  
    public void setActivityInfoRepository(ActivityInfoDao repository) {  
        setRepository(repository);  
        activityInfoDao=repository;
    }

    //--CustomBegin***///
	public List<ActivityInfoInfo> findByParamLikeForPage(PageParameter page) {
		return activityInfoDao.findByParamLikeForPage(page);
	}  
	//--CustomEnd*****///
}

