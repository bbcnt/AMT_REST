package ch.heigvd.amt_rest.api;

import ch.heigvd.amt_rest.dto.FactDTO;
import ch.heigvd.amt_rest.model.Fact;
import ch.heigvd.amt_rest.services.FactManagerLocal;
import ch.heigvd.amt_rest.services.ObservationManagerLocal;
import ch.heigvd.amt_rest.services.OrganizationManagerLocal;
import ch.heigvd.amt_rest.services.SensorManagerLocal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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

@Path("facts")
@Stateless
/*
 * Fact ressource. Enables the API user to work with Facts.
 * CRUD operations have been implemented, but should not be used.
 * Users only need to consult the facts, not edit them.
 * Authors: Bignens Julien & Brito Carvalho Bruno
 */
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
    /**
     * returns a list of Facts using the various criterias
     * @param idOrg id of an organization
     * @param idSen id of a sensor
     * @param ldate the date of the given fact (format : yyyy-MM-d)
     * @throws ParseException error in parsing the value of the date
     * @return List<FactDTO> list of factsDTO 
     */
    public List<FactDTO> getFacts(@QueryParam("organizationid") Long idOrg,
                                  @QueryParam("sensorid") Long idSen,
                                  @QueryParam("date") String ldate) 
                                  throws ParseException
    {
        
        //Casting the received date in String, to the Date format.
        Date receivedDate;
        if(!(ldate == null)){
            DateFormat format = new SimpleDateFormat("yyyy-MM-d", 
                Locale.ENGLISH);
            receivedDate = format.parse(ldate);
        }
        else
            receivedDate = null;
        
        List<Fact> facts = fManager.findFacts(idOrg, idSen, receivedDate);
        List<FactDTO> results = new LinkedList<>();

        for(Fact f: facts){
            results.add(toDTO(f));
        }
        return results;
    }
    
    @Path("/{id}")
    @GET
    @Produces("application/json")
    /**
     * returns a Fact using the provided id
     * @param id id of the wanted fact
     * @return a FactDTO
     */
    public FactDTO getFact (@PathParam("id") long id){
        return toDTO(fManager.findFact(id));
    }
    
    @POST
    @Consumes("application/json")
    /**
     * Creates a Fact using the provided FactDTO
     * @param fDTO the Fact provided by the user
     * @return long the id of the created fact
     */
    public long createFact(FactDTO fDTO){
        Fact f = toFact(fDTO);
        return fManager.createFact(f);
    }
    
    @Path("/{id}")
    @PUT 
    @Produces("application/json")
    /**
     * Updates a Fact using the provided FactDTO
     * @param fDTO the Fact provided by the user
     */
    public void updateFact(FactDTO fDTO){
        fManager.updateFact(toFact(fDTO));
    }
    
    @Path("/{id}")
    @DELETE
    /**
     * Deletes a Fact using the provided id
     * @param id the Fact provided by the user
     */
    public void deleteFact(@PathParam("id") long id){
        fManager.deleteFact(id);
    }
    
    /**
     * Converts a Fact into a FactDTO
     * @param f the Fact to be converted into a FactDTO
     * @return FactDTO a factDTO
     */
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
    
    /**
     * Converts a FactDTO into a Fact
     * @param fDTO the FactDTO to be converted into a Fact
     * @return Fact a fact
     */
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
