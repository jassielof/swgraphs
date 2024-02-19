package com.jgoodies.looks.plastic.theme;

import javax.swing.plaf.ColorUIResource;

public class ExperienceGreen extends ExperienceBlue {
  public String getName() {
    return "Experience Green";
  }
  
  private static final ColorUIResource FOCUS = new ColorUIResource(245, 165, 16);
  
  protected ColorUIResource getPrimary1() {
    return Colors.GREEN_LOW_DARK;
  }
  
  protected ColorUIResource getPrimary2() {
    return Colors.GREEN_LOW_MEDIUM;
  }
  
  protected ColorUIResource getPrimary3() {
    return Colors.GREEN_LOW_LIGHTEST;
  }
  
  public ColorUIResource getFocusColor() {
    return FOCUS;
  }
}
