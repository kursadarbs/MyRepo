package com.exigen.poker.services;

import java.util.List;

import com.exigen.poker.domain.EstimationEntity;
import com.exigen.poker.domain.EstimationHEntity;
import com.exigen.poker.domain.RequirementEntity;


public interface EstimationHService {

	public void putInHistory(EstimationEntity est) throws Exception;

	public List<EstimationHEntity> findEstHistory(RequirementEntity currentRequirement) throws Exception;

}
