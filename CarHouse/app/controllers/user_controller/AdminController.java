package controllers.user_controller;

import helpers.Authorization;
import play.mvc.Controller;
import play.mvc.Security;

/**
 * Created by Enver on 12/12/2016.
 */
@Security.Authenticated(Authorization.Admin.class)
public class AdminController extends Controller {

}
