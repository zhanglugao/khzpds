package com.khzpds.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.UserInfoDao;
import com.khzpds.vo.UserInfoInfo;
@Service
public class UserInfoService extends IBaseService<UserInfoInfo> {
    private UserInfoDao userInfoDao;
    
    @Autowired  
    public void setUserInfoRepository(UserInfoDao repository) {  
        setRepository(repository);  
        userInfoDao=repository;
    }  
    
    //--CustomBegin***///
//--CustomEnd*****///
}

