package controllers;

import com.google.inject.Inject;
import helpers.DateTimeHelper;
import helpers.SessionHelper;
import models.users.User;
import models.users.help_user_models.UserForLogin;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import resources.FlashMessages;
import views.html.user_control.verify_email;

/**
 * Created by Enver on 1/22/2017.
 */
public class LoginController extends Controller {

    @Inject
    FormFactory formFactory;


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

    public Result loginUser(){
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        dynamicForm.bindFromRequest(request());
        if (dynamicForm.hasErrors()) {
            return redirect("/login");
        }
        UserForLogin userForLogin = new UserForLogin();
        userForLogin.setEmail(dynamicForm.get("email"));
        userForLogin.setPassword(dynamicForm.get("password"));
        User user = User.getUserForLogin(userForLogin);

        if (user != null) {
            if(user.getUserType() < User.INACTIVE){
                clear();
                return redirect(routes.RegisterController.loginErr("Your account is blocked or deleted"));
            }
            if(user.getUserType() == User.INACTIVE){
                return redirect(routes.RegisterController.loginErr("Confirm your email address"));
            }
            if(user.getUserType() > User.INACTIVE){
                createSession(user);
                user.setLoginCount(user.getLoginCount() + 1);
                user.setLastLogin(DateTimeHelper.getCurrentDateFormated(DateTimeHelper.LOGIN_FORMAT));
                user.update();
            }
            return redirect("/");
        }else{
            flash("error", FlashMessages.LOGIN_FAIL);
            return redirect("login");
        }
    }

    public Result verifyEmail(Long id){
        User user = User.findById(id);
        return ok(verify_email.render(user, formFactory.form(User.class)));
    }

    public Result activateUser(Long id){
        User user = User.findById(id);
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        dynamicForm.bindFromRequest(request());
        if (dynamicForm.hasErrors()) {
            return redirect("/login");
        }
        if(dynamicForm.get("token").equals(user.getToken())){
            user.setUserType(2);
            createSession(user);
            user.setLoginCount(user.getLoginCount() + 1);
            user.update();
            flash("success", FlashMessages.LOGIN_SUCCESS);
        }else{
            singUp();
        }
        return redirect("/");
    }

    public Result confirmEmail(String token){
        User user = User.findByToken(token);
        if(user != null){
            singUp();
            if(user.getUserType() == 1){
                user.setUserType(2);
                createSession(user);
                user.setLoginCount(user.getLoginCount() + 1);
                user.update();
            }
        }
        return redirect("/");
    }
}
