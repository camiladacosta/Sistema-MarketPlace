package com.example.MarketPlace.model.dao;

import com.example.MarketPlace.model.entity.Venda;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class VendasRepository {        

    @PersistenceContext
    private EntityManager em;

    public void save(Venda venda){
        em.persist(venda);
    }

    public Venda venda(int id){
        return em.find(Venda.class, id);
    }

    public List<Venda> vendas(){
        Query query = em.createQuery("from Venda");
        return query.getResultList();
    }

    public void remove(int id){
        Venda v = em.find(Venda.class, id);
        em.remove(v);
    }

    public void update(Venda venda){
        em.merge(venda);
    }    
    
    public List<Venda> vendas(LocalDate data) {
        String hql = "from Venda as v where v.data = :data";
        Query query = em.createQuery(hql, Venda.class);
        query.setParameter("data", data);
        return query.getResultList();
    }
}
