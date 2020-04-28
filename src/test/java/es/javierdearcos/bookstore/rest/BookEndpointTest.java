package es.javierdearcos.bookstore.rest;

import es.javierdearcos.bookstore.model.Book;
import es.javierdearcos.bookstore.model.Language;
import es.javierdearcos.bookstore.repository.BookRepository;
import es.javierdearcos.bookstore.util.DateUtil;
import es.javierdearcos.bookstore.util.TextUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@RunAsClient
public class BookEndpointTest {

    @Test
    public void createBook(@ArquillianResteasyResource("api/books") WebTarget webTarget) {
        Response countBooks = webTarget.path("count").request().get();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), countBooks.getStatus());
    }

    @Deployment(testable = false)
    public static Archive<?> createDeploymentPackage() {

        return ShrinkWrap.create(WebArchive.class)
                .addClass(Book.class)
                .addClass(Language.class)
                .addClass(BookRepository.class)
                .addClass(DateUtil.class)
                .addClass(TextUtil.class)
                .addClass(BookEndpoint.class)
                .addClass(JAXRSConfiguration.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
    }
}
