package com.app.mapper;

import com.app.entity.CovidCasesBonusEntity;
import com.app.model.CovidCasesBonus;

import fr.xebia.extras.selma.IgnoreMissing;
import fr.xebia.extras.selma.Mapper;
import fr.xebia.extras.selma.Maps;

//TODO: Practical bonus final
//complete this as Mapper
@Mapper()
public interface CovidAreaBonusMapper {
	
	@Maps(withIgnoreMissing = IgnoreMissing.ALL)
	public CovidCasesBonusEntity asEntity(CovidCasesBonus covidCasesBonus);

	@Maps(withIgnoreMissing = IgnoreMissing.ALL)
	public CovidCasesBonus asResource(CovidCasesBonusEntity covidAreaBonusEntity);

}
