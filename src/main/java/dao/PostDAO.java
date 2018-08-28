package dao;

import entities.Post;
import entities.User;

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

    /**
     * Busca la lista de publicaciones que realizo el usuario pasado por parametro
     * @param position
     * @param user
     * @return
     */
    public List<Post> findSomeByUser(int position, User user) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Post> query = em.createQuery("from Post p where p.user = :user order by p.date desc", Post.class);
        query.setParameter("user", user);
        query.setMaxResults(10);
        query.setFirstResult(position);
        return query.getResultList();
    }
}
