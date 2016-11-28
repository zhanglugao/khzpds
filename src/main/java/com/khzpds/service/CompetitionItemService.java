package com.khzpds.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khzpds.base.IBaseService;
import com.khzpds.dao.CompetitionItemDao;
import com.khzpds.vo.CompetitionItemInfo;

@Service
public class CompetitionItemService extends IBaseService<CompetitionItemInfo> {
    private CompetitionItemDao competitionItemDao;
    
    @Autowired  
    public void setCompetitionItemRepository(CompetitionItemDao repository) {  
        setRepository(repository);  
        competitionItemDao=repository;
    }  
    
    //--CustomBegin***///
    public List<CompetitionItemInfo> findPublishedCompetitionItem(String type) {
		// TODO Auto-generated method stub
		return competitionItemDao.findPublishedCompetitionItem(type);
	}
//--CustomEnd*****///
}

