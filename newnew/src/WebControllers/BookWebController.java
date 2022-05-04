package WebControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dsbook.*;
import hibernateControllers.bookHibernate;
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
public class BookWebController {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BookShop");
    bookHibernate bookController = new bookHibernate(entityManagerFactory);

    @RequestMapping(value = "/books/addBook", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String addWebBook(@RequestBody String request) {
        Gson parser = new Gson();
        Properties data = parser.fromJson(request, Properties.class);
        Book book = null;
        bookController.createBook(new Book(data.getProperty("title"),
                LocalDate.parse(data.getProperty("datePublished")),
                Integer.parseInt(data.getProperty("pages")),
                data.getProperty("author"),
                Integer.parseInt(data.getProperty("edition")),
                data.getProperty("description"),
                Genre.valueOf(data.getProperty("genre")),
                Double.parseDouble(data.getProperty("price"))));

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        return "Fail";
    }

    @RequestMapping(value = "/books/deleteBook/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String deleteBookWeb(@PathVariable(name = "id") int id)
    {
        bookController.deleteBook(id);

        Book book = bookController.getBookById(id);
        if (book == null) return "Success";
        else return "Not deleted";
    }

}
