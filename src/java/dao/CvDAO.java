/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.Query; 
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Cv;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Daryna_Ragimova
 */
@Stateless
@LocalBean
public class CvDAO extends AbstractDAO<Cv> {
    private CriteriaBuilder cb;
    private CriteriaQuery query;
    private Root root;
    private Boolean vacancySorter, salarySorter, citySorter;
    
    private List<Predicate> criteriaList = new ArrayList<Predicate>();

    public List<Cv> findCvEntities() {
        return findCvEntities(true, -1, -1);
    }

    public List<Cv> findCvEntities(int maxResults, int firstResult) {
        return findCvEntities(false, maxResults, firstResult);
    }

    private List<Cv> findCvEntities(boolean all, int maxResults, int firstResult) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Cv.class));
        Query q = em.createQuery(cq);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();

    }

    public Cv findCv(Integer id) {
        return em.find(Cv.class, id);

    }

    @Override
    public void delete(Cv cv){
        Query q = em.createQuery("DELETE FROM Cv obj where obj = :toDelete");
        q.setParameter("toDelete", cv);
        q.executeUpdate();
    }
    
    public int getCvCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Cv> rt = cq.from(Cv.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();

    }

    @Override
    public Class<Cv> getEntityClass() {
        return Cv.class;
    }
    
    public void addSorter(String sortField,SortOrder sortOrder){
        if (sortField.equals("vacancy")){
            if (SortOrder.ASCENDING.equals(sortOrder))
                query.orderBy(cb.asc(root.get("vacancy")));
            else
                query.orderBy(cb.desc(root.get("vacancy")));
            }else{
                if (sortField.equals("salary")){
                    if (SortOrder.ASCENDING.equals(sortOrder))
                        query.orderBy(cb.asc(root.get("salary")));
                    else
                        query.orderBy(cb.desc(root.get("salary")));
                }
                else{
                    if (SortOrder.ASCENDING.equals(sortOrder))
                        query.orderBy(cb.asc(root.get("applicant").get("city").get("name")));
                    else
                        query.orderBy(cb.desc(root.get("applicant").get("city").get("name")));
                }
            }
    }
    public  int findByFilters(){
        cb = em.getCriteriaBuilder();
        query = cb.createQuery();
        root = query.from(Cv.class);
        Predicate cvStatus = cb.equal(root.get("status"), 1);
        criteriaList.add(cvStatus);
        query.select(root);
        query.where(cb.and(criteriaList.toArray(new Predicate[0])));
        TypedQuery tQuery = em.createQuery(query);
        return tQuery.getResultList().size();
}
    
    public List<Cv> find(int maxResults, int firstResult){
        TypedQuery tQuery = em.createQuery(query);
        tQuery.setMaxResults(maxResults);
        tQuery.setFirstResult(firstResult);
        criteriaList = new ArrayList<Predicate>();
        return tQuery.getResultList();
    }
    
    public void addFilterSalary(double salary){
        Predicate bgSalary = cb.ge(root.get("salary"),salary);
        criteriaList.add(bgSalary);
    }
    public void addFilterVacancy(String vacancy){
        Predicate cvName = cb.like(cb.upper(root.get("vacancy")), vacancy.toUpperCase() + "%");
        criteriaList.add(cvName);
    }
    public void addFilterCity(String city){
        Predicate cityId = cb.like(cb.upper(root.get("applicant").get("city").get("name")), city.toUpperCase() + "%");
        criteriaList.add(cityId);
    }
}
