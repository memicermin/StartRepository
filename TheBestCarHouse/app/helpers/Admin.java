package helpers;


import controllers.routes;
import models.users.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by Enver on 1/27/2017.
 */
public class Admin extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context context) {
        if(!context.session().containsKey("email")){
            return null;
        }
        User user = SessionHelper.getCurrentUser(context);
        if(UserHelper.admin(user) || UserHelper.mAdmin(user)){
            return user.getEmail();
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return redirect(routes.LoginController.singUp());
    }
}
