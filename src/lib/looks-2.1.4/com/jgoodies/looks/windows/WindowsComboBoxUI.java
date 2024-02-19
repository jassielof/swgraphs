package com.jgoodies.looks.windows;

import com.jgoodies.looks.LookUtils;
import com.sun.java.swing.plaf.windows.WindowsComboBoxUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

public class WindowsComboBoxUI extends WindowsComboBoxUI {
  private static final String CELL_EDITOR_KEY = "JComboBox.isTableCellEditor";
  
  private static final JTextField PHANTOM = new JTextField("Phantom");
  
  private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
  
  private static final Border EMPTY_BORDER = new EmptyBorder(EMPTY_INSETS);
  
  private boolean tableCellEditor;
  
  private PropertyChangeListener propertyChangeListener;
  
  public static ComponentUI createUI(JComponent b) {
    ensurePhantomHasWindowsUI();
    return new WindowsComboBoxUI();
  }
  
  private static void ensurePhantomHasWindowsUI() {
    if (!(PHANTOM.getUI() instanceof com.sun.java.swing.plaf.windows.WindowsTextFieldUI))
      PHANTOM.updateUI(); 
  }
  
  public void installUI(JComponent c) {
    super.installUI(c);
    this.tableCellEditor = isTableCellEditor();
  }
  
  protected void installListeners() {
    super.installListeners();
    this.propertyChangeListener = new TableCellEditorPropertyChangeHandler();
    this.comboBox.addPropertyChangeListener("JComboBox.isTableCellEditor", this.propertyChangeListener);
  }
  
  protected void uninstallListeners() {
    super.uninstallListeners();
    this.comboBox.removePropertyChangeListener("JComboBox.isTableCellEditor", this.propertyChangeListener);
    this.propertyChangeListener = null;
  }
  
  protected JButton createArrowButton() {
    return LookUtils.IS_LAF_WINDOWS_XP_ENABLED ? super.createArrowButton() : new WindowsArrowButton(5);
  }
  
  protected ComboBoxEditor createEditor() {
    return new WindowsComboBoxEditor.UIResource(this.tableCellEditor);
  }
  
  protected LayoutManager createLayoutManager() {
    return new WindowsComboBoxLayoutManager();
  }
  
  protected void configureEditor() {
    super.configureEditor();
    if (!this.comboBox.isEnabled())
      this.editor.setBackground(UIManager.getColor("ComboBox.disabledBackground")); 
  }
  
  protected ComboPopup createPopup() {
    return new WindowsComboPopup(this.comboBox);
  }
  
  protected ListCellRenderer createRenderer() {
    if (this.tableCellEditor)
      return super.createRenderer(); 
    BasicComboBoxRenderer renderer = new BasicComboBoxRenderer.UIResource();
    renderer.setBorder(UIManager.getBorder("ComboBox.rendererBorder"));
    return renderer;
  }
  
  public Dimension getMinimumSize(JComponent c) {
    if (!this.isMinimumSizeDirty)
      return new Dimension(this.cachedMinimumSize); 
    Dimension size = getDisplaySize();
    Insets insets = getInsets();
    size.height += insets.top + insets.bottom;
    int buttonWidth = getEditableButtonWidth();
    size.width += insets.left + insets.right + buttonWidth;
    size.width++;
    ListCellRenderer renderer = this.comboBox.getRenderer();
    if (renderer instanceof JComponent) {
      JComponent component = (JComponent)renderer;
      Insets rendererInsets = component.getInsets();
      Insets editorInsets = UIManager.getInsets("ComboBox.editorInsets");
      int offsetLeft = Math.max(0, editorInsets.left - rendererInsets.left);
      int offsetRight = Math.max(0, editorInsets.right - rendererInsets.right);
      size.width += offsetLeft + offsetRight;
    } 
    Dimension textFieldSize = PHANTOM.getMinimumSize();
    size.height = (LookUtils.IS_OS_WINDOWS_VISTA && !LookUtils.IS_LAF_WINDOWS_XP_ENABLED) ? textFieldSize.height : Math.max(textFieldSize.height, size.height);
    this.cachedMinimumSize.setSize(size.width, size.height);
    this.isMinimumSizeDirty = false;
    return new Dimension(size);
  }
  
  public Dimension getPreferredSize(JComponent c) {
    return getMinimumSize(c);
  }
  
  public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
    Component c;
    ListCellRenderer renderer = this.comboBox.getRenderer();
    boolean isVistaReadOnlyCombo = isVistaXPStyleReadOnlyCombo();
    if (hasFocus && !isPopupVisible(this.comboBox)) {
      c = renderer.getListCellRendererComponent(this.listBox, this.comboBox.getSelectedItem(), -1, true, false);
    } else {
      c = renderer.getListCellRendererComponent(this.listBox, this.comboBox.getSelectedItem(), -1, false, false);
      c.setBackground(UIManager.getColor("ComboBox.background"));
    } 
    Border oldBorder = null;
    Rectangle originalBounds = new Rectangle(bounds);
    if (c instanceof JComponent && !this.tableCellEditor) {
      JComponent component = (JComponent)c;
      if (isRendererBorderRemovable(component)) {
        oldBorder = component.getBorder();
        component.setBorder(EMPTY_BORDER);
      } 
      Insets rendererInsets = component.getInsets();
      Insets editorInsets = UIManager.getInsets("ComboBox.editorInsets");
      int offsetLeft = Math.max(0, editorInsets.left - rendererInsets.left);
      int offsetRight = Math.max(0, editorInsets.right - rendererInsets.right);
      int offsetTop = Math.max(0, editorInsets.top - rendererInsets.top);
      int offsetBottom = Math.max(0, editorInsets.bottom - rendererInsets.bottom);
      bounds.x += offsetLeft;
      bounds.y += offsetTop;
      bounds.width -= offsetLeft + offsetRight - 1;
      bounds.height -= offsetTop + offsetBottom;
    } 
    c.setFont(this.comboBox.getFont());
    if (hasFocus && !isPopupVisible(this.comboBox) && !isVistaReadOnlyCombo) {
      c.setForeground(this.listBox.getSelectionForeground());
      c.setBackground(this.listBox.getSelectionBackground());
    } else if (this.comboBox.isEnabled()) {
      c.setForeground(this.comboBox.getForeground());
      c.setBackground(this.comboBox.getBackground());
    } else {
      c.setForeground(UIManager.getColor("ComboBox.disabledForeground"));
      c.setBackground(UIManager.getColor("ComboBox.disabledBackground"));
    } 
    boolean shouldValidate = c instanceof javax.swing.JPanel;
    Boolean oldOpaque = null;
    if (isVistaReadOnlyCombo && c instanceof JComponent && !(c instanceof javax.swing.DefaultListCellRenderer)) {
      oldOpaque = Boolean.valueOf(c.isOpaque());
      ((JComponent)c).setOpaque(false);
    } 
    this.currentValuePane.paintComponent(g, c, this.comboBox, bounds.x, bounds.y, bounds.width, bounds.height, shouldValidate);
    if (hasFocus) {
      Color oldColor = g.getColor();
      g.setColor(this.comboBox.getForeground());
      if (isVistaReadOnlyCombo) {
        int width = originalBounds.width - 2;
        if (width % 2 == 0)
          width++; 
        WindowsUtils.drawRoundedDashedRect(g, originalBounds.x + 1, originalBounds.y + 1, width, originalBounds.height - 2);
      } 
      g.setColor(oldColor);
    } 
    if (oldOpaque != null)
      ((JComponent)c).setOpaque(oldOpaque.booleanValue()); 
    if (oldBorder != null)
      ((JComponent)c).setBorder(oldBorder); 
  }
  
  protected boolean isRendererBorderRemovable(JComponent rendererComponent) {
    if (rendererComponent instanceof BasicComboBoxRenderer.UIResource)
      return true; 
    Object hint = rendererComponent.getClientProperty("isBorderRemovable");
    if (hint != null)
      return Boolean.TRUE.equals(hint); 
    Border border = rendererComponent.getBorder();
    return border instanceof EmptyBorder;
  }
  
  private boolean isVistaXPStyleReadOnlyCombo() {
    return (LookUtils.IS_OS_WINDOWS_VISTA && LookUtils.IS_LAF_WINDOWS_XP_ENABLED && !this.comboBox.isEditable());
  }
  
  protected Rectangle rectangleForCurrentValue() {
    int width = this.comboBox.getWidth();
    int height = this.comboBox.getHeight();
    Insets insets = getInsets();
    int buttonWidth = getEditableButtonWidth();
    if (this.arrowButton != null)
      buttonWidth = this.arrowButton.getWidth(); 
    if (this.comboBox.getComponentOrientation().isLeftToRight())
      return new Rectangle(insets.left, insets.top, width - insets.left + insets.right + buttonWidth, height - insets.top + insets.bottom); 
    return new Rectangle(insets.left + buttonWidth, insets.top, width - insets.left + insets.right + buttonWidth, height - insets.top + insets.bottom);
  }
  
  private int getEditableButtonWidth() {
    return UIManager.getInt("ScrollBar.width");
  }
  
  private boolean isTableCellEditor() {
    return Boolean.TRUE.equals(this.comboBox.getClientProperty("JComboBox.isTableCellEditor"));
  }
  
  private final class WindowsComboBoxLayoutManager extends BasicComboBoxUI.ComboBoxLayoutManager {
    private final WindowsComboBoxUI this$0;
    
    private WindowsComboBoxLayoutManager() {}
    
    public void layoutContainer(Container parent) {
      JComboBox cb = (JComboBox)parent;
      int width = cb.getWidth();
      int height = cb.getHeight();
      Insets insets = WindowsComboBoxUI.this.getInsets();
      int buttonWidth = WindowsComboBoxUI.this.getEditableButtonWidth();
      int buttonHeight = height - insets.top + insets.bottom;
      if (WindowsComboBoxUI.this.arrowButton != null)
        if (cb.getComponentOrientation().isLeftToRight()) {
          WindowsComboBoxUI.this.arrowButton.setBounds(width - insets.right + buttonWidth, insets.top, buttonWidth, buttonHeight);
        } else {
          WindowsComboBoxUI.this.arrowButton.setBounds(insets.left, insets.top, buttonWidth, buttonHeight);
        }  
      if (WindowsComboBoxUI.this.editor != null)
        WindowsComboBoxUI.this.editor.setBounds(WindowsComboBoxUI.this.rectangleForCurrentValue()); 
    }
  }
  
  private static final class WindowsComboPopup extends BasicComboPopup {
    private WindowsComboPopup(JComboBox combo) {
      super(combo);
    }
    
    protected Rectangle computePopupBounds(int px, int py, int pw, int ph) {
      Rectangle defaultBounds = super.computePopupBounds(px, py, pw, ph);
      Object popupPrototypeDisplayValue = this.comboBox.getClientProperty("ComboBox.popupPrototypeDisplayValue");
      if (popupPrototypeDisplayValue == null)
        return defaultBounds; 
      ListCellRenderer renderer = this.list.getCellRenderer();
      Component c = renderer.getListCellRendererComponent(this.list, popupPrototypeDisplayValue, -1, true, true);
      pw = (c.getPreferredSize()).width;
      boolean hasVerticalScrollBar = (this.comboBox.getItemCount() > this.comboBox.getMaximumRowCount());
      if (hasVerticalScrollBar) {
        JScrollBar verticalBar = this.scroller.getVerticalScrollBar();
        pw += (verticalBar.getPreferredSize()).width;
      } 
      Rectangle prototypeBasedBounds = super.computePopupBounds(px, py, pw, ph);
      return (prototypeBasedBounds.width > defaultBounds.width) ? prototypeBasedBounds : defaultBounds;
    }
  }
  
  private final class TableCellEditorPropertyChangeHandler implements PropertyChangeListener {
    private final WindowsComboBoxUI this$0;
    
    private TableCellEditorPropertyChangeHandler() {}
    
    public void propertyChange(PropertyChangeEvent evt) {
      WindowsComboBoxUI.this.tableCellEditor = WindowsComboBoxUI.this.isTableCellEditor();
      if (WindowsComboBoxUI.this.comboBox.getRenderer() == null || WindowsComboBoxUI.this.comboBox.getRenderer() instanceof javax.swing.plaf.UIResource)
        WindowsComboBoxUI.this.comboBox.setRenderer(WindowsComboBoxUI.this.createRenderer()); 
      if (WindowsComboBoxUI.this.comboBox.getEditor() == null || WindowsComboBoxUI.this.comboBox.getEditor() instanceof javax.swing.plaf.UIResource)
        WindowsComboBoxUI.this.comboBox.setEditor(WindowsComboBoxUI.this.createEditor()); 
    }
  }
}
