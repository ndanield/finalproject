package dao;

import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

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
    
            List<User> suggestedFriends = query.getResultList();
            for (User friend : user.getFriendList()) {
                suggestedFriends.remove(friend);
            }

            return suggestedFriends;
        } finally {
            em.close();
        }
    }

    public void establishFriendship(User currentUser, User requestUser) {
        currentUser.getFriendList().add(requestUser); // This is already initialized in ViewUtil.java
        List<User> requesterFriends = getFriends(requestUser);
        requestUser.setFriendList(requesterFriends); // Initialize first
        requestUser.getFriendList().add(currentUser);
        update(currentUser);
        update(requestUser);
    }

    public List<User> getFriends(User user) {
        EntityManager em = emf.createEntityManager();
        try {
            String qlString = "select distinct u " +
			        "from User u " +
			        "join fetch u.friendList fl " +
                    "where fl = :user";
			TypedQuery<User> tq = em.createQuery(qlString, User.class).setParameter("user", user);
            return tq.getResultList();
        } catch (Exception e) {
            System.out.println("Buscar los amigos del usuario " + user.getUsername() + " salio mal: " + e.getMessage());
        } finally {
            em.close();
        }
        return null;
    }
}
