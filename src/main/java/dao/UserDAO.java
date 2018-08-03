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
}
