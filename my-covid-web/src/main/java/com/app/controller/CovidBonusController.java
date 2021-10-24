package com.app.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.CovidCasesBonus;
import com.app.service.covid.CovidBonusService;

import lombok.extern.slf4j.Slf4j;

@Transactional
@RestController
@Slf4j
public class CovidBonusController {

  private final static String GET_COVID_DESC = "/bonus/get/desc";

  private final static String DELETE_COVID = "/bonus/delete";

	private final static String ADD_COVID = "/bonus/add";

	private final static String PUT_API = "/bonus/put";

	private final static String GET_MY_BONUS = "/bonus/get/bonus";

	private final static String POST_API = "/bonus/post";

	private final static String DELETE_COVID_SOAPUI = "/bonus/delete/soap";

	private final static String FIND_DUPLICATE_DELETE_COVID = "/bonus/delete/duplicate";

	@Autowired
	private CovidBonusService covidBonusService;

	// @Autowired
	// CovidMiningAPITotalCases covidMiningAPITotalCases;

	// TODO: Practical Bonus Desc Final
	// Objective: to create a set of spring and hibernate services to retrieve data
	// from a new table call "trx_covid_cases_bonus"

	// 1. Complete the CovidCasesBonusEntity.java and auto generate a table on DB
	// Enable the line below from application.properties to create new bonus table
	// # spring.jpa.hibernate.ddl-auto=update
	// Then restart application and table being created on the log
	// CREATE TABLE / PRIMARY KEY will create implicit index
	// "trx_covid_cases_bonus_pkey" for table "trx_covid_cases_bonus"

	// 2. Insert the dummy data into trx_covid_cases_bonus using PGAdmin

	// 3. Complete the method below to return list of CovidCasesBonus from table
	// trx_covid_cases_bonus
	// Files to be modified as below

	// CovidCasesBonus - Java POJO
	// CovidCasesBonusEntity - DB Entity File
	// CovidAreaBonusMapper - Mapper from Java Entity file above to POJO
	// CovidCasesBonusRepository - Spring JPA Repository or library to query DB.
	// i.e. FindAll() method
	// CovidBonusService - Interface for the service below
	// CovidBonusServiceImpl - Implementation of the service between controller and
	// repo

	@GetMapping(ADD_COVID)
	@Transactional
	CovidCasesBonus addCovid(@RequestParam(required = true) String desc) throws Exception {
		log.info("addCovid() started={}", desc);

		CovidCasesBonus covidCasesDesc = null;
		try {

			if (desc == null || desc.equals("undefined") || desc.equals("")) {
				throw new NullPointerException(ADD_COVID + ", desc is null or empty");
			}

			covidCasesDesc = covidBonusService.processCovidDesc(desc);
		} catch (Exception e) {
			log.error("add() exception " + e.getMessage());
			throw new com.app.error.ControllerException(ADD_COVID, e.getMessage());
		}

		return covidCasesDesc;
	}

	@PutMapping(PUT_API)
	CovidCasesBonus putCovid(@RequestBody CovidCasesBonus covidCasesDesc) throws RuntimeException {
		log.info("putCovid() started, covidCasesDesc={}", covidCasesDesc);

		covidCasesDesc = covidBonusService.putCovid(covidCasesDesc);
		// complete the implementation below
		log.info("putCovid() ends, covidCasesDescSaved={}", covidCasesDesc);

		// return should be the Saved CovidCasesDesc with values
		return covidCasesDesc;
	}

	@GetMapping(GET_MY_BONUS)
	List<CovidCasesBonus> getBonusDesc() throws Exception {
		List<CovidCasesBonus> covidCasesBonus = null;
		log.info("bonus() started");

		try {
			covidCasesBonus = covidBonusService.getBonusDesc();
			if (covidCasesBonus == null) {
				throw new Exception("No bonus yet");
			} 
		} catch (Exception e) {
			log.error("bonus() exception " + e.getMessage());
			throw new Exception(e);
		}

		log.info(GET_MY_BONUS + " return = {}" + covidCasesBonus);
		return covidCasesBonus;
	}

	@PostMapping(POST_API)
	@Transactional
	CovidCasesBonus postDescCovid(@RequestParam(required = true) String desc) throws Exception {
		log.info("postDescCovid() started={}", desc);

		CovidCasesBonus covidCasesDesc = null;
		try {

			if (desc == null || desc.equals("undefined") || desc.equals("")) {
				throw new NullPointerException(POST_API + ", desc is null or empty");
			}

			covidCasesDesc = covidBonusService.processCovidDesc(desc);
		} catch (Exception e) {
			log.error("postDescCovid() exception " + e.getMessage());
			throw new com.app.error.ControllerException(POST_API, e.getMessage());
		}

		return covidCasesDesc;
	}

	@DeleteMapping(DELETE_COVID_SOAPUI)
	int deleteCovidSoap(@RequestParam(required = true) String desc) throws Exception {
		log.info("deleteCovidSoap() started desc={}", desc);

		// complete the implementation below
		int recordsDeleted = covidBonusService.deleteDescWithCondition(desc);

		log.info("deleteCovidSoap() i={}", recordsDeleted);
		return recordsDeleted;
	}

	@DeleteMapping(FIND_DUPLICATE_DELETE_COVID)
	List<String> findDuplicateNdelete() throws Exception {
		log.info("findDuplicateNdelete() started");

		// complete the implementation below
		// ensure logic related to repo move to service implementation
		List<String> deletedList = covidBonusService.findDuplicateNdelete();

		log.info("findDuplicateNdelete() ended deletedList={}", deletedList);
		return deletedList;
	}

	@DeleteMapping(DELETE_COVID)
	int deleteCovid(@RequestParam(required = true) long id) throws Exception {
		log.info(DELETE_COVID + " started id={}", id);

		int result = 0;
		try {

			result = covidBonusService.deleteCovid(id);

		} catch (Exception e) {
			log.error("deleteCovid() exception " + e.getMessage());
			throw new Exception(e.getMessage());
		}
		log.info(DELETE_COVID+ " end result={}", result);
		return result;
	}

	@GetMapping(GET_COVID_DESC)
	List<CovidCasesBonus> findAllDesc() throws Exception {
		log.info(GET_COVID_DESC + " started");
		List<CovidCasesBonus> covidCasesdescs = null;
		try {
			covidCasesdescs = covidBonusService.getBonusDesc();
		} catch (Exception e) {
			log.error(" findAll() exception " + e.getMessage());
			throw new Exception(e.getMessage());
		}

		log.info(GET_COVID_DESC + "  return = {}" + covidCasesdescs);
		return covidCasesdescs;
	}

}
