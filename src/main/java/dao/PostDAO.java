package dao;

import entities.Post;

import javax.persistence.*;

public class PostDAO extends DAOImpl<Post, Long> {
    private static EntityManagerFactory emf;

    public PostDAO(Class<Post> entityClass) {
        super(entityClass);
    }

    @Override
    public Post find(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Post.class, id);
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        } finally {
            em.close();
        }
        return null;
    }
}
