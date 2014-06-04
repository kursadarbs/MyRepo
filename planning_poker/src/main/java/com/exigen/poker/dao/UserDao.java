package com.exigen.poker.dao;

import java.util.List;

import com.exigen.poker.domain.UserEntity;
import com.exigen.poker.domain.base.GenericDAO;

public interface UserDao extends GenericDAO<UserEntity, Long> {
	public UserEntity getUserByUsername(String userName);
	public List<UserEntity> getUserlListDao();
	public Object getUsertById(String value);
}
