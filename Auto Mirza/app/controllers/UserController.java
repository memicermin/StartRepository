package controllers;

import com.google.inject.Inject;
import helpers.DateTimeHelper;
import models.User;
import models.help_models.UserForLogin;
import models.help_models.UserForRegister;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import resources.FlashMessages;
import views.html.users.login;
import views.html.users.register;

/**
 * Created by Enver on 11/20/2016.
 */
public class UserController extends Controller {

    String ERROR_IMAGE = "https://thumbs.dreamstime.com/z/computer-virus-warning-sign-retro-style-vector-eps-34366261.jpg";


    @Inject
    FormFactory formFactory;

    public Result login(){
        return ok(login.render(formFactory.form(UserForLogin.class)));
    }

    public Result loginUser() {
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        dynamicForm.bindFromRequest(request());
        if (dynamicForm.hasErrors()) {
            return login();
        }
        UserForLogin userForLogin = new UserForLogin();
        userForLogin.setEmail(dynamicForm.get("email"));
        userForLogin.setPassword(dynamicForm.get("password"));

        User user = User.getUserForLogin(userForLogin);

        if (user != null) {
            if(user.getUserLevel() < 0){
                flash("error", "You are blocked");
                return redirect(routes.HomeController.index());
            }
            createSession(user);
            flash("success", FlashMessages.LOGIN_SUCCESS);
            return redirect("/");
        }else{
            flash("error", FlashMessages.LOGIN_FAIL);
            return login();
        }
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

        User temp = User.createNewUser(userForRegister);
        if (temp != null) {

            createSession(temp);
            flash("success", FlashMessages.REGISTRATION_SUCCESS);
            return redirect("/");


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
        return redirect(ERROR_IMAGE);
    }
}
