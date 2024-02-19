package com.jgoodies.looks.plastic;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.text.JTextComponent;

final class PlasticBorders {
  private static Border comboBoxEditorBorder;
  
  private static Border comboBoxArrowButtonBorder;
  
  private static Border etchedBorder;
  
  private static Border flush3DBorder;
  
  private static Border menuBarHeaderBorder;
  
  private static Border menuBorder;
  
  private static Border menuItemBorder;
  
  private static Border popupMenuBorder;
  
  private static Border noMarginPopupMenuBorder;
  
  private static Border rolloverButtonBorder;
  
  private static Border scrollPaneBorder;
  
  private static Border separatorBorder;
  
  private static Border textFieldBorder;
  
  private static Border thinLoweredBorder;
  
  private static Border thinRaisedBorder;
  
  private static Border toolBarHeaderBorder;
  
  static Border getButtonBorder(Insets buttonMargin) {
    return new BorderUIResource.CompoundBorderUIResource(new ButtonBorder(buttonMargin), new BasicBorders.MarginBorder());
  }
  
  static Border getComboBoxArrowButtonBorder() {
    if (comboBoxArrowButtonBorder == null)
      comboBoxArrowButtonBorder = new CompoundBorder(new ComboBoxArrowButtonBorder(), new BasicBorders.MarginBorder()); 
    return comboBoxArrowButtonBorder;
  }
  
  static Border getComboBoxEditorBorder() {
    if (comboBoxEditorBorder == null)
      comboBoxEditorBorder = new CompoundBorder(new ComboBoxEditorBorder(), new BasicBorders.MarginBorder()); 
    return comboBoxEditorBorder;
  }
  
  static Border getEtchedBorder() {
    if (etchedBorder == null)
      etchedBorder = new BorderUIResource.CompoundBorderUIResource(new EtchedBorder(), new BasicBorders.MarginBorder()); 
    return etchedBorder;
  }
  
  static Border getFlush3DBorder() {
    if (flush3DBorder == null)
      flush3DBorder = new Flush3DBorder(); 
    return flush3DBorder;
  }
  
  static Border getInternalFrameBorder() {
    return new InternalFrameBorder();
  }
  
  static Border getMenuBarHeaderBorder() {
    if (menuBarHeaderBorder == null)
      menuBarHeaderBorder = new BorderUIResource.CompoundBorderUIResource(new MenuBarHeaderBorder(), new BasicBorders.MarginBorder()); 
    return menuBarHeaderBorder;
  }
  
  static Border getMenuBorder() {
    if (menuBorder == null)
      menuBorder = new BorderUIResource.CompoundBorderUIResource(new MenuBorder(), new BasicBorders.MarginBorder()); 
    return menuBorder;
  }
  
  static Border getMenuItemBorder() {
    if (menuItemBorder == null)
      menuItemBorder = new BorderUIResource(new BasicBorders.MarginBorder()); 
    return menuItemBorder;
  }
  
  static Border getPopupMenuBorder() {
    if (popupMenuBorder == null)
      popupMenuBorder = new PopupMenuBorder(); 
    return popupMenuBorder;
  }
  
  static Border getNoMarginPopupMenuBorder() {
    if (noMarginPopupMenuBorder == null)
      noMarginPopupMenuBorder = new NoMarginPopupMenuBorder(); 
    return noMarginPopupMenuBorder;
  }
  
  static Border getPaletteBorder() {
    return new PaletteBorder();
  }
  
  static Border getRolloverButtonBorder() {
    if (rolloverButtonBorder == null)
      rolloverButtonBorder = new CompoundBorder(new RolloverButtonBorder(), new RolloverMarginBorder()); 
    return rolloverButtonBorder;
  }
  
  static Border getScrollPaneBorder() {
    if (scrollPaneBorder == null)
      scrollPaneBorder = new ScrollPaneBorder(); 
    return scrollPaneBorder;
  }
  
  static Border getSeparatorBorder() {
    if (separatorBorder == null)
      separatorBorder = new BorderUIResource.CompoundBorderUIResource(new SeparatorBorder(), new BasicBorders.MarginBorder()); 
    return separatorBorder;
  }
  
  static Border getTextFieldBorder() {
    if (textFieldBorder == null)
      textFieldBorder = new BorderUIResource.CompoundBorderUIResource(new TextFieldBorder(), new BasicBorders.MarginBorder()); 
    return textFieldBorder;
  }
  
  static Border getThinLoweredBorder() {
    if (thinLoweredBorder == null)
      thinLoweredBorder = new ThinLoweredBorder(); 
    return thinLoweredBorder;
  }
  
  static Border getThinRaisedBorder() {
    if (thinRaisedBorder == null)
      thinRaisedBorder = new ThinRaisedBorder(); 
    return thinRaisedBorder;
  }
  
  static Border getToggleButtonBorder(Insets buttonMargin) {
    return new BorderUIResource.CompoundBorderUIResource(new ToggleButtonBorder(buttonMargin), new BasicBorders.MarginBorder());
  }
  
  static Border getToolBarHeaderBorder() {
    if (toolBarHeaderBorder == null)
      toolBarHeaderBorder = new BorderUIResource.CompoundBorderUIResource(new ToolBarHeaderBorder(), new BasicBorders.MarginBorder()); 
    return toolBarHeaderBorder;
  }
  
  private static class Flush3DBorder extends AbstractBorder implements UIResource {
    private Flush3DBorder() {}
    
    private static final Insets INSETS = new Insets(2, 2, 2, 2);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      if (c.isEnabled()) {
        PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
      } else {
        PlasticUtils.drawDisabledBorder(g, x, y, w, h);
      } 
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
  
  private static class ButtonBorder extends AbstractBorder implements UIResource {
    protected final Insets insets;
    
    protected ButtonBorder(Insets insets) {
      this.insets = insets;
    }
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      AbstractButton button = (AbstractButton)c;
      ButtonModel model = button.getModel();
      if (model.isEnabled()) {
        boolean isPressed = (model.isPressed() && model.isArmed());
        boolean isDefault = (button instanceof JButton && ((JButton)button).isDefaultButton());
        if (isPressed && isDefault) {
          PlasticUtils.drawDefaultButtonPressedBorder(g, x, y, w, h);
        } else if (isPressed) {
          PlasticUtils.drawPressed3DBorder(g, x, y, w, h);
        } else if (isDefault) {
          PlasticUtils.drawDefaultButtonBorder(g, x, y, w, h, false);
        } else {
          PlasticUtils.drawButtonBorder(g, x, y, w, h, false);
        } 
      } else {
        PlasticUtils.drawDisabledBorder(g, x, y, w - 1, h - 1);
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
  
  private static final class ComboBoxArrowButtonBorder extends AbstractBorder implements UIResource {
    private ComboBoxArrowButtonBorder() {}
    
    protected static final Insets INSETS = new Insets(1, 1, 1, 1);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      AbstractButton button = (AbstractButton)c;
      ButtonModel model = button.getModel();
      if (model.isEnabled()) {
        boolean isPressed = (model.isPressed() && model.isArmed());
        if (isPressed) {
          PlasticUtils.drawPressed3DBorder(g, x, y, w, h);
        } else {
          PlasticUtils.drawButtonBorder(g, x, y, w, h, false);
        } 
      } else {
        PlasticUtils.drawDisabledBorder(g, x, y, w - 1, h - 1);
      } 
    }
    
    public Insets getBorderInsets(Component c) {
      return INSETS;
    }
  }
  
  private static final class ComboBoxEditorBorder extends AbstractBorder {
    private ComboBoxEditorBorder() {}
    
    private static final Insets INSETS = new Insets(2, 2, 2, 0);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      if (c.isEnabled()) {
        PlasticUtils.drawFlush3DBorder(g, x, y, w + 2, h);
      } else {
        PlasticUtils.drawDisabledBorder(g, x, y, w + 2, h - 1);
        g.setColor(UIManager.getColor("control"));
        g.drawLine(x, y + h - 1, x + w, y + h - 1);
      } 
    }
    
    public Insets getBorderInsets(Component c) {
      return INSETS;
    }
  }
  
  private static final class InternalFrameBorder extends AbstractBorder implements UIResource {
    private InternalFrameBorder() {}
    
    private static final Insets NORMAL_INSETS = new Insets(1, 1, 1, 1);
    
    private static final Insets MAXIMIZED_INSETS = new Insets(1, 1, 0, 0);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      JInternalFrame frame = (JInternalFrame)c;
      if (frame.isMaximum()) {
        paintMaximizedBorder(g, x, y, w, h);
      } else {
        PlasticUtils.drawThinFlush3DBorder(g, x, y, w, h);
      } 
    }
    
    private void paintMaximizedBorder(Graphics g, int x, int y, int w, int h) {
      g.translate(x, y);
      g.setColor(PlasticLookAndFeel.getControlHighlight());
      g.drawLine(0, 0, w - 2, 0);
      g.drawLine(0, 0, 0, h - 2);
      g.translate(-x, -y);
    }
    
    public Insets getBorderInsets(Component c) {
      return ((JInternalFrame)c).isMaximum() ? MAXIMIZED_INSETS : NORMAL_INSETS;
    }
  }
  
  private static final class PaletteBorder extends AbstractBorder implements UIResource {
    private PaletteBorder() {}
    
    private static final Insets INSETS = new Insets(1, 1, 1, 1);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      g.translate(x, y);
      g.setColor(PlasticLookAndFeel.getControlDarkShadow());
      g.drawRect(0, 0, w - 1, h - 1);
      g.translate(-x, -y);
    }
    
    public Insets getBorderInsets(Component c) {
      return INSETS;
    }
  }
  
  private static final class SeparatorBorder extends AbstractBorder implements UIResource {
    private SeparatorBorder() {}
    
    private static final Insets INSETS = new Insets(0, 0, 2, 1);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      g.translate(x, y);
      g.setColor(UIManager.getColor("Separator.foreground"));
      g.drawLine(0, h - 2, w - 1, h - 2);
      g.setColor(UIManager.getColor("Separator.background"));
      g.drawLine(0, h - 1, w - 1, h - 1);
      g.translate(-x, -y);
    }
    
    public Insets getBorderInsets(Component c) {
      return INSETS;
    }
  }
  
  private static final class ThinRaisedBorder extends AbstractBorder implements UIResource {
    private ThinRaisedBorder() {}
    
    private static final Insets INSETS = new Insets(2, 2, 2, 2);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      PlasticUtils.drawThinFlush3DBorder(g, x, y, w, h);
    }
    
    public Insets getBorderInsets(Component c) {
      return INSETS;
    }
  }
  
  private static final class ThinLoweredBorder extends AbstractBorder implements UIResource {
    private ThinLoweredBorder() {}
    
    private static final Insets INSETS = new Insets(2, 2, 2, 2);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      PlasticUtils.drawThinPressed3DBorder(g, x, y, w, h);
    }
    
    public Insets getBorderInsets(Component c) {
      return INSETS;
    }
  }
  
  private static final class EtchedBorder extends AbstractBorder implements UIResource {
    private EtchedBorder() {}
    
    private static final Insets INSETS = new Insets(2, 2, 2, 2);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      PlasticUtils.drawThinPressed3DBorder(g, x, y, w, h);
      PlasticUtils.drawThinFlush3DBorder(g, x + 1, y + 1, w - 2, h - 2);
    }
    
    public Insets getBorderInsets(Component c) {
      return INSETS;
    }
  }
  
  private static final class MenuBarHeaderBorder extends AbstractBorder implements UIResource {
    private MenuBarHeaderBorder() {}
    
    private static final Insets INSETS = new Insets(2, 2, 1, 2);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      PlasticUtils.drawThinPressed3DBorder(g, x, y, w, h + 1);
      PlasticUtils.drawThinFlush3DBorder(g, x + 1, y + 1, w - 2, h - 1);
    }
    
    public Insets getBorderInsets(Component c) {
      return INSETS;
    }
  }
  
  private static final class ToolBarHeaderBorder extends AbstractBorder implements UIResource {
    private ToolBarHeaderBorder() {}
    
    private static final Insets INSETS = new Insets(1, 2, 2, 2);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      PlasticUtils.drawThinPressed3DBorder(g, x, y - 1, w, h + 1);
      PlasticUtils.drawThinFlush3DBorder(g, x + 1, y, w - 2, h - 1);
    }
    
    public Insets getBorderInsets(Component c) {
      return INSETS;
    }
  }
  
  private static final class MenuBorder extends AbstractBorder implements UIResource {
    private MenuBorder() {}
    
    private static final Insets INSETS = new Insets(2, 2, 2, 2);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      JMenuItem b = (JMenuItem)c;
      ButtonModel model = b.getModel();
      if (model.isArmed() || model.isSelected()) {
        g.setColor(PlasticLookAndFeel.getControlDarkShadow());
        g.drawLine(0, 0, w - 2, 0);
        g.drawLine(0, 0, 0, h - 1);
        g.setColor(PlasticLookAndFeel.getPrimaryControlHighlight());
        g.drawLine(w - 1, 0, w - 1, h - 1);
      } else if (model.isRollover()) {
        g.translate(x, y);
        PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
        g.translate(-x, -y);
      } 
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
  
  private static final class PopupMenuBorder extends AbstractBorder implements UIResource {
    private PopupMenuBorder() {}
    
    private static final Insets INSETS = new Insets(3, 3, 3, 3);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      g.translate(x, y);
      g.setColor(PlasticLookAndFeel.getControlDarkShadow());
      g.drawRect(0, 0, w - 1, h - 1);
      g.setColor(PlasticLookAndFeel.getMenuItemBackground());
      g.drawRect(1, 1, w - 3, h - 3);
      g.drawRect(2, 2, w - 5, h - 5);
      g.translate(-x, -y);
    }
    
    public Insets getBorderInsets(Component c) {
      return INSETS;
    }
  }
  
  private static final class NoMarginPopupMenuBorder extends AbstractBorder implements UIResource {
    private NoMarginPopupMenuBorder() {}
    
    private static final Insets INSETS = new Insets(1, 1, 1, 1);
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      g.translate(x, y);
      g.setColor(PlasticLookAndFeel.getControlDarkShadow());
      g.drawRect(0, 0, w - 1, h - 1);
      g.translate(-x, -y);
    }
    
    public Insets getBorderInsets(Component c) {
      return INSETS;
    }
  }
  
  private static class RolloverButtonBorder extends ButtonBorder {
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
          PlasticUtils.drawPressed3DBorder(g, x, y, w, h);
        } else {
          PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
        } 
      } else if (model.isSelected()) {
        PlasticUtils.drawDark3DBorder(g, x, y, w, h);
      } 
    }
  }
  
  static final class RolloverMarginBorder extends EmptyBorder {
    RolloverMarginBorder() {
      super(1, 1, 1, 1);
    }
    
    public Insets getBorderInsets(Component c) {
      return getBorderInsets(c, new Insets(0, 0, 0, 0));
    }
    
    public Insets getBorderInsets(Component c, Insets insets) {
      Insets margin = null;
      if (c instanceof AbstractButton)
        margin = ((AbstractButton)c).getMargin(); 
      if (margin == null || margin instanceof UIResource) {
        insets.left = this.left;
        insets.top = this.top;
        insets.right = this.right;
        insets.bottom = this.bottom;
      } else {
        insets.left = margin.left;
        insets.top = margin.top;
        insets.right = margin.right;
        insets.bottom = margin.bottom;
      } 
      return insets;
    }
  }
  
  private static final class ScrollPaneBorder extends MetalBorders.ScrollPaneBorder {
    private ScrollPaneBorder() {}
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      g.translate(x, y);
      g.setColor(PlasticLookAndFeel.getControlDarkShadow());
      g.drawRect(0, 0, w - 2, h - 2);
      g.setColor(PlasticLookAndFeel.getControlHighlight());
      g.drawLine(w - 1, 0, w - 1, h - 1);
      g.drawLine(0, h - 1, w - 1, h - 1);
      g.translate(-x, -y);
    }
  }
  
  private static final class TextFieldBorder extends Flush3DBorder {
    private TextFieldBorder() {}
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      if (!(c instanceof JTextComponent)) {
        if (c.isEnabled()) {
          PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
        } else {
          PlasticUtils.drawDisabledBorder(g, x, y, w, h);
        } 
        return;
      } 
      if (c.isEnabled() && ((JTextComponent)c).isEditable()) {
        PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
      } else {
        PlasticUtils.drawDisabledBorder(g, x, y, w, h);
      } 
    }
  }
  
  private static final class ToggleButtonBorder extends ButtonBorder {
    private ToggleButtonBorder(Insets insets) {
      super(insets);
    }
    
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
      if (!c.isEnabled()) {
        PlasticUtils.drawDisabledBorder(g, x, y, w - 1, h - 1);
      } else {
        AbstractButton button = (AbstractButton)c;
        ButtonModel model = button.getModel();
        if (model.isPressed() && model.isArmed()) {
          PlasticUtils.drawPressed3DBorder(g, x, y, w, h);
        } else if (model.isSelected()) {
          PlasticUtils.drawDark3DBorder(g, x, y, w, h);
        } else {
          PlasticUtils.drawFlush3DBorder(g, x, y, w, h);
        } 
      } 
    }
  }
}
