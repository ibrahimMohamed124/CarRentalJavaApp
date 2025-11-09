//package utils.localization;
//
//
//import java.util.*;
//
//public class LanguageManager {
//
//    private static Locale currentLocale = new Locale("en");
//    private static ResourceBundle bundle = ResourceBundle.getBundle("localization.messages", currentLocale);
//
//    public static void setLanguage(String langCode) {
//        currentLocale = new Locale(langCode);
//        bundle = ResourceBundle.getBundle("localization.messages", currentLocale);
//    }
//
//    public static String get(String key) {
//        return bundle.getString(key);
//    }
//
//    public static Locale getCurrentLocale() {
//        return currentLocale;
//    }
//
//    public static boolean isArabic() {
//        return "ar".equals(currentLocale.getLanguage());
//    }
//}
