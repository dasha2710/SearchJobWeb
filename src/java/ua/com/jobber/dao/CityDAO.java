/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.jobber.dao;

import ua.com.jobber.entities.City; 

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import javax.persistence.Query;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Daryna_Ragimova
 */
@Stateless
@LocalBean
public class CityDAO extends AbstractDAO<City> {

    public List<City> findCityEntities() {
        return findCityEntities(true, -1, -1);
    }

    public List<City> findCityEntities(int maxResults, int firstResult) {
        return findCityEntities(false, maxResults, firstResult);
    }

    private List<City> findCityEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(City.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();

    }

    public City findCity(Integer id) {
        return em.find(City.class, id);

    }

    public City findCity(String name) {
        TypedQuery<City> query = em.createNamedQuery("City.findByName", City.class);
        query.setParameter("name", name);
        return query.getSingleResult();

    }

    public int getCityCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<City> rt = cq.from(City.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();

    }

    @Override
    public Class<City> getEntityClass() {
        return City.class;
    }

}
