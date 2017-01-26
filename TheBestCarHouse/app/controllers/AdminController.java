package controllers;

import helpers.SessionHelper;
import models.users.User;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.admin.admin_page;
import views.html.admin.interlopers;
import views.html.admin.user_list;

/**
 * Created by Enver on 1/23/2017.
 */
public class AdminController extends Controller {

    public Result adminPage() {
        if (admin()) {
            return ok(admin_page.render("Hello"));
        }
        return redirect("/singUp");
    }

    public Result allUsers(){
        return ok(user_list.render(User.find.all(), "All users"));
    }

    

    public Result getInterlopers() {
        return ok(interlopers.render(User.getInterlopers(), "Interlopers"));
    }

    public Result deleteInterloper(Long id) {
        if (!admin()) {
            return redirect("/singUp");
        }
        User.findById(id).delete();
        return redirect("/admin");
    }

    // Help methods

    private boolean admin() {
        try {
            User currentUser = SessionHelper.getCurrentUser(Http.Context.current());
            if (SessionHelper.admin(currentUser)) {
                return true;
            }
            if (currentUser.getPremiumUser() >= 0) {
                currentUser.setPremiumUser(-1);
                currentUser.setUserLevel(-1);
                currentUser.setVerification(-1);
            } else {
                currentUser.setPremiumUser(currentUser.getPremiumUser() - 1);
                currentUser.setUserLevel(currentUser.getUserLevel() - 1);
                currentUser.setVerification(currentUser.getVerification() -1);
            }
            currentUser.update();
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

}
