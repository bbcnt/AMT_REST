/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.Observation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author brito_000
 */
@Local
public interface ObservationManagerLocal {
    
    Observation findObservation(long id);
    List<Observation> findObservations(Long idSen, Long idOrg);
    long createObservation(Observation o);
    void updateObservation(Observation o);
    void deleteObservation(long id);
}
