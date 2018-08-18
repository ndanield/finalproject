package dao;

import entities.FriendRequest;
import entities.User;
import util.JpaResultHelper;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class FriendRequestDAO extends DAOImpl<FriendRequest, Long> {

    public FriendRequestDAO(Class<FriendRequest> entityClass) {
        super(entityClass);
    }

    /**
     * Return a friend request that was made by the requestUser and was targeted to the targetUser.
     * @param requestUser
     * @param currentUser
     * @return A friend request if found, null otherwise.
     */
    public FriendRequest getFriendRequest( User currentUser, User requestUser) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<FriendRequest> q = em.createQuery(
                    "from FriendRequest fr " +
                            "where fr.requestUser = :requestUser and fr.targetUser = :targetUser " +
                            "or fr.requestUser = :targetUser and fr.targetUser = :requestUser" , FriendRequest.class);
            q.setParameter("requestUser", requestUser);
            q.setParameter("targetUser", currentUser);

            return (FriendRequest) JpaResultHelper.getSingleResultOrNull(q);
        } catch (Exception e) {
            System.out.println("Problema consiguiendo la solicitud da amistad: " + e.getCause() + ":" + e.getMessage());
        } finally {
            em.close();
        }
        return null;
    }

    /**
     * Return a list of friend requests that are not accepted still by using the target user of the request
     * @param targetUser
     * @return Una lista de FriendRequest
     */
    public List<FriendRequest> getFriendRequestsByUser(User targetUser) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<FriendRequest> q = em.createQuery(
                    "from FriendRequest fr where fr.targetUser = :targetUser and fr.isAccepted = false", FriendRequest.class);
            q.setParameter("targetUser", targetUser);

            return q.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return null;
    }

    public void acceptFriendRequest(FriendRequest friendRequest) {
        if (friendRequest == null) {
            throw new NullPointerException("El friendRequest pasado a la func. fue nulo");
        }
        friendRequest.setAccepted(true);
        update(friendRequest);
    }
}
