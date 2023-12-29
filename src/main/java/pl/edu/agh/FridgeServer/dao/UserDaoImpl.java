package pl.edu.agh.FridgeServer.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.agh.FridgeServer.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

    private EntityManager entityManager;

    @Autowired
    UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User findByName(String userName) {

        TypedQuery<User> query = entityManager.createQuery("from User where userName = :name", User.class);
        query.setParameter("name", userName);

        User user;

        try {
            user = query.getSingleResult();
        } catch (Exception exception) {

            System.out.println(exception.getMessage());
            user = null;
        }

        return user;
    }

    @Override
    public void save(User user) {
        entityManager.merge(user);
    }
}
