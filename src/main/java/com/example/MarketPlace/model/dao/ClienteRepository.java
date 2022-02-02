package com.example.MarketPlace.model.dao;

import com.example.MarketPlace.model.entity.ClientePF;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(ClientePF clientePF){
        em.persist(clientePF);
    }

    public ClientePF clientePF(int id){
        return em.find(ClientePF.class, id);
    }

    public List<ClientePF> clientes(){
        Query query = em.createQuery("from ClientePF");
        return query.getResultList();
    }

    public void remove(int id){
        ClientePF c = em.find(ClientePF.class, id);
        em.remove(c);
    }

    public void update(ClientePF clientePF){
        em.merge(clientePF);
    }
    
    public List<ClientePF> clientes(String nome){//filtrando cliente
        String hql = "from ClientePF as v where v.nome = :nome";
        Query query = em.createQuery(hql, ClientePF.class);
        query.setParameter("nome", nome);
        return query.getResultList();
    }
    
}
