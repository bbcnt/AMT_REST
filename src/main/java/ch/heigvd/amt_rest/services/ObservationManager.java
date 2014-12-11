/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.Fact;
import ch.heigvd.amt_rest.model.Observation;
import java.sql.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.joda.time.LocalDate;

/**
 *
 * @author brito_000
 */
@Stateless
public class ObservationManager implements ObservationManagerLocal {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Observation findObservation(long id) {
        return em.find(Observation.class, id);
    }

    @Override
    public List<Observation> findObservations(Long idSen, Long idOrg) {
            
        Query q;
        
        if(idSen == null && idOrg == null)
            q = em.createNamedQuery(Observation.GET_ALL_OBSERVATIONS);
        else if(idOrg != null){
            q = em.createNamedQuery(Observation.GET_ALL_OBSERVATIONS_ORGANIZATION);
            q.setParameter("idOrg", idOrg);    
        }
        //We assume here that t
        else if(idSen != null){
            q = em.createNamedQuery(Observation.GET_ALL_OBSERVATIONS_SENSOR);
            q.setParameter("idSen", idSen);
        }
        else
            return null;
        return q.getResultList();
    }

    @Override
    public long createObservation(Observation o) {
        em.persist(o);
        
        createFactCounter(o);
        createFactDate(o);
        em.flush();
        return o.getId();
    }   

    @Override
    public void updateObservation(Observation o) {
        em.merge(o);
    }

    @Override
    public void deleteObservation(long id) {
        em.remove(em.find(Observation.class, id));
    }
    
    private void createFactCounter(Observation o){
        Query q;
        q = em.createNamedQuery(Fact.GET_TOTAL_OBS_SENSOR)
                .setParameter("ftype", Fact.COUNTER)
                .setParameter("idSen", o.getSensor().getId())
                .setParameter("sensorType", o.getSensor().getType());
        
        if(q.getResultList().isEmpty()){
            Fact f = new Fact();
            f.setInformation("1");
            f.setOrganization(o.getSensor().getOrganization());
            f.setSensor(o.getSensor());
            f.setType(Fact.COUNTER);
            f.setVisibility(Fact.VISIBILITY_ALL);
            f.setDate(Date.valueOf(new LocalDate().toString()));
            f.setSensorType(o.getSensor().getType());
            em.persist(f);
        }
        else{
            int info = Integer.parseInt(em.find(Fact.class, q.getSingleResult()).getInformation());
            info+=1;
            Fact f2= em.find(Fact.class, q.getSingleResult());
            f2.setInformation(String.valueOf(info));
            //em.merge(f2);
            
        }
        
    }
    
    private void createFactDate(Observation o){
        Query q;
        
        Date t = Date.valueOf(new LocalDate().toString());
        
        q = em.createNamedQuery(Fact.GET_FACTS_BY_DATE)
                .setParameter("dateToShow", t)
                .setParameter("ftype", Fact.DATE_COUNTER)
                .setParameter("idSen", o.getSensor().getId())
                .setParameter("sensorType", o.getSensor().getType());
        
        if(q.getResultList().isEmpty()){
            Fact f = new Fact();
            f.setInformation("1");
            f.setOrganization(null);
            f.setSensor(null);
            f.setType(Fact.DATE_COUNTER);
            f.setVisibility(Fact.VISIBILITY_ALL);
            f.setSensor(o.getSensor());
            f.setOrganization(o.getSensor().getOrganization());
            f.setSensorType(o.getSensor().getType());
            f.setDate(Date.valueOf(new LocalDate().toString()));
            em.persist(f);
        }
        else{
            int info = Integer.parseInt(em.find(Fact.class, q.getSingleResult()).getInformation());
            info+=1;
            Fact f2= em.find(Fact.class, q.getSingleResult());
            f2.setInformation(String.valueOf(info));
            //em.merge(f2);
        }
    }
}
