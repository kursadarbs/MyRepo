package com.exigen.poker.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import com.exigen.poker.dao.EstimationHDao;
import com.exigen.poker.domain.EstimationHEntity;
import com.exigen.poker.domain.RequirementEntity;
import com.exigen.poker.domain.base.GenericJPADAO;
public class EstimationHJpaDao extends GenericJPADAO<EstimationHEntity, Long> implements EstimationHDao {
	private static final String QUERY_EST = 
			"select ehe from EstimationHEntity ehe where ehe.requirement = :req";
	
	public EstimationHJpaDao() {
		super(EstimationHEntity.class);
	}

	@SuppressWarnings("unchecked")
	public List<EstimationHEntity> findEstHistory(RequirementEntity currentRequirement) {
		
		Query query = getEntityManager().createQuery(QUERY_EST);
		query.setParameter("req", currentRequirement);
		List<EstimationHEntity> result = query.getResultList();
		return result;
	}
	
}
















