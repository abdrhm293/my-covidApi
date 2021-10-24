package com.app.service.covid;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.CovidCasesAreaEntity;
import com.app.entity.CovidCasesDescEntity;
import com.app.error.IDNotFoundException;
import com.app.mapper.CovidAreaDescMapper;
import com.app.mapper.CovidCasesAreaMapper;
import com.app.model.CovidCasesArea;
import com.app.model.CovidCasesDesc;
import com.app.repository.covid.CovidCasesDescRepository;
import com.app.repository.covid.CovidCasesRepository;

import fr.xebia.extras.selma.Selma;

@Service
@Transactional
public class CovidServiceImpl implements CovidService {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CovidServiceImpl.class);
	private final static String ADD_COVID = "/covid/add";
	
	@Autowired
	CovidCasesRepository covidCasesRepository;
	
	@Autowired
	CovidCasesDescRepository covidCasesDescRepository;
	
	@Override
	
	public List<CovidCasesArea> getCovid() {
		log.info("getCovid started");
		
		CovidCasesAreaMapper mapper = Selma.builder(CovidCasesAreaMapper.class).build();
		List<CovidCasesAreaEntity> covidCaseEntities = covidCasesRepository.findAll();
		List<CovidCasesArea> covidCasesAreaList = new ArrayList<CovidCasesArea>();
		if (covidCaseEntities == null) {
			throw new IDNotFoundException(0L);
		} else {

			for (CovidCasesAreaEntity covidCasesEntity : covidCaseEntities) {
				CovidCasesArea covidCasesArea = mapper.asResource(covidCasesEntity);
				covidCasesAreaList.add(covidCasesArea);
				log.info("covidCasesEntity total Cases={}", covidCasesEntity.getCases());
			}
			log.info(" getCovid() return Size={}", covidCaseEntities.size());
		}

		return covidCasesAreaList;

	}

	@Override
	public List<CovidCasesDesc> getCovidDesc() {
		log.info("getCovidDesc started");
		CovidAreaDescMapper mapper = Selma.builder(CovidAreaDescMapper.class).build();
		List<CovidCasesDescEntity> covidCaseDescEntities = covidCasesDescRepository.findAll();
		List<CovidCasesDesc> covidCasesDescList = new ArrayList<CovidCasesDesc>();
		if (covidCaseDescEntities == null) {
			throw new IDNotFoundException(0L);
		} else {

			for (CovidCasesDescEntity entity : covidCaseDescEntities) {
				CovidCasesDesc model = mapper.asResource(entity);
				covidCasesDescList.add(model);
				log.info("entity total desc={}", entity.getDescription());
			}
			log.info(" getCovidDesc() return Size={}", covidCaseDescEntities.size());
		}

		return covidCasesDescList;

	}
	
	// TODO: Related to Practical 4 (Add)
	@Override
	public CovidCasesDesc addCovid(String desc) {
		log.info("addCovid started");
		
		CovidCasesDesc covidCasesDesc = null;
		try {

			if (desc == null || desc.equals("undefined") || desc.equals(""))  {
				throw new NullPointerException(ADD_COVID + ", desc is null or empty");
			}
			List<CovidCasesAreaEntity> cases = covidCasesRepository.findAll();
			CovidCasesAreaEntity covidCasesAreaEntity = cases.get(0);
			CovidCasesAreaEntity covidCasesAreaEntityNew = new CovidCasesAreaEntity();

			covidCasesAreaEntityNew.setArea(covidCasesAreaEntity.getArea());
			covidCasesAreaEntityNew.setDate(LocalDate.now());

			CovidCasesDescEntity covidAreaDescEntity = new CovidCasesDescEntity();

			covidAreaDescEntity.setDescription(desc);

			CovidCasesDescEntity savedEntity = covidCasesDescRepository.save(covidAreaDescEntity);

			CovidAreaDescMapper mapper = Selma.builder(CovidAreaDescMapper.class).build();

			covidCasesDesc = mapper.asResource(savedEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("add() exception " + e.getMessage());
			throw new com.app.error.ControllerException(ADD_COVID, e.getMessage());
		}

		return covidCasesDesc;

	}

	// TODO: Related to Practical 4 (Delete)
	public List<CovidCasesArea> deleteCovid(long id) throws Exception {
		log.info("deleteCovid started");

		try {

			Optional<CovidCasesDescEntity> entityOptional = covidCasesDescRepository.findById(id);

			log.info("Entity found == " + entityOptional.isPresent());

			if (entityOptional.isPresent()) {
				CovidCasesDescEntity covidAreaDescEntity = entityOptional.get();
				covidCasesDescRepository.delete(covidAreaDescEntity);
				return null;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("deleteCovid() exception " + e.getMessage());
			throw new Exception(e.getMessage());
		}

		return null;

	}
	
	public int deleteDescWithCondition(String desc) {
		int i = covidCasesDescRepository.deleteDescWithCondition(desc);
        return i;
	}
	
	@Override
	public CovidCasesDesc putCovid(CovidCasesDesc covidCasesDesc){
		
		CovidAreaDescMapper mapper = Selma.builder(CovidAreaDescMapper.class).build();
		
		CovidCasesDescEntity covidCasesDescEntity = mapper.asEntity(covidCasesDesc);
		
		CovidCasesDescEntity savedEntity = covidCasesDescRepository.save(covidCasesDescEntity);
		covidCasesDesc = mapper.asResource(savedEntity);
		return covidCasesDesc;
	}
	
	@Override
	public List<String> findDuplicateNdelete() {
		List<String> e = covidCasesDescRepository.findDuplicateNdelete();
		return e;
	}
	
	@Override
	public int deleteDuplicateDesc(String desc) {
		int records = covidCasesDescRepository.deleteDescWithCondition(desc);
        return records;
	}
}
