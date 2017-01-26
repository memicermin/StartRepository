package controllers.user_controller;

import com.google.inject.Inject;
import helpers.DateTimeHelper;
import helpers.FlashMessages;
import models.users.User;
import models.users.help_models.UserForLogin;
import models.users.help_models.UserForRegister;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.user.login;
import views.html.user.register;

/**
 * Created by Enver on 12/12/2016.
 */
public class LoginRegisterController extends Controller {


    @Inject
    FormFactory formFactory;


    public Result login(){
        return ok(login.render(formFactory.form(UserForLogin.class)));
    }

    public Result register(){
        return ok(register.render(formFactory.form(UserForRegister.class)));
    }

    public Result registerUser(){
        Form<UserForRegister> submitted = formFactory.form(UserForRegister.class).bindFromRequest();
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        dynamicForm.bindFromRequest(request());

        if (dynamicForm.hasErrors()) {
            return badRequest(register.render(submitted));
        }
        if (dynamicForm.get("gender").equals("not_selecteed")) {
            flash("wrong", FlashMessages.GENDER_NOT_SELECTED);
            return badRequest(register.render(submitted));
        }

        if (!dynamicForm.get("password").equals(dynamicForm.get("password_again"))) {
            flash("error", FlashMessages.PASSWORD_WRONG);
            return badRequest(register.render(submitted));
        }

        if (!DateTimeHelper.isRealsAge(dynamicForm.get("birth_date"))) {
            flash("warning", FlashMessages.AGE_WRONG);
            return badRequest(register.render(submitted));
        }

        UserForRegister userForRegister = UserForRegister.createUser(dynamicForm);
        if(userForRegister != null){
            User temp = User.createNewUser(userForRegister);
            if (temp != null) {
                flash("success", FlashMessages.REGISTRATION_SUCCESS);
                createSession(temp);
                return redirect("/");
            }
        }
        flash("error", FlashMessages.REGISTRATION_FAIL);
        return redirect("/");
    }

    private void createSession(User user) {
        session().clear();
        session().put("email", user.getEmail());
    }

    public Result singUp() {
        session().clear();
        session().remove("email");
        response().discardCookie("email");
        response().cookies().clear();
        return redirect("https://www.facebook.com/");
    }
}
