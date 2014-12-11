/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.model;

import java.io.Serializable;
import java.sql.Date;
import org.joda.time.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 *
 * @author brito_000
 */
@NamedQueries({
    @NamedQuery(name = Fact.GET_ALL_FACTS, query = "SELECT f FROM Fact f"),
    @NamedQuery(name = Fact.GET_ALL_FACTS_ORG, query = "SELECT f FROM Fact f WHERE :idOrg = f.organization.id"),
    @NamedQuery(name = Fact.GET_TOTAL_OBS_SENSOR, query = "SELECT f.id FROM Fact f WHERE f.sensor.id = :idSen AND f.type = :ftype"),
    @NamedQuery(name = Fact.GET_FACTS_BY_DATE, query = "SELECT f.id FROM Fact f WHERE f.creationDate = :dateToShow AND f.type = :ftype")
})

@Entity
public class Fact implements Serializable {
    
    public static final String GET_ALL_FACTS = "Fact.get_all_facts";
    public static final String GET_ALL_FACTS_ORG = "Fact.get_all_facts_org";
    public static final String GET_TOTAL_OBS_SENSOR = "Fact.get_total_obs_sensor";
    public static final String GET_FACTS_BY_DATE = "Fact.get_facts_by_date";
    
    public static final String COUNTER = "COUNTER";
    public static final String DATE_COUNTER = "DATE_COUNTER";
    public static final String VISIBILITY_ALL = "ALL";
    public static final String VISIBILITY_PRIVATE = "PRIVATE";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    private String information;
    private String type;
    private String visibility;
    
    private Date creationDate;
    
    @ManyToOne
    private Organization organization;
    
    @ManyToOne
    private Sensor sensor;
    

    public Fact(long id, String information, String type, String visibility, 
            Organization organization, Observation observation, Sensor sensor,
            Date creationDate) {
        this.id = id;
        this.information = information;
        this.type = type;
        this.visibility = visibility;
        this.organization = organization;
        this.sensor = sensor;
        this.creationDate = creationDate;
    }

    public Fact() {
    }

    public long getId() {
        return id;
    }

    public String getInformation() {
        return information;
    }

    public String getType() {
        return type;
    }

    public String getVisibility() {
        return visibility;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }  

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public void setDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDate() {
        return creationDate;
    }
    
}
