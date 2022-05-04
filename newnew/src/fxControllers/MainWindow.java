package fxControllers;

import dsbook.*;
import fxControllers.ManageBookWindow;
import fxmlFiles.SendTheOrderToTheEmployee;
import hibernateControllers.bookHibernate;
import hibernateControllers.userHibernate;
import hibernateControllers.orderHibernate;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Getter;
import org.hibernate.criterion.Order;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

import static utilities.AlertMessages.throwMessage;

@Getter
public class MainWindow implements Initializable
{
    @FXML
    public ListView shopBookList;
    @FXML
    public TextField searchTitle;
    @FXML
    public TextField searchAuthor;
    @FXML
    public TextField bookPageNumberFrom;
    @FXML
    public TextField bookPageNumberTo;
    @FXML
    public TextField searchEdition;
    @FXML
    public TextField searchPriceFrom;
    @FXML
    public TextField searchPriceTo;
    @FXML
    public MenuItem editItem;
    @FXML
    public MenuItem viewInfoItem;
    @FXML
    public TextArea bookInfoField;
    @FXML
    public ListView orderList;
    @FXML
    public Button addBookButtonFXID;
    @FXML
    public Button addUserButton;
    @FXML
    public Button removeUserButton;
    @FXML
    public Tab adminTAB;
    @FXML
    public Button manageOrdersButton;
    @FXML
    public Button terminateSessionButton;
    @FXML
    public Button viewProfileInformationButton;
    @FXML
    public MenuItem addToCart;
    @FXML
    public MenuItem viewInfoFromShop;
    @FXML
    public MenuItem viewComments;
    @FXML
    public Button totalPriceButton;
    public ListView userOrder;
    @FXML
    public Button payAskVerificationButton;
    @FXML
    public Button askForVerificationButton;
    @FXML
    public RadioButton AdminVerificationButton;
    @FXML
    public RadioButton regularVerificationButton;
    public ToggleGroup chooseVerification;
    public TextField givenAdminId;
    private int userID;
    private int selectedBookId;
    private List<Book> chosenBooks;

    public int getUserId()
    {
        return userID;
    }

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookShop");
    userHibernate userHibernateController = new userHibernate(entityManagerFactory);
    bookHibernate bookhibernate  = new bookHibernate(entityManagerFactory);
    orderHibernate orderhibernate = new orderHibernate(entityManagerFactory);

    public void setUserID(int userID)
    {
        this.userID = userID;
        changeAccessLevel();
    }

    public void changeAccessLevel()
    {
        User user = userHibernateController.getUserById(userID);
        if(user.getType() == UserType.CUSTOMER)
        {
            adminTAB.setDisable(true);
        } else if (user.getType() == UserType.EMPLOYEE)
        {
            addUserButton.setDisable(true);
            removeUserButton.setDisable(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        chosenBooks = new ArrayList<>();
        payAskVerificationButton.setDisable(true);
        regularVerificationButton.setSelected(true);
        givenAdminId.setDisable(true);
    }

    public void addUser() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(StartController.class.getResource("/fxmlFiles/manuallyAddUser.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Sign up");
        stage.setScene(scene);
        stage.show();
    }

    public void removeUser() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/fxmlFiles/removeUser.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Remove User");
        stage.setScene(scene);
        stage.show();
    }

    public void manageBooks() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(StartController.class.getResource("/fxmlFiles/manageBookWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Manage Books");
        stage.setScene(scene);
        stage.show();
    }

    public void shopTabSelected(Event event)
    {
        List<Book> allAvailableBooks = bookhibernate.getAllAvailableBooks(false);
        shopBookList.getItems().clear();
        allAvailableBooks.forEach(book -> shopBookList.getItems().add(book.getId() + ":" + book.getBookTitle()));
    }

    public void terminateSessionButtonSelected() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(StartController.class.getResource("/fxmlFiles/Start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) terminateSessionButton.getScene().getWindow();
        stage.setTitle("Book Shop");
        stage.setScene(scene);
        stage.show();
    }

    public void viewProfileInformationButtonSelected() throws IOException
    {
        User user = userHibernateController.getUserById(userID);
        throwMessage("User information:", user.toString());
    }

    public void viewInfoFromShopButtonSelected(ActionEvent actionEvent)
    {
        String bookId = shopBookList.getSelectionModel().getSelectedItem().toString().split(":")[0];
        Book book = bookhibernate.getBookById(Integer.parseInt(bookId));
        throwMessage("Book Info:", book.toString());
    }

    public void applyFiltersAndSearchButtonSelected()
    {
        shopBookList.getItems().clear();
        int priceFrom;
        int priceTo;
        int pageFrom;
        int pageTo;

        if (Objects.equals(searchPriceFrom.getText(), ""))
        {
            priceFrom = -1;
        }
        else
        {
            priceFrom = Integer.parseInt(searchPriceFrom.getText());
        }

        if (Objects.equals(searchPriceTo.getText(), ""))
        {
            priceTo = -1;
        }
        else
        {
            priceTo = Integer.parseInt(searchPriceTo.getText());
        }

        if (Objects.equals(bookPageNumberFrom.getText(), ""))
        {
            pageFrom = -1;
        }
        else
        {
            pageFrom = Integer.parseInt(bookPageNumberFrom.getText());
        }

        if (Objects.equals(bookPageNumberTo.getText(), ""))
        {
            pageTo = -1;
        }
        else
        {
            pageTo = Integer.parseInt(bookPageNumberTo.getText());
        }
        List<Book> books = bookhibernate.getFilteredBooks(searchTitle.getText(), searchAuthor.getText(), priceFrom, priceTo, pageFrom, pageTo);
        shopBookList.getItems().clear();
        try
        {
            books.forEach(book -> shopBookList.getItems().add(book.getId() + ":" + book.getBookTitle()));
        }
        catch (NullPointerException exception)
        {
            throwMessage("Error!", "No books found.");
        }
    }

    public void clearCriteriaButtonSelected(ActionEvent actionEvent)
    {
        searchAuthor.clear();
        searchPriceTo.clear();
        searchTitle.clear();
        searchPriceFrom.clear();
        bookPageNumberTo.clear();
        bookPageNumberFrom.clear();
        applyFiltersAndSearchButtonSelected();
    }

    public void addToCartButtonSelected(ActionEvent actionEvent)
    {
        String bookId = shopBookList.getSelectionModel().getSelectedItem().toString().split(":")[0];
        selectedBookId = Integer.parseInt(bookId);
        Book book = bookhibernate.getBookById(selectedBookId);
        chosenBooks.add(book);
    }

    public void manageOrders() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(StartController.class.getResource("/fxmlFiles/manageOrders.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Manage Orders");
        stage.setScene(scene);
        stage.show();
    }

    public void enableFields()
    {
        if (AdminVerificationButton.isSelected())
        {
            askForVerificationButton.setDisable(true);
            payAskVerificationButton.setDisable(false);
            givenAdminId.setDisable(false);
        }
        else
        {
            payAskVerificationButton.setDisable(true);
            askForVerificationButton.setDisable(false);
            givenAdminId.setDisable(true);
        }
    }

    public void askForVerificationButtonSelected() throws IOException
    {
        User user = userHibernateController.getUserById(userID);
        if (orderList == null)
        {
            throwMessage("Error!", "Your cart is empty.");
        }
        if (user.getOrderId() != 0)
        {
            throwMessage("Error!", "You can have only one order being managed!");
        }
        else if (chosenBooks.size() != 0)
        {
            FXMLLoader fxmlLoader = new FXMLLoader(StartController.class.getResource("/fxmlFiles/manageOrders.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            String bId = "";
            for (int i = 0; i < chosenBooks.size(); i++)
            {
                bId += chosenBooks.get(i).getId();
                bId += ",";
            }
            chosenBooks.clear();
            orderList.getItems().clear();

            if(AdminVerificationButton.isSelected())
            {
                Order_table order_table = new Order_table(LocalDate.now(), userID, Integer.parseInt(givenAdminId.getText()), bId, false);
                orderhibernate.createOrder(order_table);
                user.setOrderId(order_table.getOrderId());
                ManageOrders manageOrders = fxmlLoader.getController();
                System.out.println("\n\n\n\n" + order_table.getOrderId());
                manageOrders.setOrderId(order_table.getOrderId());
                userHibernateController.editUser(user);
            }
            else
            {
                Order_table order_table = new Order_table(LocalDate.now(), userID, bId, false);
                orderhibernate.createOrder(order_table);
                user.setOrderId(order_table.getOrderId());
                ManageOrders manageOrders = fxmlLoader.getController();
                System.out.println("\n\n\n\n" + order_table.getOrderId());
                manageOrders.setOrderId(order_table.getOrderId());
                userHibernateController.editUser(user);
            }

            cartTabSelected();
            //throwMessage("Success!", "Your order is being verified!");
        }
    }

    public static void sendTheOrderToEmployee(boolean answer)
    {

    }

    public static void dontSendTheOrderToEmployee(boolean answer)
    {

    }

    public void cartTabSelected()
    {
        User user = userHibernateController.getUserById(userID);
        orderList.getItems().clear();
        if (chosenBooks != null)
        {
            orderList.getItems().clear();
            if (chosenBooks.size() == 1)
            {
                orderList.getItems().add(chosenBooks.get(0).getId() + ":" + chosenBooks.get(0).getBookTitle());
            }
            if (chosenBooks.size() > 1)
            {
                chosenBooks.forEach(book -> orderList.getItems().add(book.getId() + ":" + book.getBookTitle()));
            }
        }

        if (user.getOrderId() == -1)
        {
            throwMessage("yay!", "Your order was verified. You can now delete your order to continue using this shop. Check your e-mail box to collect your books :)");
            user.setOrderId(0);
            userHibernateController.editUser(user);
        }

        if (userOrder != null)
        {
            Order_table order = orderhibernate.getOrderById(user.getOrderId());
            if (user.getOrderId() != 0 )
            {
                userOrder.getItems().clear();
                userOrder.getItems().add("Order Id:" + order.getOrderId() + ", Order date:" + order.getOrderDate() + ", Book Id's: " + order.getBookIds().substring(0, order.getBookIds().length() - 1));
                //+ order.getOrderToSpecificEmployee() == "0" ? ("Not specified to employee") : ("specified to employee"));
            }
        }

        totalPriceButtonCalculateCost();
    }

    public void removeFromCartButtonSelected(ActionEvent actionEvent)
    {
        String bookId = orderList.getSelectionModel().getSelectedItem().toString().split(":")[0];
        selectedBookId = Integer.parseInt(bookId);
        for (int i = 0; i < chosenBooks.size(); i++)
        {
            if (chosenBooks.get(i).getId() == selectedBookId)
            {
                chosenBooks.remove(i);
                break;
            }
        }
        cartTabSelected();
    }

    public void viewCommentsButtonSelected() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(StartController.class.getResource("/fxmlFiles/commentWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("View Comments");
        stage.setScene(scene);
        stage.show();
    }

    public void totalPriceButtonCalculateCost()
    {
        double price = 0;
        for (int i = 0; i < chosenBooks.size(); i++)
        {
            price += chosenBooks.get(i).getPrice();
        }
        totalPriceButton.setText(String.valueOf(price + "$"));
    }

    public void deleteThisOrderButtonSelected(ActionEvent actionEvent)
    {
        String orderId = userOrder.getSelectionModel().getSelectedItem().toString().split(":")[1];
        orderId = orderId.split(",")[0];
        Order_table order = orderhibernate.getOrderById(Integer.parseInt(orderId));
        User user = userHibernateController.getUserById(userID);
        user.setOrderId(0);
        userHibernateController.editUser(user);
        orderhibernate.removeOrderById(Integer.parseInt(orderId));
        userOrder.getItems().clear();
    }

    public void AdminVerificationButtonSelected(ActionEvent actionEvent)
    {
        enableFields();
    }

    public void regularVerificationButtonSelected(ActionEvent actionEvent)
    {
        enableFields();
    }

    public void payAskVerificationButton(ActionEvent actionEvent)
    {

    }
}
