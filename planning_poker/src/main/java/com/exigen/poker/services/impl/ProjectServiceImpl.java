package com.exigen.poker.services.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.exigen.poker.dao.ProjectDao;
import com.exigen.poker.dao.UserDao;
import com.exigen.poker.domain.ProjectEntity;
import com.exigen.poker.domain.UserEntity;
import com.exigen.poker.services.ProjectService;

public class ProjectServiceImpl implements ProjectService {

	private ProjectDao projectDao;
	private UserDao userDao;
	
	public boolean createProjectIfNotExist(ProjectEntity projectEntity) throws Exception {
		
		try {
			projectDao.save(projectEntity);
		} catch(DataIntegrityViolationException e) {
			if (e.getRootCause() instanceof SQLException) {
				if (((SQLException)e.getRootCause()).getErrorCode() == 1) {
					throw new Exception("Project name exists!");
				}
				else 
					throw e;
			}
		}
		return false;
	}
	
	public ProjectEntity addProjectExpertIfNotExist(Long projectID, Long userID) throws Exception {
		try {
			
			if (projectDao.expertExists(projectID, userID))
				throw new Exception("User already added!");
			
			return projectDao.addUserDao(projectID, userID);
			
		} catch(Exception e) {
			throw e;
		}
	}
	
	public ProjectEntity removeProjExpert(UserEntity user, ProjectEntity currentProject) throws Exception {
		try {
			return projectDao.removeProjExpert(user, currentProject);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void deleteProject(ProjectEntity project) throws Exception {
		try {
			projectDao.delete(project);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<ProjectEntity> userProjects(long givenUserID) throws Exception{
		try {
			return projectDao.userProjects(givenUserID);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<UserEntity> allProjectUsers(Long projectID) throws Exception{
		try {
			return projectDao.allProjectUsers(projectID);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<ProjectEntity> getProjectlList() throws Exception {
		try {
			return projectDao.getProjectlListDao();
		} catch (Exception e) {
			throw e;
		}
	}
	

	public void editProject(ProjectEntity currentProject) throws Exception {
		try {
			projectDao.update(currentProject);
		} catch(DataIntegrityViolationException e) {
			if (e.getRootCause() instanceof SQLException) {
				if (((SQLException)e.getRootCause()).getErrorCode() == 1) {
					throw new Exception("Project name exists!");
				}
				else 
					throw e;
			}
		}
	}

	public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}
