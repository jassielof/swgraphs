package com.jgoodies.looks;

import java.awt.Font;
import javax.swing.UIDefaults;

public final class FontPolicies {
  public static FontPolicy createFixedPolicy(FontSet fontSet) {
    return new FixedPolicy(fontSet);
  }
  
  public static FontPolicy customSettingsPolicy(FontPolicy defaultPolicy) {
    return new CustomSettingsPolicy(defaultPolicy);
  }
  
  public static FontPolicy getDefaultPlasticOnWindowsPolicy() {
    return new DefaultPlasticOnWindowsPolicy();
  }
  
  public static FontPolicy getDefaultPlasticPolicy() {
    if (LookUtils.IS_OS_WINDOWS)
      return getDefaultPlasticOnWindowsPolicy(); 
    return getLogicalFontsPolicy();
  }
  
  public static FontPolicy getDefaultWindowsPolicy() {
    return new DefaultWindowsPolicy();
  }
  
  public static FontPolicy getLogicalFontsPolicy() {
    return createFixedPolicy(FontSets.getLogicalFontSet());
  }
  
  public static FontPolicy getLooks1xPlasticPolicy() {
    Font controlFont = Fonts.getDefaultGUIFontWesternModernWindowsNormal();
    Font menuFont = controlFont;
    Font titleFont = controlFont.deriveFont(1);
    FontSet fontSet = FontSets.createDefaultFontSet(controlFont, menuFont, titleFont);
    return createFixedPolicy(fontSet);
  }
  
  public static FontPolicy getLooks1xWindowsPolicy() {
    return new Looks1xWindowsPolicy();
  }
  
  public static FontPolicy getTransitionalPlasticPolicy() {
    return LookUtils.IS_OS_WINDOWS ? getDefaultPlasticOnWindowsPolicy() : getLooks1xPlasticPolicy();
  }
  
  private static FontSet getCustomFontSet(String lafName) {
    String controlFontKey = lafName + ".controlFont";
    String menuFontKey = lafName + ".menuFont";
    String decodedControlFont = LookUtils.getSystemProperty(controlFontKey);
    if (decodedControlFont == null)
      return null; 
    Font controlFont = Font.decode(decodedControlFont);
    String decodedMenuFont = LookUtils.getSystemProperty(menuFontKey);
    Font menuFont = (decodedMenuFont != null) ? Font.decode(decodedMenuFont) : null;
    Font titleFont = "Plastic".equals(lafName) ? controlFont.deriveFont(1) : controlFont;
    return FontSets.createDefaultFontSet(controlFont, menuFont, titleFont);
  }
  
  private static FontPolicy getCustomPolicy(String lafName) {
    return null;
  }
  
  private static final class CustomSettingsPolicy implements FontPolicy {
    private final FontPolicy wrappedPolicy;
    
    CustomSettingsPolicy(FontPolicy wrappedPolicy) {
      this.wrappedPolicy = wrappedPolicy;
    }
    
    public FontSet getFontSet(String lafName, UIDefaults table) {
      FontPolicy customPolicy = FontPolicies.getCustomPolicy(lafName);
      if (customPolicy != null)
        return customPolicy.getFontSet(null, table); 
      FontSet customFontSet = FontPolicies.getCustomFontSet(lafName);
      if (customFontSet != null)
        return customFontSet; 
      return this.wrappedPolicy.getFontSet(lafName, table);
    }
  }
  
  private static final class DefaultPlasticOnWindowsPolicy implements FontPolicy {
    private DefaultPlasticOnWindowsPolicy() {}
    
    public FontSet getFontSet(String lafName, UIDefaults table) {
      Font controlFont, windowsControlFont = Fonts.getWindowsControlFont();
      if (windowsControlFont != null) {
        controlFont = windowsControlFont;
      } else if (table != null) {
        controlFont = table.getFont("Button.font");
      } else {
        controlFont = new Font("Dialog", 0, 12);
      } 
      Font menuFont = (table == null) ? controlFont : table.getFont("Menu.font");
      Font titleFont = controlFont.deriveFont(1);
      return FontSets.createDefaultFontSet(controlFont, menuFont, titleFont);
    }
  }
  
  private static final class DefaultWindowsPolicy implements FontPolicy {
    private DefaultWindowsPolicy() {}
    
    public FontSet getFontSet(String lafName, UIDefaults table) {
      Font controlFont, windowsControlFont = Fonts.getWindowsControlFont();
      if (windowsControlFont != null) {
        controlFont = windowsControlFont;
      } else if (table != null) {
        controlFont = table.getFont("Button.font");
      } else {
        controlFont = new Font("Dialog", 0, 12);
      } 
      Font menuFont = (table == null) ? controlFont : table.getFont("Menu.font");
      Font titleFont = controlFont;
      Font messageFont = (table == null) ? controlFont : table.getFont("OptionPane.font");
      Font smallFont = (table == null) ? controlFont.deriveFont(controlFont.getSize2D() - 2.0F) : table.getFont("ToolTip.font");
      Font windowTitleFont = (table == null) ? controlFont : table.getFont("InternalFrame.titleFont");
      return FontSets.createDefaultFontSet(controlFont, menuFont, titleFont, messageFont, smallFont, windowTitleFont);
    }
  }
  
  private static final class FixedPolicy implements FontPolicy {
    private final FontSet fontSet;
    
    FixedPolicy(FontSet fontSet) {
      this.fontSet = fontSet;
    }
    
    public FontSet getFontSet(String lafName, UIDefaults table) {
      return this.fontSet;
    }
  }
  
  private static final class Looks1xWindowsPolicy implements FontPolicy {
    private Looks1xWindowsPolicy() {}
    
    public FontSet getFontSet(String lafName, UIDefaults table) {
      Font controlFont, windowsControlFont = Fonts.getLooks1xWindowsControlFont();
      if (windowsControlFont != null) {
        controlFont = windowsControlFont;
      } else if (table != null) {
        controlFont = table.getFont("Button.font");
      } else {
        controlFont = new Font("Dialog", 0, 12);
      } 
      return FontSets.createDefaultFontSet(controlFont);
    }
  }
}
