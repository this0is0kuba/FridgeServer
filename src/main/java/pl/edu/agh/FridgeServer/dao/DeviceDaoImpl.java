package pl.edu.agh.FridgeServer.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.agh.FridgeServer.entity.Device;
import pl.edu.agh.FridgeServer.entity.User;

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

    @Override
    public Device findById(String deviceId) {

        TypedQuery<Device> query = entityManager.createQuery("from Device where id = :deviceId", Device.class);
        query.setParameter("deviceId", deviceId);

        Device device;

        try {
            device = query.getSingleResult();
        } catch (Exception exception) {

            System.out.println(exception.getMessage());
            device = null;
        }

        return device;
    }
}
