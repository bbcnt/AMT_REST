package ch.heigvd.amt_rest.api;

import ch.heigvd.amt_rest.dto.SensorDTO;
import ch.heigvd.amt_rest.model.Sensor;
import ch.heigvd.amt_rest.services.OrganizationManagerLocal;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import ch.heigvd.amt_rest.services.SensorManagerLocal;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/*
 * Sensor Resource implements the full CRUD operations, as it should.
 * Using this resource should allow the user to create sensors, that will then
 * push observations.
 * Authors: Bignens Julien & Brito Carvalho Bruno
 */
@Path("sensors")
@Stateless
public class SensorResource {
    
    @EJB
    SensorManagerLocal sManager;
    @EJB
    OrganizationManagerLocal oManager;
    
    public SensorResource(){
        //Empty constructor is needed
    }
    
    @GET
    @Produces("application/json")
    /**
    * returns a list of Sensors using the various criterias
    * @param idOrg id of an organization
    * @return List<SensorsDTO> list of Sensors
    */
    public List<SensorDTO> getSensors(@QueryParam("organizationid") Long idOrg)
    {
        List<Sensor> sensors = sManager.findSensors(idOrg);
        List<SensorDTO> results = new LinkedList<>();

        for(Sensor s: sensors)
            results.add(toDTO(s));
        
        return results;
    }
    
    @Path("/{id}")
    @GET
    @Produces("application/json")  
    /**
    * returns a Sensor using the provided id
    * @param id id of the wanted Sensor
    * @return a SensorDTO
    */
    public SensorDTO getSensor (@PathParam("id") long id){
        return toDTO(sManager.findSensor(id));
    }
    
    @POST
    @Consumes("application/json")
    
    /**
    * Creates a Sensor using the provided SensorDTO
    * @param sDTO The SensorDTO to use to create an Sensor
    * @return long the id of the created Sensor
    */
    public long createSensor(SensorDTO sDTO){
        Sensor s = toSensor(sDTO);
        return sManager.createSensor(s);
    }
    
    @PUT
    @Produces("application/json")
    
    /**
    * Updates a Sensor using the provided SensorDTO
    * @param oDTO The SensorDTO to be updated
    */
    public void updateSensor(SensorDTO sDTO){
        sManager.updateSensor(toSensor(sDTO));
    }
    
    @Path("/{id}")
    @DELETE
    /**
    * Deletes an Sensor using the provided id
    * @param id The id of the Sensor to be deleted
    */
    public void deleteSensor(@PathParam("id") long id){
        sManager.deleteSensor(id);
    }
    
    /**
    * Converts a Sensor into a SensorDTO
    * @param s the Sensor to be converted into an SensorDTO
    * @return SensorDTO a SensorDTO
    */
    private SensorDTO toDTO(Sensor s){
        SensorDTO sDTO = new SensorDTO();
        sDTO.setId(s.getId());
        sDTO.setName(s.getName());
        sDTO.setDescription(s.getDescription());
        sDTO.setType(s.getType());
        sDTO.setOrganizationId(s.getOrganization().getId());
        sDTO.setVisibility(s.getVisibility());
        return sDTO;
    }
    
    /**
    * Converts a SensorDTO into an Sensor
    * @param sDTO the SensorDTO to be converted into an Sensor
    * @return Sensor a Sensor
    */
    private Sensor toSensor(SensorDTO sDTO) {
        Sensor s = new Sensor();
        s.setId(sDTO.getId());
        s.setName(sDTO.getName());
        s.setDescription(sDTO.getDescription());
        s.setType(sDTO.getType());
        s.setOrganization(oManager.findOrganization(sDTO.getOrganizationId()));
        s.setVisibility(sDTO.getVisibility());
        
        return s;
    }
}
