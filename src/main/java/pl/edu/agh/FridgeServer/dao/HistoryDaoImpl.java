package pl.edu.agh.FridgeServer.dao;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import pl.edu.agh.FridgeServer.entity.History;

@Repository
public class HistoryDaoImpl implements HistoryDao {

    private EntityManager entityManager;

    HistoryDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(History history) {
        entityManager.merge(history);
    }
}
