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
        if(UserHelper.admin(user.getId()) || UserHelper.mAdmin(user.getId())){
            return user.getEmail();
        }
        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return redirect(routes.LoginController.singUp());
    }

    public static boolean isAdmin(){
        User currentUser = SessionHelper.getCurrentUser(Http.Context.current());
        if(currentUser != null){
            if(UserHelper.admin(currentUser.getId()) || UserHelper.mAdmin(currentUser.getId())){
                return true;
            }
        }
        return false;
    }
}
