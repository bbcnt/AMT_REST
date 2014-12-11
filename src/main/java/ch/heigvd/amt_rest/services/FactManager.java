/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.Fact;
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
public class FactManager implements FactManagerLocal {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Fact findFact(long id) {
        return em.find(Fact.class, id);
    }

    @Override
    public List<Fact> findFacts(Long idOrg, Long idSen) {
        
        Query q;
        if(idOrg == null && idSen == null)
            q = em.createNamedQuery(Fact.GET_ALL_FACTS);
        else if(idSen == null && idOrg != null){
            q = em.createNamedQuery(Fact.GET_ALL_FACTS_ORG);
            q.setParameter("idOrg", idOrg);
        }
        else if(idSen != null && idOrg == null){
            q = em.createNamedQuery(Fact.GET_TOTAL_OBS_SENSOR);
            q.setParameter("idSen", idSen);
        }
        else
            return null;
        return q.getResultList();
    }

    @Override
    public long createFact(Fact f) {
        em.persist(f);
        em.flush();
        return f.getId();
    }


    @Override
    public void deleteFact(long id) {
        em.remove(em.find(Fact.class, id));
    }

    @Override
    public void updateFact(Fact f) {
        em.merge(f);
    }

}
