/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.Sensor;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author brito_000
 */
@Local
public interface SensorManagerLocal {
    
    public List<Sensor> findSensors(Long idOrg);
    public Sensor findSensor(long id);
    public long createSensor(Sensor s);
    public void updateSensor(Sensor s);
    public void deleteSensor(long id);
    
}
