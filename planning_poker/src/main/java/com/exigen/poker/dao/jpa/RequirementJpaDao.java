package com.exigen.poker.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import com.exigen.poker.dao.RequirementDao;
import com.exigen.poker.domain.EstimationEntity;
import com.exigen.poker.domain.EstimationHEntity;
import com.exigen.poker.domain.ProjectEntity;
import com.exigen.poker.domain.RequirementEntity;
import com.exigen.poker.domain.base.GenericJPADAO;

public class RequirementJpaDao extends GenericJPADAO<RequirementEntity, Long> implements RequirementDao {

	private static final String QUERY_REQUIREMENT = 
		"select requirements from RequirementEntity requirements where requirements.name = :requirementName";
	private static final String QUERY_REQUIREMENT2 = "select r from RequirementEntity r";
	private static final String QUERY_REQUIREMENT3 = 
			"select requirements from RequirementEntity requirements where requirements.project = :pr";
	
	public RequirementJpaDao() {
		super(RequirementEntity.class);
	}

	@SuppressWarnings("unchecked")
	public RequirementEntity getRequirementByName(String requirementName) {
		
		RequirementEntity requirementEntity = null;
		
		Query query = getEntityManager().createQuery(QUERY_REQUIREMENT);
		query.setParameter("requirementName", requirementName);
		query.setMaxResults(1);
		
		List<RequirementEntity> result = query.getResultList();
		
		requirementEntity = result.get(0);
		
		return requirementEntity;
	}
	
	@SuppressWarnings("unchecked")
	public List<RequirementEntity> getRequirementListDao(){

		Query query = getEntityManager().createQuery(QUERY_REQUIREMENT2);
		query.setMaxResults(10);
		
		
		List<RequirementEntity> result = query.getResultList();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<RequirementEntity> requirementsByProject(ProjectEntity project) {
		
		Query query = getEntityManager().createQuery(QUERY_REQUIREMENT3);
		query.setParameter("pr", project);
		
		List<RequirementEntity> result = query.getResultList();
		
		return result;
	}

	public EstimationEntity addEsti(RequirementEntity req, EstimationEntity est) {
		RequirementEntity requirement = getEntityManager().find(RequirementEntity.class, req.getId());
		requirement.addEstimation(est);
		return est;
	}
	
	public EstimationHEntity addEstiH(RequirementEntity req, EstimationHEntity estH) {
		RequirementEntity requirement = getEntityManager().find(RequirementEntity.class, req.getId());
		requirement.addEstimationH(estH);
		return estH;
	}
	
}
















