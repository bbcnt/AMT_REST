/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.dto;

/**
 *
 * @author brito_000
 */
public class OrganizationDTO {
    
    private long id;
    private String name;
    private long contactId;

    public OrganizationDTO(long id, String name, long contactId) {
        this.id = id;
        this.name = name;
        this.contactId = contactId;
    }

    public OrganizationDTO() {
    }

    
    
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getContactId() {
        return contactId;
    }
    
    
}
