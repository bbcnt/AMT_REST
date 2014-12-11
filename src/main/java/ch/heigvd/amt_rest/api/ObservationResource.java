/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author brito_000
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
    public List<ObservationDTO> getObservation(@QueryParam("sensorid") Long idSen,
                                               @QueryParam("organizationid") Long idOrg)
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
    public ObservationDTO getObservation (@PathParam("id") long id){
        return toDTO(oManager.findObservation(id));
    }
    
    @POST
    @Consumes("application/json")
    public long createObservation(ObservationDTO oDTO){
        Observation o = toObservation(oDTO);
        return oManager.createObservation(o);
    }
    
    @Path("/{id}")
    @PUT 
    @Produces("application/json")
    public void updateObservation(ObservationDTO oDTO){
        oManager.updateObservation(toObservation(oDTO));
    }
    
    @Path("/{id}")
    @DELETE
    public void deleteObservation(@PathParam("id") long id){
        oManager.deleteObservation(id);
    }
    
    private ObservationDTO toDTO(Observation o){
        ObservationDTO oDTO = new ObservationDTO();
        oDTO.setId(o.getId());
        oDTO.setTimeS(o.getTimeS());
        oDTO.setValueObservation(o.getValueObservation());
        oDTO.setSensorId(o.getSensor().getId());
        
        return oDTO;
    }
    
    private Observation toObservation(ObservationDTO oDTO) {
        Observation o = new Observation();
        o.setId(oDTO.getId());
        o.setTimeS(oDTO.getTimeS());
        o.setValueObservation(oDTO.getValueObservation());
        o.setSensor(sManager.findSensor(oDTO.getSensorId()));
        return o;
    }
}
