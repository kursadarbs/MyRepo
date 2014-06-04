package com.exigen.poker.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.exigen.poker.domain.base.BaseEntity;

@Entity
@Table(name="users")
public class UserEntity extends BaseEntity {
	private static final long serialVersionUID = -2816373868776366762L;
	
	@Column(name = "first_name")
	@Size(min=1, max=20)
	private String userFirstName;
	
	@Column(name = "last_name")
	@Size(min=1, max=20)
	private String userLastName;
	
	@Column(name = "user_login", unique=true)
	@Size(min=4, max=30)
	private String userLogin;
	
	@Column(name = "password")
	@Size(min=6, max=255)
	private String userPassword;
	
	@Column(name = "is_admin")
	private Boolean isAdmin;
	
	@ManyToMany (cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name="project_experts", 
	      joinColumns=@JoinColumn(name="user_id"),
	      inverseJoinColumns=@JoinColumn(name="project_id"))  
	 private Collection<ProjectEntity> userProjects;

	@OneToMany(targetEntity=EstimationEntity.class, mappedBy="user",
			cascade=CascadeType.MERGE, fetch=FetchType.EAGER)
	private  List <EstimationEntity> estimations;
	
	public void addEstimation(EstimationEntity estimation) {
		if (getEstimations()==null) {setEstimations(new ArrayList<EstimationEntity>());}
	    if (!getEstimations().contains(estimation)) {
	    	getEstimations().add(estimation);
	    }
	    estimation.setUser(this);
	 }
	
	@OneToMany(targetEntity=EstimationHEntity.class, mappedBy="user",
			cascade=CascadeType.MERGE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private  List <EstimationHEntity> estimationsH;
	
	public void addEstimationH(EstimationHEntity estimationH) {
		if (getEstimationsH()==null) {setEstimationsH(new ArrayList<EstimationHEntity>());}
	    if (!getEstimationsH().contains(estimationH)) {
	    	getEstimationsH().add(estimationH);
	    }
	    estimationH.setUser(this);
	 }
	
	public boolean equals (Object obj) {
		if ((obj == null) || !(obj instanceof UserEntity))
            return false;
		
		if (obj == this)
		      return true;
		
        if (((UserEntity) obj).getId().equals(getId())) return true;
        
        return false;
    }
	
	public int hashCode() {
        return 3 * getId().hashCode();
    }
	
	public List<EstimationEntity> getEstimations() {
		return estimations;
	}

	public void setEstimations(List<EstimationEntity> estimations) {
		this.estimations = estimations;
	}

	public Collection<ProjectEntity> getUserProjects() {
		return userProjects;
	}

	public void setUserProjects(Collection<ProjectEntity> userProjects) {
		this.userProjects = userProjects;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getUserFirstName() {
		return userFirstName;
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

	public List<EstimationHEntity> getEstimationsH() {
		return estimationsH;
	}

	public void setEstimationsH(List<EstimationHEntity> estimationsH) {
		this.estimationsH = estimationsH;
	}

	
}
