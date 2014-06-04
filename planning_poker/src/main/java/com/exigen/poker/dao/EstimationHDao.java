package com.exigen.poker.dao;

import java.util.List;

import com.exigen.poker.domain.EstimationHEntity;
import com.exigen.poker.domain.RequirementEntity;
import com.exigen.poker.domain.base.GenericDAO;

public interface EstimationHDao extends GenericDAO<EstimationHEntity, Long> {

	List<EstimationHEntity> findEstHistory(RequirementEntity currentRequirement);

}
