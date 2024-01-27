package pl.edu.agh.FridgeServer.dao;


import pl.edu.agh.FridgeServer.entity.Device;

public interface DeviceDao {

    void save(Device device);
    Device findById(String id);
    void delete(Device device);
}
