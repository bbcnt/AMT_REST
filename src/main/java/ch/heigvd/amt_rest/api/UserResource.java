package ch.heigvd.amt_rest.api;

import ch.heigvd.amt_rest.dto.UserDTO;
import ch.heigvd.amt_rest.model.User;
import ch.heigvd.amt_rest.services.OrganizationManagerLocal;
import ch.heigvd.amt_rest.services.UserManagerLocal;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;


/*
 * User Resource implements the full CRUD operations, as it should.
 * Using this resource should allow the user to create and manage users.
 * As of now, the notion of user is only useful for a few GET methods, but 
 * in the future, it should allow for an authentification system.
 * Authors: Bignens Julien & Brito Carvalho Bruno
 */
@Path("users")
@Stateless
public class UserResource {

    @EJB
    UserManagerLocal uManager;
    @EJB
    OrganizationManagerLocal oManager;
    
    
    public UserResource(){
        //Empty constructor is needed
    }
    
    @GET
    @Produces("application/json")
    
    /**
    * returns a list of Users using the various criterias
    * @param idOrg id of an organization
    * @return List<UsersDTO> list of Users
    */
    public List<UserDTO> getUsers(@QueryParam("organizationid") Long idOrg)
    {
        List<User> users = uManager.findUsers(idOrg);
        List<UserDTO> results = new LinkedList<>();
        
        for(User u: users)
            results.add(toDTO(u));
        
        return results;
    }
    
    @Path("/{id}")
    @GET
    @Produces("application/json")
    
    /**
    * returns a User using the provided id
    * @param id id of the wanted User
    * @return a UserDTO
    */
    public UserDTO getUser (@PathParam("id") long id){
        return toDTO(uManager.findUser(id));
    }
    
    @POST
    @Consumes("application/json")
    
    /**
    * Creates a User using the provided UserDTO
    * @param uDTO The UserDTO to use to create an User
    * @return long the id of the created User
    */
    public long createUser(UserDTO uDTO){
        User u = toUser(uDTO);
        return uManager.createUser(u);
        
    }
    
    @Path("/{id}")
    @PUT
    @Produces("application/json")
    /**
    * Updates a User using the provided UserDTO
    * @param uDTO The UserDTO to be updated
    */
    public void updateUser(@PathParam("id") long id, UserDTO uDTO){
        uManager.updateUser(toUser(uDTO));
    }
    
    @Path("/{id}")
    @DELETE
    /**
    * Deletes an User using the provided id
    * @param id The id of the User to be deleted
    */
    public void deleteUser(@PathParam("id") long id){
        uManager.deleteUser(id);
    }
    
    /**
    * Converts a User into a UserDTO
    * @param u the User to be converted into an UserDTO
    * @return UserDTO a UserDTO
    */
    private UserDTO toDTO(User u){
        UserDTO uDTO = new UserDTO();
        uDTO.setId(u.getId());
        uDTO.setName(u.getName());
        uDTO.setOrganizationId(u.getOrganization().getId());
        return uDTO;
    }
    
    /**
    * Converts a UserDTO into an User
    * @param uDTO the UserDTO to be converted into an User
    * @return User a User
    */
    private User toUser(UserDTO uDTO) {
        User u = new User();
        u.setId(uDTO.getId());
        u.setName(uDTO.getName());
        u.setOrganization(oManager.findOrganization(uDTO.getOrganizationId()));
        
        return u;
    }
    
}
