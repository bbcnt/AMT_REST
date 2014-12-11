/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.Organization;
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
public class OrganizationManager implements OrganizationManagerLocal {

    
    @PersistenceContext
    private EntityManager em;
        
    @Override
    public Organization findOrganization(long id) {
        return em.find(Organization.class, id);
    }

    @Override
    public List<Organization> findOrganizations(Long idUser) {
        
        Query q;
        if(idUser == null)
            q = em.createNamedQuery(Organization.GET_ALL_ORGANIZATIONS);
        else
        {
            q = em.createNamedQuery(Organization.GET_ALL_ORGANIZATIONS_USER);
            q.setParameter("idUser", idUser);
        }
        return q.getResultList();
    }

    @Override
    public long createOrganization(Organization o) {
        em.persist(o);
        em.flush();
        return o.getId();
    }

    @Override
    public void deleteOrganization(long id) {
        em.remove(em.find(Organization.class, id));
    }

    @Override
    public void updateOrganization(Organization o) {
        em.merge(o);
    }

}
