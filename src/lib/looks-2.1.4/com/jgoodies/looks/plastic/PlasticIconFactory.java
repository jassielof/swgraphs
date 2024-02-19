package com.jgoodies.looks.plastic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

final class PlasticIconFactory {
  private static Icon checkBoxIcon;
  
  private static Icon checkBoxMenuItemIcon;
  
  private static Icon radioButtonMenuItemIcon;
  
  private static Icon menuArrowIcon;
  
  private static Icon expandedTreeIcon;
  
  private static Icon collapsedTreeIcon;
  
  private static Icon comboBoxButtonIcon;
  
  private static void drawCheck(Graphics g, int x, int y) {
    g.translate(x, y);
    g.drawLine(3, 5, 3, 5);
    g.fillRect(3, 6, 2, 2);
    g.drawLine(4, 8, 9, 3);
    g.drawLine(5, 8, 9, 4);
    g.drawLine(5, 9, 9, 5);
    g.translate(-x, -y);
  }
  
  private static class CheckBoxIcon implements Icon, UIResource, Serializable {
    private static final int SIZE = 13;
    
    private CheckBoxIcon() {}
    
    public int getIconWidth() {
      return 13;
    }
    
    public int getIconHeight() {
      return 13;
    }
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
      JCheckBox cb = (JCheckBox)c;
      ButtonModel model = cb.getModel();
      if (model.isEnabled()) {
        if (cb.isBorderPaintedFlat()) {
          g.setColor(PlasticLookAndFeel.getControlDarkShadow());
          g.drawRect(x, y, 11, 11);
          g.setColor(PlasticLookAndFeel.getControlHighlight());
          g.fillRect(x + 1, y + 1, 10, 10);
        } else if (model.isPressed() && model.isArmed()) {
          g.setColor(MetalLookAndFeel.getControlShadow());
          g.fillRect(x, y, 12, 12);
          PlasticUtils.drawPressed3DBorder(g, x, y, 13, 13);
        } else {
          PlasticUtils.drawFlush3DBorder(g, x, y, 13, 13);
        } 
        g.setColor(MetalLookAndFeel.getControlInfo());
      } else {
        g.setColor(MetalLookAndFeel.getControlShadow());
        g.drawRect(x, y, 11, 11);
      } 
      if (model.isSelected())
        PlasticIconFactory.drawCheck(g, x, y); 
    }
  }
  
  private static class CheckBoxMenuItemIcon implements Icon, UIResource, Serializable {
    private static final int SIZE = 13;
    
    private CheckBoxMenuItemIcon() {}
    
    public int getIconWidth() {
      return 13;
    }
    
    public int getIconHeight() {
      return 13;
    }
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
      JMenuItem b = (JMenuItem)c;
      if (b.isSelected())
        PlasticIconFactory.drawCheck(g, x, y + 1); 
    }
  }
  
  private static class RadioButtonMenuItemIcon implements Icon, UIResource, Serializable {
    private static final int SIZE = 13;
    
    private RadioButtonMenuItemIcon() {}
    
    public int getIconWidth() {
      return 13;
    }
    
    public int getIconHeight() {
      return 13;
    }
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
      JMenuItem b = (JMenuItem)c;
      if (b.isSelected())
        drawDot(g, x, y); 
    }
    
    private void drawDot(Graphics g, int x, int y) {
      g.translate(x, y);
      g.drawLine(5, 4, 8, 4);
      g.fillRect(4, 5, 6, 4);
      g.drawLine(5, 9, 8, 9);
      g.translate(-x, -y);
    }
  }
  
  private static class MenuArrowIcon implements Icon, UIResource, Serializable {
    private static final int WIDTH = 4;
    
    private static final int HEIGHT = 8;
    
    private MenuArrowIcon() {}
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
      JMenuItem b = (JMenuItem)c;
      g.translate(x, y);
      if (PlasticUtils.isLeftToRight(b)) {
        g.drawLine(0, 0, 0, 7);
        g.drawLine(1, 1, 1, 6);
        g.drawLine(2, 2, 2, 5);
        g.drawLine(3, 3, 3, 4);
      } else {
        g.drawLine(4, 0, 4, 7);
        g.drawLine(3, 1, 3, 6);
        g.drawLine(2, 2, 2, 5);
        g.drawLine(1, 3, 1, 4);
      } 
      g.translate(-x, -y);
    }
    
    public int getIconWidth() {
      return 4;
    }
    
    public int getIconHeight() {
      return 8;
    }
  }
  
  private static class ExpandedTreeIcon implements Icon, Serializable {
    protected static final int SIZE = 9;
    
    protected static final int HALF_SIZE = 4;
    
    private ExpandedTreeIcon() {}
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
      g.setColor(Color.WHITE);
      g.fillRect(x, y, 8, 8);
      g.setColor(Color.GRAY);
      g.drawRect(x, y, 8, 8);
      g.setColor(Color.BLACK);
      g.drawLine(x + 2, y + 4, x + 6, y + 4);
    }
    
    public int getIconWidth() {
      return 9;
    }
    
    public int getIconHeight() {
      return 9;
    }
  }
  
  private static class CollapsedTreeIcon extends ExpandedTreeIcon {
    private CollapsedTreeIcon() {}
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
      super.paintIcon(c, g, x, y);
      g.drawLine(x + 4, y + 2, x + 4, y + 6);
    }
  }
  
  private static class ComboBoxButtonIcon implements Icon, Serializable {
    private ComboBoxButtonIcon() {}
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
      JComponent component = (JComponent)c;
      int iconWidth = getIconWidth();
      g.translate(x, y);
      g.setColor(component.isEnabled() ? MetalLookAndFeel.getControlInfo() : MetalLookAndFeel.getControlShadow());
      g.drawLine(0, 0, iconWidth - 1, 0);
      g.drawLine(1, 1, 1 + iconWidth - 3, 1);
      g.drawLine(2, 2, 2 + iconWidth - 5, 2);
      g.drawLine(3, 3, 3 + iconWidth - 7, 3);
      g.translate(-x, -y);
    }
    
    public int getIconWidth() {
      return 8;
    }
    
    public int getIconHeight() {
      return 4;
    }
  }
  
  static Icon getCheckBoxIcon() {
    if (checkBoxIcon == null)
      checkBoxIcon = new CheckBoxIcon(); 
    return checkBoxIcon;
  }
  
  static Icon getCheckBoxMenuItemIcon() {
    if (checkBoxMenuItemIcon == null)
      checkBoxMenuItemIcon = new CheckBoxMenuItemIcon(); 
    return checkBoxMenuItemIcon;
  }
  
  static Icon getRadioButtonMenuItemIcon() {
    if (radioButtonMenuItemIcon == null)
      radioButtonMenuItemIcon = new RadioButtonMenuItemIcon(); 
    return radioButtonMenuItemIcon;
  }
  
  static Icon getMenuArrowIcon() {
    if (menuArrowIcon == null)
      menuArrowIcon = new MenuArrowIcon(); 
    return menuArrowIcon;
  }
  
  static Icon getExpandedTreeIcon() {
    if (expandedTreeIcon == null)
      expandedTreeIcon = new ExpandedTreeIcon(); 
    return expandedTreeIcon;
  }
  
  static Icon getCollapsedTreeIcon() {
    if (collapsedTreeIcon == null)
      collapsedTreeIcon = new CollapsedTreeIcon(); 
    return collapsedTreeIcon;
  }
  
  static Icon getComboBoxButtonIcon() {
    if (comboBoxButtonIcon == null)
      comboBoxButtonIcon = new ComboBoxButtonIcon(); 
    return comboBoxButtonIcon;
  }
}
