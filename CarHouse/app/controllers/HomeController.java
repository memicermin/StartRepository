package controllers;

import helpers.DateTimeHelper;
import org.joda.time.DateTime;
import play.Logger;
import play.mvc.*;
import resources.patterns.DateTimePatterns;
import views.html.*;

public class HomeController extends Controller {

    public Result index() {
        Logger.info(DateTimeHelper.getFormattedDateTime(new DateTime(), DateTimePatterns.DATE_TIME));
        Logger.info(DateTimeHelper.getFormattedDateTime(new DateTime(), DateTimePatterns.DAY_DATE_TIME));
        return ok(index.render("Welcome"));
    }

}
