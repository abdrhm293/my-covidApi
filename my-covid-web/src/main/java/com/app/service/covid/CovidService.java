package com.app.service.covid;

import java.util.List;

import com.app.model.CovidCasesArea;
import com.app.model.CovidCasesDesc;

public interface CovidService {

	List<CovidCasesArea> getCovid();

	List<CovidCasesDesc> getCovidDesc();

	CovidCasesDesc addCovid(String desc);
	
	CovidCasesDesc putCovid(CovidCasesDesc covidCasesDesc);

	int deleteDescWithCondition(String desc);
	
	List<String> findDuplicateNdelete();
	
	int deleteDuplicateDesc(String desc);
}
