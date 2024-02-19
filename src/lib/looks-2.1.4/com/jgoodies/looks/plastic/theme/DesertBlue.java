package com.jgoodies.looks.plastic.theme;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;

public class DesertBlue extends DesertBluer {
  public String getName() {
    return "Desert Blue";
  }
  
  private static final ColorUIResource SECONDARY2 = new ColorUIResource(148, 144, 140);
  
  private static final ColorUIResource SECONDARY3 = new ColorUIResource(211, 210, 204);
  
  protected ColorUIResource getPrimary1() {
    return Colors.GRAY_DARK;
  }
  
  protected ColorUIResource getPrimary2() {
    return Colors.BLUE_LOW_MEDIUM;
  }
  
  protected ColorUIResource getPrimary3() {
    return Colors.BLUE_LOW_LIGHTEST;
  }
  
  protected ColorUIResource getSecondary1() {
    return Colors.GRAY_MEDIUM;
  }
  
  protected ColorUIResource getSecondary2() {
    return SECONDARY2;
  }
  
  protected ColorUIResource getSecondary3() {
    return SECONDARY3;
  }
  
  public ColorUIResource getTitleTextColor() {
    return Colors.BLUE_MEDIUM_DARKEST;
  }
  
  public ColorUIResource getFocusColor() {
    return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.YELLOW_FOCUS : Colors.BLUE_MEDIUM_DARK;
  }
  
  public ColorUIResource getPrimaryControlShadow() {
    return getPrimary3();
  }
  
  public ColorUIResource getMenuItemSelectedBackground() {
    return getPrimary1();
  }
  
  public void addCustomEntriesToTable(UIDefaults table) {
    super.addCustomEntriesToTable(table);
    Object[] uiDefaults = { "ScrollBar.is3DEnabled", Boolean.FALSE, "ScrollBar.thumbHighlight", getPrimaryControlHighlight(), "ScrollBar.maxBumpsWidth", null };
    table.putDefaults(uiDefaults);
  }
}
