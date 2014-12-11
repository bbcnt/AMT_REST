/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.model;

import java.io.Serializable;
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
    @NamedQuery(name = Sensor.GET_ALL_SENSORS, query = "SELECT s FROM Sensor s"),
    @NamedQuery(name = Sensor.GET_ALL_SENSORS_ORG, query = "SELECT s FROM Sensor s WHERE s.organization.id = :idOrg")
})

@Entity
public class Sensor implements Serializable {
    
    
    public static final String GET_ALL_SENSORS = "Sensor.get_all_sensors";
    public static final String GET_ALL_SENSORS_ORG = "Sensor.get_all_sensors_org";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    private String name;
    private String description;
    private String type;
    
    private String visibility;
    
    @ManyToOne
    private Organization organization;

    public Sensor(long id, String name, String desc, String type, String 
            visibility, Organization organization) {
        this.id = id;
        this.name = name;
        this.description = desc;
        this.type = type;
        this.visibility = visibility;
        this.organization = organization;
    }
        
    public Sensor() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getType() {
        return type;
    }

    public String getVisibility() {
        return visibility;
    }

    public Organization getOrganization() {
        return organization;
    }
       
}
