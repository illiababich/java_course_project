package fxmlFiles;

import dsbook.Order_table;
import dsbook.User;
import hibernateControllers.bookHibernate;
import hibernateControllers.orderHibernate;
import hibernateControllers.userHibernate;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import static fxControllers.MainWindow.dontSendTheOrderToEmployee;
import static fxControllers.MainWindow.sendTheOrderToEmployee;

import fxControllers.MainWindow;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class SendTheOrderToTheEmployee
{

    public Button yesButton;
    public Button noButton;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookShop");
    userHibernate userHibernateController = new userHibernate(entityManagerFactory);
    orderHibernate orderhibernate = new orderHibernate(entityManagerFactory);

    public void YesButtonSelected(ActionEvent actionEvent)
    {

    }

    public void noButtonSelected(ActionEvent actionEvent)
    {
        dontSendTheOrderToEmployee(true);
    }

}
