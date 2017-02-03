package controllers;

import com.google.inject.Inject;
import helpers.Admin;
import helpers.HAT36N579;
import helpers.SessionHelper;
import models.users.User;
import notifiers.Emails;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.admin_page;
import views.html.admin.interlopers;
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
        Integer active;
        try {
            active = Integer.parseInt(dynamicForm.get("active"));
        } catch (NumberFormatException e) {
            return editUser(id);
        }
        if (User.adminUpdateNotes(id, " \nUpdated admin \"" + SessionHelper.getCurrentUser(ctx()).getEmail() + "\" Ex data is: ")) {
            if (active < 0) {
                user.setActive(active);
                user.setVerification(-1);
                user.setUserLevel(-1);
                user.setGuest(1);
                user.setPremiumUser(-1);
                user.setToken(HAT36N579.getHat36(UUID.randomUUID().toString()));
                user.setLoginCount(-1);
            } else {
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
                } catch (NumberFormatException e) {
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
        flash("error", "User Update Notes Error");
        return editUser(id);
    }

    public Result blockUser(Long id) {
        User user = User.findById(id);
        User.adminUpdateNotes(id, " Blocked admin \" " + SessionHelper.getCurrentUser(ctx()).getEmail() + "\" Ex data is: ");
        user.setActive(0);
        user.setVerification(-1);
        user.setGuest(1);
        user.setUserLevel(-1);
        user.setPremiumUser(0);
        user.setLoginCount(0);
        user.update();
        Emails.sendtest(user.getEmail(), "You are activate");
        return user(user.getId());
    }

    public Result blockBlockUser(Long id) {
        User user = User.findById(id);
        User.adminUpdateNotes(id, " BlockedBlocked admin \" " + SessionHelper.getCurrentUser(ctx()).getEmail() + "\" Ex data is: ");
        user.setActive(-1);
        user.setVerification(-1);
        user.setGuest(1);
        user.setUserLevel(-1);
        user.setPremiumUser(-1);
        user.setLoginCount(0);
        user.update();
        return user(user.getId());
    }

    public Result activateUser(Long id) {
        User user = User.findById(id);
        User.adminUpdateNotes(id, " Activated admin \" " + SessionHelper.getCurrentUser(ctx()).getEmail() + "\" Ex data is: ");
        user.setActive(1);
        user.setVerification(0);
        user.setGuest(0);
        user.setUserLevel(0);
        user.setPremiumUser(0);
        user.setLoginCount(0);
        user.setToken(HAT36N579.getHat36(UUID.randomUUID().toString()));
        user.update();
        Emails.sendtest(user.getEmail(), "You are activate");
        return user(user.getId());
    }

    public Result adminPage() {
        return ok(admin_page.render("Hello"));
    }

    public Result allUsers() {
        return ok(user_list.render(User.find.all(), "All users"));
    }


    public Result getInterlopers() {
        return ok(interlopers.render(User.getInterlopers(), "Interlopers"));
    }

    public Result getBlockedUsers() {
        return ok(user_list.render(User.find.where().eq("active", 0).findList(), "Blocked users"));
    }

    public Result getBlockBlockUsers() {
        return ok(user_list.render(User.find.where().lt("active", 0).findList(), "Blocked users"));
    }


    public Result getUnverifiedUsers() {
        return ok(user_list.render(User.find.where().le("verification", 0).findList(), "Unverified"));
    }

    public Result getPremiumUsers() {
        return ok(user_list.render(User.find.where().ge("premium_user", 10).where().le("premium_user", 99).findList(), "Premium"));
    }

    public Result getAdms() {
        List<User> adms = User.find.where().ge("user_level", 1000).where().le("guest", -1).findList();
        for (int i = 0; i < adms.size(); i++) {
            if (!SessionHelper.admin(adms.get(i))) {
                adms.remove(i);
            }
        }
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
        if (!SessionHelper.mAdmin(admin)) {
            User.adminUpdateNotes(id, " \nUnautorized admin: " + SessionHelper.getCurrentUser(ctx()).getEmail() + "\" Ex data is: ");
            admin.setGuest(0);
            admin.setUserLevel(0);
            admin.setToken(HAT36N579.getHat36(UUID.randomUUID().toString()));
            admin.update();
        }else{
            User currentUser;
            try {
                currentUser = SessionHelper.getCurrentUser(ctx());
                User.adminUpdateNotes(currentUser.getId(), " \nUnauthorized by APP, Besause " +
                        currentUser.getUsername()  + " tried to delete the main admin: " + admin.getUsername() + ". EX data is: ");
                currentUser.setUserLevel(0);
                currentUser.setPremiumUser(0);
                currentUser.setGuest(0);
                currentUser.setToken(HAT36N579.getHat36(UUID.randomUUID().toString()));
                currentUser.update();

            }catch (Exception e){
                return redirect("/");
            }
        }
        return user(admin.getId());
    }

    public Result deleteInterloper(Long id) {
        User.findById(id).delete();
        return redirect("/admin");
    }
}
