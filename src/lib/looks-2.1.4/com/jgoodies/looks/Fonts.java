package com.jgoodies.looks;

import java.awt.Font;
import java.awt.Toolkit;
import java.util.Locale;

public final class Fonts {
  public static final String TAHOMA_NAME = "Tahoma";
  
  public static final String SEGOE_UI_NAME = "Segoe UI";
  
  public static final Font TAHOMA_11PT = new Font("Tahoma", 0, 11);
  
  public static final Font TAHOMA_13PT = new Font("Tahoma", 0, 13);
  
  public static final Font TAHOMA_14PT = new Font("Tahoma", 0, 14);
  
  public static final Font SEGOE_UI_12PT = new Font("Segoe UI", 0, 12);
  
  public static final Font SEGOE_UI_13PT = new Font("Segoe UI", 0, 13);
  
  public static final Font SEGOE_UI_15PT = new Font("Segoe UI", 0, 15);
  
  public static final Font WINDOWS_XP_96DPI_NORMAL = TAHOMA_11PT;
  
  public static final Font WINDOWS_XP_96DPI_DEFAULT_GUI = TAHOMA_11PT;
  
  public static final Font WINDOWS_XP_96DPI_LARGE = TAHOMA_13PT;
  
  public static final Font WINDOWS_XP_120DPI_NORMAL = TAHOMA_14PT;
  
  public static final Font WINDOWS_XP_120DPI_DEFAULT_GUI = TAHOMA_13PT;
  
  public static final Font WINDOWS_VISTA_96DPI_NORMAL = SEGOE_UI_12PT;
  
  public static final Font WINDOWS_VISTA_96DPI_LARGE = SEGOE_UI_15PT;
  
  static final Font WINDOWS_VISTA_101DPI_NORMAL = SEGOE_UI_13PT;
  
  public static final Font WINDOWS_VISTA_120DPI_NORMAL = SEGOE_UI_15PT;
  
  static final String WINDOWS_DEFAULT_GUI_FONT_KEY = "win.defaultGUI.font";
  
  static final String WINDOWS_ICON_FONT_KEY = "win.icon.font";
  
  static Font getDefaultGUIFontWesternModernWindowsNormal() {
    return LookUtils.IS_LOW_RESOLUTION ? WINDOWS_XP_96DPI_DEFAULT_GUI : WINDOWS_XP_120DPI_DEFAULT_GUI;
  }
  
  static Font getDefaultIconFontWesternModernWindowsNormal() {
    return LookUtils.IS_LOW_RESOLUTION ? WINDOWS_XP_96DPI_NORMAL : WINDOWS_XP_120DPI_NORMAL;
  }
  
  static Font getDefaultIconFontWesternWindowsVistaNormal() {
    return LookUtils.IS_LOW_RESOLUTION ? WINDOWS_VISTA_96DPI_NORMAL : WINDOWS_VISTA_120DPI_NORMAL;
  }
  
  static Font getLooks1xWindowsControlFont() {
    if (!LookUtils.IS_OS_WINDOWS)
      throw new UnsupportedOperationException(); 
    return getDesktopFont("win.defaultGUI.font");
  }
  
  public static Font getWindowsControlFont() {
    if (!LookUtils.IS_OS_WINDOWS)
      throw new UnsupportedOperationException(); 
    Font defaultGUIFont = getDefaultGUIFont();
    if (LookUtils.IS_OS_WINDOWS_95 || LookUtils.IS_OS_WINDOWS_98 || LookUtils.IS_OS_WINDOWS_NT || LookUtils.IS_OS_WINDOWS_ME)
      return defaultGUIFont; 
    if (LookUtils.IS_OS_WINDOWS_VISTA && LookUtils.IS_JAVA_1_4_OR_5) {
      Font tahoma = getDefaultGUIFontWesternModernWindowsNormal();
      return Boolean.TRUE.equals(canDisplayLocalizedText(tahoma, Locale.getDefault())) ? tahoma : defaultGUIFont;
    } 
    Font iconFont = getDesktopFont("win.icon.font");
    return Boolean.TRUE.equals(canDisplayLocalizedText(iconFont, Locale.getDefault())) ? iconFont : defaultGUIFont;
  }
  
  private static Font getDefaultGUIFont() {
    Font font = getDesktopFont("win.defaultGUI.font");
    if (font != null)
      return font; 
    return new Font("Dialog", 0, 12);
  }
  
  public static Boolean canDisplayLocalizedText(Font font, Locale locale) {
    if (localeHasLocalizedDisplayLanguage(locale))
      return Boolean.valueOf(canDisplayLocalizedDisplayLanguage(font, locale)); 
    String fontName = font.getName();
    String language = locale.getLanguage();
    if ("Tahoma".equals(fontName)) {
      if ("hi".equals(language))
        return Boolean.FALSE; 
      if ("ja".equals(language))
        return Boolean.FALSE; 
      if ("ko".equals(language))
        return Boolean.FALSE; 
      if ("zh".equals(language))
        return Boolean.FALSE; 
    } 
    if ("Microsoft Sans Serif".equals(fontName)) {
      if ("ja".equals(language))
        return Boolean.FALSE; 
      if ("ko".equals(language))
        return Boolean.FALSE; 
      if ("zh".equals(language))
        return Boolean.FALSE; 
    } 
    return null;
  }
  
  private static boolean canDisplayLocalizedDisplayLanguage(Font font, Locale locale) {
    String testString = locale.getDisplayLanguage(locale);
    int index = font.canDisplayUpTo(testString);
    return (index == -1);
  }
  
  private static boolean localeHasLocalizedDisplayLanguage(Locale locale) {
    if (locale.getLanguage().equals(Locale.ENGLISH.getLanguage()))
      return true; 
    String englishDisplayLanguage = locale.getDisplayLanguage(Locale.ENGLISH);
    String localizedDisplayLanguage = locale.getDisplayLanguage(locale);
    return !englishDisplayLanguage.equals(localizedDisplayLanguage);
  }
  
  private static Font getDesktopFont(String fontName) {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    return (Font)toolkit.getDesktopProperty(fontName);
  }
}
