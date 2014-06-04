package com.exigen.poker.services.impl;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.exigen.poker.domain.UserEntity;
import com.exigen.poker.services.UserService;

@ManagedBean
@RequestScoped
public class UserConverter implements Converter{

	   UserService userService;
	   
	   public Object getAsObject(FacesContext context, UIComponent component, String value){
	     try {
			return userService.getUsertById(value);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("defaultGrowl",
	                new FacesMessage(e.getMessage()));
		}
		return null;
	   }

	   public String getAsString(FacesContext context, UIComponent component, Object value)     
	   {
	     return ((UserEntity) value).getId().toString();
	   }

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	}