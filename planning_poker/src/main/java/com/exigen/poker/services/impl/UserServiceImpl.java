package com.exigen.poker.services.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.exigen.poker.dao.EstimationDao;
import com.exigen.poker.dao.UserDao;
import com.exigen.poker.domain.EstimationEntity;
import com.exigen.poker.domain.UserEntity;
import com.exigen.poker.services.UserService;

public class UserServiceImpl implements UserService, UserDetailsService {

	private UserDao userDao;
	private EstimationDao estimationDao;
	
	public boolean createUserIfNotExist(UserEntity userEntity) throws Exception {
		
		try {
			//userEntity.setUserPassword(encodePassword(userEntity.getUserPassword(),));
			userDao.save(userEntity);
		} catch(DataIntegrityViolationException e) {
			if (e.getRootCause() instanceof SQLException) {
				if (((SQLException)e.getRootCause()).getErrorCode() == 1) {
					throw new Exception("User exists!");
				}
			}
		}
		return false;
	}
	
	public long givenUserID(String userLogin) throws Exception{
		try{
			return userDao.getUserByUsername(userLogin).getId();
		}
		catch (Exception e){
			throw e;
		}
	}
	
	public boolean UserType(String userLogin) throws Exception {
		try {
			if (userDao.getUserByUsername(userLogin).getIsAdmin())
				return false;
			else 
				return true;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<UserEntity> getUserlList() throws Exception{
		try {
			return userDao.getUserlListDao();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<EstimationEntity> UserEstimationList(String username) throws Exception{
		try {
			return estimationDao.UserEstimationListDao(userDao.getUserByUsername(username));
		} catch (Exception e) {
			throw e;
		}
	}
	
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserEntity user = userDao.getUserByUsername(userName);
		
		if (user == null) {
			throw new UsernameNotFoundException("Invalid credentials!");
		}
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		if (user.getIsAdmin()==true){
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
		User userDetails = new User(user.getUserLogin(), user.getUserPassword(), authorities);
		
		return userDetails;
	}

	public UserEntity getUserByUsername(String userLogin) throws Exception {
		try {
			return userDao.getUserByUsername(userLogin);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Object getUsertById(String value) throws Exception {
		try {
			return userDao.getUsertById(value);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public EstimationDao getEstimationDao() {
		return estimationDao;
	}

	public void setEstimationDao(EstimationDao estimationDao) {
		this.estimationDao = estimationDao;
	}
	
}
