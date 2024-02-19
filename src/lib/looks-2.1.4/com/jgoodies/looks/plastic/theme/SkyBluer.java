package com.jgoodies.looks.plastic.theme;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticTheme;
import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;

public class SkyBluer extends PlasticTheme {
  public String getName() {
    return "Sky Bluer";
  }
  
  protected ColorUIResource getPrimary1() {
    return Colors.BLUE_MEDIUM_DARKEST;
  }
  
  protected ColorUIResource getPrimary2() {
    return Colors.BLUE_MEDIUM_MEDIUM;
  }
  
  protected ColorUIResource getPrimary3() {
    return Colors.BLUE_MEDIUM_LIGHTEST;
  }
  
  protected ColorUIResource getSecondary1() {
    return Colors.GRAY_MEDIUMDARK;
  }
  
  protected ColorUIResource getSecondary2() {
    return Colors.GRAY_LIGHT;
  }
  
  protected ColorUIResource getSecondary3() {
    return Colors.GRAY_LIGHTER;
  }
  
  public ColorUIResource getMenuItemSelectedBackground() {
    return getPrimary2();
  }
  
  public ColorUIResource getMenuItemSelectedForeground() {
    return getWhite();
  }
  
  public ColorUIResource getMenuSelectedBackground() {
    return getSecondary2();
  }
  
  public ColorUIResource getFocusColor() {
    return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.YELLOW_FOCUS : super.getFocusColor();
  }
  
  public void addCustomEntriesToTable(UIDefaults table) {
    super.addCustomEntriesToTable(table);
    Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", new Integer(30) };
    table.putDefaults(uiDefaults);
  }
}
