package com.exigen.poker.services;

import java.util.List;

import com.exigen.poker.domain.ProjectEntity;
import com.exigen.poker.domain.UserEntity;

public interface ProjectService {
	public boolean createProjectIfNotExist(ProjectEntity projectEntity) throws Exception;
	public List<ProjectEntity> getProjectlList() throws Exception;
	public ProjectEntity addProjectExpertIfNotExist(Long long1,
			Long long2) throws Exception;
	public List<UserEntity> allProjectUsers(Long projectID) throws Exception;
	public List<ProjectEntity> userProjects(long givenUserID) throws Exception;
	public void deleteProject(ProjectEntity project) throws Exception;
	public ProjectEntity removeProjExpert(UserEntity user, ProjectEntity currentProject) throws Exception;
	public void editProject(ProjectEntity currentProject) throws Exception;
}
