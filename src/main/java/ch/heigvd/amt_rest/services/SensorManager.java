package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.Sensor;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Singleton
public class SensorManager implements SensorManagerLocal {

    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public List<Sensor> findSensors(Long idOrg) {
        
        Query q;
        
        //Getting all sensors
        if(idOrg == null)
            q = em.createNamedQuery(Sensor.GET_ALL_SENSORS);
        
        //Getting all the sensors from the given organization
        else
        {
            q = em.createNamedQuery(Sensor.GET_ALL_SENSORS_ORG);
            q.setParameter("idOrg", idOrg);
        }
        return q.getResultList();
    }

    @Override
    public Sensor findSensor(long id) {

        return em.find(Sensor.class, id);
    }

    @Override
    public long createSensor(Sensor s) {
    
        em.persist(s);
        
        em.flush();
        return s.getId();
    }

    @Override
    public void updateSensor(Sensor s) {
        em.merge(s);
    }

    @Override
    public void deleteSensor(long id) {
        em.remove(em.find(Sensor.class, id));
    }
}
