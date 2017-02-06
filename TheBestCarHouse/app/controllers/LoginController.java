package controllers;

import com.google.inject.Inject;
import helpers.DateTimeHelper;
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
            if(user.getActive() < 0){
                clear();
                return redirect("errlog");
            }
            if(user.getActive() == 0){
                return redirect(routes.LoginController.verifyEmail(user.getId()));
            }
            createSession(user);
            user.setLoginCount(user.getLoginCount() + 1);
            user.setLastLogin(DateTimeHelper.getCurrentDateFormated(DateTimeHelper.LOGIN_FORMAT));
            user.update();
            //flash("success", FlashMessages.LOGIN_SUCCESS);
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
            user.setActive(1);
            user.setVerification(1);
            createSession(user);
            user.setLoginCount(user.getLoginCount() + 1);
            user.update();
            flash("success", FlashMessages.LOGIN_SUCCESS);
        }else{
            singUp();
        }
        return redirect("/");
    }

}
