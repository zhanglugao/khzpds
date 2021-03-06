package com.khzpds.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.ActivityMarkingSetupDao;
import com.khzpds.vo.ActivityMarkingSetupInfo;
@Service
public class ActivityMarkingSetupService extends IBaseService<ActivityMarkingSetupInfo> {
    private ActivityMarkingSetupDao activityMarkingSetupDao;
    
    @Autowired  
    public void setActivityMarkingSetupRepository(ActivityMarkingSetupDao repository) {  
        setRepository(repository);  
        activityMarkingSetupDao=repository;
    }

	
    
    //--CustomBegin***///
    public List<ActivityMarkingSetupInfo> findByParamSort(
			ActivityMarkingSetupInfo setUpFind) {
		return activityMarkingSetupDao.findByParamSort(setUpFind);
	}  
//--CustomEnd*****///
}

