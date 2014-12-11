/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author brito_000
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
    public List<UserDTO> getUsers(@QueryParam("organizationid") Long id)
    {
        List<User> users = uManager.findUsers(id);
        List<UserDTO> results = new LinkedList<>();
        
        for(User u: users)
            results.add(toDTO(u));
        
        return results;
    }
    
    @Path("/{id}")
    @GET
    @Produces("application/json")
    public UserDTO getUser (@PathParam("id") long id){
        return toDTO(uManager.findUser(id));
    }
    
    @POST
    @Consumes("application/json")
    public long createUser(UserDTO uDTO){
        User u = toUser(uDTO);
        return uManager.createUser(u);
        
    }
    
    @Path("/{id}")
    @PUT
    @Produces("application/json")
    public void updateUser(@PathParam("id") long id, UserDTO uDTO){
        uManager.updateUser(toUser(uDTO));
    }
    
    @Path("/{id}")
    @DELETE
    public void deleteUser(@PathParam("id") long id){
        uManager.deleteUser(id);
    }
    
    private UserDTO toDTO(User u){
        UserDTO uDTO = new UserDTO();
        uDTO.setId(u.getId());
        uDTO.setName(u.getName());
        uDTO.setOrganizationId(u.getOrganization().getId());
        return uDTO;
    }
    
    private User toUser(UserDTO uDTO) {
        User u = new User();
        u.setId(uDTO.getId());
        u.setName(uDTO.getName());
        u.setOrganization(oManager.findOrganization(uDTO.getOrganizationId()));
        
        return u;
    }
    
}
