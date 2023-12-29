package pl.edu.agh.FridgeServer.dao;

import pl.edu.agh.FridgeServer.entity.User;

public interface UserDao {

    User findByName(String userName);
    void save(User user);
}
