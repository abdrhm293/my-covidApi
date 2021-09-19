package com.app.mining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.mining.service.covid.api.CovidMiningAPITotalCases;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MyCovidMiningController {

	private final static String MINING_MY_COVID = "/covid/mining/my";
	
	@Autowired
	CovidMiningAPITotalCases covidMiningAPITotalCases;

	// CovidMiningApiTotalCasesImpl need to be fixed too. Refer to the file TODO remarks
	@GetMapping(MINING_MY_COVID)
	String mining() throws Exception {
		log.info("mining() started");
		String strReturn = null;

		try {
			strReturn = covidMiningAPITotalCases.doMining();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("mining() exception " + e.getMessage());
			throw new Exception(e);
		}

		log.info(MINING_MY_COVID + " return = {}" + strReturn);
		return strReturn;
	}
	
}
