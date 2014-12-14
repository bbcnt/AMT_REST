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
import org.json.*;

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
        
        //if no param were given
        if(idSen == null && idOrg == null)
            q = em.createNamedQuery(Observation.GET_ALL_OBSERVATIONS);
        
        //if an organization id was given (we don't care about the sensor here)
        //because having a sensor would mean the same as only specifiying
        //the sensor id
        else if(idOrg != null){
            q = em.createNamedQuery(Observation.GET_ALL_OBSERVATIONS_ORGANIZATION);
            q.setParameter("idOrg", idOrg);    
        }
        //We assume here that the organization id is redundant
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
            
            JSONObject obj = new JSONObject();
            obj.put("counter", "1").toString();
            obj.put("min", o.getValueObservation()).toString();
            obj.put("max", o.getValueObservation()).toString();
            obj.put("avg", o.getValueObservation()).toString();
            
            Fact f = new Fact();
            f.setInformation(obj.toString());
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
            
            String info = em.find(Fact.class, 
                q.getSingleResult()).getInformation();

            JSONObject obj = new JSONObject(info);
            
            int counter = obj.getInt("counter");
            counter++;
            double min = obj.getDouble("min");
            double max = obj.getDouble("max");
            double avg = obj.getDouble("avg");
            
            if(o.getValueObservation() > max) max = o.getValueObservation();
            if(o.getValueObservation() < min) min = o.getValueObservation();
            avg = (avg + o.getValueObservation())/ 2;
            
            JSONObject obj2 = new JSONObject();
            
            obj2.put("counter", counter).toString();
            obj2.put("min", min);
            obj2.put("max", max);
            obj2.put("avg", avg);
            
            Fact f2= em.find(Fact.class, q.getSingleResult());
            //f2.setInformation(infoToWrite);
            f2.setInformation(obj2.toString());
            //em.merge(f2);
        }
    }
}
