package ch.heigvd.amt_rest.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/** 
 * An organization is a group of users, managed by one of them. 
 * Mostly used to make querries within the API.
 */

@NamedQueries({
    @NamedQuery(name = Organization.GET_ALL_ORGANIZATIONS, query = 
            "SELECT o FROM Organization o"),
    @NamedQuery(name = Organization.GET_ALL_ORGANIZATIONS_USER, query = 
            "SELECT o FROM Organization o WHERE o.contact.id = :idUser")
})

@Entity
public class Organization implements Serializable {
    
    //Gets all the organizations
    public static final String GET_ALL_ORGANIZATIONS = 
            "Organization.get_all_organizations";
    
    //Gets the organization for a given user
    public static final String GET_ALL_ORGANIZATIONS_USER = 
            "Organization.get_all_organizations_user";
    
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
