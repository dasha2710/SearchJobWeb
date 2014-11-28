/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import entities.User;
import javax.ejb.LocalBean;
import javax.ejb.Stateless; 
import javax.persistence.Query;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Daryna_Ragimova
 */
@Stateless
@LocalBean
public class UserDAO extends AbstractDAO<User> {

    public User findUser(Integer id) {
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public User findUser(String login) {
        try {
            TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
            query.setParameter("email", login);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findUser(String login, String password) {
        try {
            TypedQuery<User> query = em.createNamedQuery("User.findByEmailAndPassword", User.class);
            query.setParameter("email", login).setParameter("password", password);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public int getUserCount() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<User> rt = cq.from(User.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();

    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

}
