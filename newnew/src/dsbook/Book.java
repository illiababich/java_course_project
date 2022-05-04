package dsbook;
//import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
//@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String bookTitle;
    private LocalDate publishDate;
    private int pageNum;
    private String authors;
    private int edition;
    private String description;
    private Genre genre;
    private double price;

    public Book(String bookTitle, LocalDate publishDate, int pageNum, String authors, int edition, Genre genre, double price)
    {
        this.bookTitle = bookTitle;
        this.publishDate = publishDate;
        this.pageNum = pageNum;
        this.authors = authors;
        this.edition = edition;
        this.genre = genre;
        this.price = price;
    }

    public Book(String bookTitle, LocalDate publishDate, int pageNum, String authors, int edition, String description, Genre genre, double price)
    {
        this.bookTitle = bookTitle;
        this.publishDate = publishDate;
        this.pageNum = pageNum;
        this.authors = authors;
        this.edition = edition;
        this.description = description;
        this.genre = genre;
        this.price = price;
    }

    public Book(int id, String bookTitle, LocalDate publishDate, int pageNum, String authors, int edition, String description, Genre genre, double price)
    {
        this.id = id;
        this.bookTitle = bookTitle;
        this.publishDate = publishDate;
        this.pageNum = pageNum;
        this.authors = authors;
        this.edition = edition;
        this.description = description;
        this.genre = genre;
        this.price = price;
    }

    @Override
    public String toString()
    {
        return "Book Author(s): " + authors +
                "\nBook Title: " + bookTitle +
                "\nBook Description: " + description +
                "\nBook Edition: " + edition +
                "\nBook Genre: " + genre +
                "\nBook Page Number: " + pageNum +
                "\nBook Publish Date: " + publishDate +
                "\nBook Price: " + price + "$";
    }
}
