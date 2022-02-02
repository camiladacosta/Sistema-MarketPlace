package com.example.MarketPlace.model.dao;

import com.example.MarketPlace.model.entity.ClientePF;
import com.example.MarketPlace.model.entity.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository {
    
    @PersistenceContext
    private EntityManager em;
    
    public Usuario usuario(String login){
        String hql = "from Usuario as u where u.login = :login";
        Query query = em.createQuery(hql, Usuario.class);
        query.setParameter("login", login);
        return (Usuario) query.getSingleResult();
    }
    
    public void save(Usuario usuario) {
        em.persist(usuario);
    }
    public void update(ClientePF clientePF){
        em.merge(clientePF);
    }
}
