package com.jgoodies.looks;

import java.awt.Font;
import javax.swing.plaf.FontUIResource;

public final class FontSets {
  private static FontSet logicalFontSet;
  
  public static FontSet createDefaultFontSet(Font controlFont) {
    return createDefaultFontSet(controlFont, null);
  }
  
  public static FontSet createDefaultFontSet(Font controlFont, Font menuFont) {
    return createDefaultFontSet(controlFont, menuFont, null, null, null, null);
  }
  
  public static FontSet createDefaultFontSet(Font controlFont, Font menuFont, Font titleFont) {
    return createDefaultFontSet(controlFont, menuFont, titleFont, null, null, null);
  }
  
  public static FontSet createDefaultFontSet(Font controlFont, Font menuFont, Font titleFont, Font messageFont, Font smallFont, Font windowTitleFont) {
    return new DefaultFontSet(controlFont, menuFont, titleFont, messageFont, smallFont, windowTitleFont);
  }
  
  public static FontSet getLogicalFontSet() {
    if (logicalFontSet == null)
      logicalFontSet = new LogicalFontSet(); 
    return logicalFontSet;
  }
  
  private static final class DefaultFontSet implements FontSet {
    private final FontUIResource controlFont;
    
    private final FontUIResource menuFont;
    
    private final FontUIResource titleFont;
    
    private final FontUIResource messageFont;
    
    private final FontUIResource smallFont;
    
    private final FontUIResource windowTitleFont;
    
    public DefaultFontSet(Font controlFont, Font menuFont, Font titleFont, Font messageFont, Font smallFont, Font windowTitleFont) {
      this.controlFont = new FontUIResource(controlFont);
      this.menuFont = (menuFont != null) ? new FontUIResource(menuFont) : this.controlFont;
      this.titleFont = (titleFont != null) ? new FontUIResource(titleFont) : this.controlFont;
      this.messageFont = (messageFont != null) ? new FontUIResource(messageFont) : this.controlFont;
      this.smallFont = new FontUIResource((smallFont != null) ? smallFont : controlFont.deriveFont(controlFont.getSize2D() - 2.0F));
      this.windowTitleFont = (windowTitleFont != null) ? new FontUIResource(windowTitleFont) : this.titleFont;
    }
    
    public FontUIResource getControlFont() {
      return this.controlFont;
    }
    
    public FontUIResource getMenuFont() {
      return this.menuFont;
    }
    
    public FontUIResource getTitleFont() {
      return this.titleFont;
    }
    
    public FontUIResource getWindowTitleFont() {
      return this.windowTitleFont;
    }
    
    public FontUIResource getSmallFont() {
      return this.smallFont;
    }
    
    public FontUIResource getMessageFont() {
      return this.messageFont;
    }
  }
  
  private static final class LogicalFontSet implements FontSet {
    private FontUIResource controlFont;
    
    private FontUIResource titleFont;
    
    private FontUIResource systemFont;
    
    private FontUIResource smallFont;
    
    private LogicalFontSet() {}
    
    public FontUIResource getControlFont() {
      if (this.controlFont == null)
        this.controlFont = new FontUIResource(Font.getFont("swing.plaf.metal.controlFont", new Font("Dialog", 0, 12))); 
      return this.controlFont;
    }
    
    public FontUIResource getMenuFont() {
      return getControlFont();
    }
    
    public FontUIResource getTitleFont() {
      if (this.titleFont == null)
        this.titleFont = new FontUIResource(getControlFont().deriveFont(1)); 
      return this.titleFont;
    }
    
    public FontUIResource getSmallFont() {
      if (this.smallFont == null)
        this.smallFont = new FontUIResource(Font.getFont("swing.plaf.metal.smallFont", new Font("Dialog", 0, 10))); 
      return this.smallFont;
    }
    
    public FontUIResource getMessageFont() {
      if (this.systemFont == null)
        this.systemFont = new FontUIResource(Font.getFont("swing.plaf.metal.systemFont", new Font("Dialog", 0, 12))); 
      return this.systemFont;
    }
    
    public FontUIResource getWindowTitleFont() {
      return getTitleFont();
    }
  }
}
