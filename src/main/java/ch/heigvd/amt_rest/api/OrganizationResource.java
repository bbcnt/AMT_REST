/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.api;

import ch.heigvd.amt_rest.dto.OrganizationDTO;
import ch.heigvd.amt_rest.model.Organization;
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
@Path("organizations")
@Stateless
public class OrganizationResource {

    
    @EJB
    UserManagerLocal uManager;
    @EJB
    OrganizationManagerLocal oManager;
    
    
    public OrganizationResource(){
        //Empty constructor is needed
    }
    
    @GET
    @Produces("application/json")
    public List<OrganizationDTO> getOrganizations(@QueryParam("sensorid") Long idUser)
    {
        List<Organization> organizations = oManager.findOrganizations(idUser);
        List<OrganizationDTO> results = new LinkedList<>();
        
        for(Organization o: organizations)
            results.add(toDTO(o));
        
        return results;
    }
    
    @Path("/{id}")
    @GET
    @Produces("application/json")
    public OrganizationDTO getOrganization (@PathParam("id") long id){
        return toDTO(oManager.findOrganization(id));
    }
    
    @POST
    @Consumes("application/json")
    public long createOrganization(OrganizationDTO oDTO){
        Organization o = toOrganization(oDTO);
        return oManager.createOrganization(o);
    }
    
    @Path("/{id}")
    @PUT 
    @Produces("application/json")
    public void updateOrganization(OrganizationDTO oDTO){
        oManager.updateOrganization(toOrganization(oDTO));
    }
    
    @Path("/{id}")
    @DELETE
    public void deleteOrganization(@PathParam("id") long id){
        oManager.deleteOrganization(id);
    }
    
    private OrganizationDTO toDTO(Organization o){
        OrganizationDTO oDTO = new OrganizationDTO();
        
        oDTO.setId(o.getId());
        oDTO.setName(o.getName());
        oDTO.setContactId(o.getContact().getId());
        return oDTO;
    }
    
    private Organization toOrganization(OrganizationDTO oDTO) {
        Organization o = new Organization();
        o.setId(oDTO.getId());
        o.setName(oDTO.getName());
        o.setContact(uManager.findUser(oDTO.getContactId()));
        
        return o;
    }
}
