package helpers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Enver on 11/9/2016.
 */
public class Help {




    public static List<Integer> getLastHundredYears(){
        List<Integer> years = new ArrayList<>();
       Integer year = Calendar.getInstance().get(Calendar.YEAR);
        for(int i = 0; i < 100; i++){
            years.add(year - i);
        }
        return years;
    }


}
