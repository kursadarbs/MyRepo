package com.exigen.poker.services;

import java.util.List;

import com.exigen.poker.domain.ProjectEntity;
import com.exigen.poker.domain.RequirementEntity;

public interface RequirementService {
	public boolean createRequirementIfNotExist(ProjectEntity currentProject, RequirementEntity requirementEntity) throws Exception;

	public void deleteRequirement(RequirementEntity req) throws Exception;

	public List<RequirementEntity> findAllProjReq(ProjectEntity currentProject) throws Exception;

	public void editRequirement(RequirementEntity currentRequirement) throws Exception;
}
