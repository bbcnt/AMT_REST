package ch.heigvd.amt_rest.model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@NamedQueries({
    @NamedQuery(name = Observation.GET_ALL_OBSERVATIONS, query = "SELECT o FROM "
            + "Observation o"),
    @NamedQuery(name = Observation.GET_ALL_OBSERVATIONS_SENSOR, query = "SELECT o "
            + "FROM Observation o WHERE o.sensor.id = :idSen"),
    @NamedQuery(name = Observation.GET_ALL_OBSERVATIONS_ORGANIZATION, query = 
            "SELECT o FROM Observation o WHERE o.sensor.organization.id = :idOrg")
})

/**
 * An observation is an information sent by a sensor, containing a value
 * and a date of creation. Users can querry the REST API to get these 
 * observations or facts, that are directly derived from observations.
 */
@Entity
public class Observation implements Serializable {
    
    //Gets all the observations
    public static final String GET_ALL_OBSERVATIONS = 
            "Observation.get_all_observations";
    
    //Gets all the observation for a given sensor
    public static final String GET_ALL_OBSERVATIONS_SENSOR = 
            "Observation.get_all_observations_sensor";
    
    //Gets all the observations for a given organization
    public static final String GET_ALL_OBSERVATIONS_ORGANIZATION = 
            "Observation.get_all_observations_organization";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
   
    private Timestamp timeS;
    private double valueObservation;

    @ManyToOne
    private Sensor sensor;
    
    public Observation(long id, Timestamp timeS, double value, Sensor sensor) {
        this.id = id;
        this.timeS = timeS;
        this.valueObservation = value;
        this.sensor = sensor;
    }

    public Observation() {
    }

    public long getId() {
        return id;
    }

    public Timestamp getTimeS() {
        return timeS;
    }

    public double getValueObservation() {
        return valueObservation;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTimeS(Timestamp timeS) {
        this.timeS = timeS;
    }

    public void setValueObservation(double valueObservation) {
        this.valueObservation = valueObservation;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
       
}
