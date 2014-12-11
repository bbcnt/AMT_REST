/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.Organization;
import ch.heigvd.amt_rest.model.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author brito_000
 */
@Stateless
public class UserManager implements UserManagerLocal {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public User findUser(long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findUsers(Long idOrg) {
        
        Query q;
        if(idOrg == null)
            q = em.createNamedQuery(User.GET_ALL_USERS);
        else
        {
            q = em.createNamedQuery(User.GET_ALL_USERS_ORG);
            q.setParameter("idOrg", idOrg);
        }
        return q.getResultList();
                                                      
    }

    @Override
    public long createUser(User u) {
        em.persist(u);
        if(em.find(Organization.class, u.getOrganization().getId()).getContact() == null){
            em.find(Organization.class, u.getOrganization().getId()).setContact(u);
        }
        em.flush();
        return u.getId();
    }

    @Override
    public void deleteUser(long id) {
        em.remove(em.find(User.class, id));
    }

    @Override
    public void updateUser(User u) {
        em.merge(u);
    }

  
}
