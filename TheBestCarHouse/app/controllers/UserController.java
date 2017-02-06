package controllers;

import helpers.UserSecyrity;
import models.users.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.user_control.verify_email;

/**
 * Created by Enver on 2/2/2017.
 */
@Security.Authenticated(UserSecyrity.class)
public class UserController extends Controller {





}
