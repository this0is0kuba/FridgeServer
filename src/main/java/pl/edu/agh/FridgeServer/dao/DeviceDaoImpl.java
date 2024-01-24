package pl.edu.agh.FridgeServer.dao;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.agh.FridgeServer.entity.Device;

@Repository
public class DeviceDaoImpl implements DeviceDao {

    private EntityManager entityManager;

    @Autowired
    public DeviceDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Device device) {
        entityManager.merge(device);
    }
}
