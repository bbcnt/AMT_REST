package ch.heigvd.amt_rest.api;

import ch.heigvd.amt_rest.dto.ObservationDTO;
import ch.heigvd.amt_rest.model.Observation;
import ch.heigvd.amt_rest.services.ObservationManagerLocal;
import ch.heigvd.amt_rest.services.SensorManagerLocal;
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
 * Observation ressource. Enables the API user to work with observations.
 * Mostly used by sensors to push observations, this resource also implements
 * the full CRUD operations.
 * Authors: Bignens Julien & Brito Carvalho Bruno
 */
@Path("observations")
@Stateless
public class ObservationResource {

    
    @EJB
    SensorManagerLocal sManager;
    @EJB
    ObservationManagerLocal oManager;
    
    public ObservationResource(){
        //Empty constructor is needed
    }
    
    @GET
    @Produces("application/json")
    /**
     * returns a list of Observations using the various criterias
     * @param idOrg id of an organization
     * @param idSen id of a sensor
     * @return List<ObservationDTO> list of observations
     */
    public List<ObservationDTO> getObservation(@QueryParam("sensorid") 
                                                    Long idSen,
                                               @QueryParam("organizationid") 
                                                    Long idOrg)
    {
        List<Observation> observations = oManager.findObservations(idSen, idOrg);
        List<ObservationDTO> results = new LinkedList<>();
        
        for(Observation o: observations)
            results.add(toDTO(o));
        
        return results;
    }
    
    @Path("/{id}")
    @GET
    @Produces("application/json")
    /**
     * returns an observation using the provided id
     * @param id id of the wanted observation
     * @return an observationDTO
     */
    public ObservationDTO getObservation (@PathParam("id") long id){
        return toDTO(oManager.findObservation(id));
    }
    
    @POST
    @Consumes("application/json")
    /**
     * Creates an Observation using the provided ObservationDTO
     * @param oDTO the Observation provided by the user
     * @return long the id of the created Observation
     */
    public long createObservation(ObservationDTO oDTO){
        Observation o = toObservation(oDTO);
        return oManager.createObservation(o);
    }
    
    @PUT 
    @Produces("application/json")
    /**
     * Updates an Observation using the provided ObservationDTO
     * @param oDTO the Observation provided by the user
     */
    public void updateObservation(ObservationDTO oDTO){
        oManager.updateObservation(toObservation(oDTO));
    }
    
    @Path("/{id}")
    @DELETE
    /**
     * Deletes an Observation using the provided ObservationDTO
     * @param id the id of the Observation to delete
     */
    public void deleteObservation(@PathParam("id") long id){
        oManager.deleteObservation(id);
    }
    
    /**
     * Converts an Observation into a ObservationDTO
     * @param o the Observation to be converted into an ObservationDTO
     * @return ObservationDTO an ObservationDTO
     */
    private ObservationDTO toDTO(Observation o){
        ObservationDTO oDTO = new ObservationDTO();
        oDTO.setId(o.getId());
        oDTO.setTimeS(o.getTimeS());
        oDTO.setValueObservation(o.getValueObservation());
        oDTO.setSensorId(o.getSensor().getId());
        
        return oDTO;
    }
    /**
    * Converts an ObservationDTO into an Observation
    * @param oDTO the ObservationDTO to be converted into an Observation
    * @return Observation an observation
    */ 
    private Observation toObservation(ObservationDTO oDTO) {
        Observation o = new Observation();
        o.setId(oDTO.getId());
        o.setTimeS(oDTO.getTimeS());
        o.setValueObservation(oDTO.getValueObservation());
        o.setSensor(sManager.findSensor(oDTO.getSensorId()));
        return o;
    }
}
