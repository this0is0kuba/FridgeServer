package pl.edu.agh.FridgeServer.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.edu.agh.FridgeServer.entity.User;

public interface FridgeService extends UserDetailsService {

    User findUserByUserName(String userName);

    void saveUser(User user);
}
