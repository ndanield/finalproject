package dao;

import entities.User;

import javax.persistence.*;
import java.util.List;

public class UserDAO extends DAOImpl<User, String> {

    public UserDAO(Class<User> entityClass) {
        super(entityClass);
    }

    @Override
    public User find(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(entityClass, username);
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        } finally {
            em.close();
        }
        return null;
    }

    public boolean isEmpty() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery("select count(*) from User u", Long.class);
            return Integer.parseInt(q.getSingleResult().toString()) <= 0;
        } finally {
            em.close();
        }
    }

    public List<User> getSuggestedFriends(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("from User u where u.username <> :username and " +
                    "u.city = :city or " +
                    "u.nationality = :nationality or " +
                    "u.estudyPlace = :estudyPlace or " +
                    "u.workPlace = :workPlace", User.class)
                    .setParameter("username", user.getUsername())
                    .setParameter("nationality", user.getNationality())
                    .setParameter("city", user.getCity())
                    .setParameter("estudyPlace", user.getEstudyPlace())
                    .setParameter("workPlace", user.getWorkPlace());
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
