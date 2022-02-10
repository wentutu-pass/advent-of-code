package common;

public class StringUtil {
    public static boolean issLowerStr(String str){
        boolean isLower = true;
        for(String letter: str.split("")){
            if (!letter.matches("[a-z]")) {
                isLower = false;
                break;
            }
        }
        return isLower;

    }

}
