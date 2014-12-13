package ch.heigvd.amt_rest.api;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * Generating an easy WebServlet for us !
 */

@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application{

    
     @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses (resources);
        return resources;
    }
    
    private void addRestResourceClasses(Set<Class<?>> resources){
        resources.add(ch.heigvd.amt_rest.api.FactResource.class);
        resources.add(ch.heigvd.amt_rest.api.ObservationResource.class);
        resources.add(ch.heigvd.amt_rest.api.OrganizationResource.class);
        resources.add(ch.heigvd.amt_rest.api.SensorResource.class);
        resources.add(ch.heigvd.amt_rest.api.UserResource.class);
    }
}
