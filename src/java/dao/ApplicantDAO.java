/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Applicant; 
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

/**
 *
 * @author Daryna_Ragimova
 */
@Stateless
@LocalBean
public class ApplicantDAO extends AbstractDAO<Applicant> {

    public Applicant findApplicant(Integer id) {

        return em.find(Applicant.class, id);

    }

    public Applicant findApplicantByUserId(Integer userId) {
        TypedQuery<Applicant> query = em.createNamedQuery("Applicant.findByUserId", Applicant.class);
        return query.setParameter("iduser", userId).getSingleResult();

    }

    @Override
    public Class<Applicant> getEntityClass() {
        return Applicant.class;
    }

}
