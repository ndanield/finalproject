package dao;

import entities.Post;

import javax.persistence.*;
import java.util.List;

public class PostDAO extends DAOImpl<Post, Long> {

    public PostDAO(Class<Post> entityClass) {
        super(entityClass);
    }

    @Override
    public Post find(Long id) {
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

    // Post Controller
    public List<Post> findSome(int position) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Post> query = em.createQuery("from Post p order by p.date desc", Post.class);
        query.setMaxResults(10);
        query.setFirstResult(position);
        return query.getResultList();
    }
}
