package dao;

import entities.FriendRequest;
import entities.User;

import javax.persistence.EntityManager;

public class FriendRequestDAO extends DAOImpl<FriendRequest, Long> {

    public FriendRequestDAO(Class<FriendRequest> entityClass) {
        super(entityClass);
    }

}
