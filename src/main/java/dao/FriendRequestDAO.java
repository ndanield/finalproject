package dao;

import entities.FriendRequest;
import entities.User;
import util.JpaResultHelper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class FriendRequestDAO extends DAOImpl<FriendRequest, Long> {

    public FriendRequestDAO(Class<FriendRequest> entityClass) {
        super(entityClass);
    }

    public FriendRequest getFriendRequest(User requestUser, User targetUser) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<FriendRequest> q = em.createQuery(
                    "from FriendRequest fr where fr.requestUser = :requestUser and fr.targetUser = :targetUser", FriendRequest.class);
            q.setParameter("requestUser", requestUser);
            q.setParameter("targetUser", targetUser);

            return (FriendRequest) JpaResultHelper.getSingleResultOrNull(q);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return null;
    }

}
