package helpers;

import models.users.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by Enver on 1/29/2017.
 */
public class AutenticUser extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context context) {
        if (!context.session().containsKey("email")) {
            return null;
        }
        User user = SessionHelper.getCurrentUser(context);
        if (SessionHelper.user(user)) {
            return user.getEmail();
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        User user = SessionHelper.getCurrentUser(context);
        if (user != null) {
            if (user.getActive() < 1) {
                return redirect("/login");
            }
        }
        return redirect("/singUp");
    }
}
