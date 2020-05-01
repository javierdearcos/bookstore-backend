package es.javierdearcos.bookstore.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@ApiModel("Book")
public class Book {
    @Id
    @GeneratedValue
    @ApiModelProperty("Book identifier")
    private Long id;

    @Column(length = 200)
    @NotNull
    @Size(min = 1, max = 200)
    @ApiModelProperty("Title of the book")
    private String title;

    @Column(length = 10000)
    @Size(min = 10, max = 10000)
    @ApiModelProperty("Description fo the book")
    private String description;

    @Column(name = "unit_cost")
    @Min(1)
    @ApiModelProperty("Price of the book")
    private Float unitCost;

    @Column(length = 50)
    @NotNull
    @Size(min = 1, max = 50)
    // TODO: Replace with a ISBN validator
    @ApiModelProperty("ISBN of the book")
    private String isbn;

    @Column(name = "publication_date")
    @Temporal(TemporalType.DATE)
    @Past
    @ApiModelProperty("Date when book was published")
    private Date publicationDate;

    @Column(name = "number_of_pages")
    @ApiModelProperty("Number of pages of the book")
    private Integer numberOfPages;

    @Column(name = "image_url")
    @ApiModelProperty("URL with the book cover image")
    private String imageUrl;

    @Enumerated
    @ApiModelProperty("Language of the book")
    private Language language;

    public Book() {
    }

    public Book(String title, String description, Float unitCost, String isbn, Date publicationDate,
                Integer numberOfPages, String imageUrl, Language language) {
        this.title = title;
        this.description = description;
        this.unitCost = unitCost;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.numberOfPages = numberOfPages;
        this.imageUrl = imageUrl;
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Float unitCost) {
        this.unitCost = unitCost;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(description, book.description) &&
                Objects.equals(unitCost, book.unitCost) &&
                Objects.equals(isbn, book.isbn) &&
                Objects.equals(publicationDate, book.publicationDate) &&
                Objects.equals(numberOfPages, book.numberOfPages) &&
                Objects.equals(imageUrl, book.imageUrl) &&
                language == book.language;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, unitCost, isbn, publicationDate, numberOfPages, imageUrl, language);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", unitCost=" + unitCost +
                ", isbn='" + isbn + '\'' +
                ", publicationDate=" + publicationDate +
                ", numberOfPages=" + numberOfPages +
                ", imageUrl='" + imageUrl + '\'' +
                ", language=" + language +
                '}';
    }
}
