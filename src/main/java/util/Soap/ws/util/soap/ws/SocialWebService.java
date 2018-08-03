
package util.soap.ws;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SocialWebService", targetNamespace = "http://ws.Soap.util/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SocialWebService {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<util.soap.ws.Post>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getUserPosts", targetNamespace = "http://ws.Soap.util/", className = "util.soap.ws.GetUserPosts")
    @ResponseWrapper(localName = "getUserPostsResponse", targetNamespace = "http://ws.Soap.util/", className = "util.soap.ws.GetUserPostsResponse")
    @Action(input = "http://ws.Soap.util/SocialWebService/getUserPostsRequest", output = "http://ws.Soap.util/SocialWebService/getUserPostsResponse")
    public List<Post> getUserPosts(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg3
     * @param arg2
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "createPost", targetNamespace = "http://ws.Soap.util/", className = "util.soap.ws.CreatePost")
    @ResponseWrapper(localName = "createPostResponse", targetNamespace = "http://ws.Soap.util/", className = "util.soap.ws.CreatePostResponse")
    @Action(input = "http://ws.Soap.util/SocialWebService/createPostRequest", output = "http://ws.Soap.util/SocialWebService/createPostResponse")
    public void createPost(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        String arg3);

}
