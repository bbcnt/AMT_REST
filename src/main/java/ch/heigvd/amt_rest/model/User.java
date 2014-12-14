package ch.heigvd.amt_rest.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * A User is ... a user. They should be able to login within the API and 
 * get observations and facts from their organization. This is not implemented
 * thus far, so everyone can see everything.
 */
@NamedQueries({
    @NamedQuery(name = User.GET_ALL_USERS, query = "SELECT u FROM User u"),
    @NamedQuery(name = User.GET_ALL_USERS_ORG, query = 
            "SELECT u FROM User u WHERE u.organization.id = :idOrg")

})

@Entity
@Table(name="userAPI")
public class User implements Serializable {
    
    //Gets all the users
    public static final String GET_ALL_USERS = "User.get_all_users";
    
    //gets all the users for a given organization
    public static final String GET_ALL_USERS_ORG = "User.get_all_users_org";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String name;
    
    @ManyToOne
    private Organization organization;

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    
    
}
