package es.javierdearcos.bookstore.rest;

import es.javierdearcos.bookstore.model.Book;
import es.javierdearcos.bookstore.repository.BookRepository;

import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/books")
public class BookEndpoint {

    @Inject
    private BookRepository bookRepository;

    @GET
    @Path("/{id: \\d+}")
    @Produces(APPLICATION_JSON)
    public Response getBook(@PathParam("id") @Min(1) Long id) {
        Book book = bookRepository.find(id);

        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(book).build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    public Response getBooks() {
        List<Book> books = bookRepository.findAll();

        if (books.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(books).build();
    }

    @GET
    @Path("/count")
    @Produces(TEXT_PLAIN)
    public Response countBooks() {
        Long numberOfBooks = bookRepository.countAll();

        if (numberOfBooks == 0) {
            return Response.noContent().build();
        }

        return Response.ok(numberOfBooks).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response createBook(Book book, @Context UriInfo uriInfo) {
        Book createdBook = bookRepository.create(book);
        URI createdBookUri = uriInfo.getAbsolutePathBuilder().path(createdBook.getId().toString()).build();
        return Response.created(createdBookUri).build();
    }

    @DELETE
    @Path("/{id: \\d+}")
    public Response deleteBook(@PathParam("id") @Min(1) Long id) {
        bookRepository.delete(id);
        return Response.noContent().build();
    }
}
