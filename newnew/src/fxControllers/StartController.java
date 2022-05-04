package fxControllers;

import dsbook.User;
import hibernateControllers.userHibernate;
import utilities.AlertMessages;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class StartController extends Application {
    @FXML
    public TextField loginF;
    @FXML
    public PasswordField pswF;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookShop");
    userHibernate userHibernateController = new userHibernate(entityManagerFactory);

    @Override
    //Stage = window
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartController.class.getResource("/fxmlFiles/Start.fxml"));
        //window needs to store scene
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Book Shop");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void validateAndLogin(ActionEvent event) throws IOException
    {
        User user = userHibernate.getUserByCredentials(loginF.getText(), pswF.getText());
        if (user != null)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(StartController.class.getResource("/fxmlFiles/mainWindow.fxml"));

            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setUserID(user.getId());
            Stage stage = (Stage) loginF.getScene().getWindow();
            stage.setTitle("Book Shop");
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            AlertMessages.throwMessage("Error!", "Invalid login/password or no such user exists.");
        }
    }

    public void loadSignUpForm() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(StartController.class.getResource("/fxmlFiles/signUpPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) loginF.getScene().getWindow();
        stage.setTitle("Sign up");
        stage.setScene(scene);
        stage.show();
    }
}
