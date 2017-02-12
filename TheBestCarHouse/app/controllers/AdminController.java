package controllers;

import com.google.inject.Inject;
import helpers.Admin;
import helpers.HAT36N579;
import helpers.SessionHelper;
import helpers.UserHelper;
import models.users.User;
import notifiers.Emails;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.admin_page;
import views.html.admin.user.edit_user;
import views.html.admin.user.user;
import views.html.admin.user_list;

import java.util.List;
import java.util.UUID;

/**
 * Created by Enver on 1/23/2017.
 */
@Security.Authenticated(Admin.class)
public class AdminController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result updateUser(Long id) {
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        User user = User.findById(id);
        if (User.adminUpdateNotes(id, " \nUpdated admin \"" + SessionHelper.getCurrentUser(ctx()).getEmail() + "\" Ex data is: ")) {

            int userType;
            int premiumUser;
            int loginCount;
            try {
                userType = Integer.parseInt(dynamicForm.get("user_type"));
                premiumUser = Integer.parseInt(dynamicForm.get("premium_user"));
                loginCount = Integer.parseInt(dynamicForm.get("login_count"));
            } catch (NumberFormatException e) {
                return editUser(id);
            }

            user.setUserType(userType);
            user.setPremiumUser(premiumUser);
            user.setToken(dynamicForm.get("token"));
            user.setLoginCount(loginCount);

            user.update();
            return user(id);
        }
        flash("error", "User Update Notes Error");
        return editUser(id);
    }

    public Result unverifiedUser(Long id) {
        User user = User.findById(id);
        User.adminUpdateNotes(id, " Unverified admin \" " + SessionHelper.getCurrentUser(ctx()).getEmail() + "\" Ex data is: ");
        user.setUserType(1);
        user.setToken(HAT36N579.getHat36(UUID.randomUUID().toString()));
        user.update();
        Emails.confirmToken(user.getEmail(), user.getToken(), "Vasa email adresa nije verifikovana");
        return user(user.getId());
    }

    public Result blockUser(Long id) {
        User user = User.findById(id);
        User.adminUpdateNotes(id, " Blocked admin \" " + SessionHelper.getCurrentUser(ctx()).getEmail() + "\" Ex data is: ");
        user.setUserType(0);
        user.setToken(HAT36N579.getHat36(UUID.randomUUID().toString()));
        user.update();
        return user(user.getId());
    }

    public Result deblockUser(Long id) {
        User user = User.findById(id);
        User.adminUpdateNotes(id, " Deblocked admin \" " + SessionHelper.getCurrentUser(ctx()).getEmail() + "\" Ex data is: ");
        user.setUserType(1);
        user.setPremiumUser(0);
        user.setLoginCount(0);
        user.setToken(HAT36N579.getHat36(UUID.randomUUID().toString()));
        user.update();
        Emails.confirmToken(user.getEmail(), user.getToken(), "Izvrsena je deblokada vaseg racuna");
        return user(user.getId());
    }

    public Result activateUser(Long id) {
        User user = User.findById(id);
        User.adminUpdateNotes(id, " Activated admin \" " + SessionHelper.getCurrentUser(ctx()).getEmail() + "\" Ex data is: ");
        user.setUserType(2);
        user.setToken(HAT36N579.getHat36(UUID.randomUUID().toString()));
        user.update();
        Emails.sendEmailMessage(user.getEmail(), "Your account has been reactivated.");
        return user(user.getId());
    }

    public Result deleteUser(Long id){
        User.findById(id).delete();
        return allUsers();
    }

    public Result adminPage() {
        return ok(admin_page.render("Hello"));
    }

    public Result allUsers() {
        return ok(user_list.render(User.find.all(), "All users"));
    }

    public Result getBlockedUsers() {
        return ok(user_list.render(User.find.where().eq("user_type", 0).findList(), "Blocked users"));
    }

    public Result getUnverifiedUsers() {
        return ok(user_list.render(User.find.where().eq("user_type", 1).findList(), "Unverified"));
    }

    public Result getPremiumUsers() {
        return ok(user_list.render(User.find.where().ge("premium_user", 10).where().le("premium_user", 99).findList(), "Premium"));
    }

    public Result getAdms() {
        List<User> adms = User.find.where().ge("user_type", User.ADMIN).findList();
        return ok(user_list.render(adms, "Adms"));
    }

    public Result user(Long id) {
        return ok(user.render(User.findById(id)));
    }

    public Result editUser(Long id) {
        return ok(edit_user.render(User.findById(id), formFactory.form(User.class)));
    }

    /**
     * This method eliminates autority of admin.
     * The method can not eliminate authority of main admin,
     * but if admin tries to apply this method on the main admin,
     * being to blocked;
     *
     * @param id
     * @return
     */
    public Result unauthorityAdmin(Long id) {
        User admin;
        try {
            admin = User.findById(id);
        } catch (Exception e) {
            return redirect("/admin");
        }
        if (!UserHelper.mAdmin(admin.getId())) {
            User.adminUpdateNotes(id, " \nUnautorized admin: " + SessionHelper.getCurrentUser(ctx()).getEmail() + "\" Ex data is: ");
            admin.setUserType(2);
            admin.setToken(HAT36N579.getHat36(UUID.randomUUID().toString()));
            admin.update();
        } else {
            User currentUser;
            try {
                currentUser = SessionHelper.getCurrentUser(ctx());
                User.adminUpdateNotes(currentUser.getId(), " \nUnauthorized by APP, Besause " +
                        currentUser.getUsername() + " tried to delete the main admin: " + admin.getUsername() + ". EX data is: ");
                currentUser.setUserType(1);
                currentUser.setPremiumUser(0);
                currentUser.setToken(HAT36N579.getHat36(UUID.randomUUID().toString()));
                currentUser.update();
            } catch (Exception e) {
                return redirect("/");
            }
        }
        return user(admin.getId());
    }
}
