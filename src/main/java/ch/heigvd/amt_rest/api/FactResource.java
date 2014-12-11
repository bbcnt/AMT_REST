/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.api;

import ch.heigvd.amt_rest.dto.FactDTO;
import ch.heigvd.amt_rest.model.Fact;
import ch.heigvd.amt_rest.services.FactManagerLocal;
import ch.heigvd.amt_rest.services.ObservationManagerLocal;
import ch.heigvd.amt_rest.services.OrganizationManagerLocal;
import ch.heigvd.amt_rest.services.SensorManagerLocal;
import java.util.Calendar;
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
@Path("facts")
@Stateless
public class FactResource {
    
    @EJB
    FactManagerLocal fManager;
    @EJB
    OrganizationManagerLocal oManager;
    @EJB
    ObservationManagerLocal obsManager;
    @EJB
    SensorManagerLocal sManager;
    
    public FactResource(){
        //Empty constructor is needed
    }
    
    @GET
    @Produces("application/json")
    public List<FactDTO> getFacts(@QueryParam("organizationid") Long idOrg,
                                  @QueryParam("sensorid") Long idSen)
    {
        List<Fact> facts = fManager.findFacts(idOrg, idSen);
        List<FactDTO> results = new LinkedList<>();

        for(Fact f: facts){
            results.add(toDTO(f));

        }
        return results;
    }
    
    @Path("/{id}")
    @GET
    @Produces("application/json")
    public FactDTO getFact (@PathParam("id") long id){
        return toDTO(fManager.findFact(id));
    }
    
    @POST
    @Consumes("application/json")
    public long createFact(FactDTO fDTO){
        Fact f = toFact(fDTO);
        return fManager.createFact(f);
    }
    
    @Path("/{id}")
    @PUT 
    @Produces("application/json")
    public void updateFact(FactDTO fDTO){
        fManager.updateFact(toFact(fDTO));
    }
    
    @Path("/{id}")
    @DELETE
    public void deleteFact(@PathParam("id") long id){
        fManager.deleteFact(id);
    }
    
    private FactDTO toDTO(Fact f){
        FactDTO fDTO = new FactDTO();
        fDTO.setId(f.getId());
        fDTO.setInformation(f.getInformation());
        fDTO.setType(f.getType());
        fDTO.setVisibility(f.getVisibility());
        fDTO.setOrganizationId(f.getOrganization().getId());
        fDTO.setSensorId(f.getSensor().getId());
        
        return fDTO;
    }
    
    private Fact toFact(FactDTO fDTO) {
        Fact f = new Fact();
        f.setId(fDTO.getId());
        f.setInformation(fDTO.getInformation());
        f.setType(fDTO.getType());
        f.setVisibility(fDTO.getVisibility());
        f.setOrganization(oManager.findOrganization(fDTO.getOrganizationId()));
        f.setSensor(sManager.findSensor(fDTO.getSensorId()));
        return f;
    }
}
