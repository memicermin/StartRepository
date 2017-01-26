package helpers;

import models.users.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import static play.mvc.Controller.flash;
import static play.mvc.Controller.session;

/**
 * Created by Enver on 12/12/2016.
 */
public class Authorization {

    public static class Admin extends Security.Authenticator {

        @Override
        public String getUsername(Http.Context context){
            if (!context.session().containsKey("username")) {
                return null;
            }
            User temp = User.findByEmail(context.session().get("username"));
            if(temp != null){
               if(temp.getUserLevel() > 0 && temp.getPremiumUser() > 1000 && temp.getLoginCount() > 0 && temp.getVerification() > 0){
                   return temp.getEmail();
               }
            }
            return null;
        }

        @Override
        public Result onUnauthorized(Http.Context context) {
            session().clear();
            flash("warning", "You don't have right permissions. You've been logged out.");
            return redirect("/login");
        }
    }
}
