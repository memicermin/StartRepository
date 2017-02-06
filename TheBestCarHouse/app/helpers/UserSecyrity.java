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
        if(SessionHelper.user(user)){
            return user.getEmail();
        }
        return null;    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return redirect(routes.LoginController.singUp());
    }

    public static boolean isUnverified (Long id){
        User user = User.findById(id);
        if(user.getActive() == 0 && user.getVerification() == 0){
            return true;
        }
        return false;
    }

    public static boolean isBlocked (Long id){
        User user = User.findById(id);
        if(user.getGuest() == 1){
            if(user.getVerification() == -1 && user.getUserLevel() == -1){
                if(user.getActive() == 0 && user.getLoginCount() == 0){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isBlockBlocked(Long id){
        User user = User.findById(id);
        if(user.getGuest() == 1){
            if(user.getVerification() < -1 && user.getUserLevel() < -1){
                if(user.getActive() < 0 && user.getPremiumUser() < 0){
                    return true;
                }
            }
        }
        return false;
    }
}
