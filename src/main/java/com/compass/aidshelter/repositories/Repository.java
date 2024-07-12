package com.compass.aidshelter.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public abstract class Repository<T> {
    private final EntityManagerFactory emf;
    private final EntityManager em;
    private final Class<T> clasz;

    public Repository(Class<T> clasz) {
        this.clasz = clasz;
        emf = Persistence.createEntityManagerFactory("aidShelterPU");
        em = emf.createEntityManager();
    }
    public void save(T object){
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
    }
    public T findById(Long id) {
        return em.find(clasz, id);
    }

    public List<T> findAll() {
        TypedQuery<T> query = em.createQuery("SELECT o FROM "+clasz.getName()+" o", clasz);
        return query.getResultList();
    }

    public void update(T object) {
        em.getTransaction().begin();
        em.merge(object);
        em.getTransaction().commit();
    }

    public void delete(Long id) {
        em.getTransaction().begin();
        T object = em.find(clasz, id);
        if (object != null) {
            em.remove(object);
        }
        em.getTransaction().commit();
    }

    public void close() {
        em.close();
        emf.close();
    }
}
