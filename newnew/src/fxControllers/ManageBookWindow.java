package fxControllers;

import dsbook.Book;
import dsbook.Genre;
import fxControllers.EditBook;
import hibernateControllers.bookHibernate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Getter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static utilities.AlertMessages.throwMessage;
@Getter
public class ManageBookWindow implements Initializable
{
    @FXML
    public TextArea bookDescription;
    @FXML
    public TextField bookTitle;
    @FXML
    public ListView shopBookList;
    @FXML
    public ComboBox bookGenre;
    @FXML
    public DatePicker bookPublishDate;
    @FXML
    public TextField bookPageNumber;
    @FXML
    public TextField bookEdition;
    @FXML
    public TextField bookAuthors;
    @FXML
    public TextField bookPrice;
    @FXML
    public MenuItem editItem;
    @FXML
    public MenuItem viewInfoItem;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookShop");
    bookHibernate bookhibernate  = new bookHibernate(entityManagerFactory);

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        bookGenre.getItems().clear();
        bookGenre.getItems().addAll(Genre.values());

        List<Book> allAvailableBooks = bookhibernate.getAllAvailableBooks(false);
        allAvailableBooks.forEach(b -> shopBookList.getItems().add(b.getId() + ":" + b.getBookTitle()));
    }

    public void addBook(ActionEvent actionEvent)
    {
        Book book = new Book(bookTitle.getText(), bookPublishDate.getValue(), Integer.parseInt(bookPageNumber.getText()), bookAuthors.getText(), Integer.parseInt(bookEdition.getText()), bookDescription.getText(), Genre.valueOf(bookGenre.getSelectionModel().getSelectedItem().toString()), Double.parseDouble(bookPrice.getText()));
        bookhibernate.createBook(book);
        refreshBookList();
        throwMessage("Attention!", "Book Added successfully.");
    }

    public void deleteBook() throws IOException
    {
        String bookId = shopBookList.getSelectionModel().getSelectedItem().toString().split(":")[0];
        bookhibernate.deleteBook(Integer.parseInt( bookId));
        refreshBookList();
    }

    public void refreshBookList()
    {
        List<Book> allAvailableBooks = bookhibernate.getAllAvailableBooks(false);
        shopBookList.getItems().clear();
        allAvailableBooks.forEach(book -> shopBookList.getItems().add(book.getId() + ":" + book.getBookTitle()));

        bookTitle.clear();
        bookAuthors.clear();
        bookEdition.clear();
        bookPublishDate.getEditor().clear();
        bookPrice.clear();
        bookPageNumber.clear();
        bookDescription.clear();
    }

    public void editItemMenuButton() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(StartController.class.getResource("/fxmlFiles/editBook.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        String bookId = shopBookList.getSelectionModel().getSelectedItem().toString().split(":")[0];
        Book book = bookhibernate.getBookById(Integer.parseInt(bookId));
        EditBook editBook = fxmlLoader.getController();
        editBook.setBookId(book.getId());
        Stage stage = new Stage();
        stage.setTitle("Edit Book");
        stage.setScene(scene);
        stage.show();
    }

    public void viewInfoMenuButton() throws IOException
    {
        String bookId = shopBookList.getSelectionModel().getSelectedItem().toString().split(":")[0];
        Book book = bookhibernate.getBookById(Integer.parseInt(bookId));
        throwMessage("Book Info:", book.toString());
    }
}
