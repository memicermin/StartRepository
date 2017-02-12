package controllers;

import com.google.inject.Inject;
import helpers.DateTimeHelper;
import models.users.User;
import models.users.help_user_models.UserForLogin;
import models.users.help_user_models.UserForRegister;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import resources.FlashMessages;
import views.html.error.error_login;
import views.html.user_control.login;
import views.html.user_control.register_user;

/**
 * Created by Enver on 1/22/2017.
 */
public class RegisterController extends Controller {


    @Inject
    FormFactory formFactory;


    public Result login(){
        return ok(login.render(formFactory.form(UserForLogin.class)));
    }

    public Result loginErr(String message){
        return ok(error_login.render(message));
    }


    public Result register(){
        return ok(register_user.render(formFactory.form(UserForRegister.class)));
    }

    public Result registerUser(){
        Form<UserForRegister> submitted = formFactory.form(UserForRegister.class).bindFromRequest();
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        dynamicForm.bindFromRequest(request());

        if (dynamicForm.hasErrors()) {
            return badRequest(register_user.render(submitted));
        }
        if (dynamicForm.get("gender").equals("not_selecteed")) {
            flash("wrong", FlashMessages.GENDER_NOT_SELECTED);
            return badRequest(register_user.render(submitted));
        }

        if (!dynamicForm.get("password").equals(dynamicForm.get("password_again"))) {
            flash("error", FlashMessages.PASSWORD_WRONG);
            return badRequest(register_user.render(submitted));
        }

        if (!DateTimeHelper.isRealsAge(dynamicForm.get("birth_date"))) {
            flash("warning", FlashMessages.AGE_WRONG);
            return badRequest(register_user.render(submitted));
        }

        UserForRegister userForRegister = UserForRegister.createUser(dynamicForm);

        User temp = User.createNewUser(userForRegister);
        if (temp != null) {

            return redirect("/login");
        }
        flash("error", FlashMessages.REGISTRATION_FAIL);
        return ok();
    }

}
