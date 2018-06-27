package easynvr.easy.com.easynvr.HTTP;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import easynvr.easy.com.easynvr.Model.User;

public class UserTypeAdapter implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            Gson gson = new Gson();
            return gson.fromJson(json, User.class);
        }

        return null;
    }
}
