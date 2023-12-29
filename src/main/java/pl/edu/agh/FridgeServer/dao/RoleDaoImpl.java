package pl.edu.agh.FridgeServer.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import pl.edu.agh.FridgeServer.entity.Role;

@Repository
public class RoleDaoImpl implements RoleDao {

    private EntityManager entityManager;

    RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Role findByName(String roleName) {

        TypedQuery<Role> query = entityManager.createQuery("from Role where roleName = :name", Role.class);
        query.setParameter("name", roleName);

        Role role;

        try {
            role = query.getSingleResult();
        } catch (Exception exception) {

            System.out.println(exception.getMessage());
            role = null;
        }

        return role;
    }
}
