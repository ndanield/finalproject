package dao;

import entities.Image;

public class ImageDAO extends DAOImpl<Image, Long> {

    public ImageDAO(Class<Image> entityClass) {
        super(entityClass);
    }
}
