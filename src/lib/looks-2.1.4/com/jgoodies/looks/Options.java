package com.jgoodies.looks;

import com.jgoodies.looks.common.ShadowPopup;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import javax.swing.UIManager;

public final class Options {
  public static final String PLASTIC_NAME = "com.jgoodies.looks.plastic.PlasticLookAndFeel";
  
  public static final String PLASTIC3D_NAME = "com.jgoodies.looks.plastic.Plastic3DLookAndFeel";
  
  public static final String PLASTICXP_NAME = "com.jgoodies.looks.plastic.PlasticXPLookAndFeel";
  
  public static final String JGOODIES_WINDOWS_NAME = "com.jgoodies.looks.windows.WindowsLookAndFeel";
  
  public static final String DEFAULT_LOOK_NAME = "com.jgoodies.looks.plastic.PlasticXPLookAndFeel";
  
  private static final Map LAF_REPLACEMENTS = new HashMap();
  
  public static final String PLASTIC_FONT_POLICY_KEY = "Plastic.fontPolicy";
  
  public static final String PLASTIC_CONTROL_FONT_KEY = "Plastic.controlFont";
  
  public static final String PLASTIC_MENU_FONT_KEY = "Plastic.menuFont";
  
  public static final String WINDOWS_FONT_POLICY_KEY = "Windows.fontPolicy";
  
  public static final String WINDOWS_CONTROL_FONT_KEY = "Windows.controlFont";
  
  public static final String WINDOWS_MENU_FONT_KEY = "Windows.menuFont";
  
  public static final String USE_SYSTEM_FONTS_KEY = "swing.useSystemFontSettings";
  
  public static final String USE_SYSTEM_FONTS_APP_KEY = "Application.useSystemFontSettings";
  
  public static final String PLASTIC_MICRO_LAYOUT_POLICY_KEY = "Plastic.MicroLayoutPolicy";
  
  public static final String WINDOWS_MICRO_LAYOUT_POLICY_KEY = "Windows.MicroLayoutPolicy";
  
  public static final String DEFAULT_ICON_SIZE_KEY = "jgoodies.defaultIconSize";
  
  public static final String USE_NARROW_BUTTONS_KEY = "jgoodies.useNarrowButtons";
  
  public static final String TAB_ICONS_ENABLED_KEY = "jgoodies.tabIconsEnabled";
  
  public static final String POPUP_DROP_SHADOW_ENABLED_KEY = "jgoodies.popupDropShadowEnabled";
  
  public static final String HI_RES_GRAY_FILTER_ENABLED_KEY = "HiResGrayFilterEnabled";
  
  public static final String IS_ETCHED_KEY = "jgoodies.isEtched";
  
  public static final String HEADER_STYLE_KEY = "jgoodies.headerStyle";
  
  public static final String NO_ICONS_KEY = "jgoodies.noIcons";
  
  public static final String NO_MARGIN_KEY = "JPopupMenu.noMargin";
  
  public static final String TREE_LINE_STYLE_KEY = "JTree.lineStyle";
  
  public static final String TREE_LINE_STYLE_ANGLED_VALUE = "Angled";
  
  public static final String TREE_LINE_STYLE_NONE_VALUE = "None";
  
  public static final String NO_CONTENT_BORDER_KEY = "jgoodies.noContentBorder";
  
  public static final String EMBEDDED_TABS_KEY = "jgoodies.embeddedTabs";
  
  public static final String COMBO_POPUP_PROTOTYPE_DISPLAY_VALUE_KEY = "ComboBox.popupPrototypeDisplayValue";
  
  public static final String COMBO_RENDERER_IS_BORDER_REMOVABLE = "isBorderRemovable";
  
  public static final String HI_RES_DISABLED_ICON_CLIENT_KEY = "generateHiResDisabledIcon";
  
  static {
    initializeDefaultReplacements();
  }
  
  private static final Boolean USE_SYSTEM_FONTS_SYSTEM_VALUE = LookUtils.getBooleanSystemProperty("swing.useSystemFontSettings", "Use system fonts");
  
  private static final Boolean USE_NARROW_BUTTONS_SYSTEM_VALUE = LookUtils.getBooleanSystemProperty("jgoodies.useNarrowButtons", "Use narrow buttons");
  
  private static final Boolean TAB_ICONS_ENABLED_SYSTEM_VALUE = LookUtils.getBooleanSystemProperty("jgoodies.tabIconsEnabled", "Icons in tabbed panes");
  
  private static final Boolean POPUP_DROP_SHADOW_ENABLED_SYSTEM_VALUE = LookUtils.getBooleanSystemProperty("jgoodies.popupDropShadowEnabled", "Popup drop shadows");
  
  private static final Dimension DEFAULT_ICON_SIZE = new Dimension(20, 20);
  
  public static final String NO_REPLACEMENT = "none";
  
  public static boolean getUseSystemFonts() {
    return (USE_SYSTEM_FONTS_SYSTEM_VALUE != null) ? USE_SYSTEM_FONTS_SYSTEM_VALUE.booleanValue() : (!Boolean.FALSE.equals(UIManager.get("Application.useSystemFontSettings")));
  }
  
  public static void setUseSystemFonts(boolean useSystemFonts) {
    UIManager.put("Application.useSystemFontSettings", Boolean.valueOf(useSystemFonts));
  }
  
  public static Dimension getDefaultIconSize() {
    Dimension size = UIManager.getDimension("jgoodies.defaultIconSize");
    return (size == null) ? DEFAULT_ICON_SIZE : size;
  }
  
  public static void setDefaultIconSize(Dimension defaultIconSize) {
    UIManager.put("jgoodies.defaultIconSize", defaultIconSize);
  }
  
  public static boolean getUseNarrowButtons() {
    return (USE_NARROW_BUTTONS_SYSTEM_VALUE != null) ? USE_NARROW_BUTTONS_SYSTEM_VALUE.booleanValue() : (!Boolean.FALSE.equals(UIManager.get("jgoodies.useNarrowButtons")));
  }
  
  public static void setUseNarrowButtons(boolean b) {
    UIManager.put("jgoodies.useNarrowButtons", Boolean.valueOf(b));
  }
  
  public static boolean isTabIconsEnabled() {
    return (TAB_ICONS_ENABLED_SYSTEM_VALUE != null) ? TAB_ICONS_ENABLED_SYSTEM_VALUE.booleanValue() : (!Boolean.FALSE.equals(UIManager.get("jgoodies.tabIconsEnabled")));
  }
  
  public static void setTabIconsEnabled(boolean b) {
    UIManager.put("jgoodies.tabIconsEnabled", Boolean.valueOf(b));
  }
  
  public static boolean isPopupDropShadowActive() {
    return (!LookUtils.getToolkitUsesNativeDropShadows() && ShadowPopup.canSnapshot() && isPopupDropShadowEnabled());
  }
  
  public static boolean isPopupDropShadowEnabled() {
    if (POPUP_DROP_SHADOW_ENABLED_SYSTEM_VALUE != null)
      return POPUP_DROP_SHADOW_ENABLED_SYSTEM_VALUE.booleanValue(); 
    Object value = UIManager.get("jgoodies.popupDropShadowEnabled");
    return (value == null) ? isPopupDropShadowEnabledDefault() : Boolean.TRUE.equals(value);
  }
  
  public static void setPopupDropShadowEnabled(boolean b) {
    UIManager.put("jgoodies.popupDropShadowEnabled", Boolean.valueOf(b));
  }
  
  private static boolean isPopupDropShadowEnabledDefault() {
    return LookUtils.IS_OS_WINDOWS_MODERN;
  }
  
  public static boolean isHiResGrayFilterEnabled() {
    return !Boolean.FALSE.equals(UIManager.get("HiResGrayFilterEnabled"));
  }
  
  public static void setHiResGrayFilterEnabled(boolean b) {
    UIManager.put("HiResGrayFilterEnabled", Boolean.valueOf(b));
  }
  
  public static void putLookAndFeelReplacement(String original, String replacement) {
    LAF_REPLACEMENTS.put(original, replacement);
  }
  
  public static void removeLookAndFeelReplacement(String original) {
    LAF_REPLACEMENTS.remove(original);
  }
  
  private static void initializeDefaultReplacements() {
    putLookAndFeelReplacement("javax.swing.plaf.metal.MetalLookAndFeel", "com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
    putLookAndFeelReplacement("com.sun.java.swing.plaf.windows.WindowsLookAndFeel", "com.jgoodies.looks.windows.WindowsLookAndFeel");
    putLookAndFeelReplacement("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel", "none");
  }
  
  public static String getReplacementClassNameFor(String className) {
    String replacement = (String)LAF_REPLACEMENTS.get(className);
    if (replacement == null)
      return className; 
    if (replacement.equals("none"))
      return null; 
    return replacement;
  }
  
  public static String getCrossPlatformLookAndFeelClassName() {
    return "com.jgoodies.looks.plastic.PlasticXPLookAndFeel";
  }
  
  public static String getSystemLookAndFeelClassName() {
    if (LookUtils.IS_OS_WINDOWS)
      return "com.jgoodies.looks.windows.WindowsLookAndFeel"; 
    if (LookUtils.IS_OS_MAC)
      return UIManager.getSystemLookAndFeelClassName(); 
    return getCrossPlatformLookAndFeelClassName();
  }
}
