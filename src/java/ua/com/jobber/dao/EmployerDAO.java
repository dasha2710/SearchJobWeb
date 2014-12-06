/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.jobber.dao;


import ua.com.jobber.entities.Employer;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import javax.persistence.TypedQuery;

/**
 *
 * @author Daryna_Ragimova
 */
@Stateless
@LocalBean
public class EmployerDAO extends AbstractDAO<Employer> {

    public Employer findEmployer(Integer id) {
        return em.find(Employer.class, id);

    }

    public Employer findEmployerByUserId(Integer userId) {
        TypedQuery<Employer> query = em.createNamedQuery("Employer.findByUserId", Employer.class);
        return query.setParameter("iduser", userId).getSingleResult();

    }

    public Object findEmployerByName(String name) {
        TypedQuery<Employer> query = em.createNamedQuery("Employer.findByName", Employer.class);
        return query.setParameter("name", name).getSingleResult();
    }

    @Override
    public Class<Employer> getEntityClass() {
        return Employer.class;
    }

    
}
