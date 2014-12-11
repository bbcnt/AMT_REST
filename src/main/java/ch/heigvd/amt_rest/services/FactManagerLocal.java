/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.Fact;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import org.joda.time.LocalDate;

/**
 *
 * @author brito_000
 */
@Local
public interface FactManagerLocal {
    
    Fact findFact(long id);
    List<Fact> findFacts(Long idOrg, Long idSen, Date ldate);
    long createFact(Fact f);
    void updateFact(Fact f);
    void deleteFact(long id);
}
