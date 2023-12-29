package pl.edu.agh.FridgeServer.dao;

import pl.edu.agh.FridgeServer.entity.Role;

public interface RoleDao {

    Role findByName(String roleName);
}
