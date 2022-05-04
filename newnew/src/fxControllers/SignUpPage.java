package fxControllers;

import dsbook.Individual;
import dsbook.LegalPerson;
import utilities.AlertMessages;
import dsbook.UserType;
import hibernateControllers.userHibernate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utilities.AlertMessages.throwMessage;

public class SignUpPage implements Initializable {
    @FXML
    public RadioButton radioC;
    @FXML
    public RadioButton radioP;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField addressField;
    @FXML
    public TextField zipCodeField;
    @FXML
    public TextField personNameField;
    @FXML
    public TextField personSurnameField;
    @FXML
    public DatePicker personBirthDateField;
    @FXML
    public TextField personPhoneNumber;
    @FXML
    public TextField companyNameField;
    @FXML
    public TextField companyCEOField;
    @FXML
    public TextField companyPhoneField;
    @FXML
    public ToggleGroup userType;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookShop");
    userHibernate userHibernateController = new userHibernate(entityManagerFactory);

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        companyNameField.setDisable(true);
        companyPhoneField.setDisable(true);
        companyCEOField.setDisable(true);
    }

    public void enableFields(ActionEvent actionEvent)
    {
        if (radioP.isSelected())
        {
            companyCEOField.setDisable(true);
            companyNameField.setDisable(true);
            companyPhoneField.setDisable(true);

            personNameField.setDisable(false);
            personSurnameField.setDisable(false);
            personBirthDateField.setDisable(false);
            addressField.setDisable(false);
            personPhoneNumber.setDisable(false);
        }
        else
        {
            personNameField.setDisable(true);
            personSurnameField.setDisable(true);
            personBirthDateField.setDisable(true);
            personPhoneNumber.setDisable(true);

            companyCEOField.setDisable(false);
            companyNameField.setDisable(false);
            companyPhoneField.setDisable(false);
        }
    }

    public void createUser() throws IOException
    {
        if (radioP.isSelected())
        {
            userHibernateController.createUser(new Individual(UserType.CUSTOMER, loginField.getText(), passwordField.getText(), emailField.getText(), addressField.getText(), Integer.parseInt(zipCodeField.getText()), Integer.parseInt(personPhoneNumber.getText()), personNameField.getText(), personSurnameField.getText(), personBirthDateField.getValue()));
        }
        else
        {
            userHibernateController.createUser(new LegalPerson(UserType.CUSTOMER, loginField.getText(), passwordField.getText(), emailField.getText(), addressField.getText(), Integer.parseInt(zipCodeField.getText()), Integer.parseInt(companyPhoneField.getText()), companyNameField.getText(), companyCEOField.getText()));
        }
        throwMessage("Attention!", "User created successfully.");
        returnToLogin();
    }

    public void returnToLogin() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(StartController.class.getResource("/fxmlFiles/Start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) passwordField.getScene().getWindow();
        stage.setTitle("Book Shop");
        stage.setScene(scene);
        stage.show();
    }
}
