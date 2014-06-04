package com.exigen.poker.dao;

import java.util.List;

import com.exigen.poker.domain.EstimationEntity;
import com.exigen.poker.domain.EstimationHEntity;
import com.exigen.poker.domain.ProjectEntity;
import com.exigen.poker.domain.RequirementEntity;
import com.exigen.poker.domain.base.GenericDAO;

public interface RequirementDao extends GenericDAO<RequirementEntity, Long> {
	public RequirementEntity getRequirementByName(String requirementName);
	public List<RequirementEntity> getRequirementListDao();
	public List<RequirementEntity> requirementsByProject(ProjectEntity project);
	public EstimationEntity addEsti(RequirementEntity req, EstimationEntity estimation);
	public EstimationHEntity addEstiH(RequirementEntity req, EstimationHEntity estH);
}
