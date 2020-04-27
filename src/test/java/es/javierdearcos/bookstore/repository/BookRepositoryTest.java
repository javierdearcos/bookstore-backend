package es.javierdearcos.bookstore.repository;

import es.javierdearcos.bookstore.model.Book;
import es.javierdearcos.bookstore.model.Language;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BookRepositoryTest {

    @Inject
    private BookRepository bookRepository;

    @Test
    public void create() {
        assertEquals(Long.valueOf(0), bookRepository.countAll());
        assertEquals(Collections.emptyList(), bookRepository.findAll());

        Book newBook = new Book("title", "the book description", 24.99f, "isbn", new Date(),
                300, "http://image.url", Language.ENGLISH);
        newBook = bookRepository.create(newBook);

        assertNotNull(newBook.getId());
        assertEquals(Long.valueOf(1), bookRepository.countAll());
        assertEquals(Collections.singletonList(newBook), bookRepository.findAll());

        Book findBook = bookRepository.find(newBook.getId());

        assertEquals(newBook, findBook);

        bookRepository.delete(newBook.getId());

        assertEquals(Long.valueOf(0), bookRepository.countAll());
        assertEquals(Collections.emptyList(), bookRepository.findAll());
    }

    @Test(expected = Exception.class)
    public void createInvalid() {
        Book newBook = new Book(null, "the book description", 24.99f, "isbn", new Date(),
                 300, "http://image.url", Language.ENGLISH);
        bookRepository.create(newBook);
     }

    @Test(expected = Exception.class)
    public void findInvalid() {
        bookRepository.find(null);
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(BookRepository.class)
                .addClass(Book.class)
                .addClass(Language.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");
    }
}
