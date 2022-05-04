package dsbook;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public abstract class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private UserType type;
    private String login;
    private String password;
    private String email;
    private String address;
    private int zipCode;
    private int phoneNumber;
    private LocalDate dateCreated;
    private LocalDate dateModified;
    private int orderId;

    public User(int id, UserType type, String login, String password, String email, String address, int zipCode, int phoneNumber)
    {
        this.id = id;
        this.type = type;
        this.login = login;
        this.password = password;
        this.email = email;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.dateCreated = LocalDate.now();
        this.dateModified = LocalDate.now();
    }

    public User(UserType type, String login, String password, String email, String address, int zipCode, int phoneNumber)
    {
        this.type = type;
        this.login = login;
        this.password = password;
        this.email = email;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.dateCreated = LocalDate.now();
        this.dateModified = LocalDate.now();
    }

    @Override
    public String toString()
    {
        return "id: " + id +
                "\ntype: " + type +
                "\nlogin: '" + login + '\'' +
                "\npassword: '" + password + '\'' +
                "\ne-mail: '" + email + '\'' +
                "\naddress: '" + address + '\'' +
                "\nzip code: " + zipCode +
                "\nphone number: " + phoneNumber +
                "\ndate created: " + dateCreated +
                "\ndate modified: " + dateModified;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }
}
