package com.jgoodies.looks.plastic;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.CellRendererPane;
import javax.swing.DefaultButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

final class PlasticComboBoxButton extends JButton {
  private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
  
  private static final Border EMPTY_BORDER = new EmptyBorder(EMPTY_INSETS);
  
  private static final int LEFT_MARGIN = 2;
  
  private static final int RIGHT_MARGIN = 2;
  
  private final JList listBox;
  
  private final CellRendererPane rendererPane;
  
  private JComboBox comboBox;
  
  private Icon comboIcon;
  
  private boolean iconOnly = false;
  
  private boolean borderPaintsFocus;
  
  PlasticComboBoxButton(JComboBox comboBox, Icon comboIcon, boolean iconOnly, CellRendererPane rendererPane, JList listBox) {
    super("");
    setModel(new DefaultButtonModel() {
          private final PlasticComboBoxButton this$0;
          
          public void setArmed(boolean armed) {
            super.setArmed((isPressed() || armed));
          }
        });
    this.comboBox = comboBox;
    this.comboIcon = comboIcon;
    this.iconOnly = iconOnly;
    this.rendererPane = rendererPane;
    this.listBox = listBox;
    setEnabled(comboBox.isEnabled());
    setFocusable(false);
    setRequestFocusEnabled(comboBox.isEnabled());
    setBorder(UIManager.getBorder("ComboBox.arrowButtonBorder"));
    setMargin(new Insets(0, 2, 0, 2));
    this.borderPaintsFocus = UIManager.getBoolean("ComboBox.borderPaintsFocus");
  }
  
  public JComboBox getComboBox() {
    return this.comboBox;
  }
  
  public void setComboBox(JComboBox cb) {
    this.comboBox = cb;
  }
  
  public Icon getComboIcon() {
    return this.comboIcon;
  }
  
  public void setComboIcon(Icon i) {
    this.comboIcon = i;
  }
  
  public boolean isIconOnly() {
    return this.iconOnly;
  }
  
  public void setIconOnly(boolean b) {
    this.iconOnly = b;
  }
  
  public void setEnabled(boolean enabled) {
    super.setEnabled(enabled);
    if (enabled) {
      setBackground(this.comboBox.getBackground());
      setForeground(this.comboBox.getForeground());
    } else {
      setBackground(UIManager.getColor("ComboBox.disabledBackground"));
      setForeground(UIManager.getColor("ComboBox.disabledForeground"));
    } 
  }
  
  public boolean isFocusTraversable() {
    return false;
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    boolean leftToRight = PlasticUtils.isLeftToRight(this.comboBox);
    Insets insets = getInsets();
    int width = getWidth() - insets.left + insets.right;
    int height = getHeight() - insets.top + insets.bottom;
    if (height <= 0 || width <= 0)
      return; 
    int left = insets.left;
    int top = insets.top;
    int right = left + width - 1;
    int iconWidth = 0;
    int iconLeft = leftToRight ? right : left;
    if (this.comboIcon != null) {
      int iconTop;
      iconWidth = this.comboIcon.getIconWidth();
      int iconHeight = this.comboIcon.getIconHeight();
      if (this.iconOnly) {
        iconLeft = (getWidth() - iconWidth) / 2;
        iconTop = (getHeight() - iconHeight) / 2;
      } else {
        iconLeft = leftToRight ? (left + width - 1 - iconWidth) : left;
        iconTop = (getHeight() - iconHeight) / 2;
      } 
      this.comboIcon.paintIcon(this, g, iconLeft, iconTop);
    } 
    if (!this.iconOnly && this.comboBox != null) {
      ListCellRenderer renderer = this.comboBox.getRenderer();
      boolean renderPressed = getModel().isPressed();
      Component c = renderer.getListCellRendererComponent(this.listBox, this.comboBox.getSelectedItem(), -1, renderPressed, false);
      int x = leftToRight ? left : (left + iconWidth);
      int y = top;
      int w = getWidth() - left - PlasticComboBoxUI.getEditableButtonWidth();
      int h = height;
      Border oldBorder = null;
      if (c instanceof JComponent && !isTableCellEditor()) {
        JComponent component = (JComponent)c;
        if (c instanceof javax.swing.plaf.basic.BasicComboBoxRenderer.UIResource) {
          oldBorder = component.getBorder();
          component.setBorder(EMPTY_BORDER);
        } 
        Insets rendererInsets = component.getInsets();
        Insets editorInsets = UIManager.getInsets("ComboBox.editorInsets");
        int offsetTop = Math.max(0, editorInsets.top - rendererInsets.top);
        int offsetBottom = Math.max(0, editorInsets.bottom - rendererInsets.bottom);
        y += offsetTop;
        h -= offsetTop + offsetBottom;
      } 
      c.setFont(this.rendererPane.getFont());
      configureColors(c);
      boolean shouldValidate = c instanceof javax.swing.JPanel;
      if (!is3D() || !(c instanceof JComponent) || !c.isOpaque()) {
        this.rendererPane.paintComponent(g, c, this, x, y, w, h, shouldValidate);
      } else {
        JComponent component = (JComponent)c;
        boolean oldOpaque = component.isOpaque();
        component.setOpaque(false);
        this.rendererPane.paintComponent(g, c, this, x, y, w, h, shouldValidate);
        component.setOpaque(oldOpaque);
      } 
      if (oldBorder != null)
        ((JComponent)c).setBorder(oldBorder); 
    } 
    if (this.comboIcon != null) {
      boolean hasFocus = this.comboBox.hasFocus();
      if (!this.borderPaintsFocus && hasFocus) {
        g.setColor(PlasticLookAndFeel.getFocusColor());
        g.drawRect(2, 2, getWidth() - 6, getHeight() - 6);
      } 
    } 
  }
  
  private void configureColors(Component c) {
    if (this.model.isArmed() && this.model.isPressed()) {
      if (isOpaque())
        c.setBackground(UIManager.getColor("Button.select")); 
      c.setForeground(this.comboBox.getForeground());
    } else if (!this.comboBox.isEnabled()) {
      if (isOpaque())
        c.setBackground(UIManager.getColor("ComboBox.disabledBackground")); 
      c.setForeground(UIManager.getColor("ComboBox.disabledForeground"));
    } else {
      c.setForeground(this.comboBox.getForeground());
      c.setBackground(this.comboBox.getBackground());
    } 
  }
  
  private boolean is3D() {
    if (PlasticUtils.force3D(this.comboBox))
      return true; 
    if (PlasticUtils.forceFlat(this.comboBox))
      return false; 
    return PlasticUtils.is3D("ComboBox.");
  }
  
  private boolean isTableCellEditor() {
    return Boolean.TRUE.equals(this.comboBox.getClientProperty("JComboBox.isTableCellEditor"));
  }
}
