package com.jgoodies.looks.plastic.theme;

import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;

public class ExperienceBlue extends DesertBluer {
  public String getName() {
    return "Experience Blue";
  }
  
  protected static final ColorUIResource LUNA_BACKGROUND = new ColorUIResource(236, 233, 216);
  
  protected static final ColorUIResource LUNA_BACKGROUND_DARKER = new ColorUIResource(189, 190, 176);
  
  private static final ColorUIResource SECONDARY1 = Colors.GRAY_MEDIUM;
  
  private static final ColorUIResource SECONDARY2 = LUNA_BACKGROUND_DARKER;
  
  private static final ColorUIResource SECONDARY3 = LUNA_BACKGROUND;
  
  protected ColorUIResource getPrimary1() {
    return Colors.BLUE_MEDIUM_DARK;
  }
  
  protected ColorUIResource getPrimary2() {
    return Colors.BLUE_LOW_MEDIUM;
  }
  
  protected ColorUIResource getPrimary3() {
    return Colors.BLUE_LOW_LIGHTEST;
  }
  
  protected ColorUIResource getSecondary1() {
    return SECONDARY1;
  }
  
  protected ColorUIResource getSecondary2() {
    return SECONDARY2;
  }
  
  protected ColorUIResource getSecondary3() {
    return SECONDARY3;
  }
  
  public ColorUIResource getFocusColor() {
    return Colors.ORANGE_FOCUS;
  }
  
  public ColorUIResource getPrimaryControlShadow() {
    return getPrimary3();
  }
  
  public ColorUIResource getMenuSelectedBackground() {
    return getPrimary1();
  }
  
  public ColorUIResource getMenuSelectedForeground() {
    return WHITE;
  }
  
  public ColorUIResource getMenuItemBackground() {
    return WHITE;
  }
  
  public ColorUIResource getToggleButtonCheckColor() {
    return Colors.GREEN_CHECK;
  }
  
  public void addCustomEntriesToTable(UIDefaults table) {
    super.addCustomEntriesToTable(table);
    Object[] uiDefaults = { "ScrollBar.thumbHighlight", getPrimaryControlHighlight(), "ScrollBar.maxBumpsWidth", new Integer(22) };
    table.putDefaults(uiDefaults);
  }
}
