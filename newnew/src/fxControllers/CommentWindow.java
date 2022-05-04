package fxControllers;

import hibernateControllers.userHibernate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CommentWindow
{
    @FXML
    public Button addCommentButton;
    @FXML
    public TextArea WriteYourCommentHereWindow;
    @FXML
    public ListView commentWindow;
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookShop");
    userHibernate userHibernateController = new userHibernate(entityManagerFactory);

    public void addCommentButtonSelected(ActionEvent actionEvent)
    {

    }
}
