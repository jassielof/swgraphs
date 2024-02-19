package com.jgoodies.looks.plastic;

import java.awt.Color;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class PlasticInternalFrameUI extends BasicInternalFrameUI {
  private static final String FRAME_TYPE = "JInternalFrame.frameType";
  
  public static final String IS_PALETTE = "JInternalFrame.isPalette";
  
  private static final String PALETTE_FRAME = "palette";
  
  private static final String OPTION_DIALOG = "optionDialog";
  
  private static final Border EMPTY_BORDER = new EmptyBorder(0, 0, 0, 0);
  
  private PlasticInternalFrameTitlePane titlePane;
  
  private PropertyChangeListener paletteListener;
  
  private PropertyChangeListener contentPaneListener;
  
  public PlasticInternalFrameUI(JInternalFrame b) {
    super(b);
  }
  
  public static ComponentUI createUI(JComponent c) {
    return new PlasticInternalFrameUI((JInternalFrame)c);
  }
  
  public void installUI(JComponent c) {
    this.frame = (JInternalFrame)c;
    this.paletteListener = new PaletteListener(this);
    this.contentPaneListener = new ContentPaneListener(this);
    c.addPropertyChangeListener(this.paletteListener);
    c.addPropertyChangeListener(this.contentPaneListener);
    super.installUI(c);
    Object paletteProp = c.getClientProperty("JInternalFrame.isPalette");
    if (paletteProp != null)
      setPalette(((Boolean)paletteProp).booleanValue()); 
    Container content = this.frame.getContentPane();
    stripContentBorder(content);
  }
  
  public void uninstallUI(JComponent c) {
    this.frame = (JInternalFrame)c;
    c.removePropertyChangeListener(this.paletteListener);
    c.removePropertyChangeListener(this.contentPaneListener);
    Container cont = ((JInternalFrame)c).getContentPane();
    if (cont instanceof JComponent) {
      JComponent content = (JComponent)cont;
      if (content.getBorder() == EMPTY_BORDER)
        content.setBorder((Border)null); 
    } 
    super.uninstallUI(c);
  }
  
  protected void installDefaults() {
    super.installDefaults();
    JComponent contentPane = (JComponent)this.frame.getContentPane();
    if (contentPane != null) {
      Color bg = contentPane.getBackground();
      if (bg instanceof javax.swing.plaf.UIResource)
        contentPane.setBackground((Color)null); 
    } 
    this.frame.setBackground(UIManager.getLookAndFeelDefaults().getColor("control"));
  }
  
  protected void installKeyboardActions() {}
  
  protected void uninstallKeyboardActions() {}
  
  private void stripContentBorder(Object c) {
    if (c instanceof JComponent) {
      JComponent contentComp = (JComponent)c;
      Border contentBorder = contentComp.getBorder();
      if (contentBorder == null || contentBorder instanceof javax.swing.plaf.UIResource)
        contentComp.setBorder(EMPTY_BORDER); 
    } 
  }
  
  protected JComponent createNorthPane(JInternalFrame w) {
    this.titlePane = new PlasticInternalFrameTitlePane(w);
    return this.titlePane;
  }
  
  public void setPalette(boolean isPalette) {
    String key = isPalette ? "InternalFrame.paletteBorder" : "InternalFrame.border";
    LookAndFeel.installBorder(this.frame, key);
    this.titlePane.setPalette(isPalette);
  }
  
  private void setFrameType(String frameType) {
    String key;
    boolean hasPalette = frameType.equals("palette");
    if (frameType.equals("optionDialog")) {
      key = "InternalFrame.optionDialogBorder";
    } else if (hasPalette) {
      key = "InternalFrame.paletteBorder";
    } else {
      key = "InternalFrame.border";
    } 
    LookAndFeel.installBorder(this.frame, key);
    this.titlePane.setPalette(hasPalette);
  }
  
  private static final class PaletteListener implements PropertyChangeListener {
    private final PlasticInternalFrameUI ui;
    
    private PaletteListener(PlasticInternalFrameUI ui) {
      this.ui = ui;
    }
    
    public void propertyChange(PropertyChangeEvent e) {
      String name = e.getPropertyName();
      Object value = e.getNewValue();
      if (name.equals("JInternalFrame.frameType")) {
        if (value instanceof String)
          this.ui.setFrameType((String)value); 
      } else if (name.equals("JInternalFrame.isPalette")) {
        this.ui.setPalette(Boolean.TRUE.equals(value));
      } 
    }
  }
  
  private static final class ContentPaneListener implements PropertyChangeListener {
    private final PlasticInternalFrameUI ui;
    
    private ContentPaneListener(PlasticInternalFrameUI ui) {
      this.ui = ui;
    }
    
    public void propertyChange(PropertyChangeEvent e) {
      String name = e.getPropertyName();
      if (name.equals("contentPane"))
        this.ui.stripContentBorder(e.getNewValue()); 
    }
  }
}
