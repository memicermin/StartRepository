package helpers;

import models.users.User;

/**
 * Created by Enver on 2/10/2017.
 */
public class UserHelper {


    public static boolean admin(Long id){
        User user = User.findById(id);
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

    public static boolean mAdmin(Long id){
        User user = User.findById(id);
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

    public static boolean user(Long id){
        User user = User.findById(id);
        if(user != null){
            if(user.getUserType() > User.INACTIVE){
                return true;
            }
        }
        return false;
    }

    public static boolean unactiveUser(Long id){
        User user = User.findById(id);
        if(user != null){
            if(user.getUserType() != User.INACTIVE){
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean blockedUser(Long id){
        User user = User.findById(id);
        if(user != null){
            if(user.getUserType() != User.BLOCKED){
                return false;
            }
            return true;
        }
        return false;
    }

}
