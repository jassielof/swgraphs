package com.jgoodies.looks;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticTheme;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public final class LookUtils {
  private static final String JAVA_VERSION = getSystemProperty("java.version");
  
  private static final String OS_NAME = getSystemProperty("os.name");
  
  private static final String OS_VERSION = getSystemProperty("os.version");
  
  public static final boolean IS_JAVA_1_4 = startsWith(JAVA_VERSION, "1.4");
  
  public static final boolean IS_JAVA_1_4_0 = startsWith(JAVA_VERSION, "1.4.0");
  
  public static final boolean IS_JAVA_1_4_2_OR_LATER = (!startsWith(JAVA_VERSION, "1.4.0") && !startsWith(JAVA_VERSION, "1.4.1"));
  
  public static final boolean IS_JAVA_5 = startsWith(JAVA_VERSION, "1.5");
  
  public static final boolean IS_JAVA_5_OR_LATER = !IS_JAVA_1_4;
  
  public static final boolean IS_JAVA_6 = startsWith(JAVA_VERSION, "1.6");
  
  public static final boolean IS_JAVA_6_OR_LATER = (!IS_JAVA_1_4 && !IS_JAVA_5);
  
  public static final boolean IS_JAVA_1_4_OR_5 = (IS_JAVA_1_4 || IS_JAVA_5);
  
  public static final boolean IS_OS_FREEBSD = startsWithIgnoreCase(OS_NAME, "FreeBSD");
  
  public static final boolean IS_OS_LINUX = startsWithIgnoreCase(OS_NAME, "Linux");
  
  public static final boolean IS_OS_OS2 = startsWith(OS_NAME, "OS/2");
  
  public static final boolean IS_OS_MAC = startsWith(OS_NAME, "Mac");
  
  public static final boolean IS_OS_WINDOWS = startsWith(OS_NAME, "Windows");
  
  public static final boolean IS_OS_WINDOWS_MODERN = (startsWith(OS_NAME, "Windows") && !startsWith(OS_VERSION, "4.0"));
  
  public static final boolean IS_OS_WINDOWS_95 = (startsWith(OS_NAME, "Windows 9") && startsWith(OS_VERSION, "4.0"));
  
  public static final boolean IS_OS_WINDOWS_98 = (startsWith(OS_NAME, "Windows 9") && startsWith(OS_VERSION, "4.1"));
  
  public static final boolean IS_OS_WINDOWS_NT = startsWith(OS_NAME, "Windows NT");
  
  public static final boolean IS_OS_WINDOWS_ME = (startsWith(OS_NAME, "Windows") && startsWith(OS_VERSION, "4.9"));
  
  public static final boolean IS_OS_WINDOWS_2000 = (startsWith(OS_NAME, "Windows") && startsWith(OS_VERSION, "5.0"));
  
  public static final boolean IS_OS_WINDOWS_XP = (startsWith(OS_NAME, "Windows") && startsWith(OS_VERSION, "5.1"));
  
  public static final boolean IS_OS_WINDOWS_VISTA = (startsWith(OS_NAME, "Windows") && startsWith(OS_VERSION, "6.0"));
  
  public static final boolean IS_OS_SOLARIS = startsWith(OS_NAME, "Solaris");
  
  public static final boolean IS_LAF_WINDOWS_XP_ENABLED = isWindowsXPLafEnabled();
  
  public static final boolean IS_LOW_RESOLUTION = isLowResolution();
  
  private static boolean loggingEnabled = true;
  
  public static String getSystemProperty(String key) {
    try {
      return System.getProperty(key);
    } catch (SecurityException e) {
      log("Can't read the System property " + key + ".");
      return null;
    } 
  }
  
  public static String getSystemProperty(String key, String defaultValue) {
    try {
      return System.getProperty(key, defaultValue);
    } catch (SecurityException e) {
      log("Can't read the System property " + key + ".");
      return defaultValue;
    } 
  }
  
  public static Boolean getBooleanSystemProperty(String key, String logMessage) {
    Boolean result;
    String value = getSystemProperty(key, "");
    if (value.equalsIgnoreCase("false")) {
      result = Boolean.FALSE;
    } else if (value.equalsIgnoreCase("true")) {
      result = Boolean.TRUE;
    } else {
      result = null;
    } 
    if (result != null)
      log(logMessage + " have been " + (result.booleanValue() ? "en" : "dis") + "abled in the system properties."); 
    return result;
  }
  
  private static boolean isWindowsXPLafEnabled() {
    return ((IS_OS_WINDOWS_XP || IS_OS_WINDOWS_VISTA) && IS_JAVA_1_4_2_OR_LATER && Boolean.TRUE.equals(Toolkit.getDefaultToolkit().getDesktopProperty("win.xpstyle.themeActive")) && getSystemProperty("swing.noxp") == null);
  }
  
  public static boolean isTrueColor(Component c) {
    return (c.getToolkit().getColorModel().getPixelSize() >= 24);
  }
  
  public static boolean getToolkitUsesNativeDropShadows() {
    return IS_OS_MAC;
  }
  
  public static Color getSlightlyBrighter(Color color) {
    return getSlightlyBrighter(color, 1.1F);
  }
  
  public static Color getSlightlyBrighter(Color color, float factor) {
    float[] hsbValues = new float[3];
    Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsbValues);
    float hue = hsbValues[0];
    float saturation = hsbValues[1];
    float brightness = hsbValues[2];
    float newBrightness = Math.min(brightness * factor, 1.0F);
    return Color.getHSBColor(hue, saturation, newBrightness);
  }
  
  public static void setLookAndTheme(LookAndFeel laf, Object theme) throws UnsupportedLookAndFeelException {
    if (laf instanceof PlasticLookAndFeel && theme != null && theme instanceof PlasticTheme)
      PlasticLookAndFeel.setPlasticTheme((PlasticTheme)theme); 
    UIManager.setLookAndFeel(laf);
  }
  
  public static Object getDefaultTheme(LookAndFeel laf) {
    return (laf instanceof PlasticLookAndFeel) ? PlasticLookAndFeel.createMyDefaultTheme() : null;
  }
  
  public static List getInstalledThemes(LookAndFeel laf) {
    return (laf instanceof PlasticLookAndFeel) ? PlasticLookAndFeel.getInstalledThemes() : Collections.EMPTY_LIST;
  }
  
  public static void setLoggingEnabled(boolean enabled) {
    loggingEnabled = enabled;
  }
  
  public static void log() {
    if (loggingEnabled)
      System.out.println(); 
  }
  
  public static void log(String message) {
    if (loggingEnabled)
      System.out.println("JGoodies Looks: " + message); 
  }
  
  private static boolean isLowResolution() {
    return (Toolkit.getDefaultToolkit().getScreenResolution() < 120);
  }
  
  private static boolean startsWith(String str, String prefix) {
    return (str != null && str.startsWith(prefix));
  }
  
  private static boolean startsWithIgnoreCase(String str, String prefix) {
    return (str != null && str.toUpperCase(Locale.ENGLISH).startsWith(prefix.toUpperCase(Locale.ENGLISH)));
  }
}
