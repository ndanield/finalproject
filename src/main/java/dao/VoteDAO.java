package dao;

import entities.Vote;
import util.JpaResultHelper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class VoteDAO extends DAOImpl<Vote, Long> {
    public VoteDAO(Class<Vote> entityClass) {
        super(entityClass);
    }

    public Vote findByUserNPost(Long postId, String username) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Vote> q = em.createQuery("from Vote v where v.user.username = :username and v.post.id = :postId", Vote.class)
            .setParameter("username", username)
            .setParameter("postId", postId);

            return (Vote) JpaResultHelper.getSingleResultOrNull(q);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }

        return null;
    }

    public Vote findByUserNComment(Long commentId, String username) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Vote> q = em.createQuery("from Vote v where v.user.username = :username and v.comment.id = :commentId", Vote.class)
                    .setParameter("username", username)
                    .setParameter("commentId", commentId);

            return (Vote) JpaResultHelper.getSingleResultOrNull(q);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }

        return null;
    }

    public List<Vote> findAllByPost(Long postid) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Vote> q = em.createQuery("from Vote v where v.post = :postid", Vote.class)
                    .setParameter("postid", postid);
            return q.getResultList();
        } catch (Exception e) {
            System.out.printf(e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public String voteCountByPost(Long postid, String type) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery("select count(*) from Vote v where v.post.id = :postid and v.type = :type", Long.class)
                    .setParameter("postid", postid)
                    .setParameter("type", type);

            return q.getSingleResult().toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }

        return "0";
    }

    public String voteCountByComment(Long commentid, String type) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery("select count(*) from Vote v where v.comment.id = :commentid and v.type = :type", Long.class)
                    .setParameter("commentid", commentid)
                    .setParameter("type", type);

            return q.getSingleResult().toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }

        return "0";
    }
}
