package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.Organization;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
        
        //No param, we get them all
        if(idUser == null)
            q = em.createNamedQuery(Organization.GET_ALL_ORGANIZATIONS);
        
        // We get the organization of the given user.
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
