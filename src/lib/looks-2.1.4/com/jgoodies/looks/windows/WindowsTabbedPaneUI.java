package com.jgoodies.looks.windows;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.Options;
import com.sun.java.swing.plaf.windows.WindowsTabbedPaneUI;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.text.View;

public final class WindowsTabbedPaneUI extends WindowsTabbedPaneUI {
  private static final boolean IS_XP_LAF_5_OR_LATER = (LookUtils.IS_JAVA_5_OR_LATER && LookUtils.IS_LAF_WINDOWS_XP_ENABLED);
  
  private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
  
  private static final int INSET = IS_XP_LAF_5_OR_LATER ? -1 : 1;
  
  private static final Insets NO_CONTENT_BORDER_NORTH_INSETS = new Insets(INSET, 0, 0, 0);
  
  private static final Insets NO_CONTENT_BORDER_WEST_INSETS = new Insets(0, INSET, 0, 0);
  
  private static final Insets NO_CONTENT_BORDER_SOUTH_INSETS = new Insets(0, 0, INSET, 0);
  
  private static final Insets NO_CONTENT_BORDER_EAST_INSETS = new Insets(0, 0, 0, INSET);
  
  private static final Insets CONTENT_BORDER_NORTH_INSETS = new Insets(0, 2, 4, 4);
  
  private static final Insets CONTENT_BORDER_WEST_INSETS = new Insets(2, 0, 4, 4);
  
  private static final Insets CONTENT_BORDER_SOUTH_INSETS = new Insets(4, 2, 0, 4);
  
  private static final Insets CONTENT_BORDER_EAST_INSETS = new Insets(2, 4, 4, 0);
  
  private static boolean isTabIconsEnabled = Options.isTabIconsEnabled();
  
  private Boolean noContentBorder;
  
  private Boolean embeddedTabs;
  
  public static ComponentUI createUI(JComponent x) {
    return new WindowsTabbedPaneUI();
  }
  
  public void installUI(JComponent c) {
    super.installUI(c);
    this.embeddedTabs = (Boolean)c.getClientProperty("jgoodies.embeddedTabs");
    this.noContentBorder = (Boolean)c.getClientProperty("jgoodies.noContentBorder");
  }
  
  private boolean hasNoContentBorder() {
    return (hasEmbeddedTabs() || Boolean.TRUE.equals(this.noContentBorder));
  }
  
  private boolean hasEmbeddedTabs() {
    return (this.embeddedTabs == null) ? false : this.embeddedTabs.booleanValue();
  }
  
  protected PropertyChangeListener createPropertyChangeListener() {
    return new MyPropertyChangeHandler();
  }
  
  private void doLayout() {
    this.tabPane.revalidate();
    this.tabPane.repaint();
  }
  
  private void embeddedTabsPropertyChanged(Boolean newValue) {
    this.embeddedTabs = newValue;
    doLayout();
  }
  
  private void noContentBorderPropertyChanged(Boolean newValue) {
    this.noContentBorder = newValue;
    doLayout();
  }
  
  protected Icon getIconForTab(int tabIndex) {
    String title = this.tabPane.getTitleAt(tabIndex);
    boolean hasTitle = (title != null && title.length() > 0);
    return (!isTabIconsEnabled && hasTitle) ? null : super.getIconForTab(tabIndex);
  }
  
  protected Insets getContentBorderInsets(int tabPlacement) {
    if (!hasNoContentBorder()) {
      if (IS_XP_LAF_5_OR_LATER) {
        switch (tabPlacement) {
          case 4:
            return CONTENT_BORDER_EAST_INSETS;
          case 2:
            return CONTENT_BORDER_WEST_INSETS;
          case 1:
            return CONTENT_BORDER_NORTH_INSETS;
        } 
        return CONTENT_BORDER_SOUTH_INSETS;
      } 
      return this.contentBorderInsets;
    } 
    if (hasEmbeddedTabs())
      return EMPTY_INSETS; 
    switch (tabPlacement) {
      case 4:
        return NO_CONTENT_BORDER_EAST_INSETS;
      case 2:
        return NO_CONTENT_BORDER_WEST_INSETS;
      case 1:
        return NO_CONTENT_BORDER_NORTH_INSETS;
    } 
    return NO_CONTENT_BORDER_SOUTH_INSETS;
  }
  
  protected int getTabLabelShiftX(int tabPlacement, int tabIndex, boolean isSelected) {
    switch (tabPlacement) {
      case 4:
        return isSelected ? 2 : 0;
      case 2:
        return isSelected ? -2 : 0;
    } 
    return 0;
  }
  
  protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected) {
    return 0;
  }
  
  protected Insets getSelectedTabPadInsets(int tabPlacement) {
    if (hasEmbeddedTabs())
      return EMPTY_INSETS; 
    if (hasNoContentBorder()) {
      int inset = IS_XP_LAF_5_OR_LATER ? 1 : 0;
      switch (tabPlacement) {
        case 2:
          return new Insets(1, 2, 1, inset);
        case 4:
          return new Insets(1, inset, 1, 2);
        case 1:
          return new Insets(2, 2, inset, 2);
        case 3:
          return new Insets(inset, 2, 2, 2);
      } 
      return EMPTY_INSETS;
    } 
    Insets superInsets = super.getSelectedTabPadInsets(tabPlacement);
    int equalized = superInsets.left + superInsets.right / 2;
    superInsets.left = superInsets.right = equalized;
    return superInsets;
  }
  
  protected Insets getTabAreaInsets(int tabPlacement) {
    return hasEmbeddedTabs() ? EMPTY_INSETS : super.getTabAreaInsets(tabPlacement);
  }
  
  protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
    if (hasNoContentBorder() && tabPlacement != 1)
      return; 
    Rectangle selRect = (selectedIndex < 0) ? null : getTabBounds(selectedIndex, this.calcRect);
    if (tabPlacement != 1 || selectedIndex < 0 || selRect.y + selRect.height + 1 < y || selRect.x < x || selRect.x > x + w) {
      super.paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
    } else {
      g.setColor(this.lightHighlight);
      g.fillRect(x, y, selRect.x + 1 - x, 1);
      g.fillRect(selRect.x + selRect.width, y, x + w - 2 - selRect.x - selRect.width, 1);
    } 
  }
  
  protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
    if (!hasNoContentBorder()) {
      Rectangle selRect = (selectedIndex < 0) ? null : getTabBounds(selectedIndex, this.calcRect);
      if (tabPlacement != 3 || selectedIndex < 0 || selRect.y - 1 > h + y || selRect.x < x || selRect.x > x + w) {
        super.paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
      } else {
        g.setColor(this.lightHighlight);
        g.fillRect(x, y + h - 1, 1, 1);
        g.setColor(this.shadow);
        g.fillRect(x + 1, y + h - 2, selRect.x - 1 - x, 1);
        g.fillRect(selRect.x + selRect.width, y + h - 2, x + w - 2 - selRect.x - selRect.width, 1);
        g.setColor(this.darkShadow);
        g.fillRect(x, y + h - 1, selRect.x - x, 1);
        g.fillRect(selRect.x + selRect.width - 1, y + h - 1, x + w - selRect.x - selRect.width, 1);
      } 
    } else if (tabPlacement == 3) {
      g.setColor(this.shadow);
      g.fillRect(x, y + h, w, 1);
    } 
  }
  
  protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
    if (!hasNoContentBorder()) {
      Rectangle selRect = (selectedIndex < 0) ? null : getTabBounds(selectedIndex, this.calcRect);
      if (tabPlacement != 2 || selectedIndex < 0 || selRect.x + selRect.width + 1 < x || selRect.y < y || selRect.y > y + h) {
        super.paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
      } else {
        g.setColor(this.lightHighlight);
        g.fillRect(x, y, 1, selRect.y + 1 - y);
        g.fillRect(x, selRect.y + selRect.height, 1, y + h - 1 - selRect.y - selRect.height);
      } 
    } else if (tabPlacement == 2) {
      g.setColor(this.shadow);
      g.fillRect(x, y, 1, h);
    } 
  }
  
  protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
    if (!hasNoContentBorder()) {
      Rectangle selRect = (selectedIndex < 0) ? null : getTabBounds(selectedIndex, this.calcRect);
      if (tabPlacement != 4 || selectedIndex < 0 || selRect.x - 1 > x + w || selRect.y < y || selRect.y > y + h) {
        super.paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);
      } else {
        g.setColor(this.lightHighlight);
        g.fillRect(x + w - 1, y, 1, 1);
        g.setColor(this.shadow);
        g.fillRect(x + w - 2, y + 1, 1, selRect.y - 1 - y);
        g.fillRect(x + w - 2, selRect.y + selRect.height, 1, y + h - 1 - selRect.y - selRect.height);
        g.setColor(this.darkShadow);
        g.fillRect(x + w - 1, y, 1, selRect.y - y);
        g.fillRect(x + w - 1, selRect.y + selRect.height - 1, 1, y + h - selRect.y - selRect.height);
      } 
    } else if (tabPlacement == 4) {
      g.setColor(this.shadow);
      g.fillRect(x + w, y, 1, h);
    } 
  }
  
  protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
    int w1, w2, w3, h1, h2, h3;
    if (!hasEmbeddedTabs()) {
      super.paintTabBorder(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
      return;
    } 
    g.translate(x - 1, y - 1);
    switch (tabPlacement) {
      case 1:
        w1 = 1;
        w2 = w - 2;
        w3 = 1;
        h1 = 1;
        h2 = h - 1;
        h3 = 0;
        break;
      case 3:
        w1 = 1;
        w2 = w - 2;
        w3 = 1;
        h1 = 0;
        h2 = h - 1;
        h3 = 1;
        break;
      case 2:
        w1 = 1;
        w2 = w - 1;
        w3 = 0;
        h1 = 1;
        h2 = h - 3;
        h3 = 1;
        break;
      default:
        w1 = 0;
        w2 = w - 1;
        w3 = 1;
        h1 = 1;
        h2 = h - 3;
        h3 = 1;
        break;
    } 
    if (isSelected) {
      g.setColor(this.lightHighlight);
      g.drawRect(w1, h1, w1 + w2 + w3, h1 + h2 + h3);
      g.setColor(this.shadow);
      g.fillRect(1 + w1, 0, w2, h1);
      g.fillRect(0, 1 + h1, w1, h2);
      g.fillRect(2 * w1 + w2 + 2 * w3, 1 + h1, w3, h2);
      g.fillRect(1 + w1, 2 * h1 + h2 + 2 * h3, w2, h3);
      g.fillRect(1, 1, w1, h1);
      g.fillRect(2 * w1 + w2 + w3, 1, w3, h1);
      g.fillRect(1, 2 * h1 + h2 + h3, w1, h3);
      g.fillRect(2 * w1 + w2 + w3, 2 * h1 + h2 + h3, w3, h3);
    } else {
      g.setColor(this.shadow);
      g.fillRect(w1 + w2 + 2 * w3, h3 * h2 / 2, w3, h2 * 2 / 3);
      g.fillRect(w3 * w2 / 2, h1 + h2 + 2 * h3, w2 / 2 + 2, h3);
    } 
    g.translate(-x + 1, -y + 1);
  }
  
  protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rectangles, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
    if (!hasEmbeddedTabs()) {
      super.paintFocusIndicator(g, tabPlacement, rectangles, tabIndex, iconRect, textRect, isSelected);
      return;
    } 
    if (this.tabPane.hasFocus() && isSelected) {
      g.setColor(this.focus);
      BasicGraphicsUtils.drawDashedRect(g, textRect.x - 2, textRect.y, textRect.width + 3, textRect.height);
    } 
  }
  
  protected boolean shouldRotateTabRuns(int tabPlacement) {
    return !hasEmbeddedTabs();
  }
  
  protected void layoutLabel(int tabPlacement, FontMetrics metrics, int tabIndex, String title, Icon icon, Rectangle tabRect, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
    textRect.x = textRect.y = iconRect.x = iconRect.y = 0;
    View v = getTextViewForTab(tabIndex);
    if (v != null)
      this.tabPane.putClientProperty("html", v); 
    int xNudge = getTabLabelShiftX(tabPlacement, tabIndex, isSelected);
    int yNudge = getTabLabelShiftY(tabPlacement, tabIndex, isSelected);
    if ((tabPlacement == 4 || tabPlacement == 2) && icon != null && title != null && !title.equals("")) {
      SwingUtilities.layoutCompoundLabel(this.tabPane, metrics, title, icon, 0, 2, 0, 11, tabRect, iconRect, textRect, this.textIconGap);
      xNudge += 4;
    } else {
      SwingUtilities.layoutCompoundLabel(this.tabPane, metrics, title, icon, 0, 0, 0, 11, tabRect, iconRect, textRect, this.textIconGap);
    } 
    this.tabPane.putClientProperty("html", (Object)null);
    iconRect.x += xNudge;
    iconRect.y += yNudge;
    textRect.x += xNudge;
    textRect.y += yNudge;
  }
  
  private final class MyPropertyChangeHandler extends BasicTabbedPaneUI.PropertyChangeHandler {
    private final WindowsTabbedPaneUI this$0;
    
    private MyPropertyChangeHandler() {}
    
    public void propertyChange(PropertyChangeEvent e) {
      super.propertyChange(e);
      String pName = e.getPropertyName();
      if (null == pName)
        return; 
      if (pName.equals("jgoodies.embeddedTabs")) {
        WindowsTabbedPaneUI.this.embeddedTabsPropertyChanged((Boolean)e.getNewValue());
        return;
      } 
      if (pName.equals("jgoodies.noContentBorder")) {
        WindowsTabbedPaneUI.this.noContentBorderPropertyChanged((Boolean)e.getNewValue());
        return;
      } 
    }
  }
}
