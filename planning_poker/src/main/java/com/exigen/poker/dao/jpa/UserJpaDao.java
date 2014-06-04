package com.exigen.poker.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import com.exigen.poker.dao.UserDao;
import com.exigen.poker.domain.UserEntity;
import com.exigen.poker.domain.base.GenericJPADAO;

public class UserJpaDao extends GenericJPADAO<UserEntity, Long> implements UserDao {

	private static final String QUERY_USER = 
		"select user from UserEntity user where user.userLogin = :userName";
	private static final String QUERY_USER1 = "select users from UserEntity users";
	private static final String QUERY_USER3 = 
			"select user from UserEntity user where user.id = :id";
	
	public UserJpaDao() {
		super(UserEntity.class);
	}

	@SuppressWarnings("unchecked")
	public UserEntity getUserByUsername(String userName) {
		
		UserEntity userEntity = null;
		
		Query query = getEntityManager().createQuery(QUERY_USER);
		query.setParameter("userName", userName);
		query.setMaxResults(1);
		
		List<UserEntity> result = query.getResultList();
		
		userEntity = result.get(0);
		
		return userEntity;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserEntity> getUserlListDao(){

		Query query = getEntityManager().createQuery(QUERY_USER1);
		
		List<UserEntity> result = query.getResultList();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public Object getUsertById(String id) {
		UserEntity userEntity = null;
		
		Query query = getEntityManager().createQuery(QUERY_USER3);
		query.setParameter("id", Long.parseLong(id));
		query.setMaxResults(1);
		
		List<UserEntity> result = query.getResultList();
		
		userEntity = result.get(0);
		
		return userEntity;
	}
	
}
















