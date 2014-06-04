package com.exigen.poker.services;

import java.util.List;

import com.exigen.poker.domain.EstimationEntity;
import com.exigen.poker.domain.UserEntity;

public interface UserService {
	public boolean createUserIfNotExist(UserEntity userEntity) throws Exception;
	public List<UserEntity> getUserlList() throws Exception;
	public List<EstimationEntity> UserEstimationList(String username) throws Exception;
	public boolean UserType(String userLogin) throws Exception;
	public long givenUserID(String userLogin) throws Exception;
	public UserEntity getUserByUsername(String userLogin) throws Exception;
	public Object getUsertById(String value) throws Exception;
}
