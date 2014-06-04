package com.exigen.poker.services.impl;

import java.sql.Timestamp;
import java.util.List;

import com.exigen.poker.dao.EstimationHDao;
import com.exigen.poker.dao.RequirementDao;
import com.exigen.poker.domain.EstimationEntity;
import com.exigen.poker.domain.EstimationHEntity;
import com.exigen.poker.domain.RequirementEntity;
import com.exigen.poker.services.EstimationHService;

public class EstimationHServiceImpl implements EstimationHService {
	public EstimationHDao estimationHDao;
	public RequirementDao requirementDao;

	public void putInHistory(EstimationEntity est) throws Exception {
		try {
			EstimationHEntity estH = new EstimationHEntity();
			
			java.util.Date date= new java.util.Date();
			estH.setUpdateTime(new Timestamp(date.getTime()));
			estH.setValue(est.getValue());
			estH.setComment(est.getComment());
			
			//est.getRequirement().addEstimationH(estH);
			estH = requirementDao.addEstiH(est.getRequirement(), estH);
			est.getUser().addEstimationH(estH);
			
			estimationHDao.update(estH);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<EstimationHEntity> findEstHistory(RequirementEntity currentRequirement) throws Exception {
		try {
			return estimationHDao.findEstHistory(currentRequirement);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public EstimationHDao getEstimationHDao() {
		return estimationHDao;
	}

	public void setEstimationHDao(EstimationHDao estimationHDao) {
		this.estimationHDao = estimationHDao;
	}

	public RequirementDao getRequirementDao() {
		return requirementDao;
	}

	public void setRequirementDao(RequirementDao requirementDao) {
		this.requirementDao = requirementDao;
	}
	
}
