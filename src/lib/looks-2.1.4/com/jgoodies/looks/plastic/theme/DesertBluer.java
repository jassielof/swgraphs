package com.jgoodies.looks.plastic.theme;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import javax.swing.plaf.ColorUIResource;

public class DesertBluer extends SkyBluer {
  private final ColorUIResource primary1 = new ColorUIResource(10, 36, 106);
  
  private final ColorUIResource primary2 = new ColorUIResource(85, 115, 170);
  
  private final ColorUIResource primary3 = new ColorUIResource(172, 210, 248);
  
  private final ColorUIResource secondary2 = new ColorUIResource(148, 144, 140);
  
  private final ColorUIResource secondary3 = new ColorUIResource(212, 208, 200);
  
  public String getName() {
    return "Desert Bluer";
  }
  
  protected ColorUIResource getPrimary1() {
    return this.primary1;
  }
  
  protected ColorUIResource getPrimary2() {
    return this.primary2;
  }
  
  protected ColorUIResource getPrimary3() {
    return this.primary3;
  }
  
  protected ColorUIResource getSecondary1() {
    return Colors.GRAY_MEDIUM;
  }
  
  protected ColorUIResource getSecondary2() {
    return this.secondary2;
  }
  
  protected ColorUIResource getSecondary3() {
    return this.secondary3;
  }
  
  public ColorUIResource getTextHighlightColor() {
    return getPrimary1();
  }
  
  public ColorUIResource getHighlightedTextColor() {
    return getWhite();
  }
  
  public ColorUIResource getMenuItemSelectedBackground() {
    return getPrimary1();
  }
  
  public ColorUIResource getFocusColor() {
    return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.ORANGE_FOCUS : super.getFocusColor();
  }
}
