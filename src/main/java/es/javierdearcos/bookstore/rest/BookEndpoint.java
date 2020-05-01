package es.javierdearcos.bookstore.rest;

import es.javierdearcos.bookstore.model.Book;
import es.javierdearcos.bookstore.repository.BookRepository;
import io.swagger.annotations.*;

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
@Api("Book")
public class BookEndpoint {

    @Inject
    private BookRepository bookRepository;

    @GET
    @Path("/{id: \\d+}")
    @Produces(APPLICATION_JSON)
    @ApiOperation("Returns a book given an identifier")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Book with given identifier returned", response = Book.class),
            @ApiResponse(code = 404, message = "Book not found")
    })
    public Response getBook(@PathParam("id") @Min(1) Long id) {
        Book book = bookRepository.find(id);

        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(book).build();
    }

    @GET
    @Produces(APPLICATION_JSON)
    @ApiOperation("Returns all books available")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Books available returned"),
            @ApiResponse(code = 204, message = "No books available")
    })
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
    @ApiOperation("Returns the number of books available")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Number of books available returned"),
            @ApiResponse(code = 204, message = "No books available")
    })
    public Response countBooks() {
        Long numberOfBooks = bookRepository.countAll();

        if (numberOfBooks == 0) {
            return Response.noContent().build();
        }

        return Response.ok(numberOfBooks).build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @ApiOperation("Creates a new book with the given information")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Book created"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    public Response createBook(@ApiParam(value = "Book to be created", required = true) Book book, @Context UriInfo uriInfo) {
        Book createdBook = bookRepository.create(book);
        URI createdBookUri = uriInfo.getAbsolutePathBuilder().path(createdBook.getId().toString()).build();
        return Response.created(createdBookUri).build();
    }

    @DELETE
    @Path("/{id: \\d+}")
    @ApiOperation("Deletes the book with the given identifier")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Book with the given identifier deleted"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    public Response deleteBook(@PathParam("id") @Min(1) Long id) {
        bookRepository.delete(id);
        return Response.noContent().build();
    }
}
