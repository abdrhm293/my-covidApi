package com.app.service.covid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.CovidCasesBonusEntity;
import com.app.error.IDNotFoundException;
import com.app.mapper.CovidAreaBonusMapper;
import com.app.model.CovidCasesBonus;
import com.app.repository.covid.CovidCasesBonusRepository;

import fr.xebia.extras.selma.Selma;
import lombok.extern.slf4j.Slf4j;

//TODO: Practical bonus final
//complete this as Dependencies Injection Service
@Slf4j
@Service
@Transactional
public class CovidBonusServiceImpl implements CovidBonusService {

	private final static String ADD_COVID = "/covid/add";

	@Autowired
	CovidCasesBonusRepository covidCasesBonusRepository;

	// hint
	// the method is similar to getCovidDesc() CovidServiceImpl file
	@Override
	public CovidCasesBonus processCovidDesc(String desc) throws Exception {
		CovidCasesBonus covidCasesDesc = null;

		CovidCasesBonusEntity covidAreaDescEntity = new CovidCasesBonusEntity();

		covidAreaDescEntity.setDescription(desc);

		CovidCasesBonusEntity savedEntity = this.saveEntity(covidAreaDescEntity);

		CovidAreaBonusMapper mapper = Selma.builder(CovidAreaBonusMapper.class).build();

		covidCasesDesc = mapper.asResource(savedEntity);

		return covidCasesDesc;
	}
	
	@Override
	public CovidCasesBonus putCovid(CovidCasesBonus covidCasesDesc) {

		CovidAreaBonusMapper mapper = Selma.builder(CovidAreaBonusMapper.class).build();

		CovidCasesBonusEntity covidCasesDescEntity = mapper.asEntity(covidCasesDesc);

		CovidCasesBonusEntity savedEntity = covidCasesBonusRepository.save(covidCasesDescEntity);
		covidCasesDesc = mapper.asResource(savedEntity);
		return covidCasesDesc;
	}

	@Override
	public List<CovidCasesBonus> getBonusDesc() {
		log.info("getCovidDesc started");
		CovidAreaBonusMapper mapper = Selma.builder(CovidAreaBonusMapper.class).build();
		List<CovidCasesBonusEntity> covidCaseDescEntities = covidCasesBonusRepository.findAll();
		List<CovidCasesBonus> covidCasesDescList = new ArrayList<CovidCasesBonus>();
		if (covidCaseDescEntities == null) {
			throw new IDNotFoundException(0L);
		} else {

			for (CovidCasesBonusEntity entity : covidCaseDescEntities) {
				CovidCasesBonus model = mapper.asResource(entity);
				covidCasesDescList.add(model);
				log.info("entity total desc={}", entity.getDescription());
			}
			log.info(" getCovidDesc() return Size={}", covidCaseDescEntities.size());
		}

		return covidCasesDescList;

	}

	//Related to Practical 4 (Add)
	@Override
	public CovidCasesBonus addCovid(String desc) {
		log.info("addCovid started");

		CovidCasesBonus covidCasesDesc = null;
		try {

			if (desc == null || desc.equals("undefined") || desc.equals("")) {
				throw new NullPointerException(ADD_COVID + ", desc is null or empty");
			}

			CovidCasesBonusEntity covidAreaDescEntity = new CovidCasesBonusEntity();

			covidAreaDescEntity.setDescription(desc);

			CovidCasesBonusEntity savedEntity = covidCasesBonusRepository.save(covidAreaDescEntity);

			CovidAreaBonusMapper mapper = Selma.builder(CovidAreaBonusMapper.class).build();

			covidCasesDesc = mapper.asResource(savedEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("add() exception " + e.getMessage());
			throw new com.app.error.ControllerException(ADD_COVID, e.getMessage());
		}

		return covidCasesDesc;

	}

	// Related to Practical 4 (Delete)
	@Override
	public int deleteCovid(long id) throws Exception {
		log.info("deleteCovid started");
		int iDelete = 0;
		try {

			Optional<CovidCasesBonusEntity> entityOptional = covidCasesBonusRepository.findById(id);

			log.info("Entity found == " + entityOptional.isPresent());

			if (entityOptional.isPresent()) {
				CovidCasesBonusEntity covidAreaDescEntity = entityOptional.get();
				covidCasesBonusRepository.delete(covidAreaDescEntity);
				iDelete = 1;
			}

		} catch (Exception e) {
			log.error("deleteCovid() exception " + e.getMessage());
			throw new Exception(e.getMessage());
		}
		log.info("deleteCovid ended");
		return iDelete;
	}

	public int deleteDescWithCondition(String desc) {
		int i = covidCasesBonusRepository.deleteDescWithCondition(desc);
		return i;
	}

	@Override
	public List<String> findDuplicateNdelete() {
		List<String> duplicates = covidCasesBonusRepository.findDuplicateNdelete();
		
		for (String s : duplicates) {
			log.info("Duplicate value found on Description Table--->" + s);
			log.info("Value Deleted--->" + s);
			int records = deleteDuplicateDesc(s);
			log.info("Delete Status--->" + records);
		}
		return duplicates;
	}

	@Override
	public int deleteDuplicateDesc(String desc) {
		int records = covidCasesBonusRepository.deleteDescWithCondition(desc);
		return records;
	}
	
	@Override
	public CovidCasesBonusEntity saveEntity(CovidCasesBonusEntity covidAreaBonusEntity) {
		CovidCasesBonusEntity savedEntity = covidCasesBonusRepository.save(covidAreaBonusEntity);
		return savedEntity;
	}
}
