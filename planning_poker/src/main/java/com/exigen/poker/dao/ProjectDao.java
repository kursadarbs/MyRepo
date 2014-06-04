package com.exigen.poker.dao;

import java.util.List;

import com.exigen.poker.domain.ProjectEntity;
import com.exigen.poker.domain.RequirementEntity;
import com.exigen.poker.domain.UserEntity;
import com.exigen.poker.domain.base.GenericDAO;

public interface ProjectDao extends GenericDAO<ProjectEntity, Long> {
	public ProjectEntity getProjectByProjectName(String projectName);
	public List<ProjectEntity> getProjectlListDao();
	public List<UserEntity> allProjectUsers(Long projectID);
	public ProjectEntity addUserDao(Long projectID, Long userID);
	public List<ProjectEntity> userProjects(long givenUserID);
	public ProjectEntity removeProjExpert(UserEntity user,
			ProjectEntity currentProject);
	public boolean expertExists(Long projectID, Long userID);
	public ProjectEntity addReq(ProjectEntity currentProject,
			RequirementEntity requirementEntity);
}
