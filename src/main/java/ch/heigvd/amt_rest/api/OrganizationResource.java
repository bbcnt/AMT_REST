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



@Path("organizations")
@Stateless
/*
 * Organization Resource implements the full CRUD operations, as it should.
 * Using this resource should be done by some kind of management API.
 * Authors: Bignens Julien & Brito Carvalho Bruno
 */
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
    /**
     * returns a list of Organizations using the various criterias
     * @param idUser id of an user
     * @return List<OrganizationsDTO> list of Organizations
     */
    public List<OrganizationDTO> getOrganizations(@QueryParam("userid") Long idUser)
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
    /**
    * returns an Organization using the provided id
    * @param id id of the wanted Organization
    * @return an OrganizationDTO
    */
    public OrganizationDTO getOrganization (@PathParam("id") long id){
        return toDTO(oManager.findOrganization(id));
    }
    
    @POST
    @Consumes("application/json")
    /**
    * Creates an Organization using the provided OrganizationDTO
    * @param oDTO The OrganizationDTO to use to create an Organization
    * @return long the id of the created Organization
    */
    public long createOrganization(OrganizationDTO oDTO){
        Organization o = toOrganization(oDTO);
        return oManager.createOrganization(o);
    }
    
    @Path("/{id}")
    @PUT 
    @Produces("application/json")
    /**
    * Updates an Organization using the provided OrganizationDTO
    * @param oDTO The OrganizationDTO to be updated
    */
    public void updateOrganization(OrganizationDTO oDTO){
        oManager.updateOrganization(toOrganization(oDTO));
    }
    
    @Path("/{id}")
    @DELETE
    /**
    * Deletes an Organization using the provided id
    * @param id The id of the Organization to use to be deleted
    */
    public void deleteOrganization(@PathParam("id") long id){
        oManager.deleteOrganization(id);
    }
    
    /**
    * Converts an Organization into a OrganizationDTO
    * @param o the Organization to be converted into an OrganizationDTO
    * @return OrganizationDTO an OrganizationDTO
    */
    private OrganizationDTO toDTO(Organization o){
        OrganizationDTO oDTO = new OrganizationDTO();
        
        oDTO.setId(o.getId());
        oDTO.setName(o.getName());
        oDTO.setContactId(o.getContact().getId());
        return oDTO;
    }
    
    /**
    * Converts an OrganizationDTO into an Organization
    * @param oDTO the OrganizationDTO to be converted into an Organization
    * @return Organization an Organization
    */
    private Organization toOrganization(OrganizationDTO oDTO) {
        Organization o = new Organization();
        o.setId(oDTO.getId());
        o.setName(oDTO.getName());
        o.setContact(uManager.findUser(oDTO.getContactId()));
        
        return o;
    }
}
