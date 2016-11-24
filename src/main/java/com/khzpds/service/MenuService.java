package com.khzpds.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.MenuDao;
import com.khzpds.vo.MenuInfo;
@Service
public class MenuService extends IBaseService<MenuInfo> {
    private MenuDao menuDao;
    
    @Autowired  
    public void setMenuRepository(MenuDao repository) {  
        setRepository(repository);  
        menuDao=repository;
    }

    
    //--CustomBegin***///
    public List<MenuInfo> findMenusByUserId(String id) {
		return menuDao.findMenusByUserId(id);
	}  
    //--CustomEnd*****///
}

