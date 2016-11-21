package helpers;

import models.User;
import play.mvc.Http;

/**
 * Created by Enver on 6/6/2016.
 */
public class SessionHelper {

    public static User getCurrentUser(Http.Context context) {
        String email = context.session().get("email");
        if (email == null) {
            return null;
        }
        User user = User.findByEmail(email);
        if (user == null) {
            return null;
        }
        return user;
    }

}
