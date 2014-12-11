/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author brito_000
 */

@NamedQueries({
    @NamedQuery(name = Observation.GET_ALL_OBSERVATIONS, query = "SELECT o FROM Observation o"),
    @NamedQuery(name = Observation.GET_ALL_OBSERVATIONS_SENSOR, query = "SELECT o FROM Observation o WHERE o.sensor.id = :idSen"),
    @NamedQuery(name = Observation.GET_ALL_OBSERVATIONS_ORGANIZATION, query = "SELECT o FROM Observation o WHERE o.sensor.organization.id = :idOrg")
})


@Entity
public class Observation implements Serializable {
    
    public static final String GET_ALL_OBSERVATIONS = "Observation.get_all_observations";
    public static final String GET_ALL_OBSERVATIONS_SENSOR = "Observation.get_all_observations_sensor";
    public static final String GET_ALL_OBSERVATIONS_ORGANIZATION = "Observation.get_all_observations_organization";
    
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
