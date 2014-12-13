package ch.heigvd.amt_rest.services;

import ch.heigvd.amt_rest.model.User;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UserManagerLocal {
    
    User findUser(long id);
    List<User> findUsers(Long idOrg);
    long createUser(User u);
    void updateUser(User u);
    void deleteUser(long id);
    
}
