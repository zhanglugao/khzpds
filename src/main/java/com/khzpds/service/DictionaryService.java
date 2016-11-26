package com.khzpds.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.DictionaryDao;
import com.khzpds.vo.DictionaryInfo;
@Service
public class DictionaryService extends IBaseService<DictionaryInfo> {
    private DictionaryDao dictionaryDao;
    
    @Autowired  
    public void setDictionaryRepository(DictionaryDao repository) {  
        setRepository(repository);  
        dictionaryDao=repository;
    }

    //--CustomBegin***///
    //根据父id返回map map key为字典表id value为字典表name
	public Map<String, String> getDicMapByParentId(String parentId) {
		DictionaryInfo findInfo=new DictionaryInfo();
		findInfo.setParentId(parentId);
		List<DictionaryInfo> dics=dictionaryDao.findByParam(findInfo,null);
		Map<String,String> map=new HashMap<String, String>();
		for(DictionaryInfo dicInfo:dics){
			map.put(dicInfo.getId(), dicInfo.getName());
		}
		return map;
	} 
//--CustomEnd*****///
}

