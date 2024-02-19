package com.jgoodies.looks.plastic.theme;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import javax.swing.plaf.ColorUIResource;

public class SkyKrupp extends AbstractSkyTheme {
  public String getName() {
    return "Sky Krupp";
  }
  
  private final ColorUIResource primary1 = new ColorUIResource(54, 54, 90);
  
  private final ColorUIResource primary2 = new ColorUIResource(156, 156, 178);
  
  private final ColorUIResource primary3 = new ColorUIResource(197, 197, 221);
  
  protected ColorUIResource getPrimary1() {
    return this.primary1;
  }
  
  protected ColorUIResource getPrimary2() {
    return this.primary2;
  }
  
  protected ColorUIResource getPrimary3() {
    return this.primary3;
  }
  
  public ColorUIResource getMenuItemSelectedBackground() {
    return getPrimary1();
  }
  
  public ColorUIResource getMenuItemSelectedForeground() {
    return getWhite();
  }
  
  public ColorUIResource getMenuSelectedBackground() {
    return getSecondary2();
  }
  
  public ColorUIResource getFocusColor() {
    return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.ORANGE_FOCUS : Colors.GRAY_DARK;
  }
}
