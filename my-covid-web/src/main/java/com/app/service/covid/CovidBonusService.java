package com.app.service.covid;

import java.util.List;

import com.app.entity.CovidCasesBonusEntity;
import com.app.model.CovidCasesBonus;

public interface CovidBonusService {

	CovidCasesBonus processCovidDesc(String desc) throws Exception;

	List<CovidCasesBonus> getBonusDesc();

	CovidCasesBonus addCovid(String desc);
	
	CovidCasesBonus putCovid(CovidCasesBonus covidCasesDesc);
	
	int deleteDescWithCondition(String desc);
	
    List<String> findDuplicateNdelete();
	
	int deleteDuplicateDesc(String desc);
	
	CovidCasesBonusEntity saveEntity(CovidCasesBonusEntity covidAreaBonusEntity);

	int deleteCovid(long id) throws Exception;
}
