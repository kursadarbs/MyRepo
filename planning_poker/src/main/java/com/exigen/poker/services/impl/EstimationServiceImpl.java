package com.exigen.poker.services.impl;

import java.util.Iterator;
import java.util.List;

import com.exigen.poker.dao.EstimationDao;
import com.exigen.poker.dao.ProjectDao;
import com.exigen.poker.dao.RequirementDao;
import com.exigen.poker.domain.EstimationEntity;
import com.exigen.poker.domain.ProjectEntity;
import com.exigen.poker.domain.RequirementEntity;
import com.exigen.poker.domain.UserEntity;
import com.exigen.poker.services.EstimationService;

public class EstimationServiceImpl implements EstimationService {

	private EstimationDao estimationDao;
	private RequirementDao requirementDao;
	private ProjectDao projectDao;

	public EstimationDao getEstimationDao() {
		return estimationDao;
	}

	public void setEstimationDao(EstimationDao estimationDao) {
		this.estimationDao = estimationDao;
	}

	public void createEstimations(List <UserEntity> users, RequirementEntity requirement) throws Exception {

		try {
			
			Iterator<UserEntity> myListIterator = users.iterator(); 
			
			while (myListIterator.hasNext()) {
				UserEntity user = myListIterator.next();  
				
				EstimationEntity estimation = new EstimationEntity();
				estimation.setUpdated(false);
				estimation.setHasRated(false);
				requirement.addEstimation(estimation);
				user.addEstimation(estimation);
				
				estimationDao.save(estimation);
			}
			
		} catch(Exception e) {
			throw e;
		}
	}
	
	public void createEstimationsUserAdd(ProjectEntity project, UserEntity user) throws Exception {
		try {
			List <RequirementEntity> reqList = requirementDao.requirementsByProject(project);
			Iterator<RequirementEntity> myListIterator = reqList.iterator(); 
			
			while (myListIterator.hasNext()) {
				RequirementEntity req = myListIterator.next();  
				
				EstimationEntity estimation = new EstimationEntity();
				estimation.setUpdated(false);
				estimation.setHasRated(false);
				estimation = requirementDao.addEsti(req, estimation);
				user.addEstimation(estimation);
				
				estimationDao.update(estimation);
			}
		} catch(Exception e) {
			throw e;
		}
	}
	
	public void deleteProjectExpertEstimations(UserEntity user, ProjectEntity currentProject) throws Exception {//1!!!!
		try {
			currentProject = projectDao.findById(currentProject.getId());
			
			Iterator<RequirementEntity> myListIterator = currentProject.getRequirements().iterator();
			
			while (myListIterator.hasNext()) {
				//delete req+user estimation
				estimationDao.deleteUserProjEstim(myListIterator.next(), user);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public List<EstimationEntity> ReqEstimations(RequirementEntity requirementEntity) throws Exception {
		try {
			return estimationDao.ReqEstimations(requirementEntity);
		} catch (Exception e) {
			throw e;
		}
	}

	public void updateEstimation(EstimationEntity est) throws Exception {
		try {
			estimationDao.update(est);
		} catch (Exception e) {
			throw e;
		}
	}

	public EstimationEntity getEstimation(UserEntity currentUser, RequirementEntity currentRequirement) throws Exception {
		try {
			return estimationDao.getEstimation(currentUser, currentRequirement);
		} catch (Exception e) {
			throw e;
		}
	}

	public void resetEstimations(List<EstimationEntity> reqEstimations) throws Exception {
		try {
			Iterator<EstimationEntity> myListIterator = reqEstimations.iterator(); 
			
			while (myListIterator.hasNext()) {
				EstimationEntity est = myListIterator.next();  
				
				est.setHasRated(false);
				est.setUpdated(false);
				
				estimationDao.update(est);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public RequirementDao getRequirementDao() {
		return requirementDao;
	}

	public void setRequirementDao(RequirementDao requirementDao) {
		this.requirementDao = requirementDao;
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}
	
}
