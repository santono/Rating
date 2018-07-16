package rating.util;


public class ShortFio {
    public static String getShortFio(String fio) {
        String retVal;
        String[] tmp; 
        String f=fio;
        while (f.contains("  ")) {
            f=f.replaceAll("  "," ");
        }
        tmp=f.split(" ");
        if (tmp.length==3) {
          retVal=tmp[0]+" "+tmp[1].charAt(0)+". "+tmp[2].charAt(0)+".";
        } else {
          retVal=fio;
        }
        return retVal;
    }
}
