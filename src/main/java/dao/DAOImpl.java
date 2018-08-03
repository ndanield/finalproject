package dao;

import entities.Post;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class DAOImpl<T, K> {
    protected static EntityManagerFactory emf;

    protected Class<T> entityClass;

    public DAOImpl(Class<T> entityClass) {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("Persistence");
        }
        this.entityClass = entityClass;
    }

    public void persist(T entity) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (IllegalArgumentException iae) {
            System.out.println("The instance" + iae.toString() + "is not an entity. "+ iae.getMessage());
        } catch (EntityExistsException eee) {
            System.out.println("The entity is already persisted. " + eee.getMessage());
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw  e;
        } finally {
            em.close();
        }

    }

    public T find(K id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(entityClass, id);
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        } finally {
            em.close();
        }
        return null;
    }

    public void update(T entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            throw  e;
        } finally {
            em.close();
        }
    }

    public void remove(T entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        }catch (Exception ex){
            em.getTransaction().rollback();
            throw  ex;
        } finally {
            em.close();
        }
    }

    public List<T> findAll() {
        EntityManager em = emf.createEntityManager();
        try{
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
            criteriaQuery.select(criteriaQuery.from(entityClass));
            return em.createQuery(criteriaQuery).getResultList();
        } finally {
            em.close();
        }
    }

}
