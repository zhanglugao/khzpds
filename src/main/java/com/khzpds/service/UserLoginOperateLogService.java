package com.khzpds.service;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.base.PageParameter;
import com.khzpds.dao.UserLoginOperateLogDao;
import com.khzpds.util.UUIDUtil;
import com.khzpds.vo.UserLoginOperateLogInfo;
@Service
public class UserLoginOperateLogService extends IBaseService<UserLoginOperateLogInfo> {
    private UserLoginOperateLogDao userLoginOperateLogDao;
    
    @Autowired  
    public void setUserLoginOperateLogRepository(UserLoginOperateLogDao repository) {  
        setRepository(repository);  
        userLoginOperateLogDao=repository;
    }  
    
    //--CustomBegin***///
    public void addLog(String resourceId,String resourceType,String operateType,String userId){
    	UserLoginOperateLogInfo log=new UserLoginOperateLogInfo();
    	log.setId(UUIDUtil.getUUID());
    	log.setOperateTime(new Date());
    	log.setResourceId(resourceId);
    	log.setResourceType(resourceType);
    	log.setType(operateType);
    	log.setUserId(userId);
    	userLoginOperateLogDao.insert(log);
    }
    
    public List<Map<String, String>> findIndexPage(PageParameter page) {
		return userLoginOperateLogDao.findIndexPage(page);
	}
//--CustomEnd*****///

}

