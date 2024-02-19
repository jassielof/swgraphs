package com.jgoodies.looks.windows;

import com.jgoodies.looks.common.MenuItemRenderer;
import com.sun.java.swing.plaf.windows.WindowsMenuUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;

public final class WindowsXPMenuUI extends WindowsMenuUI {
  private static final String MENU_PROPERTY_PREFIX = "Menu";
  
  private static final String SUBMENU_PROPERTY_PREFIX = "MenuItem";
  
  private String propertyPrefix = "Menu";
  
  private MenuItemRenderer renderer;
  
  public static ComponentUI createUI(JComponent b) {
    return new WindowsXPMenuUI();
  }
  
  protected void installDefaults() {
    super.installDefaults();
    if (this.arrowIcon == null || this.arrowIcon instanceof javax.swing.plaf.UIResource)
      this.arrowIcon = UIManager.getIcon("Menu.arrowIcon"); 
    this.renderer = new MenuItemRenderer(this.menuItem, false, this.acceleratorFont, this.selectionForeground, this.disabledForeground, this.acceleratorForeground, this.acceleratorSelectionForeground);
    Integer gap = (Integer)UIManager.get(getPropertyPrefix() + ".textIconGap");
    this.defaultTextIconGap = (gap != null) ? gap.intValue() : 2;
  }
  
  protected void uninstallDefaults() {
    super.uninstallDefaults();
    this.renderer = null;
  }
  
  protected String getPropertyPrefix() {
    return this.propertyPrefix;
  }
  
  protected Dimension getPreferredMenuItemSize(JComponent c, Icon aCheckIcon, Icon anArrowIcon, int textIconGap) {
    if (isSubMenu(this.menuItem)) {
      ensureSubMenuInstalled();
      return this.renderer.getPreferredMenuItemSize(c, aCheckIcon, anArrowIcon, textIconGap);
    } 
    Dimension size = super.getPreferredMenuItemSize(c, aCheckIcon, anArrowIcon, textIconGap);
    int width = size.width;
    int height = size.height;
    if (height % 2 == 1)
      height--; 
    return new Dimension(width, height);
  }
  
  protected void paintMenuItem(Graphics g, JComponent c, Icon aCheckIcon, Icon anArrowIcon, Color background, Color foreground, int textIconGap) {
    if (isSubMenu(this.menuItem)) {
      this.renderer.paintMenuItem(g, c, aCheckIcon, anArrowIcon, background, foreground, textIconGap);
    } else {
      super.paintMenuItem(g, c, aCheckIcon, anArrowIcon, background, foreground, textIconGap);
    } 
  }
  
  private void ensureSubMenuInstalled() {
    if (this.propertyPrefix.equals("MenuItem"))
      return; 
    ButtonModel model = this.menuItem.getModel();
    boolean oldArmed = model.isArmed();
    boolean oldSelected = model.isSelected();
    uninstallDefaults();
    this.propertyPrefix = "MenuItem";
    installDefaults();
    model.setArmed(oldArmed);
    model.setSelected(oldSelected);
  }
  
  private boolean isSubMenu(JMenuItem aMenuItem) {
    return !((JMenu)aMenuItem).isTopLevelMenu();
  }
}
