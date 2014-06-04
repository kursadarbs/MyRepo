package com.exigen.poker.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

import com.exigen.poker.domain.base.BaseEntity;


@Entity
@Table(name="projects")
public class ProjectEntity extends BaseEntity{
	private static final long serialVersionUID = -8407922234275444965L;

	@Column(name = "pr_name", unique=true)
	@Size(min=1, max=127)
	private String projectName;
	
	//@Lob @Basic(fetch=FetchType.EAGER)
	@Column(name = "pr_description")//, length=8190)  
	//@Size(max=8190) 
	private String projectDescription;
	
	@OneToMany(targetEntity=RequirementEntity.class, mappedBy="project",
			fetch=FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.REMOVE})
	private  List <RequirementEntity> requirements;
	
	@ManyToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="project_experts", 
    		joinColumns=@JoinColumn(name="project_id"),
    		inverseJoinColumns=@JoinColumn(name="user_id"))
    private Collection<UserEntity> projectUsers;

	public void addUser(UserEntity user) {	
	    if (!getProjectUsers().contains(user)) {
	    	getProjectUsers().add(user);
	    }
	}
	
	public void removeUser(UserEntity user) {	
	    if (getProjectUsers().contains(user)) {
	    	getProjectUsers().remove(user);
	    }
	    
	    if (user.getUserProjects().contains(this)) {
	    	user.getUserProjects().remove(this);
	    }
	}
	
	public void addRequirement(RequirementEntity requirement) {
        if (!getRequirements().contains(requirement)) {
        	getRequirements().add(requirement);
            if (requirement.getProject() != null) {
            	requirement.getProject().getRequirements().remove(requirement);
            }
            requirement.setProject(this);
        }
    }
	
	public Collection<UserEntity> getProjectUsers() {
		return projectUsers;
	}

	public void setProjectUsers(Collection<UserEntity> projectUsers) {
		this.projectUsers = projectUsers;
	}
	
	public List<RequirementEntity> getRequirements() {
		return requirements;
	}
	public void setRequirements(List<RequirementEntity> requirements) {
		this.requirements = requirements;
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
	
}
