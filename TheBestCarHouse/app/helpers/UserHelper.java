package helpers;

import models.users.User;

/**
 * Created by Enver on 2/10/2017.
 */
public class UserHelper {


    public static boolean admin(User user){
        if(user != null){
            if(user.getUserType() != User.ADMIN){
                return false;
            }
            if(!HAT36N579.isHat36(user.getToken())){
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean mAdmin(User user){
        if(user != null){
            if(user.getUserType() != User.MADMIN){
                return false;
            }
            if(!HAT36N579.isHat36(user.getToken())){
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean user(User user){
        if(user != null){
            if(user.getUserType() > User.INACTIVE){
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean unactiveUser(User user) {
        if(user != null){
            if(user.getUserType() != User.INACTIVE){
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean blockedUser(User user){
        if(user != null){
            if(user.getUserType() != User.BLOCKED){
                return false;
            }
            return true;
        }
        return false;
    }

}
