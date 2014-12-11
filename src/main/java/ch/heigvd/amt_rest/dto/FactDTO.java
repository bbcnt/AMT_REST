/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author brito_000
 */
public class FactDTO {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    private String information;
    private String type;
    private String visibility;
    
    private long organizationId;
    private long sensorId;

    public FactDTO(long id, String information, String type, String visibility, 
            long organizationId, long sensorId) {
        this.id = id;
        this.information = information;
        this.type = type;
        this.visibility = visibility;
        this.organizationId = organizationId;
        this.sensorId = sensorId;
        
    }

    public FactDTO() {
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

    public long getOrganizationId() {
        return organizationId;
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

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    
    
    
}
