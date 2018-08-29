package util.Rest;

import dao.DAOImpl;
import dao.ImageDAO;
import dao.UserDAO;
import entities.Image;
import entities.Post;
import entities.User;
import javafx.embed.swing.SwingFXUtils;
import main.Main;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.print.DocFlavor;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
        List<User> friends = Main.userDAO.getFriends(userDao.find(user));
        ImageDAO imageDAO = new ImageDAO(Image.class);
        File uploadDir = new File("UploadedImages");
        Post post = new Post();
        post.setContent(content.replace("\"",""));
        post.setDate(new Date());
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
//                    java.awt.Image tempima = Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
//            imageNw.setPath(image);
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
        post.setUser(userDao.find(user.replace("\"","")));
        post.getUser().setFriendList(friends);
        postDao.persist(post);
        return post;
    }

}
