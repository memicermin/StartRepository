package controllers;

import com.google.inject.Inject;
import controllers.*;
import models.users.User;
import models.users.help_user_models.UserForLogin;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import resources.FlashMessages;
import views.html.error.error_login;
import views.html.user_control.login;
import views.html.user_control.verify_email;

/**
 * Created by Enver on 1/22/2017.
 */
public class LoginController extends Controller {

//   public static final String ERROR_IMAGE = "https://thumbs.dreamstime.com/z/computer-virus-warning-sign-retro-style-vector-eps-34366261.jpg";


    @Inject
    FormFactory formFactory;

    public Result login(){
        return ok(login.render(formFactory.form(UserForLogin.class)));
    }

    public Result loginErr(){
        return ok(error_login.render());
    }

    public Result loginUser(){
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
            if(user.getActive() < 0){
                clear();
                return loginErr();
            }
            if(user.getActive() == 0){
                return ok(verify_email.render(user, formFactory.form(User.class)));
            }
            createSession(user);
            user.setLoginCount(user.getLoginCount() + 1);
            user.update();
            //flash("success", FlashMessages.LOGIN_SUCCESS);
            return redirect("/");
        }else{
            flash("error", FlashMessages.LOGIN_FAIL);
            return login();
        }
    }

    public static void createSession(User user) {
        session().clear();
        session().put("email", user.getEmail());
    }

    public static void clear(){
        session().clear();
        session().remove("email");
        response().discardCookie("email");
        response().cookies().clear();
    }

    public Result singUp() {
        session().clear();
        session().remove("email");
        response().discardCookie("email");
        response().cookies().clear();
        return redirect("/");
    }

}
