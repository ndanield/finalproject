package dao;

import entities.User;

import javax.persistence.*;
import java.util.List;

public class UserDAO extends DAOImpl<User, String> {
    private static EntityManagerFactory emf;

    public UserDAO(Class<User> entityClass) {
        super(entityClass);
    }

}
