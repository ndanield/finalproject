package dao;

import entities.City;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class CityDAO extends DAOImpl<City, Long> {

    public CityDAO(Class<City> entityClass) {
        super(entityClass);
    }

    public City findByName(String cityname) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<City> query = em.createQuery("from City c where c.name = :cityname", City.class);
            query.setParameter("cityname", cityname);
            return query.getSingleResult();
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        } finally {
            em.close();
        }
        return null;
    }
}
