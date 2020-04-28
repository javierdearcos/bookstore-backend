package es.javierdearcos.bookstore.repository;

import es.javierdearcos.bookstore.model.Book;
import es.javierdearcos.bookstore.util.DateUtil;
import es.javierdearcos.bookstore.util.TextUtil;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@Transactional(SUPPORTS)
public class BookRepository {

    @PersistenceContext(unitName = "bookStorePU")
    private EntityManager entityManager;

    @Inject
    private TextUtil textUtil;
    @Inject
    private DateUtil dateUtil;

    public Book find(@NotNull Long id) {
        return entityManager.find(Book.class, id);
    }

    public List<Book> findAll() {
        TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b ORDER BY b.title DESC", Book.class);
        return query.getResultList();
    }

    public Long countAll() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(b) FROM Book b", Long.class);
        return query.getSingleResult();
    }

    @Transactional(REQUIRED)
    public Book create(@NotNull Book book) {
        book.setTitle(textUtil.sanitize(book.getTitle()));
        book.setDescription(textUtil.sanitize(book.getDescription()));
        book.setPublicationDate(dateUtil.getDateWithoutTime(book.getPublicationDate()));
        entityManager.persist(book);
        return book;
    }

    @Transactional(REQUIRED)
    public void delete(@NotNull Long id) {
        entityManager.remove(entityManager.getReference(Book.class, id));
    }
}
