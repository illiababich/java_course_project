package dsbook;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Individual extends User {
    private String name;
    private String surname;
    private LocalDate birthDate;

    public Individual(UserType type, String login, String password, String email, String address, int zipCode, int phoneNumber, String name, String surname, LocalDate birthDate) {
        super(type, login, password, email, address, zipCode, phoneNumber);
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    public Individual(int id, UserType type, String login, String password, String email, String address, int zipCode, int phoneNumber, String name, String surname, LocalDate birthDate) {
        super(id, type, login, password, email, address, zipCode, phoneNumber);
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    @Override
    public String toString()
    {
        return  "name='" + name + '\'' +
                "\nsurname='" + surname + '\'' +
                "\nbirth date=" + birthDate +
                "\nid: " + getId() +
                "\ntype: " + getType() +
                "\nlogin: '" + getLogin() + '\'' +
                "\npassword: '" + getPassword() + '\'' +
                "\ne-mail: '" + getEmail() + '\'' +
                "\naddress: '" + getAddress() + '\'' +
                "\nzip code: " + getZipCode() +
                "\nphone number: " + getPhoneNumber() +
                "\ndate created: " + getDateCreated() +
                "\ndate modified: " + getDateModified();
    }
}
