package helpers;

import controllers.routes;
import models.users.User;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by Enver on 2/2/2017.
 */
public class UserSecyrity extends Security.Authenticator {
    @Override
    public String getUsername(Http.Context context) {
        if(!context.session().containsKey("email")){
            return null;
        }
        User user = SessionHelper.getCurrentUser(context);
        if(UserHelper.user(user.getId())){
            return user.getEmail();
        }
        return null;    }
    @Override
    public Result onUnauthorized(Http.Context context) {
        return redirect(routes.LoginController.singUp());
    }
}
