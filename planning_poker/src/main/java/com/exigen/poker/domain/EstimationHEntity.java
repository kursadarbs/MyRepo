package com.exigen.poker.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.exigen.poker.domain.base.BaseEntity;


@Entity
@Table(name="estimations_history")
public class EstimationHEntity extends BaseEntity{
	private static final long serialVersionUID = -3792567989109638415L;

	@Column(name = "est_value")
	@NotNull
	private Integer value;
	
	@Column(name = "est_comment")
	@Size(max=500)
	private String comment;
	
	@Column(name = "update_time")
	private Timestamp updateTime;
	
	@JoinColumn(name="user_id")
	@ManyToOne
	private UserEntity user;
	
	@JoinColumn(name="requirement_id")
	@ManyToOne
	private RequirementEntity requirement;

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
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

	public RequirementEntity getRequirement() {
		return requirement;
	}

	public void setRequirement(RequirementEntity requirement) {
		this.requirement = requirement;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}
