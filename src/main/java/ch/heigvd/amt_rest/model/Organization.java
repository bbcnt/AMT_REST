/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author brito_000
 */
@NamedQueries({
    @NamedQuery(name = Organization.GET_ALL_ORGANIZATIONS, query = "SELECT o FROM Organization o"),
    @NamedQuery(name = Organization.GET_ALL_ORGANIZATIONS_USER, query = "SELECT o FROM Organization o WHERE o.contact.id = :idUser")
})


@Entity
public class Organization implements Serializable {
    
    public static final String GET_ALL_ORGANIZATIONS = "Organization.get_all_organizations";
    public static final String GET_ALL_ORGANIZATIONS_USER = "Organization.get_all_organizations_user";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String name;
   
    @OneToOne
    private User contact;

    public Organization(long id, String name, User contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }

    public Organization() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(User contact) {
        this.contact = contact;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getContact() {
        return contact;
    }
    
    
}
