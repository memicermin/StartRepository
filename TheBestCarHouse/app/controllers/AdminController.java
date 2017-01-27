package controllers;

import com.google.inject.Inject;
import helpers.Admin;
import helpers.HAT36N579;
import helpers.SessionHelper;
import models.users.User;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.admin_page;
import views.html.admin.interlopers;
import views.html.admin.user.edit_user;
import views.html.admin.user.user;
import views.html.admin.user_list;

import java.util.UUID;

/**
 * Created by Enver on 1/23/2017.
 */
public class AdminController extends Controller {

    @Inject
    FormFactory formFactory;

    @Security.Authenticated(Admin.class)
    public Result adminPage() {
        if (admin()) {
            return ok(admin_page.render("Hello"));
        }
        return redirect("/singUp");
    }

    public Result allUsers(){
        if (!admin()) {
            return redirect("/singUp");
        }
        return ok(user_list.render(User.find.all(), "All users"));
    }

    public Result user(Long id){
        if (!admin()) {
            return redirect("/singUp");
        }
        return ok(user.render(User.findById(id)));
    }

    public Result editUser(Long id){
        if (!admin()) {
            return redirect("/singUp");
        }
        return ok(edit_user.render(User.findById(id), formFactory.form(User.class)));
    }

    public Result updateUser(Long id){
        if (!admin()) {
            return redirect("/singUp");
        }
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        User user = User.findById(id);
        Integer active;
        try {
            active = Integer.parseInt(dynamicForm.get("active"));
        }catch (NumberFormatException e){
            return editUser(id);
        }
        if(active < 0 ){
            user.setActive(active);
            user.setVerification(-1);
            user.setUserLevel(-1);
            user.setGuest(1);
            user.setPremiumUser(-1);
            user.setToken(HAT36N579.getHat36(UUID.randomUUID().toString()));
            user.setLoginCount(-1);
        }else{
            int verification;
            int userLevel;
            int guest;
            int premiumUser;
            int loginCount;
            try {
                verification = Integer.parseInt(dynamicForm.get("verification"));
                userLevel = Integer.parseInt(dynamicForm.get("user_level"));
                guest = Integer.parseInt(dynamicForm.get("guest"));
                premiumUser = Integer.parseInt(dynamicForm.get("premium_user"));
                loginCount = Integer.parseInt(dynamicForm.get("login_count"));
            }catch (NumberFormatException e){
                return editUser(id);
            }
            user.setActive(active);
            user.setVerification(verification);
            user.setUserLevel(userLevel);
            user.setGuest(guest);
            user.setPremiumUser(premiumUser);
            user.setToken(dynamicForm.get("token"));
            user.setLoginCount(loginCount);
        }
        user.update();
        return user(id);
    }

    public Result getInterlopers() {
        if (!admin()) {
            return redirect("/singUp");
        }
        return ok(interlopers.render(User.getInterlopers(), "Interlopers"));
    }

    public Result deleteInterloper(Long id) {
        if (!admin()) {
            return redirect("/singUp");
        }
        User.findById(id).delete();
        return redirect("/admin");
    }

    public Result getBlockedUsers(){
        return ok(user_list.render(User.find.where().eq("active", 0).findList(), "Blocked users"));
    }

    public Result getBlockBlockUsers(){
        return ok(user_list.render(User.find.where().lt("active", 0).findList(), "Blocked users"));
    }

    public Result getUnverifiedUsers(){
        return ok(user_list.render(User.find.where().lt("verification", 0).findList(), "Unverified"));
    }

    @Security.Authenticated(Admin.class)
    public Result getPremiumUsers(){
        return ok(user_list.render(User.find.where().ge("premium_user", 10).findList(), "Premium"));
    }

    // Help methods

    private boolean admin() {
        try {
            User currentUser = SessionHelper.getCurrentUser(Http.Context.current());
            if (SessionHelper.admin(currentUser)) {
                return true;
            }
            if(currentUser.getPremiumUser() < -2){
                currentUser.setGuest(1);
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
