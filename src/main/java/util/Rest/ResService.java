package util.Rest;

import dao.DAOImpl;
import dao.UserDAO;
import entities.Post;
import entities.User;
import main.Main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResService {

    public ResService() {
    }

    public ArrayList<Post> getUserPosts(String username){
        DAOImpl<Post,String> postDAO = new DAOImpl<>(Post.class);
        ArrayList<Post> temp = new ArrayList<>();
        UserDAO userDAO = new UserDAO(User.class);
        List<User> friends = Main.userDAO.getFriends(userDAO.find(username));
        for (Post p: postDAO.findAll()) {
            if (p.getUser().getUsername().equalsIgnoreCase(username)) {
                p.getUser().setFriendList(friends);
                temp.add(p);
            }
        }
        return temp;
    }

    public Post createPost(String content, String image, String user){
        UserDAO userDao = new UserDAO(User.class);
        DAOImpl<Post, String> postDao = new DAOImpl<>(Post.class);
        Post post = new Post();
        post.setContent(content.replace("\"",""));
        post.setDate(new Date());
        post.setUser(userDao.find(user.replace("\"","")));
        postDao.persist(post);
        return post;
    }

}
