package com.jgoodies.looks.plastic;

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
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.TextUI;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

public class PlasticComboBoxUI extends MetalComboBoxUI {
  static final String CELL_EDITOR_KEY = "JComboBox.isTableCellEditor";
  
  private static final JTextField PHANTOM = new JTextField("Phantom");
  
  private static Class phantomLafClass;
  
  private boolean tableCellEditor;
  
  private PropertyChangeListener propertyChangeListener;
  
  public static ComponentUI createUI(JComponent b) {
    ensurePhantomHasPlasticUI();
    return new PlasticComboBoxUI();
  }
  
  private static void ensurePhantomHasPlasticUI() {
    TextUI ui = PHANTOM.getUI();
    Class lafClass = UIManager.getLookAndFeel().getClass();
    if (phantomLafClass != lafClass || !(ui instanceof javax.swing.plaf.metal.MetalTextFieldUI)) {
      phantomLafClass = lafClass;
      PHANTOM.updateUI();
    } 
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
    return new PlasticComboBoxButton(this.comboBox, PlasticIconFactory.getComboBoxButtonIcon(), this.comboBox.isEditable(), this.currentValuePane, this.listBox);
  }
  
  protected ComboBoxEditor createEditor() {
    return new PlasticComboBoxEditor.UIResource(this.tableCellEditor);
  }
  
  protected LayoutManager createLayoutManager() {
    return new PlasticComboBoxLayoutManager();
  }
  
  protected ComboPopup createPopup() {
    return new PlasticComboPopup(this.comboBox);
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
    if (this.comboBox.isEditable()) {
      Insets editorBorderInsets = UIManager.getInsets("ComboBox.editorBorderInsets");
      size.width += editorBorderInsets.left + editorBorderInsets.right;
      size.width++;
    } else if (this.arrowButton != null) {
      Insets arrowButtonInsets = this.arrowButton.getInsets();
      size.width += arrowButtonInsets.left;
    } 
    int buttonWidth = getEditableButtonWidth();
    size.width += insets.left + insets.right + buttonWidth;
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
    size.height = Math.max(textFieldSize.height, size.height);
    this.cachedMinimumSize.setSize(size.width, size.height);
    this.isMinimumSizeDirty = false;
    return new Dimension(size);
  }
  
  public Dimension getPreferredSize(JComponent c) {
    return getMinimumSize(c);
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
  
  public void update(Graphics g, JComponent c) {
    if (c.isOpaque()) {
      g.setColor(c.getBackground());
      g.fillRect(0, 0, c.getWidth(), c.getHeight());
      if (isToolBarComboBox(c))
        c.setOpaque(false); 
    } 
    paint(g, c);
  }
  
  protected boolean isToolBarComboBox(JComponent c) {
    Container parent = c.getParent();
    return (parent != null && (parent instanceof javax.swing.JToolBar || parent.getParent() instanceof javax.swing.JToolBar));
  }
  
  static int getEditableButtonWidth() {
    return UIManager.getInt("ScrollBar.width") - 1;
  }
  
  private boolean isTableCellEditor() {
    return Boolean.TRUE.equals(this.comboBox.getClientProperty("JComboBox.isTableCellEditor"));
  }
  
  private final class PlasticComboBoxLayoutManager extends MetalComboBoxUI.MetalComboBoxLayoutManager {
    private final PlasticComboBoxUI this$0;
    
    private PlasticComboBoxLayoutManager() {}
    
    public void layoutContainer(Container parent) {
      JComboBox cb = (JComboBox)parent;
      if (!cb.isEditable()) {
        super.layoutContainer(parent);
        return;
      } 
      int width = cb.getWidth();
      int height = cb.getHeight();
      Insets insets = PlasticComboBoxUI.this.getInsets();
      int buttonWidth = PlasticComboBoxUI.getEditableButtonWidth();
      int buttonHeight = height - insets.top + insets.bottom;
      if (PlasticComboBoxUI.this.arrowButton != null)
        if (cb.getComponentOrientation().isLeftToRight()) {
          PlasticComboBoxUI.this.arrowButton.setBounds(width - insets.right + buttonWidth, insets.top, buttonWidth, buttonHeight);
        } else {
          PlasticComboBoxUI.this.arrowButton.setBounds(insets.left, insets.top, buttonWidth, buttonHeight);
        }  
      if (PlasticComboBoxUI.this.editor != null)
        PlasticComboBoxUI.this.editor.setBounds(PlasticComboBoxUI.this.rectangleForCurrentValue()); 
    }
  }
  
  public PropertyChangeListener createPropertyChangeListener() {
    return new PlasticPropertyChangeListener();
  }
  
  private final class PlasticPropertyChangeListener extends BasicComboBoxUI.PropertyChangeHandler {
    private final PlasticComboBoxUI this$0;
    
    private PlasticPropertyChangeListener() {}
    
    public void propertyChange(PropertyChangeEvent e) {
      super.propertyChange(e);
      String propertyName = e.getPropertyName();
      if (propertyName.equals("editable")) {
        PlasticComboBoxButton button = (PlasticComboBoxButton)PlasticComboBoxUI.this.arrowButton;
        button.setIconOnly(PlasticComboBoxUI.this.comboBox.isEditable());
        PlasticComboBoxUI.this.comboBox.repaint();
      } else if (propertyName.equals("background")) {
        Color color = (Color)e.getNewValue();
        PlasticComboBoxUI.this.arrowButton.setBackground(color);
        PlasticComboBoxUI.this.listBox.setBackground(color);
      } else if (propertyName.equals("foreground")) {
        Color color = (Color)e.getNewValue();
        PlasticComboBoxUI.this.arrowButton.setForeground(color);
        PlasticComboBoxUI.this.listBox.setForeground(color);
      } 
    }
  }
  
  private static final class PlasticComboPopup extends BasicComboPopup {
    private PlasticComboPopup(JComboBox combo) {
      super(combo);
    }
    
    protected void configureList() {
      super.configureList();
      this.list.setForeground(UIManager.getColor("MenuItem.foreground"));
      this.list.setBackground(UIManager.getColor("MenuItem.background"));
    }
    
    protected void configureScroller() {
      super.configureScroller();
      this.scroller.getVerticalScrollBar().putClientProperty("JScrollBar.isFreeStanding", Boolean.FALSE);
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
    private final PlasticComboBoxUI this$0;
    
    private TableCellEditorPropertyChangeHandler() {}
    
    public void propertyChange(PropertyChangeEvent evt) {
      PlasticComboBoxUI.this.tableCellEditor = PlasticComboBoxUI.this.isTableCellEditor();
      if (PlasticComboBoxUI.this.comboBox.getRenderer() == null || PlasticComboBoxUI.this.comboBox.getRenderer() instanceof javax.swing.plaf.UIResource)
        PlasticComboBoxUI.this.comboBox.setRenderer(PlasticComboBoxUI.this.createRenderer()); 
      if (PlasticComboBoxUI.this.comboBox.getEditor() == null || PlasticComboBoxUI.this.comboBox.getEditor() instanceof javax.swing.plaf.UIResource)
        PlasticComboBoxUI.this.comboBox.setEditor(PlasticComboBoxUI.this.createEditor()); 
    }
  }
}
