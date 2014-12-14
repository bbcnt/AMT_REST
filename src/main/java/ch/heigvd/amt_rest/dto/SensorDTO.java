package ch.heigvd.amt_rest.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * SensorDTO, the DTO version of Sensor
 */

public class SensorDTO {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    
    private String name;
    private String description;
    private String type;
    
    private String visibility;
    private long organizationId;
    
    public SensorDTO(long id, String name, String desc, String type, 
            String visibility, long organizationId) {
        this.id = id;
        this.name = name;
        this.description = desc;
        this.type = type;
        this.visibility = visibility;
        this.organizationId = organizationId;
    }
    
    public SensorDTO() {
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

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
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

}
