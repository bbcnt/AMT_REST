package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.Observation;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ObservationManagerLocal {
    
    Observation findObservation(long id);
    List<Observation> findObservations(Long idSen, Long idOrg);
    long createObservation(Observation o);
    void updateObservation(Observation o);
    void deleteObservation(long id);
}
