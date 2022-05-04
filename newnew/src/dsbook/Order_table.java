package dsbook;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Order_table
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private LocalDate orderDate;
    private int orderUserId;
    private int orderToSpecificEmployee;
    private String bookIds;

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    private boolean orderStatus;

    public boolean getOrderStatus()
    {
        return this.orderStatus;
    }

    public Order_table(int orderId, LocalDate orderDate, int orderUserId, int orderToSpecificEmployee, String bookIds, boolean orderStatus)
    {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderUserId = orderUserId;
        this.orderToSpecificEmployee = orderToSpecificEmployee;
        this.bookIds = bookIds;
        this.orderStatus = orderStatus;
    }

    public Order_table(LocalDate orderDate, int orderUserId, int orderToSpecificEmployee, String bookIds, boolean orderStatus)
    {
        this.orderDate = orderDate;
        this.orderUserId = orderUserId;
        this.orderToSpecificEmployee = orderToSpecificEmployee;
        this.bookIds = bookIds;
        this.orderStatus = orderStatus;
    }

    public Order_table(int orderId, LocalDate orderDate, int orderUserId, String bookIds, boolean orderStatus)
    {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderUserId = orderUserId;
        this.bookIds = bookIds;
        this.orderStatus = orderStatus;
    }

    public Order_table(LocalDate orderDate, int orderUserId, String bookIds, boolean orderStatus)
    {
        this.orderDate = orderDate;
        this.orderUserId = orderUserId;
        this.bookIds = bookIds;
        this.orderStatus = orderStatus;
    }

    public int getId()
    {
        return this.orderId;
    }

    @Override
    public String toString() {
        return "Order_table{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", orderUserId=" + orderUserId +
                ", orderToSpecificEmployee=" + orderToSpecificEmployee +
                ", bookIds='" + bookIds + '\'' +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
