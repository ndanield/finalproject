package util.Soap.ws;

import dao.*;
import entities.Image;
import entities.Post;
import entities.User;
import main.Main;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
        File uploadDir = new File("UploadedImages");
        Post post = new Post();
        post.setContent(content);
        try {
            java.nio.file.Path tempFile = Files.createTempFile(uploadDir.toPath(), "", ".png");
            if(image.replace("\"","").equalsIgnoreCase("")){
                post.setImage(null);
            }else {
                BufferedImage bufferedImage = null;
                byte[] imageByte;
                try{
                    BASE64Decoder decoder = new BASE64Decoder();
                    imageByte = decoder.decodeBuffer(image.replace("\"",""));
//                    ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                    InputStream bis = new ByteArrayInputStream(imageByte);
                    Files.copy(bis, tempFile, StandardCopyOption.REPLACE_EXISTING);
                    bufferedImage = ImageIO.read(bis);
                    bis.close();

                    Image imageNw = new Image();
                    imageNw.setPath("/" + tempFile.getFileName());
                    imageDAO.persist(imageNw);
                    post.setImage(imageNw);

                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }
        post.setDate(new Date());
        post.setUser(userDao.find(username));
        postDao.persist(post);
    }
}
