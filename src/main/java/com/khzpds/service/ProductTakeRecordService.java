package com.khzpds.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.ProductTakeRecordDao;
import com.khzpds.vo.ProductTakeRecordInfo;
@Service
public class ProductTakeRecordService extends IBaseService<ProductTakeRecordInfo> {
    private ProductTakeRecordDao productTakeRecordDao;
    
    @Autowired  
    public void setProductTakeRecordRepository(ProductTakeRecordDao repository) {  
        setRepository(repository);  
        productTakeRecordDao=repository;
    }  
    
    //--CustomBegin***///
//--CustomEnd*****///
}

