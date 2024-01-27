package pl.edu.agh.FridgeServer.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.edu.agh.FridgeServer.entity.Device;
import pl.edu.agh.FridgeServer.entity.History;
import pl.edu.agh.FridgeServer.entity.User;

public interface FridgeService extends UserDetailsService {

    User findUserByUserName(String userName);
    Device findDeviceById(String id);
    void saveUser(User user);
    void saveDevice(Device device);
    void saveHistory(History history);
    void deleteDevice(String deviceId);
}
