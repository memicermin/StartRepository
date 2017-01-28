package helpers;

import java.util.Random;

/**
 * Created by Enver on 1/24/2017.
 */
public class HAT36N579 {

    public static String getHat36(String string){
        Random rnd = new Random();
        int num = rnd.nextInt(90)+10;
        String s = "";
        if(pr_n(num)){
            num ++;
        }
        s = s += num;
        String second = "";
        for (int i = 0; i < 4; i++) {
            if(i==1){
                second+=s.charAt(i);
            }else if(i==3){
                second+=s.charAt(0);
            }else{
                second+=(getRndCh());
            }
        }
        return string.substring(0, 14) + second + string.substring(18, 36);
    }

    private static char getRndCh() {
        Random rnd = new Random();
        return (char) (rnd.nextInt(26)+97);
    }

    private static boolean pr_n(int num){
        boolean bol = true;
        for (int i = 2; i < num; i++) {
            if(num%i == 0){
                bol = false;
            }
        }
        return bol;
    }

    public static boolean isHat36(String string){
        String a = "";
        a += string.charAt(17);
        a += string.charAt(15);
        int num;
        try {
            num = Integer.parseInt(a);
        }catch (NumberFormatException e){
            return false;
        }
        if(pr_n(num)){
            return true;
        }
        return false;
    }
}
