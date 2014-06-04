package com.exigen.poker.domain.base;

import java.io.Serializable;
import java.util.List;

/**
 * Generic interface for data access objects containing main methods
 * 
 * @author Arturs Vinogradovs
 */
public interface GenericDAO<T, ID extends Serializable> {
	
	T save(T entity);
	T update(T entity);
	void delete(T entity);
	
	T findById(ID id);
	List<T> findAll();
	
	void flush();
	
}
