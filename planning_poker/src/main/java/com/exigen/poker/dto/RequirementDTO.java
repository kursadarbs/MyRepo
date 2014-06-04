package com.exigen.poker.dto;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

import com.exigen.poker.domain.EstimationEntity;
import com.exigen.poker.domain.ProjectEntity;
import com.exigen.poker.domain.RequirementEntity;
import com.exigen.poker.services.EstimationService;
import com.exigen.poker.services.ProjectService;
import com.exigen.poker.services.RequirementService;

public class RequirementDTO {
	
	private RequirementService requirementService;
	private EstimationService estimationService;
	private ProjectService projectService;
	
	private String requirementName;
	private String requirementDescription;
	private String requirementProjectID;

	private List<RequirementEntity> allProjReq;
	
	private RequirementEntity reqToDelete;
	private ProjectEntity currentProject;
	private EstimationEntity currentEst;
	
	public void addNewRequirement(ActionEvent event) {
		RequirementEntity requirement = new RequirementEntity();
		requirement.setRequirementDescription(requirementDescription);
		requirement.setRequirementName(requirementName);
		
		try {
			requirementService.createRequirementIfNotExist(currentProject,requirement);
			
			estimationService.createEstimations(projectService.allProjectUsers(currentProject.getId()), requirement);
			
			this.requirementDescription = null;
			this.requirementName = null;
			
			findAllProjReq();
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage("Requirement created Successfully!"));
			
			RequestContext.getCurrentInstance().execute("ReqDialog.hide()");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage(e.getMessage()));
		}
		
	}
	
	public void deleteRequirement(){
		try {
			requirementService.deleteRequirement(this.reqToDelete);
			findAllProjReq();
			
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage("Requirement deleted Successfully!"));
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage(e.getMessage()));
		}
	}
	
	public void findAllProjReq(){
		try {
			this.allProjReq = requirementService.findAllProjReq(currentProject);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage(e.getMessage()));
		}
	}
	
//---------------------------------------------------------------------------------------
	
	public RequirementService getRequirementService() {
		return requirementService;
	}

	public void setRequirementService(RequirementService requirementService) {
		this.requirementService = requirementService;
	}

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	public String getRequirementDescription() {
		return requirementDescription;
	}

	public void setRequirementDescription(String requirementDescription) {
		this.requirementDescription = requirementDescription;
	}

	public String getRequirementProjectID() {
		return requirementProjectID;
	}

	public void setRequirementProjectID(String requirementProjectID) {
		this.requirementProjectID = requirementProjectID;
	}

	public ProjectEntity getCurrentProject() {
		return currentProject;
	}


	public void setCurrentProject(ProjectEntity currentProject) {
		this.currentProject = currentProject;
	}


	public EstimationService getEstimationService() {
		return estimationService;
	}


	public void setEstimationService(EstimationService estimationService) {
		this.estimationService = estimationService;
	}


	public ProjectService getProjectService() {
		return projectService;
	}


	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public List<RequirementEntity> getAllProjReq() {
		return allProjReq;
	}

	public void setAllProjReq(List<RequirementEntity> allProjReq) {
		this.allProjReq = allProjReq;
	}

	public RequirementEntity getReqToDelete() {
		return reqToDelete;
	}

	public void setReqToDelete(RequirementEntity reqToDelete) {
		this.reqToDelete = reqToDelete;
	}

	public EstimationEntity getCurrentEst() {
		return currentEst;
	}

	public void setCurrentEst(EstimationEntity currentEst) {
		this.currentEst = currentEst;
	}

}
