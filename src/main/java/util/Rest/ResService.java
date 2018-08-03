package util.Rest;

import dao.DAOImpl;
import entities.Post;
import entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResService {

    public ResService() {
    }

    public ArrayList<Post> getUserPosts(String username){
        DAOImpl<Post,String> postDAO = new DAOImpl<>(Post.class);
        ArrayList<Post> temp = new ArrayList<>();
        for (Post p: postDAO.findAll()) {
            if (p.getUser().getUsername().equalsIgnoreCase(username)) {
                temp.add(p);
            }
        }
        return temp;
    }

    public Post createPost(Post post){
        DAOImpl<User, String> userDao = new DAOImpl<>(User.class);
        DAOImpl<Post, String> postDao = new DAOImpl<>(Post.class);
        postDao.persist(new Post(post.getTitle(), post.getContent(), post.getDate(), post.getUser()));
        return post;
    }
}
