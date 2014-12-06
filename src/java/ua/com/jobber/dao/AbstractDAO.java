/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ua.com.jobber.dao;

/**
 *
 * @author Daryna_Ragimova
 */
import java.util.List; 

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


@Stateless
@LocalBean
public abstract class AbstractDAO<T> {

	@PersistenceContext(unitName = "SearchJobWebPU")
	EntityManager em;

	public abstract Class<T> getEntityClass();

	public T create(T entity) {
		em.persist(entity);
		return entity;
	}

	public void delete(T entity) {
		em.remove(em.merge(entity));
        }

	public T find(Long id) {
		return em.find(getEntityClass(), id);
	}

	public List<T> findAll() {
		TypedQuery<T> query = em.createNamedQuery(getEntityClass().getSimpleName()
				+ ".findAll", getEntityClass());
		return query.getResultList();
	}

	public T update(T entity) {
		return em.merge(entity);
	}

}