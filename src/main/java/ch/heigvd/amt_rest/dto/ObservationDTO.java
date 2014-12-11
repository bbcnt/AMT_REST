/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.dto;

import java.sql.Timestamp;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author brito_000
 */

public class ObservationDTO {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
   
    private Timestamp timeS;
    private double valueObservation;
    
    private long sensorId;


    public ObservationDTO(long id, Timestamp timeS, double value, long sensorId) {
        this.id = id;
        this.timeS = timeS;
        this.valueObservation = value;
        this.sensorId = sensorId;
    }

    public ObservationDTO() {
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

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public long getSensorId() {
        return sensorId;
    }
    
}
