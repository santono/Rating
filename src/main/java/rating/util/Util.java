package rating.util;


import org.springframework.format.datetime.DateFormatter;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Util {
    private static int currentYear=2018;
    public static String setPrecision(double amt, int precision){
        return String.format("%." + precision + "f", amt);
    }
    public static String getCurrentDate() {
           GregorianCalendar gc=new GregorianCalendar();
//           DateFormatter df=new DateFormatter("dd.mm.YYYY");
           SimpleDateFormat dt1 = new SimpleDateFormat("dd.MM.yy");
           String s=dt1.format(gc.getTime());

           return s;        
    }
    public static int getCurrentYear() {
        return currentYear;
    }

}
