package com.jgoodies.looks;

import java.awt.Insets;
import javax.swing.plaf.InsetsUIResource;

public final class MicroLayouts {
  private static final InsetsUIResource PLASTIC_MENU_ITEM_MARGIN = new InsetsUIResource(3, 0, 3, 0);
  
  private static final InsetsUIResource PLASTIC_MENU_MARGIN = new InsetsUIResource(2, 4, 2, 4);
  
  private static final InsetsUIResource PLASTIC_CHECK_BOX_MARGIN = new InsetsUIResource(2, 0, 2, 1);
  
  public static MicroLayout createPlasticLowResMicroLayout() {
    return new MicroLayout(new InsetsUIResource(1, 1, 2, 1), new InsetsUIResource(2, 2, 2, 1), new InsetsUIResource(1, 1, 2, 1), -1, 1, new Insets(2, 3, 3, 3), getButtonMargin(1, 1), getButtonMargin(1, 1), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
  }
  
  public static MicroLayout createPlasticHiResMicroLayout() {
    return new MicroLayout(new InsetsUIResource(1, 1, 2, 1), new InsetsUIResource(2, 2, 2, 1), new InsetsUIResource(1, 1, 2, 1), -1, 1, new Insets(1, 3, 1, 3), getButtonMargin(2, 3), getButtonMargin(2, 3), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
  }
  
  public static MicroLayout createPlasticVistaMicroLayout() {
    return new MicroLayout(new InsetsUIResource(1, 1, 1, 1), new InsetsUIResource(1, 2, 1, 1), new InsetsUIResource(1, 1, 1, 1), -1, 1, new Insets(2, 3, 3, 3), getButtonMargin(0, 1), getButtonMargin(0, 1), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
  }
  
  public static MicroLayout createPlasticVistaClassicMicroLayout() {
    return new MicroLayout(new InsetsUIResource(1, 1, 2, 1), new InsetsUIResource(2, 2, 2, 1), new InsetsUIResource(1, 1, 2, 1), -1, 1, new Insets(3, 3, 3, 3), getButtonMargin(0, 1), getButtonMargin(0, 1), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
  }
  
  public static MicroLayout createPlasticXPLowResMicroLayout() {
    return new MicroLayout(new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), -1, 1, new Insets(3, 2, 3, 2), getButtonMargin(0, 1), getButtonMargin(0, 1), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
  }
  
  public static MicroLayout createPlasticXPHiResMicroLayout() {
    return new MicroLayout(new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), -1, 1, new Insets(2, 2, 2, 2), getButtonMargin(1, 2), getButtonMargin(1, 2), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
  }
  
  public static MicroLayout createPlasticXPVistaMicroLayout() {
    return new MicroLayout(new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), -1, 1, new Insets(2, 2, 3, 2), getButtonMargin(0, 0), getButtonMargin(0, 0), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
  }
  
  public static MicroLayout createPlasticXPVistaClassicMicroLayout() {
    return new MicroLayout(new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), -1, 1, new Insets(3, 2, 4, 2), getButtonMargin(0, 0), getButtonMargin(0, 0), PLASTIC_CHECK_BOX_MARGIN, PLASTIC_MENU_ITEM_MARGIN, PLASTIC_MENU_MARGIN, null);
  }
  
  private static final InsetsUIResource WINDOWS_CHECK_BOX_MARGIN = new InsetsUIResource(2, 0, 2, 0);
  
  public static MicroLayout createWindowsClassicLowResMicroLayout() {
    return new MicroLayout(new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), 2, 1, new Insets(3, 2, 4, 2), getButtonMargin(1, 1), getButtonMargin(1, 1), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(3, 0, 3, 0), new InsetsUIResource(2, 3, 2, 3), new InsetsUIResource(2, 0, 3, 0));
  }
  
  public static MicroLayout createWindowsClassicHiResMicroLayout() {
    return new MicroLayout(new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), 2, 1, new Insets(3, 2, 4, 2), getButtonMargin(1, 1), getButtonMargin(1, 1), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(2, 0, 2, 0), new InsetsUIResource(2, 4, 2, 4), new InsetsUIResource(3, 0, 4, 0));
  }
  
  public static MicroLayout createWindowsXPLowResMicroLayout() {
    return new MicroLayout(new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), 1, 1, new Insets(3, 2, 4, 2), getButtonMargin(2, 3), getButtonMargin(2, 3), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(3, 0, 3, 0), new InsetsUIResource(2, 3, 2, 4), new InsetsUIResource(2, 3, 3, 3));
  }
  
  public static MicroLayout createWindowsXPHiResMicroLayout() {
    return new MicroLayout(new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), new InsetsUIResource(2, 2, 3, 2), 1, 1, new Insets(3, 2, 4, 2), getButtonMargin(2, 3), getButtonMargin(2, 3), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(2, 0, 2, 0), new InsetsUIResource(2, 5, 2, 6), new InsetsUIResource(3, 3, 4, 3));
  }
  
  public static MicroLayout createWindowsVistaLowResMicroLayout() {
    return new MicroLayout(new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), 1, 1, new Insets(3, 2, 4, 2), getButtonMargin(1, 2), getButtonMargin(1, 2), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(3, 0, 3, 0), new InsetsUIResource(2, 3, 2, 4), new InsetsUIResource(2, 3, 3, 3));
  }
  
  public static MicroLayout createWindowsVistaHiResMicroLayout() {
    return new MicroLayout(new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), new InsetsUIResource(1, 2, 2, 2), 1, 1, new Insets(3, 2, 4, 2), getButtonMargin(1, 2), getButtonMargin(1, 2), WINDOWS_CHECK_BOX_MARGIN, new InsetsUIResource(2, 0, 2, 0), new InsetsUIResource(2, 5, 2, 6), new InsetsUIResource(3, 3, 4, 3));
  }
  
  private static InsetsUIResource getButtonMargin(int top, int bottom) {
    int pad = Options.getUseNarrowButtons() ? 4 : 14;
    return new InsetsUIResource(top, pad, bottom, pad);
  }
}
