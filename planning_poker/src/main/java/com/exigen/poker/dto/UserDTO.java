package com.exigen.poker.dto;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.exigen.poker.domain.EstimationEntity;
import com.exigen.poker.domain.EstimationHEntity;
import com.exigen.poker.domain.ProjectEntity;
import com.exigen.poker.domain.RequirementEntity;
import com.exigen.poker.domain.UserEntity;
import com.exigen.poker.services.EstimationHService;
import com.exigen.poker.services.EstimationService;
import com.exigen.poker.services.ProjectService;
import com.exigen.poker.services.RequirementService;
import com.exigen.poker.services.UserService;

public class UserDTO implements Serializable{

	private static final long serialVersionUID = -9195107974946407107L;
	
	private UserService userService;
	private EstimationService estimationService;
	private AuthenticationManager authenticationManager;
	private ProjectService projectService;
	private EstimationHService estimationHService;
	private RequirementService requirementService;

	//private List<UserEntity> allUsers; // not used and also not optimized
	private List<EstimationEntity> allUserEstimations;
	private List<EstimationEntity> reqEstimations;
	private List<ProjectEntity> userProjects;
	private List<EstimationHEntity> estHistory;

	private RequirementEntity currentRequirement;
	private EstimationEntity currentEstimation;

	private String userFirstName;
	private String userLastName;
	private String userLogin;
	private String userLoginCheck;
	private String userPassword;

	private Integer value;
	private String comment;

	public void addNewUser(ActionEvent event) {
		UserEntity user = new UserEntity();
		user.setUserFirstName(userFirstName);
		user.setUserLastName(userLastName);
		user.setUserLogin(userLogin);
		user.setUserPassword(userPassword);
		user.setIsAdmin(false);

		try {
			userService.createUserIfNotExist(user);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Register Successful! You can log in."));
			RequestContext.getCurrentInstance().execute("RegDialog.hide()");
			RequestContext.getCurrentInstance().update(":formMain");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}

	public void userAuthentication(ActionEvent event) throws Exception {
		try {
			
			Authentication request = new UsernamePasswordAuthenticationToken(
					this.userLogin, this.userPassword);

			Authentication result = authenticationManager.authenticate(request);

			SecurityContextHolder.getContext().setAuthentication(result);
		} catch (AuthenticationException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Username and/or password incorrect"));
			return;
		}

		this.userLoginCheck = this.userLogin;
		
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		try {
			if (userService.UserType(this.userLogin))
				context.redirect("/poker/app/account");
			else
				context.redirect("/poker/app/admin");
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Login successful!"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}

	public void rateEstimation(ActionEvent event) {
		try {
			EstimationEntity est = estimationService.getEstimation(
					userService.getUserByUsername(userLogin),
					this.currentRequirement);

			est.setComment(comment);
			est.setHasRated(true);

			if (est.getValue() != null) {
				if (!est.getValue().equals(this.value)) {
					est.setUpdated(true);
				}
			}

			est.setValue(value);
			estimationHService.putInHistory(est);
			estimationService.updateEstimation(est);
			
			/*if (est.getValue() != null) { //old version
				if (!est.getValue().equals(this.value)) {
					est.setValue(value);
					estimationHService.putInHistory(est);
					est.setUpdated(true);
				}
			} else {
				est.setValue(value);
				estimationHService.putInHistory(est);
			}

			estimationService.updateEstimation(est);*/

			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
					new FacesMessage("Estimation rated successfully!"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
					new FacesMessage(e.getMessage()));
		}
	}

	public void estimationStuff(RequirementEntity req) {
		try {
			currentEstimation = estimationService.getEstimation(
					userService.getUserByUsername(userLogin), req);
			this.value = currentEstimation.getValue();
			this.comment = currentEstimation.getComment();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
					new FacesMessage(e.getMessage()));
		}
	}

	public void resetEstimations() {
		try {
			estimationService.resetEstimations(getReqEstimations());
			
			requirementService.incRound(currentRequirement);
			
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
					new FacesMessage("Estimations reset succesfull"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
					new FacesMessage(e.getMessage()));
		}
	}

	public void FindUserProjects() {
		try {
			this.userProjects = projectService.userProjects(userService
					.givenUserID(this.userLogin));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
					new FacesMessage(e.getMessage()));
		}
	}

	public Boolean hasUserRated() {
		try {
			return estimationService.getEstimation(
					userService.getUserByUsername(userLogin),
					this.currentRequirement).getHasRated();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
					new FacesMessage(e.getMessage()));
			return false;
		}
	}

	public void userFindReqEstimations() {
		try {
			if (hasUserRated())
				findReqEstimations();
			else
				this.reqEstimations = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
					new FacesMessage(e.getMessage()));
		}
	}

	public void findAllUserEstimations() {// Not used
		try {
			this.setAllUserEstimations(userService
					.UserEstimationList(this.userLogin));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
					new FacesMessage(e.getMessage()));
		}
	}

	public void findReqEstimations() {
		try {
			this.reqEstimations = estimationService
					.ReqEstimations(currentRequirement);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
					new FacesMessage(e.getMessage()));
		}
	}

	public void findEstHistory() {
		try {
			this.estHistory = estimationHService
					.findEstHistory(currentRequirement);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
					new FacesMessage(e.getMessage()));
		}
	}

	public List<UserEntity> getAllUsers() { // not used and also not optimized
		try {
			return userService.getUserlList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
					new FacesMessage(e.getMessage()));
			return null;
		}
	}
	
	public void editRequirement() throws Exception{
		try {
			requirementService.editRequirement(currentRequirement);
			
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
					new FacesMessage("Requirement updated succesfully!"));
			
			RequestContext.getCurrentInstance().execute("ReqEDialog.hide()");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
					new FacesMessage(e.getMessage()));
		}
	}

	/*public void setAllUsers(List<UserEntity> allUsers) { // not used
		this.allUsers = allUsers;
	}*/

	// -----------------------------------------------------------------------------------------

	public List<ProjectEntity> getUserProjects() {
		return this.userProjects;
	}

	public void setUserProjects(List<ProjectEntity> userProjects) {
		this.userProjects = userProjects;
	}

	public List<EstimationEntity> getReqEstimations() {
		return reqEstimations;
	}

	public void setReqEstimations(List<EstimationEntity> reqEstimations) {
		this.reqEstimations = reqEstimations;
	}

	public List<EstimationEntity> getAllUserEstimations() {
		return allUserEstimations;
	}

	public void setAllUserEstimations(List<EstimationEntity> allUserEstimations) {
		this.allUserEstimations = allUserEstimations;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public EstimationHService getEstimationHService() {
		return estimationHService;
	}

	public void setEstimationHService(EstimationHService estimationHService) {
		this.estimationHService = estimationHService;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public ProjectService getProjectService() {
		return projectService;
	}

	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}

	public RequirementEntity getCurrentRequirement() {
		return currentRequirement;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public EstimationService getEstimationService() {
		return estimationService;
	}

	public void setEstimationService(EstimationService estimationService) {
		this.estimationService = estimationService;
	}

	public void setCurrentRequirement(RequirementEntity currentRequirement) {
		this.currentRequirement = currentRequirement;
	}

	public EstimationEntity getCurrentEstimation() {
		return currentEstimation;
	}

	public void setCurrentEstimation(EstimationEntity currentEstimation) {
		this.currentEstimation = currentEstimation;
	}

	public List<EstimationHEntity> getEstHistory() {
		return estHistory;
	}

	public void setEstHistory(List<EstimationHEntity> estHistory) {
		this.estHistory = estHistory;
	}

	public String getUserLoginCheck() {
		return userLoginCheck;
	}

	public void setUserLoginCheck(String userLoginCheck) {
		this.userLoginCheck = userLoginCheck;
	}

	public RequirementService getRequirementService() {
		return requirementService;
	}

	public void setRequirementService(RequirementService requirementService) {
		this.requirementService = requirementService;
	}

}
