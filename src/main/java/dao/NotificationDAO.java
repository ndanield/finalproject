package dao;

import entities.Notification;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class NotificationDAO extends DAOImpl<Notification, Long> {

    public NotificationDAO(Class<Notification> entityClass) {
        super(entityClass);
    }

    public List<Notification> findByTargetUser(User targetUser) {
        EntityManager em = emf.createEntityManager();
        try {
            String qlString = "from Notification n where n.user = :targetUser";
            TypedQuery<Notification> q = em.createQuery(qlString, Notification.class);
            return q.setParameter("targetUser", targetUser).getResultList();
        } finally {
            em.close();
        }
    }
}
