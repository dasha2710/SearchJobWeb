/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.com.jobber.dao;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ua.com.jobber.entities.Vacancy;
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
public class VacancyDAO extends AbstractDAO<Vacancy> {

    private CriteriaBuilder cb;
    private CriteriaQuery query;
    private Root root;

    private List<Predicate> criteriaList = new ArrayList<Predicate>();

    public VacancyDAO() {
    }

    @Override
    public void delete(Vacancy vacancy) {
        Query q = em.createQuery("DELETE FROM Vacancy obj where obj = :toDelete");
        q.setParameter("toDelete", vacancy);
        q.executeUpdate();
    }

    public List<Vacancy> findVacancyEntities() {
        return findVacancyEntities(true, -1, -1);
    }

    public List<Vacancy> findVacancyEntities(int maxResults, int firstResult) {
        return findVacancyEntities(false, maxResults, firstResult);
    }

    private List<Vacancy> findVacancyEntities(boolean all, int maxResults, int firstResult) {
        Query q = em.createQuery(query);
        if (!all) {
            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);
        }
        return q.getResultList();

    }

    public Vacancy findVacancy(Integer id) {
        return em.find(Vacancy.class, id);

    }

    public int getVacancyCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Vacancy> rt = cq.from(Vacancy.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public int findByFilters() {
        cb = em.getCriteriaBuilder();
        query = cb.createQuery();
        root = query.from(Vacancy.class);
        Predicate vacancyName = cb.equal(root.get("status"), 1);
        criteriaList.add(vacancyName);
        query.select(root);
        query.where(cb.and(criteriaList.toArray(new Predicate[0])));
        TypedQuery tQuery = em.createQuery(query);
        return tQuery.getResultList().size();

    }

    public List<Vacancy> find(int maxResults, int firstResult) {
        TypedQuery tQuery = em.createQuery(query);
        tQuery.setMaxResults(maxResults);
        tQuery.setFirstResult(firstResult);
        criteriaList = new ArrayList<Predicate>();
        return tQuery.getResultList();
    }

    public void addSorter(String sortField, SortOrder sortOrder) {
        if (sortField.equals("name")) {
            if (SortOrder.ASCENDING.equals(sortOrder)) {
                query.orderBy(cb.asc(root.get("name")));
            } else {
                query.orderBy(cb.desc(root.get("name")));
            }
        } else {
            if (sortField.equals("salary")) {
                if (SortOrder.ASCENDING.equals(sortOrder)) {
                    query.orderBy(cb.asc(root.get("salary")));
                } else {
                    query.orderBy(cb.desc(root.get("salary")));
                }
            } else {
                if (SortOrder.ASCENDING.equals(sortOrder)) {
                    query.orderBy(cb.asc(root.get("city").get("name")));
                } else {
                    query.orderBy(cb.desc(root.get("city").get("name")));
                }
            }
        }
    }

    public void addFilterSalary(double salary) {
        Predicate bgSalary = cb.ge(root.get("salary"), salary);
        criteriaList.add(bgSalary);
    }

    public void addFilterVacancy(String vacancy) {
        Predicate vacancyName = cb.like(cb.upper(root.get("name")), vacancy.toUpperCase() + "%");
        criteriaList.add(vacancyName);
    }

    public void addFilterCity(String city) {
        Predicate cityId = cb.like(cb.upper(root.get("city").get("name")), city.toUpperCase() + "%");
        criteriaList.add(cityId);
    }

    @Override
    public Class<Vacancy> getEntityClass() {
        return Vacancy.class;
    }

}
