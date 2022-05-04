package dsbook;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LegalPerson extends User
{
    private String companyName;
    private String CEO;

    public LegalPerson(int id, UserType type, String login, String password, String email, String address, int zipCode, int phoneNumber, String companyName, String CEO) {
        super(id, type, login, password, email, address, zipCode, phoneNumber);
        this.companyName = companyName;
        this.CEO = CEO;
    }

    public LegalPerson(UserType type, String login, String password, String email, String address, int zipCode, int phoneNumber, String companyName, String CEO) {
        super(type, login, password, email, address, zipCode, phoneNumber);
        this.companyName = companyName;
        this.CEO = CEO;
    }

    @Override
    public String toString() {
        return  "companyName='" + companyName + '\'' +
                "\nCEO='" + CEO + '\'' +
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
