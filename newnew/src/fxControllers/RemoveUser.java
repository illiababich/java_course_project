package fxControllers;

import dsbook.Book;
import dsbook.User;
import fxControllers.StartController;
import hibernateControllers.userHibernate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static utilities.AlertMessages.throwMessage;

public class RemoveUser implements Initializable
{
    @FXML
    public TextField enteredUserId;
    @FXML
    public Button applyCriteriaAndSearchButton;
    @FXML
    public MenuItem deleteThisUserContextMenu;
    @FXML
    public MenuItem viewInfoContextMenu;
    @FXML
    public ListView allUsersList;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookShop");
    userHibernate userHibernateController = new userHibernate(entityManagerFactory);

    public void deleteUserButton() throws IOException
    {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        List<User> allUsers = userHibernateController.getAllUsers();
        try
        {
            allUsers.forEach(user -> allUsersList.getItems().add(user.getId() + ":" + user.getLogin()));
        }
        catch (NullPointerException exception)
        {
            throwMessage("Error!", "List of users was not loaded!!");
        }
    }
}
