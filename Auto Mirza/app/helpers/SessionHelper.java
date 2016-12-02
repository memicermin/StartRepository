package helpers;

import models.User;
import play.mvc.Http;

/**
 * Created by Enver on 6/6/2016.
 */
public class SessionHelper {
/*
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
*/
    public static User getCurrentUser(Http.Context context) {
        String email = context.session().get("email");
        User user = User.findByEmail(email);
        if(user != null){
            return user;
        }
            return null;
    }


    public static boolean isAdmin(Http.Context context){
        String email = context.session().get("email");
        User user = User.findByEmail(email);
        if(user != null){
            if(user.getUserLevel() > 0){
                return true;
            }
        }
        return false;
    }


    public static boolean isAdminEmail(String email) {
        User admin = User.findByEmail(email);
        if (admin != null) {
            if (admin.getUserLevel() > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRegularAdmin(){
        User user = getCurrentUser(Http.Context.current());
        if(user.getUserLevel() >0){
            if(isAdminEmail(user.getEmail())){
                return true;
            }
        }
        return false;
    }

}
