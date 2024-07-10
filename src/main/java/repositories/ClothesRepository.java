package repositories;

import entities.Clothes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ClothesRepository {
    private final EntityManagerFactory emf;
    private final EntityManager em;

    public ClothesRepository() {
        emf = Persistence.createEntityManagerFactory("aidShelterPU");
        em = emf.createEntityManager();
    }

    public void addClothes(Clothes clothes) {
        em.getTransaction().begin();
        em.persist(clothes);
        em.getTransaction().commit();
    }

    public Clothes findClothesById(Long id) {
        return em.find(Clothes.class, id);
    }

    public List<Clothes> findAllClothes() {
        TypedQuery<Clothes> query = em.createQuery("SELECT c FROM Clothes c", Clothes.class);
        return query.getResultList();
    }

    public void updateClothes(Clothes clothes) {
        em.getTransaction().begin();
        em.merge(clothes);
        em.getTransaction().commit();
    }

    public void deleteClothes(Long id) {
        em.getTransaction().begin();
        Clothes clothes = em.find(Clothes.class, id);
        if (clothes != null) {
            em.remove(clothes);
        }
        em.getTransaction().commit();
    }

    public void close() {
        em.close();
        emf.close();
    }
}
