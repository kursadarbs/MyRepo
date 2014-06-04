package com.exigen.poker.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.exigen.poker.domain.base.BaseEntity;

@Entity
@Table(name="Requirements")
public class RequirementEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "name")
	@Size(min=1, max=127)
	private String requirementName;
	
	//@Lob @Basic(fetch=FetchType.EAGER)
	@Column(name = "description", length=4090)//, length=4090)
	//@Size(max=4090)
	private String requirementDescription;
	
	@JoinColumn(name="project_id")
	@ManyToOne
	@NotNull
	private ProjectEntity project;
	
	@OneToMany(targetEntity=EstimationEntity.class, mappedBy="requirement",
			cascade={CascadeType.MERGE, CascadeType.REMOVE})
	@LazyCollection(LazyCollectionOption.FALSE)
	private  List <EstimationEntity> Estimations;

	public void addEstimation(EstimationEntity estimation) {
		if (getEstimations()==null) {setEstimations(new ArrayList<EstimationEntity>());}
	    if (!getEstimations().contains(estimation)) {
	    	getEstimations().add(estimation);
	    }
	    estimation.setRequirement(this);
	 }
	
	@OneToMany(targetEntity=EstimationHEntity.class, mappedBy="requirement",
			cascade={CascadeType.MERGE, CascadeType.REMOVE})
	@LazyCollection(LazyCollectionOption.FALSE)
	private  List <EstimationHEntity> EstimationsH;

	public void addEstimationH(EstimationHEntity estimationH) {
		if (getEstimationsH()==null) {setEstimationsH(new ArrayList<EstimationHEntity>());}
	    if (!getEstimationsH().contains(estimationH)) {
	    	getEstimationsH().add(estimationH);
	    }
	    estimationH.setRequirement(this);
	 }
	
	public List<EstimationEntity> getEstimations() {
		return Estimations;
	}

	public void setEstimations(List<EstimationEntity> estimations) {
		Estimations = estimations;
	}

	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
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

	public List<EstimationHEntity> getEstimationsH() {
		return EstimationsH;
	}

	public void setEstimationsH(List<EstimationHEntity> estimationsH) {
		EstimationsH = estimationsH;
	}
	
}
