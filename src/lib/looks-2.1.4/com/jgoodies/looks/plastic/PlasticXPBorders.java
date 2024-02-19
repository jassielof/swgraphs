package com.jgoodies.looks.plastic;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.text.JTextComponent;

final class PlasticXPBorders {
  private static Border comboBoxArrowButtonBorder;
  
  private static Border comboBoxEditorBorder;
  
  private static Border scrollPaneBorder;
  
  private static Border textFieldBorder;
  
  private static Border spinnerBorder;
  
  private static Border rolloverButtonBorder;
  
  static Border getButtonBorder(Insets buttonMargin) {
    return new BorderUIResource.CompoundBorderUIResource(new XPButtonBorder(buttonMargin), new BasicBorders.MarginBorder());
  }
  
  static Border getComboBoxArrowButtonBorder() {
    if (comboBoxArrowButtonBorder == null)
      comboBoxArrowButtonBorder = new CompoundBorder(new XPComboBoxArrowButtonBorder(), new BasicBorders.MarginBorder()); 
    return comboBoxArrowButtonBorder;
  }
  
  static Border getComboBoxEditorBorder() {
    if (comboBoxEditorBorder == null)
      comboBoxEditorBorder = new CompoundBorder(new XPComboBoxEditorBorder(), new BasicBorders.MarginBorder()); 
    return comboBoxEditorBorder;
  }
  
  static Border getScrollPaneBorder() {
    if (scrollPaneBorder == null)
      scrollPaneBorder = new XPScrollPaneBorder(); 
    return scrollPaneBorder;
  }
  
  static Border getTextFieldBorder() {
    if (textFieldBorder == null)
      textFieldBorder = new BorderUIResource.CompoundBorderUIResource(new XPTextFieldBorder(), new BasicBorders.MarginBorder()); 
    return textFieldBorder;
  }
  
  static Border getToggleButtonBorder(Insets buttonMargin) {
    return new BorderUIResource.CompoundBorderUIResource(new XPButtonBorder(buttonMargin), new BasicBorders.MarginBorder());
  }
  
  static Border getSpinnerBorder() {
    if (spinnerBorder == null)
      spinnerBorder = new XPSpinnerBorder(); 
    return spinnerBorder;
  }
  
  static Border getRolloverButtonBorder() {
    if (rolloverButtonBorder == null)
      rolloverButtonBorder = new CompoundBorder(new RolloverButtonBorder(), new PlasticBorders.RolloverMarginBorder()); 
    return rolloverButtonBorder;
  }
  
  private static class XPButtonBorder extends AbstractBorder implements UIResource {
    protected final Insets insets;
    
    protected XPButtonBorder(Insets insets) {
      this.insets = insets;
    }
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      AbstractButton button = (AbstractButton)c;
      ButtonModel model = button.getModel();
      if (!model.isEnabled()) {
        PlasticXPUtils.drawDisabledButtonBorder(g, x, y, w, h);
        return;
      } 
      boolean isPressed = (model.isPressed() && model.isArmed());
      boolean isDefault = (button instanceof JButton && ((JButton)button).isDefaultButton());
      boolean isFocused = (button.isFocusPainted() && button.hasFocus());
      if (isPressed) {
        PlasticXPUtils.drawPressedButtonBorder(g, x, y, w, h);
      } else if (isFocused) {
        PlasticXPUtils.drawFocusedButtonBorder(g, x, y, w, h);
      } else if (isDefault) {
        PlasticXPUtils.drawDefaultButtonBorder(g, x, y, w, h);
      } else {
        PlasticXPUtils.drawPlainButtonBorder(g, x, y, w, h);
      } 
    }
    
    public Insets getBorderInsets(Component c) {
      return this.insets;
    }
    
    public Insets getBorderInsets(Component c, Insets newInsets) {
      newInsets.top = this.insets.top;
      newInsets.left = this.insets.left;
      newInsets.bottom = this.insets.bottom;
      newInsets.right = this.insets.right;
      return newInsets;
    }
  }
  
  private static final class XPComboBoxArrowButtonBorder extends AbstractBorder implements UIResource {
    private XPComboBoxArrowButtonBorder() {}
    
    protected static final Insets INSETS = new Insets(1, 1, 1, 1);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      PlasticComboBoxButton button = (PlasticComboBoxButton)c;
      JComboBox comboBox = button.getComboBox();
      ButtonModel model = button.getModel();
      if (!model.isEnabled()) {
        PlasticXPUtils.drawDisabledButtonBorder(g, x, y, w, h);
      } else {
        boolean isPressed = (model.isPressed() && model.isArmed());
        boolean isFocused = comboBox.hasFocus();
        if (isPressed) {
          PlasticXPUtils.drawPressedButtonBorder(g, x, y, w, h);
        } else if (isFocused) {
          PlasticXPUtils.drawFocusedButtonBorder(g, x, y, w, h);
        } else {
          PlasticXPUtils.drawPlainButtonBorder(g, x, y, w, h);
        } 
      } 
      if (comboBox.isEditable()) {
        g.setColor(model.isEnabled() ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
        g.fillRect(x, y, 1, 1);
        g.fillRect(x, y + h - 1, 1, 1);
      } 
    }
    
    public Insets getBorderInsets(Component c) {
      return INSETS;
    }
  }
  
  private static final class XPComboBoxEditorBorder extends AbstractBorder {
    private XPComboBoxEditorBorder() {}
    
    private static final Insets INSETS = new Insets(1, 1, 1, 0);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      g.setColor(c.isEnabled() ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
      PlasticXPUtils.drawRect(g, x, y, w + 1, h - 1);
    }
    
    public Insets getBorderInsets(Component c) {
      return INSETS;
    }
  }
  
  private static final class XPTextFieldBorder extends AbstractBorder {
    private XPTextFieldBorder() {}
    
    private static final Insets INSETS = new Insets(1, 1, 1, 1);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      boolean enabled = ((c instanceof JTextComponent && c.isEnabled() && ((JTextComponent)c).isEditable()) || c.isEnabled());
      g.setColor(enabled ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
      PlasticXPUtils.drawRect(g, x, y, w - 1, h - 1);
    }
    
    public Insets getBorderInsets(Component c) {
      return INSETS;
    }
    
    public Insets getBorderInsets(Component c, Insets newInsets) {
      newInsets.top = INSETS.top;
      newInsets.left = INSETS.left;
      newInsets.bottom = INSETS.bottom;
      newInsets.right = INSETS.right;
      return newInsets;
    }
  }
  
  private static final class XPScrollPaneBorder extends MetalBorders.ScrollPaneBorder {
    private XPScrollPaneBorder() {}
    
    private static final Insets INSETS = new Insets(1, 1, 1, 1);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      g.setColor(c.isEnabled() ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
      PlasticXPUtils.drawRect(g, x, y, w - 1, h - 1);
    }
    
    public Insets getBorderInsets(Component c) {
      return INSETS;
    }
    
    public Insets getBorderInsets(Component c, Insets newInsets) {
      newInsets.top = INSETS.top;
      newInsets.left = INSETS.left;
      newInsets.bottom = INSETS.bottom;
      newInsets.right = INSETS.right;
      return newInsets;
    }
  }
  
  private static final class XPSpinnerBorder extends MetalBorders.ScrollPaneBorder {
    private XPSpinnerBorder() {}
    
    private static final Insets INSETS = new Insets(1, 1, 1, 1);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      g.setColor(c.isEnabled() ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
      int arrowButtonWidth = UIManager.getInt("ScrollBar.width") - 1;
      w -= arrowButtonWidth;
      g.fillRect(x, y, w, 1);
      g.fillRect(x, y + 1, 1, h - 1);
      g.fillRect(x + 1, y + h - 1, w - 1, 1);
    }
    
    public Insets getBorderInsets(Component c) {
      return INSETS;
    }
    
    public Insets getBorderInsets(Component c, Insets newInsets) {
      newInsets.top = INSETS.top;
      newInsets.left = INSETS.left;
      newInsets.bottom = INSETS.bottom;
      newInsets.right = INSETS.right;
      return newInsets;
    }
  }
  
  private static final class RolloverButtonBorder extends XPButtonBorder {
    private RolloverButtonBorder() {
      super(new Insets(3, 3, 3, 3));
    }
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      AbstractButton b = (AbstractButton)c;
      ButtonModel model = b.getModel();
      if (!model.isEnabled())
        return; 
      if (!(c instanceof javax.swing.JToggleButton)) {
        if (model.isRollover() && (!model.isPressed() || model.isArmed()))
          super.paintBorder(c, g, x, y, w, h); 
        return;
      } 
      if (model.isRollover()) {
        if (model.isPressed() && model.isArmed()) {
          PlasticXPUtils.drawPressedButtonBorder(g, x, y, w, h);
        } else {
          PlasticXPUtils.drawPlainButtonBorder(g, x, y, w, h);
        } 
      } else if (model.isSelected()) {
        PlasticXPUtils.drawPressedButtonBorder(g, x, y, w, h);
      } 
    }
  }
}
