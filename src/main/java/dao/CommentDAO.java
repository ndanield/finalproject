package dao;

import entities.Comment;
import entities.Post;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CommentDAO extends DAOImpl<Comment, Long> {
    public CommentDAO(Class<Comment> entityClass) {
        super(entityClass);
    }

    public List<Comment> getByPost(Post post) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Comment> q = em.createQuery("from Comment c where c.post = :post", Comment.class)
                    .setParameter("post", post);
            return q.getResultList();
        } catch (Exception e) {
            System.out.printf(e.getMessage());
        } finally {
            em.close();
            return null;
        }
    }
}
