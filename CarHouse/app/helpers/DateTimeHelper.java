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
        int currentDay = Integer.parseInt(getCurrentFormattedDateTime("dd"));
        int currentMonth = Integer.parseInt(getCurrentFormattedDateTime("MM"));
        int currentYear = Integer.parseInt(getCurrentFormattedDateTime("yyyy"));

        birthDate = DateTimeHelper.formatedBirthDate(birthDate);
        String[] dates = birthDate.split("\\.");
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
