package com.exigen.poker.services;

import java.util.List;

import com.exigen.poker.domain.EstimationEntity;
import com.exigen.poker.domain.ProjectEntity;
import com.exigen.poker.domain.RequirementEntity;
import com.exigen.poker.domain.UserEntity;


public interface EstimationService {

	void createEstimations(List <UserEntity> users,
			RequirementEntity requirement) throws Exception;

	List<EstimationEntity> ReqEstimations(RequirementEntity requirementEntity) throws Exception;

	void updateEstimation(EstimationEntity est) throws Exception;

	EstimationEntity getEstimation(UserEntity currentUser,
			RequirementEntity currentRequirement) throws Exception;

	void resetEstimations(List<EstimationEntity> reqEstimations) throws Exception;

	void createEstimationsUserAdd(ProjectEntity project, UserEntity user) throws Exception;

	void deleteProjectExpertEstimations(UserEntity user,
			ProjectEntity currentProject) throws Exception;
	
}
