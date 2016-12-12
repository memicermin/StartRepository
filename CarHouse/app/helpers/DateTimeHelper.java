package helpers;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Enver on 12/12/2016.
 */
public class DateTimeHelper extends DateTimeFormat {

    public static String getFormattedDateTime(DateTime dateTime, String format){
        DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
        return dtf.print(dateTime);
    }

    public static String getCurrentFormattedDateTime(String format){
        return getFormattedDateTime(new DateTime(), format);
    }

}
