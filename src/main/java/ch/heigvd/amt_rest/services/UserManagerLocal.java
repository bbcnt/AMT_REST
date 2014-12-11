/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author brito_000
 */
@Local
public interface UserManagerLocal {
    
    User findUser(long id);
    List<User> findUsers(Long idOrg);
    long createUser(User u);
    void updateUser(User u);
    void deleteUser(long id);
    
}
