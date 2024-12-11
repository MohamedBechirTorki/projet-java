package admin_app;

public class ThemeManager {
    private static boolean isDark = false;  

    public static boolean isDarkMode() {
        return isDark;
    }

    public static void toggleTheme() {
        isDark = !isDark;
    }
}
