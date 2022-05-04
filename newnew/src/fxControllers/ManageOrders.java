package fxControllers;

import dsbook.User;
import hibernateControllers.bookHibernate;
import hibernateControllers.orderHibernate;
import hibernateControllers.userHibernate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import dsbook.Order_table;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static utilities.AlertMessages.throwMessage;

@Getter
@Setter
public class ManageOrders implements Initializable
{
    @FXML
    public MenuItem approveOrderButton;
    @FXML
    public MenuItem rejectOrderButton;
    @FXML
    public ListView employeeOrdersList;
    @FXML
    public ListView allOrdersList;

    private int orderId;

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
        System.out.println("\n\n\n\n" + this.orderId);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {}

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookShop");
    orderHibernate orderhibernate = new orderHibernate(entityManagerFactory);
    userHibernate userHibernateController = new userHibernate(entityManagerFactory);

    public void approveOrderButtonSelected() throws IOException
    {
        String orderId = allOrdersList.getSelectionModel().getSelectedItem().toString().split(":")[1];
        orderId = orderId.split(",")[0];
        Order_table order = orderhibernate.getOrderById(Integer.parseInt(orderId));
        order.setOrderStatus(true);
        User user = userHibernateController.getUserById(order.getOrderUserId());
        user.setOrderId(-1);
        userHibernateController.editUser(user);
        orderhibernate.editOrder(order);
        orderhibernate.removeOrderById(Integer.parseInt(orderId));
        throwMessage("Success!", "The order was approved.");
        showOrders();
    }

    public void rejectOrderButtonSelected() throws IOException
    {
        String orderId = allOrdersList.getSelectionModel().getSelectedItem().toString().split(":")[1];
        orderId = orderId.split(",")[0];
        Order_table order = orderhibernate.getOrderById(Integer.parseInt(orderId));
        int userId = order.getOrderUserId();
        User user = userHibernateController.getUserById(userId);
        user.setOrderId(0);
        userHibernateController.editUser(user);
        orderhibernate.removeOrderById(Integer.parseInt(orderId));
        throwMessage("Success!", "The order was rejected.");
        showOrders();
    }

    public void showOrders()
    {
        List<Order_table> orderList= orderhibernate.getAllOrders();
        employeeOrdersList.getItems().clear();
        allOrdersList.getItems().clear();
        orderhibernate.getAllOrders().forEach(o -> allOrdersList.getItems().add("Order Id:" + o.getOrderId() + ", Order date:" + o.getOrderDate() + ", Book Id's: " + o.getBookIds().substring(0, o.getBookIds().length() - 1) + ", login: " + userHibernateController.getUserById(o.getOrderUserId()).getLogin() + ", e-mail: " + userHibernateController.getUserById(o.getOrderUserId()).getEmail() + ", type: " + userHibernateController.getUserById(o.getOrderUserId()).getType()));
        int size = orderhibernate.getAllOrders().size();
        List<Integer> typeList = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            if (!typeList.contains(orderhibernate.getAllOrders().get(i).getOrderUserId()))
            {
                for (int j = 0; j < size; j++)
                {
                    if (orderhibernate.getAllOrders().get(i).getOrderToSpecificEmployee() == orderhibernate.getAllOrders().get(j).getOrderUserId())
                    {
                        System.out.println("\n\n\n\nttt\n\n\n\n");
                        employeeOrdersList.getItems().add(orderList.get(j));
                    }
                }
                typeList.add(orderhibernate.getAllOrders().get(i).getOrderUserId());
                for (int k = 0; k < typeList.size(); k++)
                {
                    System.out.println("\n\n" + typeList.get(k));
                }
            }
        }
    }

    public void refreshOrdersButtonSelected()
    {
        showOrders();
    }
}
