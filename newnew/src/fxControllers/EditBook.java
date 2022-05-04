package fxControllers;

import dsbook.Book;
import dsbook.Genre;
import fxControllers.ManageBookWindow;
import hibernateControllers.bookHibernate;
import hibernateControllers.userHibernate;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditBook implements Initializable
{
    @FXML
    public TextField bookTitle;
    @FXML
    public TextArea bookDescription;
    @FXML
    public ComboBox bookGenre;
    @FXML
    public DatePicker bookPublishDate;
    @FXML
    public TextField bookPageNumber;
    @FXML
    public TextField bookEdition;
    @FXML
    public TextField bookPrice;
    @FXML
    public TextField bookAuthors;
    private int bookId;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookShop");
    bookHibernate bookhibernate  = new bookHibernate(entityManagerFactory);

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        bookGenre.getItems().clear();
        bookGenre.getItems().addAll(Genre.values());
    }

    public void editButtonSelected(ActionEvent actionEvent)
    {
        Book currentBook = bookhibernate.getBookById(bookId);
        currentBook.setBookTitle(bookTitle.getText());
        currentBook.setDescription(bookDescription.getText());
        currentBook.setPublishDate(bookPublishDate.getValue());
        currentBook.setPageNum(Integer.parseInt(bookPageNumber.getText()));
        currentBook.setAuthors(bookAuthors.getText());
        bookhibernate.editBook(currentBook);
    }

    public void setBookId(int Id)
    {
        this.bookId = Id;
    }

    public void loadBookInfoButtonSelected(ActionEvent actionEvent)
    {
        Book book = bookhibernate.getBookById(bookId);
        bookTitle.setText(book.getBookTitle());
        bookAuthors.setText(book.getAuthors());
        bookPrice.setText(String.valueOf(book.getPageNum()));
        bookEdition.setText(String.valueOf(book.getPageNum()));
        bookPageNumber.setText(String.valueOf(book.getPageNum()));
        bookDescription.setText(book.getDescription());
        bookPublishDate.setValue(book.getPublishDate());
    }
}
