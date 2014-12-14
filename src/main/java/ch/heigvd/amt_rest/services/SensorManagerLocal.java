package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.Sensor;
import java.util.List;
import javax.ejb.Local;

@Local
public interface SensorManagerLocal {
    
    public List<Sensor> findSensors(Long idOrg);
    public Sensor findSensor(long id);
    public long createSensor(Sensor s);
    public void updateSensor(Sensor s);
    public void deleteSensor(long id);
    
}
