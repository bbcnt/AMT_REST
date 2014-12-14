package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.Organization;
import java.util.List;
import javax.ejb.Local;

@Local
public interface OrganizationManagerLocal {
    
  Organization findOrganization(long id);
  List<Organization> findOrganizations(Long idUser);
  long createOrganization(Organization o);
  void updateOrganization(Organization o);
  void deleteOrganization(long id);
  
}
