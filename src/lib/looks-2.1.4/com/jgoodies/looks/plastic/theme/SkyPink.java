package com.jgoodies.looks.plastic.theme;

import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;

public class SkyPink extends AbstractSkyTheme {
  public String getName() {
    return "Sky Pink";
  }
  
  protected ColorUIResource getPrimary1() {
    return Colors.PINK_LOW_DARK;
  }
  
  protected ColorUIResource getPrimary2() {
    return Colors.PINK_LOW_MEDIUM;
  }
  
  protected ColorUIResource getPrimary3() {
    return Colors.PINK_LOW_LIGHTER;
  }
  
  public ColorUIResource getHighlightedTextColor() {
    return getControlTextColor();
  }
  
  public void addCustomEntriesToTable(UIDefaults table) {
    super.addCustomEntriesToTable(table);
    Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", new Integer(30) };
    table.putDefaults(uiDefaults);
  }
}
