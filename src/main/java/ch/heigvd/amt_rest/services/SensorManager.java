/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.Sensor;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author brito_000
 */

@Singleton
public class SensorManager implements SensorManagerLocal {

    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public List<Sensor> findSensors(Long idOrg) {
        
        Query q;
        if(idOrg == null)
            q = em.createNamedQuery(Sensor.GET_ALL_SENSORS);
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
