package controllers;

import com.google.inject.Inject;
import helpers.DateTimeHelper;
import helpers.MD5Hash;
import helpers.SessionHelper;
import helpers.UserSecyrity;
import models.users.User;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.user.edit_data;
import views.html.user.edit_user_data;

/**
 * Created by Enver on 2/2/2017.
 */
@Security.Authenticated(UserSecyrity.class)
public class UserController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result userPage(){
        User user = SessionHelper.getCurrentUser(ctx());
        if (user != null) {
            return ok(views.html.user.user.render(user));
        }
        return redirect("/login");
    }

    public Result userInfo() {
        User user = SessionHelper.getCurrentUser(ctx());
        if (user != null) {
            return ok(edit_user_data.render(user));
        }
        return redirect("/login");
    }

    public Result editUserData(Long id, String key) {
        return ok(edit_data.render(User.findById(id), key, formFactory.form(User.class)));
    }

    public Result updateUserData(Long id, String key) {
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        User user = User.findById(id);
        boolean dataChanged = false;
        if (key.equals("username")) {
            if (!user.getUsername().equals(dynamicForm.get("username"))) {
                user.setUsername(dynamicForm.get("username"));
                dataChanged = true;
            }
        }
        if (key.equals(User.PASSWORD)) {
            if(MD5Hash.getEncriptedPasswordMD5(dynamicForm.get(User.PASSWORD)).equals(user.getPassword())){
                return editUserData(user.getId(), User.NEW_PASSWORD);
            }
        }
        if (key.equals("name")) {
            String fName = dynamicForm.get("first_name");
            String lName = dynamicForm.get("last_name");
            if (!user.getFirstName().equals(fName)) {
                user.setFirstName(fName);
                dataChanged = true;
            }
            if (!user.getLastName().equals(lName)) {
                user.setLastName(lName);
                dataChanged = true;
            }
        }
        if (key.equals(User.LOCATION)) {
            if (!user.getLocation().equals(dynamicForm.get(User.LOCATION))) {
                user.setLocation(dynamicForm.get(User.LOCATION));
                dataChanged = true;
            }

        }
        if (key.equals(User.PHONE_NUMBER)) {
            if(!user.getPhoneNumber().equals(dynamicForm.get(User.PHONE_NUMBER))){
                user.setPhoneNumber(dynamicForm.get(User.PHONE_NUMBER));
                dataChanged = true;
            }
        }
        if(key.equals(User.NEW_PASSWORD)){
            String pass = MD5Hash.getEncriptedPasswordMD5(dynamicForm.get(User.PASSWORD));
            if(!pass.equals(user.getPassword())){
                user.setPassword(pass);
                dataChanged = true;
            }
        }
        if (dataChanged) {
            user.setUpdateDate(DateTimeHelper.getCurrentDateFormated(DateTimeHelper.DEFAULT_FORMAT));
            user.update();
            flash("success", "Data is changed");
            if(key.equals(User.NEW_PASSWORD)){
                return redirect("/singUp");
            }
        }
        return userInfo();
    }
}
