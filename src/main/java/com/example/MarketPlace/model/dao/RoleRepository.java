package com.example.MarketPlace.model.dao;

import com.example.MarketPlace.model.entity.Role;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author camil
 */
@Repository
public class RoleRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Role role) {
        em.persist(role);
    }

    public Role role(int id) {
        return em.find(Role.class, id);
    }

    public List<Role> roles() {
        Query query = em.createQuery("from Role");
        return query.getResultList();
    }    
}
