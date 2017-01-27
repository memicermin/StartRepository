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
        String email = context.session().get("email");
        User user = User.findByEmail(email);
        if(SessionHelper.admin(user)){
            return user.getEmail();
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        User user = User.findByEmail(context.session().get("email"));
        if(user != null){
            if(!SessionHelper.admin(user)){
                User.penalizeUser(user.getId());
            }
        }
        return redirect(routes.LoginController.login());
    }
}
