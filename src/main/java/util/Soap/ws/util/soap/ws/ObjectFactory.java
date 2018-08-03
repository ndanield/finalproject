
package util.soap.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the util.soap.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetUserPostsResponse_QNAME = new QName("http://ws.Soap.util/", "getUserPostsResponse");
    private final static QName _CreatePost_QNAME = new QName("http://ws.Soap.util/", "createPost");
    private final static QName _GetUserPosts_QNAME = new QName("http://ws.Soap.util/", "getUserPosts");
    private final static QName _CreatePostResponse_QNAME = new QName("http://ws.Soap.util/", "createPostResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: util.soap.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreatePostResponse }
     * 
     */
    public CreatePostResponse createCreatePostResponse() {
        return new CreatePostResponse();
    }

    /**
     * Create an instance of {@link CreatePost }
     * 
     */
    public CreatePost createCreatePost() {
        return new CreatePost();
    }

    /**
     * Create an instance of {@link GetUserPosts }
     * 
     */
    public GetUserPosts createGetUserPosts() {
        return new GetUserPosts();
    }

    /**
     * Create an instance of {@link GetUserPostsResponse }
     * 
     */
    public GetUserPostsResponse createGetUserPostsResponse() {
        return new GetUserPostsResponse();
    }

    /**
     * Create an instance of {@link Post }
     * 
     */
    public Post createPost() {
        return new Post();
    }

    /**
     * Create an instance of {@link EstudyPlace }
     * 
     */
    public EstudyPlace createEstudyPlace() {
        return new EstudyPlace();
    }

    /**
     * Create an instance of {@link City }
     * 
     */
    public City createCity() {
        return new City();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link WorkPlace }
     * 
     */
    public WorkPlace createWorkPlace() {
        return new WorkPlace();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserPostsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.Soap.util/", name = "getUserPostsResponse")
    public JAXBElement<GetUserPostsResponse> createGetUserPostsResponse(GetUserPostsResponse value) {
        return new JAXBElement<GetUserPostsResponse>(_GetUserPostsResponse_QNAME, GetUserPostsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePost }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.Soap.util/", name = "createPost")
    public JAXBElement<CreatePost> createCreatePost(CreatePost value) {
        return new JAXBElement<CreatePost>(_CreatePost_QNAME, CreatePost.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserPosts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.Soap.util/", name = "getUserPosts")
    public JAXBElement<GetUserPosts> createGetUserPosts(GetUserPosts value) {
        return new JAXBElement<GetUserPosts>(_GetUserPosts_QNAME, GetUserPosts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePostResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.Soap.util/", name = "createPostResponse")
    public JAXBElement<CreatePostResponse> createCreatePostResponse(CreatePostResponse value) {
        return new JAXBElement<CreatePostResponse>(_CreatePostResponse_QNAME, CreatePostResponse.class, null, value);
    }

}
