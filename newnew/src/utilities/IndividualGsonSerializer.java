package utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dsbook.Individual;

import java.lang.reflect.Type;

public class IndividualGsonSerializer implements JsonSerializer<Individual> {
    @Override
    public JsonElement serialize(Individual individual, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject personJson = new JsonObject();
        personJson.addProperty("id", individual.getId());
        personJson.addProperty("login", individual.getLogin());
        personJson.addProperty("password", individual.getPassword());
        personJson.addProperty("dateCreated", individual.getDateCreated().toString());
        personJson.addProperty("dateModified", individual.getDateModified().toString());
        personJson.addProperty("name", individual.getName());
        personJson.addProperty("surname", individual.getSurname());
        return personJson;
    }
}