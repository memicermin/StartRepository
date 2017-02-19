package helpers;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Enver on 1/22/2017.
 */
public class DateTimeHelper extends DateTimeFormat {

    public static final String DEFAULT_FORMAT = "EEE, dd.MM.yyyy - HH:mm";
    public static final String LOGIN_FORMAT = "HH:mm.EEE.dd.MM.yyyy";

    public static String getCurrentDateFormated(String format) {
        //"EEE, dd.MM.yyyy - HH:mm"
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String getDateFormated(String format, DateTime dateTime) {
        //"EEE, dd.MM.yyyy - HH:mm"
        DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
        return dtf.print(dateTime);
    }

    public static String printDateTime() {
        DateTime dt = new DateTime();
        DateTimeFormatter dtf = DateTimeFormat.forPattern(DEFAULT_FORMAT);
        return dtf.print(dt);
    }


    public static String formatedBirthDate(String birthDate) {
        String[] arrDate;
        if (birthDate.contains("-")) {
            arrDate = birthDate.split("-");
        } else if (birthDate.contains(".")) {
            arrDate = birthDate.split(".");
        } else if (birthDate.contains("/")) {
            arrDate = birthDate.split("/");
        } else {
            return birthDate;
        }
        return arrDate[2] + "." + arrDate[1] + "." + arrDate[0];
    }

    public static boolean isRealsAge(String birthDate) {
        int currentDay = Integer.parseInt(getCurrentDateFormated("dd"));
        int currentMonth = Integer.parseInt(getCurrentDateFormated("MM"));
        int currentYear = Integer.parseInt(getCurrentDateFormated("yyyy"));
//
//            birthDate = DateTimeHelper.formatedBirthDate(birthDate);
//            String[] dates = birthDate.split("\\.");
//            int year = Integer.parseInt(dates[2]);
//            int month = Integer.parseInt(dates[1]);
//            int day = Integer.parseInt(dates[0]);

        String[] dates = birthDate.split("-");
        int year = Integer.parseInt(dates[2]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[0]);

        if (year > (currentYear - 80) && (year + 18) < currentYear) {
            return true;
        }

        if (year == (currentYear - 80)) {
            if (month > currentMonth) {
                return true;
            } else if (month == currentMonth) {
                if (day > currentDay) {
                    return true;
                }
            }
        }

        if ((year + 18) == currentYear) {
            if (month < currentMonth) {
                return true;
            } else if (month == currentMonth) {
                if (day < currentDay) {
                    return true;
                }
            }
        }


        return false;
    }
}
