package com.exigen.poker.dao;

import java.util.List;

import com.exigen.poker.domain.EstimationEntity;
import com.exigen.poker.domain.RequirementEntity;
import com.exigen.poker.domain.UserEntity;
import com.exigen.poker.domain.base.GenericDAO;

public interface EstimationDao extends GenericDAO<EstimationEntity, Long> {
	public List<EstimationEntity> UserEstimationListDao(UserEntity userE);

	public List<EstimationEntity> ReqEstimations(RequirementEntity requirementEntity);

	public EstimationEntity getEstimation(UserEntity currentUser,
			RequirementEntity currentRequirement);

	public void deleteUserProjEstim(RequirementEntity next, UserEntity user);

	/*public EstimationEntity saveO`rUpdate(EstimationEntity estimation);*/
}
