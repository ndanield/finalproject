package dao;

import entities.FriendRequest;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class FriendRequestDAO extends DAOImpl<FriendRequest, Long> {

    public FriendRequestDAO(Class<FriendRequest> entityClass) {
        super(entityClass);
    }

    public boolean requestWasSent(User requestUser) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<FriendRequest> q = em.createQuery(
                    "from FriendRequest fr where fr.requestUser = :requestUser", FriendRequest.class);
            q.setParameter("requestUser", requestUser);

            return !q.getResultList().isEmpty();
        } finally {
            em.close();
        }
    }

}
