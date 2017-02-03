package controllers;

import helpers.UserSecyrity;
import play.mvc.Controller;
import play.mvc.Security;

/**
 * Created by Enver on 2/2/2017.
 */
@Security.Authenticated(UserSecyrity.class)
public class UserController extends Controller {



}
