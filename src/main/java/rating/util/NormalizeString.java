package rating.util;


public class NormalizeString {
    public static String executeNormalizeString(String text) {
        String retVal;
//        String text = "Это  текст!       \t\t\t\t\t Тут сплошные  пробелы  :)";
//        System.out.println("befor="+text);
        if (text!=null)
           retVal=text.replaceAll("[\\s]{2,}", " ");
        else
           retVal=null;
//        System.out.println("after="+text);
        return retVal;
    }
}
