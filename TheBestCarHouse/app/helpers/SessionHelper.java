package helpers;

import models.users.User;
import play.mvc.Http;

/**
 * Created by Enver on 1/22/2017.
 */
public class SessionHelper {

    public static User getCurrentUser(Http.Context context) {
        String email = context.session().get("email");
        User user = User.findByEmail(email);
        if(user != null){
            return user;
        }
        return null;
    }

}
