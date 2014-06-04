package com.exigen.poker.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import com.exigen.poker.dao.EstimationDao;
import com.exigen.poker.domain.EstimationEntity;
import com.exigen.poker.domain.RequirementEntity;
import com.exigen.poker.domain.UserEntity;
import com.exigen.poker.domain.base.GenericJPADAO;

public class EstimationJpaDao extends GenericJPADAO<EstimationEntity, Long> implements EstimationDao {

	private static final String QUERY_EST = 
		"select ee from EstimationEntity ee where ee.user = :userE";
	private static final String QUERY_EST2 = 
			"select ee from EstimationEntity ee where ee.requirement = :req";
	private static final String QUERY_EST3 = 
			"select ee from EstimationEntity ee where (ee.requirement = :req and ee.user = :user)";
	private static final String QUERY_EST4 = 
			"delete from EstimationEntity ee where (ee.requirement = :req and ee.user = :user)";
	
	public EstimationJpaDao() {
		super(EstimationEntity.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<EstimationEntity> UserEstimationListDao(UserEntity userE){
		
		
		Query query = getEntityManager().createQuery(QUERY_EST);
		query.setParameter("userE", userE);
		
		List<EstimationEntity> result = query.getResultList();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<EstimationEntity> ReqEstimations(RequirementEntity req) {
		
		Query query = getEntityManager().createQuery(QUERY_EST2);
		query.setParameter("req", req);
		List<EstimationEntity> result = query.getResultList();
		return result;
	}

	public EstimationEntity getEstimation(UserEntity currentUser, RequirementEntity currentRequirement) {
		Query query = getEntityManager().createQuery(QUERY_EST3);
		query.setParameter("req", currentRequirement);
		query.setParameter("user", currentUser);
		return (EstimationEntity) query.getSingleResult();
	}

	public void deleteUserProjEstim(RequirementEntity req, UserEntity user) {
		Query query = getEntityManager().createQuery(QUERY_EST4);
		query.setParameter("req", req);
		query.setParameter("user", user);
		query.executeUpdate();
	}


	/*public EstimationEntity saveOrUpdate(EstimationEntity estimation) {
		EstimationEntity est = ((EstimationDao) getEntityManager()).saveOrUpdate(estimation);
		return est;
	}*/
	
}
















