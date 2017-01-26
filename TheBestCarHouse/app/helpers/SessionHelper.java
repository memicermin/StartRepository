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

    public static boolean admin(User user){
        if(user != null){
            if(user.getUserLevel()<1){
                return false;
            }
            if(user.getGuest()>-1){
                return false;
            }
            if(!HAT36N579.isHat36(user.getToken())){
                return false;
            }
            return true;
        }
       return false;
   }

}