package com.exigen.poker.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.exigen.poker.dao.EstimationDao;
import com.exigen.poker.dao.EstimationHDao;
import com.exigen.poker.dao.ProjectDao;
import com.exigen.poker.dao.RequirementDao;
import com.exigen.poker.dao.UserDao;
import com.exigen.poker.domain.ProjectEntity;
import com.exigen.poker.domain.RequirementEntity;
import com.exigen.poker.services.RequirementService;

public class RequirementServiceImpl implements RequirementService {

	private RequirementDao requirementDao;
	private EstimationDao estimationDao;
	private EstimationHDao estimationHDao;
	private UserDao userDao;
	private ProjectDao projectDao;
	
	public boolean createRequirementIfNotExist(ProjectEntity currentProject, RequirementEntity requirementEntity) throws Exception {
		
		try {
			currentProject = projectDao.addReq(currentProject, requirementEntity);
			//currentProject.addRequirement(requirementEntity);
			requirementDao.save(requirementEntity);
			
		} catch(DataIntegrityViolationException e) {
			if (e.getRootCause() instanceof SQLException) {
				if (((SQLException)e.getRootCause()).getErrorCode() == 1) {
					throw new Exception("REQUIREMENT exists!");
				}
			}
		}
		catch (Exception e){
			throw e;
		}
		return false;
	}
	
	public void deleteRequirement(RequirementEntity req) throws Exception {
			try {
				requirementDao.delete(req);
			} catch (Exception e) {
				throw e;
			}
	}
	
	public List<RequirementEntity> findAllProjReq(ProjectEntity currentProject) throws Exception {
		try {
			return requirementDao.requirementsByProject(currentProject);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void editRequirement(RequirementEntity currentRequirement) throws Exception {
		try {
			requirementDao.update(currentRequirement);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public RequirementDao getRequirementDao(){
			return requirementDao;
	}

	public void setRequirementDao(RequirementDao requirementDao) throws Exception {
			this.requirementDao = requirementDao;
	}

	public EstimationDao getEstimationDao() {
		return estimationDao;
	}

	public void setEstimationDao(EstimationDao estimationDao) {
		this.estimationDao = estimationDao;
	}

	public EstimationHDao getEstimationHDao() {
		return estimationHDao;
	}

	public void setEstimationHDao(EstimationHDao estimationHDao) {
		this.estimationHDao = estimationHDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}
	
}
