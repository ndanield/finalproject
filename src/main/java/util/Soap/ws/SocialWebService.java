package util.Soap.ws;

import dao.*;
import entities.Image;
import entities.Post;
import entities.User;
import main.Main;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebService
public class SocialWebService {

    @WebMethod
    public List<Post> getUserPosts(String username){
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

    @WebMethod
    public void createPost(String content, String image, String username){
        DAOImpl<User, String> userDao = new DAOImpl<>(User.class);
        DAOImpl<Post, String> postDao = new DAOImpl<>(Post.class);
        ImageDAO imageDAO = new ImageDAO(Image.class);
        Post post = new Post();
        post.setContent(content);
        if(image.equalsIgnoreCase("")){
            post.setImage(null);
        }else {
            Image imageNw = new Image();
            imageNw.setPath(image);
            imageDAO.persist(imageNw);
            post.setImage(imageNw);

        }
        post.setDate(new Date());
        post.setUser(userDao.find(username));
        postDao.persist(post);
    }
}
