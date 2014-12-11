/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.Organization;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author brito_000
 */
@Local
public interface OrganizationManagerLocal {
    
  Organization findOrganization(long id);
  List<Organization> findOrganizations(Long idUser);
  long createOrganization(Organization o);
  void updateOrganization(Organization o);
  void deleteOrganization(long id);
  
}
