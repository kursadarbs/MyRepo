package com.exigen.poker.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import com.exigen.poker.dao.ProjectDao;
import com.exigen.poker.domain.ProjectEntity;
import com.exigen.poker.domain.RequirementEntity;
import com.exigen.poker.domain.UserEntity;
import com.exigen.poker.domain.base.GenericJPADAO;

public class ProjectJpaDao extends GenericJPADAO<ProjectEntity, Long> implements ProjectDao {

	private static final String QUERY_PROJECT = 
		"select projects from ProjectEntity projects where projects.projectName = :projectName";
	private static final String QUERY_PROJECT2 = "select projects from ProjectEntity projects";
	private static final String QUERY_PROJECT3 = "select * from users u join " +
			"(select * from project_experts pe where pe.project_id = :projectID) stuff on u.id = stuff.user_id";  
	private static final String QUERY_PROJECT4 = "select * from projects pr join " +
			"(select * from project_experts pe where pe.user_id = :givenUserID) stuff on pr.id = stuff.project_id";
	private static final String QUERY_PROJECT5 = "select * from project_experts pe where pe.user_id = :userID and pe.project_id = :projectID";
	
	public ProjectJpaDao() {
		super(ProjectEntity.class);
	}

	@SuppressWarnings("unchecked")
	public ProjectEntity getProjectByProjectName(String projectName) {
		
		ProjectEntity projectEntity = null;
		
		Query query = getEntityManager().createQuery(QUERY_PROJECT);
		query.setParameter("projectName", projectName);
		query.setMaxResults(1);
		
		List<ProjectEntity> result = query.getResultList();
		
		projectEntity = result.get(0);
		
		return projectEntity;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProjectEntity> getProjectlListDao(){
		
		Query query = getEntityManager().createQuery(QUERY_PROJECT2);
		
		List<ProjectEntity> result = query.getResultList();
		
		return result;
	}
	@SuppressWarnings("unchecked")
	public List<UserEntity> allProjectUsers(Long projectID) {
		Query query = getEntityManager().createNativeQuery(QUERY_PROJECT3, com.exigen.poker.domain.UserEntity.class);
		query.setParameter("projectID", projectID);
		
		List<UserEntity> result = (List<UserEntity>)query.getResultList();
		
		return result;
	}

	public ProjectEntity addUserDao(Long projectID, Long userID) {
		ProjectEntity currentProject = getEntityManager().find(ProjectEntity.class, projectID);
		UserEntity currentUser = getEntityManager().find(UserEntity.class, userID);
		currentProject.addUser(currentUser);
		return currentProject;//Here be dragons
	}

	@SuppressWarnings("unchecked")
	public List<ProjectEntity> userProjects(long givenUserID) {
		
		Query query = getEntityManager().createNativeQuery(QUERY_PROJECT4, com.exigen.poker.domain.ProjectEntity.class);

		query.setParameter("givenUserID", givenUserID);
		
		return query.getResultList();
	}

	public ProjectEntity removeProjExpert(UserEntity user, ProjectEntity currentProject) {
		currentProject = getEntityManager().find(ProjectEntity.class, currentProject.getId());
		user = getEntityManager().find(UserEntity.class, user.getId());
		currentProject.removeUser(user);
		return currentProject;
	}

	@SuppressWarnings("unchecked")
	public boolean expertExists(Long projectID, Long userID) {
		Query query = getEntityManager().createNativeQuery(QUERY_PROJECT5);

		query.setParameter("projectID", projectID);
		query.setParameter("userID", userID);
		
		
		List<String> result = query.getResultList();
		
		return !result.isEmpty();
	}

	public ProjectEntity addReq(ProjectEntity currentProject, RequirementEntity requirementEntity) {
		currentProject = getEntityManager().find(ProjectEntity.class, currentProject.getId());
		currentProject.addRequirement(requirementEntity);
		return currentProject;
	}
	
}
















