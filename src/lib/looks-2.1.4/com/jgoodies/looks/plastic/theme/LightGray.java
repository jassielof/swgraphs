package com.jgoodies.looks.plastic.theme;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;

public class LightGray extends ExperienceBlue {
  private static final ColorUIResource GRAY_VERY_LIGHT = new ColorUIResource(244, 244, 244);
  
  public String getName() {
    return "Light Gray";
  }
  
  protected ColorUIResource getPrimary1() {
    return new ColorUIResource(51, 153, 255);
  }
  
  protected ColorUIResource getPrimary2() {
    return Colors.GRAY_MEDIUMLIGHT;
  }
  
  protected ColorUIResource getPrimary3() {
    return new ColorUIResource(225, 240, 255);
  }
  
  protected ColorUIResource getSecondary1() {
    return Colors.GRAY_MEDIUM;
  }
  
  protected ColorUIResource getSecondary2() {
    return getPrimary2();
  }
  
  protected ColorUIResource getSecondary3() {
    return GRAY_VERY_LIGHT;
  }
  
  public ColorUIResource getFocusColor() {
    return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.ORANGE_FOCUS : Colors.BLUE_MEDIUM_DARK;
  }
  
  public ColorUIResource getTitleTextColor() {
    return Colors.GRAY_DARKEST;
  }
  
  public ColorUIResource getSimpleInternalFrameBackground() {
    return Colors.GRAY_MEDIUMDARK;
  }
  
  public void addCustomEntriesToTable(UIDefaults table) {
    super.addCustomEntriesToTable(table);
    Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", new Integer(30), "TabbedPane.selected", getWhite(), "TabbedPane.selectHighlight", Colors.GRAY_MEDIUM };
    table.putDefaults(uiDefaults);
  }
}
