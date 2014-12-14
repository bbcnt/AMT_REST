/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.Fact;
import ch.heigvd.amt_rest.model.Sensor;
import java.util.Date;
import java.util.LinkedList;
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
    public List<Fact> findFacts(Long idOrg, Long idSen, Date ldate) {
        
        Query q;
        
        //If no param were specified
        if(idOrg == null && idSen == null && ldate == null){
            q = em.createNamedQuery(Fact.GET_ALL_FACTS);
            return q.getResultList();
        }
            
        //Only an organization id was specified
        else if(idSen == null && idOrg != null && ldate == null){
            q = em.createNamedQuery(Fact.GET_ALL_FACTS_ORG);
            q.setParameter("idOrg", idOrg);
        }
        
        //Only a sensor id was specified
        else if(idSen != null && idOrg == null && ldate == null){
            q = em.createNamedQuery(Fact.GET_TOTAL_OBS_SENSOR);
            q.setParameter("idSen", idSen);
            q.setParameter("sensorType", em.find(Sensor.class, idSen).getType());
            q.setParameter("ftype", Fact.COUNTER);
            
            Long id = (Long) q.getSingleResult();
            List<Fact> list = new LinkedList<>();
            list.add(em.find(Fact.class, id));
            return list;
        }
        
        //A sensor id and a date were specified
        else if(idSen != null && idOrg == null && ldate != null){
            q = em.createNamedQuery(Fact.GET_FACTS_BY_DATE);
            q.setParameter("idSen", idSen);
            q.setParameter("dateToShow", ldate);
            q.setParameter("ftype", Fact.DATE_COUNTER);
            q.setParameter("sensorType", em.find(Sensor.class, idSen).getType());
            
            Long id = (Long) q.getSingleResult();
            List<Fact> list = new LinkedList<>();
            list.add(em.find(Fact.class, id));
            return list;
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
