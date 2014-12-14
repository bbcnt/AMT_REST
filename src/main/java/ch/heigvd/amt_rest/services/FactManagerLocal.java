package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.Fact;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;


@Local
public interface FactManagerLocal {
    
    Fact findFact(long id);
    List<Fact> findFacts(Long idOrg, Long idSen, Date ldate);
    long createFact(Fact f);
    void updateFact(Fact f);
    void deleteFact(long id);
}
