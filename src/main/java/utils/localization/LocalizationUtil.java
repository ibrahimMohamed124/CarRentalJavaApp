package utils.localization;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.ComponentOrientation;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class LocalizationUtil {
    private static ResourceBundle bundle;
    private static String currentLang = "en"; // اللغة الحالية

    // 🔹 ضبط اللغة (عربي / إنجليزي)
    public static void setLanguage(String langCode) {
        currentLang = langCode;
        Locale locale = switch (langCode) {
            case "ar" -> new Locale("ar", "EG");
            default -> new Locale("en", "US");
        };
        bundle = getUtf8Bundle("messages", locale);
    }

    // 🔹 تحميل ملف الترجمة بـ UTF-8
    private static ResourceBundle getUtf8Bundle(String baseName, Locale locale) {
        try {
            String resourceName = baseName + "_" + locale.getLanguage() + ".properties";
            var stream = LocalizationUtil.class.getClassLoader().getResourceAsStream(resourceName);
            if (stream == null)
                stream = LocalizationUtil.class.getClassLoader().getResourceAsStream(baseName + ".properties");
            if (stream == null)
                throw new RuntimeException("Missing resource file: " + resourceName);
            return new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Error loading UTF-8 resource bundle", e);
        }
    }

    // 🔹 جلب النص المترجم من ملف .properties
    public static String get(String key) {
        if (bundle == null) setLanguage("en");
        return bundle.getString(key);
    }

    // 🔹 تطبيق خط Tahoma على كل مكونات الواجهة
    public static void applyFont(Component comp) {
        if (comp == null) return;

        Font tahomaFont = new Font("Tahoma", Font.PLAIN, 14);
        comp.setFont(tahomaFont);

        if (comp instanceof Container container) {
            for (Component child : container.getComponents()) {
                applyFont(child);
            }
        }
    }

    // 🔹 تطبيق اللغة والاتجاه والخط على الواجهة
    public static void applyLocaleSettings(Component comp, String langCode) {
        applyFont(comp);
        if ("ar".equalsIgnoreCase(langCode)) {
            comp.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        } else {
            comp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        }
    }

    // 🔹 استرجاع اللغة الحالية
    public static String getCurrentLanguage() {
        return currentLang;
    }
}
