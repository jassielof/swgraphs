package com.jgoodies.looks.plastic.theme;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;

public class Silver extends ExperienceBlue {
  private static final ColorUIResource GRAY_LIGHT_LIGHTER = new ColorUIResource(190, 190, 190);
  
  public String getName() {
    return "Silver";
  }
  
  protected ColorUIResource getPrimary1() {
    return Colors.GRAY_MEDIUMDARK;
  }
  
  protected ColorUIResource getPrimary2() {
    return Colors.GRAY_MEDIUMLIGHT;
  }
  
  protected ColorUIResource getPrimary3() {
    return GRAY_LIGHT_LIGHTER;
  }
  
  protected ColorUIResource getSecondary1() {
    return Colors.GRAY_MEDIUM;
  }
  
  protected ColorUIResource getSecondary2() {
    return getPrimary2();
  }
  
  protected ColorUIResource getSecondary3() {
    return Colors.GRAY_LIGHTER2;
  }
  
  public ColorUIResource getFocusColor() {
    return PlasticLookAndFeel.getHighContrastFocusColorsEnabled() ? Colors.ORANGE_FOCUS : Colors.BLUE_MEDIUM_DARK;
  }
  
  public ColorUIResource getTitleTextColor() {
    return Colors.GRAY_DARKEST;
  }
  
  public void addCustomEntriesToTable(UIDefaults table) {
    super.addCustomEntriesToTable(table);
    Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", new Integer(50) };
    table.putDefaults(uiDefaults);
  }
}
