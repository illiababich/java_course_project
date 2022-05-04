package WebControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dsbook.Individual;
import dsbook.LegalPerson;
import dsbook.User;
import dsbook.UserType;
import hibernateControllers.userHibernate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import utilities.IndividualGsonSerializer;
import utilities.LocalDateGsonSerializer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.Properties;

@Controller
public class UserWebController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookShop");
    userHibernate userController = new userHibernate(entityManagerFactory);

    @RequestMapping(value = "/users/test", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String Test() {
        return "test";
    }

    @RequestMapping(value = "/users/validateUser", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String validateWebUser(@RequestBody String request)
    {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        User user = userController.getUserByCredentials(data.getProperty("login"), data.getProperty("password"));

        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer())
                .registerTypeAdapter(Individual.class, new IndividualGsonSerializer());
        if (user.getClass() == Individual.class) {
            Individual individual = (Individual) user;
            return (builder.create().toJson(individual));
        } else {
            LegalPerson legalPerson = (LegalPerson) user;
            return (builder.create().toJson(legalPerson, LegalPerson.class));
        }
    }

    @RequestMapping(value = "/users/addUser", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String addWebUser(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        Individual individual = null;
        LegalPerson legalPerson = null;
        if (data.getProperty("userType").equals("LegalPerson"))
        {
            userController.createUser(new LegalPerson(
                    UserType.valueOf(data.getProperty("type")),
                    data.getProperty("login"),
                    data.getProperty("password"),
                    data.getProperty("email"),
                    data.getProperty("address"),
                    Integer.parseInt(data.getProperty("zip")),
                    Integer.parseInt(data.getProperty("phoneNumber")),
                    data.getProperty("compTitle"),
                    data.getProperty("ceo")));
            legalPerson = (LegalPerson) userController.getUserByCredentials(data.getProperty("login"), data.getProperty("password"));
        } else if (data.getProperty("userType").equals("I")) {
            individual = (Individual) userController.getUserByCredentials(data.getProperty("login"), data.getProperty("password"));
        }
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        if (individual != null) {

            return (builder.create().toJson(individual, Individual.class));
        } else if (legalPerson != null) {
            return (builder.create().toJson(legalPerson, LegalPerson.class));
        }
        return "Fail";
    }

    @RequestMapping(value = "/users/deleteUser/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteUserWeb(@PathVariable(name = "id") int id)
    {
        userController.removeUser(id);

        User user = userController.getUserById(id);
        if (user == null) return "Success";
        else return "Not deleted";
    }

    @RequestMapping(value = "/users/updateUser", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String updateWebUser(@RequestBody String request)
    {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        Individual individual = null;
        LegalPerson legalPerson = null;
        if (data.getProperty("userType").equals("I")) {
            individual = (Individual) userController.getUserById(Integer.parseInt(data.getProperty("id")));

            individual.setName(data.getProperty("name"));
            individual.setEmail(data.getProperty("email"));
            userController.editUser(individual);
            return "Updated";
        } else if (data.getProperty("userType").equals("L")) {
            //finish specific
            return "Updated";
        } else {
            return "No such user type, unable to update";
        }
    }
}
