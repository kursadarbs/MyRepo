package com.exigen.poker.dto;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;

import com.exigen.poker.domain.ProjectEntity;
import com.exigen.poker.domain.RequirementEntity;
import com.exigen.poker.domain.UserEntity;
import com.exigen.poker.services.EstimationService;
import com.exigen.poker.services.ProjectService;
import com.exigen.poker.services.UserService;

public class ProjectDTO {
	
	private ProjectService projectService;
	private EstimationService estimationService;
	private UserService userService;
	
	private String projectName;
	private String projectDescription;
	
	private ProjectEntity currentProject;
	private RequirementEntity currentRequirement;
	private ProjectEntity projectToDelete;

	private List<UserEntity> projectUsers;
	private List<ProjectEntity> allProjects;
	private List<ProjectEntity> filteredProjects;
	private DualListModel<UserEntity> userDual;
	List<UserEntity> source;
	List<UserEntity> target;

	public void addNewProject(ActionEvent event) {
		ProjectEntity project = new ProjectEntity();
		project.setProjectDescription(projectDescription);
		project.setProjectName(projectName);
		project.setValue(0);
		
		try {
			projectService.createProjectIfNotExist(project);
			
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage("Project ceated successfully!"));
			
			findAllProjects();
			RequestContext.getCurrentInstance().execute("ProjDialog.hide()");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage(e.getMessage()));
		}
		
		this.projectDescription = null;
		this.projectName = null;
		
	}
	
	public void addNewUser(UserEntity currentUser) throws Exception {
		try {
			
			currentProject = projectService.addProjectExpertIfNotExist(this.currentProject.getId(), currentUser.getId());
			
			estimationService.createEstimationsUserAdd(currentProject, currentUser);
			
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage("User added successfully!"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage(e.getMessage()));
		}
		
	}
	
	public void removeProjExpert(UserEntity user){
		try {
			estimationService.deleteProjectExpertEstimations(user, currentProject);
			this.currentProject = projectService.removeProjExpert(user, currentProject);
			
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage("User removed successfully!"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage(e.getMessage()));
		}
	}
	
	public void deleteProject(){
		try {
			projectService.deleteProject(this.projectToDelete);
			
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage("Project deleted succesfully"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage(e.getMessage()));
		}
	}
	
	public void findProjectUsers(){
		try {
			this.projectUsers = getProjectService().allProjectUsers(getCurrentProject().getId());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage(e.getMessage()));
		}
	}
	
	public void buildDualList(){
		try {
			findProjectUsers();
			target = projectUsers;
			source = userService.getUserlList();

			source.removeAll(target);
			
			userDual.setSource(source);
			userDual.setTarget(target);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage(e.getMessage()));
		}
	}
	
	public void submitLists(){
		try {
			for (UserEntity user : userDual.getTarget()){
				if (!target.contains(user)){
					addNewUser(user);
					target.add(user);
					source.remove(user);
				}
			}
			
			for (UserEntity user : userDual.getSource()){
				if (!source.contains(user)){
					removeProjExpert(user);
					source.add(user);
					target.remove(user);
				}
			}
			
			FacesContext.getCurrentInstance().addMessage("defaultmessages",
	                new FacesMessage("Project Experts updated succesfully"));
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultmessages",
	                new FacesMessage(e.getMessage()));
		}
	}
	
	public void findAllProjects(){
		try {
			this.allProjects = projectService.getProjectlList();
			this.filteredProjects = this.allProjects;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage(e.getMessage()));
		}
	}
	
	public void editProject() throws Exception{
		try {
			projectService.editProject(currentProject);
			
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
					new FacesMessage("Project updated succesfully!"));
			
			RequestContext.getCurrentInstance().execute("ProjDialog.hide()");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage(e.getMessage()));
		}
	}
	
	//----------------------------------------------------------------------------------------
	
	public List<UserEntity> getProjectUsers() {
		return this.projectUsers;
	}

	public void setProjectUsers(List<UserEntity> projectUsers) {
		this.projectUsers = projectUsers;
	}

	public void setCurrentProject(ProjectEntity projectEntity) {
		this.currentProject = projectEntity;
	}
	
	public List<ProjectEntity> getAllProjects() {
		return allProjects;
	}

	public void setAllProjects(List<ProjectEntity> allProjects) {
		this.allProjects = allProjects;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public ProjectEntity getCurrentProject() {
		return currentProject;
	}

	public RequirementEntity getCurrentRequirement() {
		return currentRequirement;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setCurrentRequirement(RequirementEntity currentRequirement) {
		this.currentRequirement = currentRequirement;
	}

	public EstimationService getEstimationService() {
		return estimationService;
	}

	public void setEstimationService(EstimationService estimationService) {
		this.estimationService = estimationService;
	}

	public List<ProjectEntity> getFilteredProjects() {
		return filteredProjects;
	}

	public void setFilteredProjects(List<ProjectEntity> filteredProjects) {
		this.filteredProjects = filteredProjects;
	}

	public ProjectEntity getProjectToDelete() {
		return projectToDelete;
	}

	public void setProjectToDelete(ProjectEntity projectToDelete) {
		this.projectToDelete = projectToDelete;
	}

	public DualListModel<UserEntity> getUserDual() {
		if (userDual == null) userDual = new DualListModel<UserEntity>(new ArrayList<UserEntity>(), new ArrayList<UserEntity>());
		return userDual;
	}

	public void setUserDual(DualListModel<UserEntity> userDual) {
		this.userDual = userDual;
	}
	
}
