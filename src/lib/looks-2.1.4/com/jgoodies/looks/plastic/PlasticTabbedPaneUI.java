package com.jgoodies.looks.plastic;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.Options;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.metal.MetalTabbedPaneUI;
import javax.swing.text.View;

public final class PlasticTabbedPaneUI extends MetalTabbedPaneUI {
  private static boolean isTabIconsEnabled = Options.isTabIconsEnabled();
  
  private Boolean noContentBorder;
  
  private Boolean embeddedTabs;
  
  private AbstractRenderer renderer;
  
  private ScrollableTabSupport tabScroller;
  
  public static ComponentUI createUI(JComponent tabPane) {
    return new PlasticTabbedPaneUI();
  }
  
  public void installUI(JComponent c) {
    super.installUI(c);
    this.embeddedTabs = (Boolean)c.getClientProperty("jgoodies.embeddedTabs");
    this.noContentBorder = (Boolean)c.getClientProperty("jgoodies.noContentBorder");
    this.renderer = createRenderer(this.tabPane);
  }
  
  public void uninstallUI(JComponent c) {
    this.renderer = null;
    super.uninstallUI(c);
  }
  
  protected void installComponents() {
    if (scrollableTabLayoutEnabled() && 
      this.tabScroller == null) {
      this.tabScroller = new ScrollableTabSupport(this.tabPane.getTabPlacement());
      this.tabPane.add(this.tabScroller.viewport);
    } 
  }
  
  protected void uninstallComponents() {
    if (scrollableTabLayoutEnabled()) {
      this.tabPane.remove(this.tabScroller.viewport);
      this.tabPane.remove(this.tabScroller.scrollForwardButton);
      this.tabPane.remove(this.tabScroller.scrollBackwardButton);
      this.tabScroller = null;
    } 
  }
  
  protected void installListeners() {
    super.installListeners();
    if (this.mouseListener != null && LookUtils.IS_JAVA_1_4 && 
      scrollableTabLayoutEnabled()) {
      this.tabPane.removeMouseListener(this.mouseListener);
      this.tabScroller.tabPanel.addMouseListener(this.mouseListener);
    } 
  }
  
  protected void uninstallListeners() {
    if (this.mouseListener != null && LookUtils.IS_JAVA_1_4) {
      if (scrollableTabLayoutEnabled()) {
        this.tabScroller.tabPanel.removeMouseListener(this.mouseListener);
      } else {
        this.tabPane.removeMouseListener(this.mouseListener);
      } 
      this.mouseListener = null;
    } 
    super.uninstallListeners();
  }
  
  protected void installKeyboardActions() {
    super.installKeyboardActions();
    if (scrollableTabLayoutEnabled()) {
      Action forwardAction = new ScrollTabsForwardAction();
      Action backwardAction = new ScrollTabsBackwardAction();
      ActionMap am = SwingUtilities.getUIActionMap(this.tabPane);
      am.put("scrollTabsForwardAction", forwardAction);
      am.put("scrollTabsBackwardAction", backwardAction);
      this.tabScroller.scrollForwardButton.setAction(forwardAction);
      this.tabScroller.scrollBackwardButton.setAction(backwardAction);
    } 
  }
  
  private boolean hasNoContentBorder() {
    return Boolean.TRUE.equals(this.noContentBorder);
  }
  
  private boolean hasEmbeddedTabs() {
    return Boolean.TRUE.equals(this.embeddedTabs);
  }
  
  private AbstractRenderer createRenderer(JTabbedPane tabbedPane) {
    return hasEmbeddedTabs() ? 
      
      AbstractRenderer.createEmbeddedRenderer(tabbedPane) : AbstractRenderer.createRenderer(this.tabPane);
  }
  
  protected PropertyChangeListener createPropertyChangeListener() {
    return new MyPropertyChangeHandler();
  }
  
  protected ChangeListener createChangeListener() {
    return new TabSelectionHandler();
  }
  
  private void doLayout() {
    this.tabPane.revalidate();
    this.tabPane.repaint();
  }
  
  private void tabPlacementChanged() {
    this.renderer = createRenderer(this.tabPane);
    if (scrollableTabLayoutEnabled())
      this.tabScroller.createButtons(); 
    doLayout();
  }
  
  private void embeddedTabsPropertyChanged(Boolean newValue) {
    this.embeddedTabs = newValue;
    this.renderer = createRenderer(this.tabPane);
    doLayout();
  }
  
  private void noContentBorderPropertyChanged(Boolean newValue) {
    this.noContentBorder = newValue;
    this.tabPane.repaint();
  }
  
  public void paint(Graphics g, JComponent c) {
    int selectedIndex = this.tabPane.getSelectedIndex();
    int tabPlacement = this.tabPane.getTabPlacement();
    ensureCurrentLayout();
    if (!scrollableTabLayoutEnabled())
      paintTabArea(g, tabPlacement, selectedIndex); 
    paintContentBorder(g, tabPlacement, selectedIndex);
  }
  
  protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect) {
    Rectangle tabRect = rects[tabIndex];
    int selectedIndex = this.tabPane.getSelectedIndex();
    boolean isSelected = (selectedIndex == tabIndex);
    Graphics2D g2 = null;
    Polygon cropShape = null;
    Shape save = null;
    int cropx = 0;
    int cropy = 0;
    if (scrollableTabLayoutEnabled() && g instanceof Graphics2D) {
      int cropline;
      g2 = (Graphics2D)g;
      Rectangle viewRect = this.tabScroller.viewport.getViewRect();
      switch (tabPlacement) {
        case 2:
        case 4:
          cropline = viewRect.y + viewRect.height;
          if (tabRect.y < cropline && tabRect.y + tabRect.height > cropline) {
            cropShape = createCroppedTabClip(tabPlacement, tabRect, cropline);
            cropx = tabRect.x;
            cropy = cropline - 1;
          } 
          break;
        default:
          cropline = viewRect.x + viewRect.width;
          if (tabRect.x < cropline && tabRect.x + tabRect.width > cropline) {
            cropShape = createCroppedTabClip(tabPlacement, tabRect, cropline);
            cropx = cropline - 1;
            cropy = tabRect.y;
          } 
          break;
      } 
      if (cropShape != null) {
        save = g.getClip();
        g2.clip(cropShape);
      } 
    } 
    paintTabBackground(g, tabPlacement, tabIndex, tabRect.x, tabRect.y, tabRect.width, tabRect.height, isSelected);
    paintTabBorder(g, tabPlacement, tabIndex, tabRect.x, tabRect.y, tabRect.width, tabRect.height, isSelected);
    String title = this.tabPane.getTitleAt(tabIndex);
    Font font = this.tabPane.getFont();
    FontMetrics metrics = g.getFontMetrics(font);
    Icon icon = getIconForTab(tabIndex);
    layoutLabel(tabPlacement, metrics, tabIndex, title, icon, tabRect, iconRect, textRect, isSelected);
    paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
    paintIcon(g, tabPlacement, tabIndex, icon, iconRect, isSelected);
    paintFocusIndicator(g, tabPlacement, rects, tabIndex, iconRect, textRect, isSelected);
    if (cropShape != null) {
      paintCroppedTabEdge(g, tabPlacement, tabIndex, isSelected, cropx, cropy);
      g.setClip(save);
    } 
  }
  
  private int[] xCropLen = new int[] { 1, 1, 0, 0, 1, 1, 2, 2 };
  
  private int[] yCropLen = new int[] { 0, 3, 3, 6, 6, 9, 9, 12 };
  
  private static final int CROP_SEGMENT = 12;
  
  private Polygon createCroppedTabClip(int tabPlacement, Rectangle tabRect, int cropline) {
    int rlen = 0;
    int start = 0;
    int end = 0;
    int ostart = 0;
    switch (tabPlacement) {
      case 2:
      case 4:
        rlen = tabRect.width;
        start = tabRect.x;
        end = tabRect.x + tabRect.width;
        ostart = tabRect.y;
        break;
      default:
        rlen = tabRect.height;
        start = tabRect.y;
        end = tabRect.y + tabRect.height;
        ostart = tabRect.x;
        break;
    } 
    int rcnt = rlen / 12;
    if (rlen % 12 > 0)
      rcnt++; 
    int npts = 2 + rcnt * 8;
    int[] xp = new int[npts];
    int[] yp = new int[npts];
    int pcnt = 0;
    xp[pcnt] = ostart;
    yp[pcnt++] = end;
    xp[pcnt] = ostart;
    yp[pcnt++] = start;
    for (int i = 0; i < rcnt; i++) {
      for (int j = 0; j < this.xCropLen.length; j++) {
        xp[pcnt] = cropline - this.xCropLen[j];
        yp[pcnt] = start + i * 12 + this.yCropLen[j];
        if (yp[pcnt] >= end) {
          yp[pcnt] = end;
          pcnt++;
          break;
        } 
        pcnt++;
      } 
    } 
    if (tabPlacement == 1 || tabPlacement == 3)
      return new Polygon(xp, yp, pcnt); 
    return new Polygon(yp, xp, pcnt);
  }
  
  private void paintCroppedTabEdge(Graphics g, int tabPlacement, int tabIndex, boolean isSelected, int x, int y) {
    int xx;
    switch (tabPlacement) {
      case 2:
      case 4:
        xx = x;
        g.setColor(this.shadow);
        while (xx <= x + (this.rects[tabIndex]).width) {
          for (int i = 0; i < this.xCropLen.length; i += 2)
            g.drawLine(xx + this.yCropLen[i], y - this.xCropLen[i], xx + this.yCropLen[i + 1] - 1, y - this.xCropLen[i + 1]); 
          xx += 12;
        } 
        return;
    } 
    int yy = y;
    g.setColor(this.shadow);
    while (yy <= y + (this.rects[tabIndex]).height) {
      for (int i = 0; i < this.xCropLen.length; i += 2)
        g.drawLine(x - this.xCropLen[i], yy + this.yCropLen[i], x - this.xCropLen[i + 1], yy + this.yCropLen[i + 1] - 1); 
      yy += 12;
    } 
  }
  
  private void ensureCurrentLayout() {
    if (!this.tabPane.isValid())
      this.tabPane.validate(); 
    if (!this.tabPane.isValid()) {
      TabbedPaneLayout layout = (TabbedPaneLayout)this.tabPane.getLayout();
      layout.calculateLayoutInfo();
    } 
  }
  
  public int tabForCoordinate(JTabbedPane pane, int x, int y) {
    ensureCurrentLayout();
    Point p = new Point(x, y);
    if (scrollableTabLayoutEnabled()) {
      translatePointToTabPanel(x, y, p);
      Rectangle viewRect = this.tabScroller.viewport.getViewRect();
      if (!viewRect.contains(p))
        return -1; 
    } 
    int tabCount = this.tabPane.getTabCount();
    for (int i = 0; i < tabCount; i++) {
      if (this.rects[i].contains(p.x, p.y))
        return i; 
    } 
    return -1;
  }
  
  protected Rectangle getTabBounds(int tabIndex, Rectangle dest) {
    dest.width = (this.rects[tabIndex]).width;
    dest.height = (this.rects[tabIndex]).height;
    if (scrollableTabLayoutEnabled()) {
      Point vpp = this.tabScroller.viewport.getLocation();
      Point viewp = this.tabScroller.viewport.getViewPosition();
      dest.x = (this.rects[tabIndex]).x + vpp.x - viewp.x;
      dest.y = (this.rects[tabIndex]).y + vpp.y - viewp.y;
    } else {
      dest.x = (this.rects[tabIndex]).x;
      dest.y = (this.rects[tabIndex]).y;
    } 
    return dest;
  }
  
  private int getClosestTab(int x, int y) {
    int min = 0;
    int tabCount = Math.min(this.rects.length, this.tabPane.getTabCount());
    int max = tabCount;
    int tabPlacement = this.tabPane.getTabPlacement();
    boolean useX = (tabPlacement == 1 || tabPlacement == 3);
    int want = useX ? x : y;
    while (min != max) {
      int minLoc, maxLoc, current = (max + min) / 2;
      if (useX) {
        minLoc = (this.rects[current]).x;
        maxLoc = minLoc + (this.rects[current]).width;
      } else {
        minLoc = (this.rects[current]).y;
        maxLoc = minLoc + (this.rects[current]).height;
      } 
      if (want < minLoc) {
        max = current;
        if (min == max)
          return Math.max(0, current - 1); 
        continue;
      } 
      if (want >= maxLoc) {
        min = current;
        if (max - min <= 1)
          return Math.max(current + 1, tabCount - 1); 
        continue;
      } 
      return current;
    } 
    return min;
  }
  
  private Point translatePointToTabPanel(int srcx, int srcy, Point dest) {
    Point vpp = this.tabScroller.viewport.getLocation();
    Point viewp = this.tabScroller.viewport.getViewPosition();
    dest.x = srcx - vpp.x + viewp.x;
    dest.y = srcy - vpp.y + viewp.y;
    return dest;
  }
  
  protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
    int tabCount = this.tabPane.getTabCount();
    Rectangle iconRect = new Rectangle();
    Rectangle textRect = new Rectangle();
    Rectangle clipRect = g.getClipBounds();
    for (int i = this.runCount - 1; i >= 0; i--) {
      int start = this.tabRuns[i];
      int next = this.tabRuns[(i == this.runCount - 1) ? 0 : (i + 1)];
      int end = (next != 0) ? (next - 1) : (tabCount - 1);
      for (int j = end; j >= start; j--) {
        if (j != selectedIndex && this.rects[j].intersects(clipRect))
          paintTab(g, tabPlacement, this.rects, j, iconRect, textRect); 
      } 
    } 
    if (selectedIndex >= 0 && this.rects[selectedIndex].intersects(clipRect))
      paintTab(g, tabPlacement, this.rects, selectedIndex, iconRect, textRect); 
  }
  
  protected void layoutLabel(int tabPlacement, FontMetrics metrics, int tabIndex, String title, Icon icon, Rectangle tabRect, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
    textRect.x = textRect.y = iconRect.x = iconRect.y = 0;
    View v = getTextViewForTab(tabIndex);
    if (v != null)
      this.tabPane.putClientProperty("html", v); 
    Rectangle calcRectangle = new Rectangle(tabRect);
    if (isSelected) {
      Insets calcInsets = getSelectedTabPadInsets(tabPlacement);
      calcRectangle.x += calcInsets.left;
      calcRectangle.y += calcInsets.top;
      calcRectangle.width -= calcInsets.left + calcInsets.right;
      calcRectangle.height -= calcInsets.bottom + calcInsets.top;
    } 
    int xNudge = getTabLabelShiftX(tabPlacement, tabIndex, isSelected);
    int yNudge = getTabLabelShiftY(tabPlacement, tabIndex, isSelected);
    if ((tabPlacement == 4 || tabPlacement == 2) && icon != null && title != null && !title.equals("")) {
      SwingUtilities.layoutCompoundLabel(this.tabPane, metrics, title, icon, 0, 2, 0, 11, calcRectangle, iconRect, textRect, this.textIconGap);
      xNudge += 4;
    } else {
      SwingUtilities.layoutCompoundLabel(this.tabPane, metrics, title, icon, 0, 0, 0, 11, calcRectangle, iconRect, textRect, this.textIconGap);
      iconRect.y += calcRectangle.height % 2;
    } 
    this.tabPane.putClientProperty("html", (Object)null);
    iconRect.x += xNudge;
    iconRect.y += yNudge;
    textRect.x += xNudge;
    textRect.y += yNudge;
  }
  
  protected Icon getIconForTab(int tabIndex) {
    String title = this.tabPane.getTitleAt(tabIndex);
    boolean hasTitle = (title != null && title.length() > 0);
    return (!isTabIconsEnabled && hasTitle) ? null : super.getIconForTab(tabIndex);
  }
  
  protected LayoutManager createLayoutManager() {
    if (this.tabPane.getTabLayoutPolicy() == 1)
      return new TabbedPaneScrollLayout(); 
    return new TabbedPaneLayout();
  }
  
  private boolean scrollableTabLayoutEnabled() {
    return this.tabPane.getLayout() instanceof TabbedPaneScrollLayout;
  }
  
  protected boolean isTabInFirstRun(int tabIndex) {
    return (getRunForTab(this.tabPane.getTabCount(), tabIndex) == 0);
  }
  
  protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
    int width = this.tabPane.getWidth();
    int height = this.tabPane.getHeight();
    Insets insets = this.tabPane.getInsets();
    int x = insets.left;
    int y = insets.top;
    int w = width - insets.right - insets.left;
    int h = height - insets.top - insets.bottom;
    switch (tabPlacement) {
      case 2:
        x += calculateTabAreaWidth(tabPlacement, this.runCount, this.maxTabWidth);
        w -= x - insets.left;
        break;
      case 4:
        w -= calculateTabAreaWidth(tabPlacement, this.runCount, this.maxTabWidth);
        break;
      case 3:
        h -= calculateTabAreaHeight(tabPlacement, this.runCount, this.maxTabHeight);
        break;
      default:
        y += calculateTabAreaHeight(tabPlacement, this.runCount, this.maxTabHeight);
        h -= y - insets.top;
        break;
    } 
    g.setColor((this.selectColor == null) ? this.tabPane.getBackground() : this.selectColor);
    g.fillRect(x, y, w, h);
    Rectangle selRect = (selectedIndex < 0) ? null : getTabBounds(selectedIndex, this.calcRect);
    boolean drawBroken = (selectedIndex >= 0 && isTabInFirstRun(selectedIndex));
    boolean isContentBorderPainted = !hasNoContentBorder();
    this.renderer.paintContentBorderTopEdge(g, x, y, w, h, drawBroken, selRect, isContentBorderPainted);
    this.renderer.paintContentBorderLeftEdge(g, x, y, w, h, drawBroken, selRect, isContentBorderPainted);
    this.renderer.paintContentBorderBottomEdge(g, x, y, w, h, drawBroken, selRect, isContentBorderPainted);
    this.renderer.paintContentBorderRightEdge(g, x, y, w, h, drawBroken, selRect, isContentBorderPainted);
  }
  
  protected Insets getContentBorderInsets(int tabPlacement) {
    return this.renderer.getContentBorderInsets(super.getContentBorderInsets(tabPlacement));
  }
  
  protected Insets getTabAreaInsets(int tabPlacement) {
    return this.renderer.getTabAreaInsets(super.getTabAreaInsets(tabPlacement));
  }
  
  protected int getTabLabelShiftX(int tabPlacement, int tabIndex, boolean isSelected) {
    return this.renderer.getTabLabelShiftX(tabIndex, isSelected);
  }
  
  protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected) {
    return this.renderer.getTabLabelShiftY(tabIndex, isSelected);
  }
  
  protected int getTabRunOverlay(int tabPlacement) {
    return this.renderer.getTabRunOverlay(this.tabRunOverlay);
  }
  
  protected boolean shouldPadTabRun(int tabPlacement, int run) {
    return this.renderer.shouldPadTabRun(run, super.shouldPadTabRun(tabPlacement, run));
  }
  
  protected int getTabRunIndent(int tabPlacement, int run) {
    return this.renderer.getTabRunIndent(run);
  }
  
  protected Insets getTabInsets(int tabPlacement, int tabIndex) {
    return this.renderer.getTabInsets(tabIndex, this.tabInsets);
  }
  
  protected Insets getSelectedTabPadInsets(int tabPlacement) {
    return this.renderer.getSelectedTabPadInsets();
  }
  
  protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rectangles, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
    this.renderer.paintFocusIndicator(g, rectangles, tabIndex, iconRect, textRect, isSelected);
  }
  
  protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
    this.renderer.paintTabBackground(g, tabIndex, x, y, w, h, isSelected);
  }
  
  protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
    this.renderer.paintTabBorder(g, tabIndex, x, y, w, h, isSelected);
  }
  
  protected boolean shouldRotateTabRuns(int tabPlacement) {
    return false;
  }
  
  private class TabSelectionHandler implements ChangeListener {
    private Rectangle rect = new Rectangle();
    
    private final PlasticTabbedPaneUI this$0;
    
    public void stateChanged(ChangeEvent e) {
      JTabbedPane tabPane = (JTabbedPane)e.getSource();
      tabPane.revalidate();
      tabPane.repaint();
      if (tabPane.getTabLayoutPolicy() == 1) {
        int index = tabPane.getSelectedIndex();
        if (index < PlasticTabbedPaneUI.this.rects.length && index != -1) {
          this.rect.setBounds(PlasticTabbedPaneUI.this.rects[index]);
          Point viewPosition = PlasticTabbedPaneUI.this.tabScroller.viewport.getViewPosition();
          if (this.rect.x < viewPosition.x) {
            this.rect.x -= PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
          } else {
            this.rect.x += PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
          } 
          PlasticTabbedPaneUI.this.tabScroller.tabPanel.scrollRectToVisible(this.rect);
        } 
      } 
    }
    
    private TabSelectionHandler() {}
  }
  
  private class MyPropertyChangeHandler extends BasicTabbedPaneUI.PropertyChangeHandler {
    private final PlasticTabbedPaneUI this$0;
    
    private MyPropertyChangeHandler() {}
    
    public void propertyChange(PropertyChangeEvent e) {
      String pName = e.getPropertyName();
      if (null == pName)
        return; 
      super.propertyChange(e);
      if (pName.equals("tabPlacement")) {
        PlasticTabbedPaneUI.this.tabPlacementChanged();
        return;
      } 
      if (pName.equals("jgoodies.embeddedTabs")) {
        PlasticTabbedPaneUI.this.embeddedTabsPropertyChanged((Boolean)e.getNewValue());
        return;
      } 
      if (pName.equals("jgoodies.noContentBorder")) {
        PlasticTabbedPaneUI.this.noContentBorderPropertyChanged((Boolean)e.getNewValue());
        return;
      } 
    }
  }
  
  private class TabbedPaneLayout extends BasicTabbedPaneUI.TabbedPaneLayout implements LayoutManager {
    private final PlasticTabbedPaneUI this$0;
    
    private TabbedPaneLayout() {}
    
    protected void calculateTabRects(int tabPlacement, int tabCount) {
      int x, y, returnAt;
      FontMetrics metrics = PlasticTabbedPaneUI.this.getFontMetrics();
      Dimension size = PlasticTabbedPaneUI.this.tabPane.getSize();
      Insets insets = PlasticTabbedPaneUI.this.tabPane.getInsets();
      Insets theTabAreaInsets = PlasticTabbedPaneUI.this.getTabAreaInsets(tabPlacement);
      int fontHeight = metrics.getHeight();
      int selectedIndex = PlasticTabbedPaneUI.this.tabPane.getSelectedIndex();
      boolean verticalTabRuns = (tabPlacement == 2 || tabPlacement == 4);
      boolean leftToRight = PlasticUtils.isLeftToRight(PlasticTabbedPaneUI.this.tabPane);
      switch (tabPlacement) {
        case 2:
          PlasticTabbedPaneUI.this.maxTabWidth = PlasticTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
          x = insets.left + theTabAreaInsets.left;
          y = insets.top + theTabAreaInsets.top;
          returnAt = size.height - insets.bottom + theTabAreaInsets.bottom;
          break;
        case 4:
          PlasticTabbedPaneUI.this.maxTabWidth = PlasticTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
          x = size.width - insets.right - theTabAreaInsets.right - PlasticTabbedPaneUI.this.maxTabWidth;
          y = insets.top + theTabAreaInsets.top;
          returnAt = size.height - insets.bottom + theTabAreaInsets.bottom;
          break;
        case 3:
          PlasticTabbedPaneUI.this.maxTabHeight = PlasticTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
          x = insets.left + theTabAreaInsets.left;
          y = size.height - insets.bottom - theTabAreaInsets.bottom - PlasticTabbedPaneUI.this.maxTabHeight;
          returnAt = size.width - insets.right + theTabAreaInsets.right;
          break;
        default:
          PlasticTabbedPaneUI.this.maxTabHeight = PlasticTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
          x = insets.left + theTabAreaInsets.left;
          y = insets.top + theTabAreaInsets.top;
          returnAt = size.width - insets.right + theTabAreaInsets.right;
          break;
      } 
      int theTabRunOverlay = PlasticTabbedPaneUI.this.getTabRunOverlay(tabPlacement);
      PlasticTabbedPaneUI.this.runCount = 0;
      PlasticTabbedPaneUI.this.selectedRun = -1;
      int tabInRun = -1;
      int runReturnAt = returnAt;
      if (tabCount == 0)
        return; 
      int i;
      for (i = 0; i < tabCount; i++) {
        Rectangle rect = PlasticTabbedPaneUI.this.rects[i];
        tabInRun++;
        if (!verticalTabRuns) {
          if (i > 0) {
            (PlasticTabbedPaneUI.this.rects[i - 1]).x += (PlasticTabbedPaneUI.this.rects[i - 1]).width;
          } else {
            PlasticTabbedPaneUI.this.tabRuns[0] = 0;
            PlasticTabbedPaneUI.this.runCount = 1;
            PlasticTabbedPaneUI.this.maxTabWidth = 0;
            rect.x = x;
          } 
          rect.width = PlasticTabbedPaneUI.this.calculateTabWidth(tabPlacement, i, metrics);
          PlasticTabbedPaneUI.this.maxTabWidth = Math.max(PlasticTabbedPaneUI.this.maxTabWidth, rect.width);
          if (tabInRun != 0 && rect.x + rect.width > runReturnAt) {
            if (PlasticTabbedPaneUI.this.runCount > PlasticTabbedPaneUI.this.tabRuns.length - 1)
              PlasticTabbedPaneUI.this.expandTabRunsArray(); 
            tabInRun = 0;
            PlasticTabbedPaneUI.this.tabRuns[PlasticTabbedPaneUI.this.runCount] = i;
            PlasticTabbedPaneUI.this.runCount++;
            rect.x = x;
            runReturnAt -= 2 * PlasticTabbedPaneUI.this.getTabRunIndent(tabPlacement, PlasticTabbedPaneUI.this.runCount);
          } 
          rect.y = y;
          rect.height = PlasticTabbedPaneUI.this.maxTabHeight;
        } else {
          if (i > 0) {
            (PlasticTabbedPaneUI.this.rects[i - 1]).y += (PlasticTabbedPaneUI.this.rects[i - 1]).height;
          } else {
            PlasticTabbedPaneUI.this.tabRuns[0] = 0;
            PlasticTabbedPaneUI.this.runCount = 1;
            PlasticTabbedPaneUI.this.maxTabHeight = 0;
            rect.y = y;
          } 
          rect.height = PlasticTabbedPaneUI.this.calculateTabHeight(tabPlacement, i, fontHeight);
          PlasticTabbedPaneUI.this.maxTabHeight = Math.max(PlasticTabbedPaneUI.this.maxTabHeight, rect.height);
          if (tabInRun != 0 && rect.y + rect.height > runReturnAt) {
            if (PlasticTabbedPaneUI.this.runCount > PlasticTabbedPaneUI.this.tabRuns.length - 1)
              PlasticTabbedPaneUI.this.expandTabRunsArray(); 
            PlasticTabbedPaneUI.this.tabRuns[PlasticTabbedPaneUI.this.runCount] = i;
            PlasticTabbedPaneUI.this.runCount++;
            rect.y = y;
            tabInRun = 0;
            runReturnAt -= 2 * PlasticTabbedPaneUI.this.getTabRunIndent(tabPlacement, PlasticTabbedPaneUI.this.runCount);
          } 
          rect.x = x;
          rect.width = PlasticTabbedPaneUI.this.maxTabWidth;
        } 
        if (i == selectedIndex)
          PlasticTabbedPaneUI.this.selectedRun = PlasticTabbedPaneUI.this.runCount - 1; 
      } 
      if (PlasticTabbedPaneUI.this.runCount > 1)
        if (PlasticTabbedPaneUI.this.shouldRotateTabRuns(tabPlacement))
          rotateTabRuns(tabPlacement, PlasticTabbedPaneUI.this.selectedRun);  
      for (i = PlasticTabbedPaneUI.this.runCount - 1; i >= 0; i--) {
        int start = PlasticTabbedPaneUI.this.tabRuns[i];
        int next = PlasticTabbedPaneUI.this.tabRuns[(i == PlasticTabbedPaneUI.this.runCount - 1) ? 0 : (i + 1)];
        int end = (next != 0) ? (next - 1) : (tabCount - 1);
        int indent = PlasticTabbedPaneUI.this.getTabRunIndent(tabPlacement, i);
        if (!verticalTabRuns) {
          for (int j = start; j <= end; j++) {
            Rectangle rect = PlasticTabbedPaneUI.this.rects[j];
            rect.y = y;
            rect.x += indent;
          } 
          if (PlasticTabbedPaneUI.this.shouldPadTabRun(tabPlacement, i))
            padTabRun(tabPlacement, start, end, returnAt - 2 * indent); 
          if (tabPlacement == 3) {
            y -= PlasticTabbedPaneUI.this.maxTabHeight - theTabRunOverlay;
          } else {
            y += PlasticTabbedPaneUI.this.maxTabHeight - theTabRunOverlay;
          } 
        } else {
          for (int j = start; j <= end; j++) {
            Rectangle rect = PlasticTabbedPaneUI.this.rects[j];
            rect.x = x;
            rect.y += indent;
          } 
          if (PlasticTabbedPaneUI.this.shouldPadTabRun(tabPlacement, i))
            padTabRun(tabPlacement, start, end, returnAt - 2 * indent); 
          if (tabPlacement == 4) {
            x -= PlasticTabbedPaneUI.this.maxTabWidth - theTabRunOverlay;
          } else {
            x += PlasticTabbedPaneUI.this.maxTabWidth - theTabRunOverlay;
          } 
        } 
      } 
      padSelectedTab(tabPlacement, selectedIndex);
      if (!leftToRight && !verticalTabRuns) {
        int rightMargin = size.width - insets.right + theTabAreaInsets.right;
        for (i = 0; i < tabCount; i++)
          (PlasticTabbedPaneUI.this.rects[i]).x = rightMargin - (PlasticTabbedPaneUI.this.rects[i]).x - (PlasticTabbedPaneUI.this.rects[i]).width + PlasticTabbedPaneUI.this.renderer.getTabsOverlay(); 
      } 
    }
    
    protected void padSelectedTab(int tabPlacement, int selectedIndex) {
      if (selectedIndex >= 0) {
        Rectangle selRect = PlasticTabbedPaneUI.this.rects[selectedIndex];
        Insets padInsets = PlasticTabbedPaneUI.this.getSelectedTabPadInsets(tabPlacement);
        selRect.x -= padInsets.left;
        selRect.width += padInsets.left + padInsets.right;
        selRect.y -= padInsets.top;
        selRect.height += padInsets.top + padInsets.bottom;
      } 
    }
  }
  
  private boolean requestFocusForVisibleComponent() {
    Component visibleComponent = getVisibleComponent();
    if (visibleComponent.isFocusable()) {
      visibleComponent.requestFocus();
      return true;
    } 
    if (visibleComponent instanceof JComponent && ((JComponent)visibleComponent).requestDefaultFocus())
      return true; 
    return false;
  }
  
  private static class ScrollTabsForwardAction extends AbstractAction {
    private ScrollTabsForwardAction() {}
    
    public void actionPerformed(ActionEvent e) {
      JTabbedPane pane = null;
      Object src = e.getSource();
      if (src instanceof JTabbedPane) {
        pane = (JTabbedPane)src;
      } else if (src instanceof PlasticArrowButton) {
        pane = (JTabbedPane)((PlasticArrowButton)src).getParent();
      } else {
        return;
      } 
      PlasticTabbedPaneUI ui = (PlasticTabbedPaneUI)pane.getUI();
      if (ui.scrollableTabLayoutEnabled())
        ui.tabScroller.scrollForward(pane.getTabPlacement()); 
    }
  }
  
  private static class ScrollTabsBackwardAction extends AbstractAction {
    private ScrollTabsBackwardAction() {}
    
    public void actionPerformed(ActionEvent e) {
      JTabbedPane pane = null;
      Object src = e.getSource();
      if (src instanceof JTabbedPane) {
        pane = (JTabbedPane)src;
      } else if (src instanceof PlasticArrowButton) {
        pane = (JTabbedPane)((PlasticArrowButton)src).getParent();
      } else {
        return;
      } 
      PlasticTabbedPaneUI ui = (PlasticTabbedPaneUI)pane.getUI();
      if (ui.scrollableTabLayoutEnabled())
        ui.tabScroller.scrollBackward(pane.getTabPlacement()); 
    }
  }
  
  private class TabbedPaneScrollLayout extends TabbedPaneLayout {
    private final PlasticTabbedPaneUI this$0;
    
    private TabbedPaneScrollLayout() {}
    
    protected int preferredTabAreaHeight(int tabPlacement, int width) {
      return PlasticTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
    }
    
    protected int preferredTabAreaWidth(int tabPlacement, int height) {
      return PlasticTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
    }
    
    public void layoutContainer(Container parent) {
      int tabPlacement = PlasticTabbedPaneUI.this.tabPane.getTabPlacement();
      int tabCount = PlasticTabbedPaneUI.this.tabPane.getTabCount();
      Insets insets = PlasticTabbedPaneUI.this.tabPane.getInsets();
      int selectedIndex = PlasticTabbedPaneUI.this.tabPane.getSelectedIndex();
      Component visibleComponent = PlasticTabbedPaneUI.this.getVisibleComponent();
      calculateLayoutInfo();
      if (selectedIndex < 0) {
        if (visibleComponent != null)
          PlasticTabbedPaneUI.this.setVisibleComponent(null); 
      } else {
        Component selectedComponent = PlasticTabbedPaneUI.this.tabPane.getComponentAt(selectedIndex);
        boolean shouldChangeFocus = false;
        if (selectedComponent != null) {
          if (selectedComponent != visibleComponent && visibleComponent != null)
            if (SwingUtilities.findFocusOwner(visibleComponent) != null)
              shouldChangeFocus = true;  
          PlasticTabbedPaneUI.this.setVisibleComponent(selectedComponent);
        } 
        Insets contentInsets = PlasticTabbedPaneUI.this.getContentBorderInsets(tabPlacement);
        Rectangle bounds = PlasticTabbedPaneUI.this.tabPane.getBounds();
        int numChildren = PlasticTabbedPaneUI.this.tabPane.getComponentCount();
        if (numChildren > 0) {
          int tx;
          int ty;
          int tw;
          int th;
          int cx;
          int cy;
          int cw;
          int ch;
          switch (tabPlacement) {
            case 2:
              tw = PlasticTabbedPaneUI.this.calculateTabAreaWidth(tabPlacement, PlasticTabbedPaneUI.this.runCount, PlasticTabbedPaneUI.this.maxTabWidth);
              th = bounds.height - insets.top - insets.bottom;
              tx = insets.left;
              ty = insets.top;
              cx = tx + tw + contentInsets.left;
              cy = ty + contentInsets.top;
              cw = bounds.width - insets.left - insets.right - tw - contentInsets.left - contentInsets.right;
              ch = bounds.height - insets.top - insets.bottom - contentInsets.top - contentInsets.bottom;
              break;
            case 4:
              tw = PlasticTabbedPaneUI.this.calculateTabAreaWidth(tabPlacement, PlasticTabbedPaneUI.this.runCount, PlasticTabbedPaneUI.this.maxTabWidth);
              th = bounds.height - insets.top - insets.bottom;
              tx = bounds.width - insets.right - tw;
              ty = insets.top;
              cx = insets.left + contentInsets.left;
              cy = insets.top + contentInsets.top;
              cw = bounds.width - insets.left - insets.right - tw - contentInsets.left - contentInsets.right;
              ch = bounds.height - insets.top - insets.bottom - contentInsets.top - contentInsets.bottom;
              break;
            case 3:
              tw = bounds.width - insets.left - insets.right;
              th = PlasticTabbedPaneUI.this.calculateTabAreaHeight(tabPlacement, PlasticTabbedPaneUI.this.runCount, PlasticTabbedPaneUI.this.maxTabHeight);
              tx = insets.left;
              ty = bounds.height - insets.bottom - th;
              cx = insets.left + contentInsets.left;
              cy = insets.top + contentInsets.top;
              cw = bounds.width - insets.left - insets.right - contentInsets.left - contentInsets.right;
              ch = bounds.height - insets.top - insets.bottom - th - contentInsets.top - contentInsets.bottom;
              break;
            default:
              tw = bounds.width - insets.left - insets.right;
              th = PlasticTabbedPaneUI.this.calculateTabAreaHeight(tabPlacement, PlasticTabbedPaneUI.this.runCount, PlasticTabbedPaneUI.this.maxTabHeight);
              tx = insets.left;
              ty = insets.top;
              cx = tx + contentInsets.left;
              cy = ty + th + contentInsets.top;
              cw = bounds.width - insets.left - insets.right - contentInsets.left - contentInsets.right;
              ch = bounds.height - insets.top - insets.bottom - th - contentInsets.top - contentInsets.bottom;
              break;
          } 
          for (int i = 0; i < numChildren; i++) {
            Component child = PlasticTabbedPaneUI.this.tabPane.getComponent(i);
            if (PlasticTabbedPaneUI.this.tabScroller != null && child == PlasticTabbedPaneUI.this.tabScroller.viewport) {
              int totalTabHeight, totalTabWidth;
              JViewport viewport = (JViewport)child;
              Rectangle viewRect = viewport.getViewRect();
              int vw = tw;
              int vh = th;
              Dimension butSize = PlasticTabbedPaneUI.this.tabScroller.scrollForwardButton.getPreferredSize();
              switch (tabPlacement) {
                case 2:
                case 4:
                  totalTabHeight = (PlasticTabbedPaneUI.this.rects[tabCount - 1]).y + (PlasticTabbedPaneUI.this.rects[tabCount - 1]).height;
                  if (totalTabHeight > th) {
                    vh = (th > 2 * butSize.height) ? (th - 2 * butSize.height) : 0;
                    if (totalTabHeight - viewRect.y <= vh)
                      vh = totalTabHeight - viewRect.y; 
                  } 
                  break;
                default:
                  totalTabWidth = (PlasticTabbedPaneUI.this.rects[tabCount - 1]).x + (PlasticTabbedPaneUI.this.rects[tabCount - 1]).width + PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
                  if (totalTabWidth > tw) {
                    vw = (tw > 2 * butSize.width) ? (tw - 2 * butSize.width) : 0;
                    if (totalTabWidth - viewRect.x <= vw)
                      vw = totalTabWidth - viewRect.x; 
                  } 
                  break;
              } 
              child.setBounds(tx, ty, vw, vh);
            } else if (PlasticTabbedPaneUI.this.tabScroller != null && (child == PlasticTabbedPaneUI.this.tabScroller.scrollForwardButton || child == PlasticTabbedPaneUI.this.tabScroller.scrollBackwardButton)) {
              int totalTabHeight, totalTabWidth;
              Component scrollbutton = child;
              Dimension bsize = scrollbutton.getPreferredSize();
              int bx = 0;
              int by = 0;
              int bw = bsize.width;
              int bh = bsize.height;
              boolean visible = false;
              switch (tabPlacement) {
                case 2:
                case 4:
                  totalTabHeight = (PlasticTabbedPaneUI.this.rects[tabCount - 1]).y + (PlasticTabbedPaneUI.this.rects[tabCount - 1]).height + PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
                  if (totalTabHeight > th) {
                    visible = true;
                    bx = (tabPlacement == 2) ? (tx + tw - bsize.width) : tx;
                    by = (child == PlasticTabbedPaneUI.this.tabScroller.scrollForwardButton) ? (bounds.height - insets.bottom - bsize.height) : (bounds.height - insets.bottom - 2 * bsize.height);
                  } 
                  break;
                default:
                  totalTabWidth = (PlasticTabbedPaneUI.this.rects[tabCount - 1]).x + (PlasticTabbedPaneUI.this.rects[tabCount - 1]).width + PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
                  if (totalTabWidth > tw) {
                    visible = true;
                    bx = (child == PlasticTabbedPaneUI.this.tabScroller.scrollForwardButton) ? (bounds.width - insets.left - bsize.width) : (bounds.width - insets.left - 2 * bsize.width);
                    by = (tabPlacement == 1) ? (ty + th - bsize.height) : ty;
                  } 
                  break;
              } 
              child.setVisible(visible);
              if (visible)
                child.setBounds(bx, by, bw, bh); 
            } else {
              child.setBounds(cx, cy, cw, ch);
            } 
          } 
          if (shouldChangeFocus && !PlasticTabbedPaneUI.this.requestFocusForVisibleComponent())
            PlasticTabbedPaneUI.this.tabPane.requestFocus(); 
        } 
      } 
    }
    
    protected void calculateTabRects(int tabPlacement, int tabCount) {
      FontMetrics metrics = PlasticTabbedPaneUI.this.getFontMetrics();
      Dimension size = PlasticTabbedPaneUI.this.tabPane.getSize();
      Insets insets = PlasticTabbedPaneUI.this.tabPane.getInsets();
      Insets tabAreaInsets = PlasticTabbedPaneUI.this.getTabAreaInsets(tabPlacement);
      int fontHeight = metrics.getHeight();
      int selectedIndex = PlasticTabbedPaneUI.this.tabPane.getSelectedIndex();
      boolean verticalTabRuns = (tabPlacement == 2 || tabPlacement == 4);
      boolean leftToRight = PlasticUtils.isLeftToRight(PlasticTabbedPaneUI.this.tabPane);
      int x = tabAreaInsets.left;
      int y = tabAreaInsets.top;
      int totalWidth = 0;
      int totalHeight = 0;
      switch (tabPlacement) {
        case 2:
        case 4:
          PlasticTabbedPaneUI.this.maxTabWidth = PlasticTabbedPaneUI.this.calculateMaxTabWidth(tabPlacement);
          break;
        default:
          PlasticTabbedPaneUI.this.maxTabHeight = PlasticTabbedPaneUI.this.calculateMaxTabHeight(tabPlacement);
          break;
      } 
      PlasticTabbedPaneUI.this.runCount = 0;
      PlasticTabbedPaneUI.this.selectedRun = -1;
      if (tabCount == 0)
        return; 
      PlasticTabbedPaneUI.this.selectedRun = 0;
      PlasticTabbedPaneUI.this.runCount = 1;
      for (int i = 0; i < tabCount; i++) {
        Rectangle rect = PlasticTabbedPaneUI.this.rects[i];
        if (!verticalTabRuns) {
          if (i > 0) {
            (PlasticTabbedPaneUI.this.rects[i - 1]).x += (PlasticTabbedPaneUI.this.rects[i - 1]).width;
          } else {
            PlasticTabbedPaneUI.this.tabRuns[0] = 0;
            PlasticTabbedPaneUI.this.maxTabWidth = 0;
            totalHeight += PlasticTabbedPaneUI.this.maxTabHeight;
            rect.x = x;
          } 
          rect.width = PlasticTabbedPaneUI.this.calculateTabWidth(tabPlacement, i, metrics);
          totalWidth = rect.x + rect.width + PlasticTabbedPaneUI.this.renderer.getTabsOverlay();
          PlasticTabbedPaneUI.this.maxTabWidth = Math.max(PlasticTabbedPaneUI.this.maxTabWidth, rect.width);
          rect.y = y;
          rect.height = PlasticTabbedPaneUI.this.maxTabHeight;
        } else {
          if (i > 0) {
            (PlasticTabbedPaneUI.this.rects[i - 1]).y += (PlasticTabbedPaneUI.this.rects[i - 1]).height;
          } else {
            PlasticTabbedPaneUI.this.tabRuns[0] = 0;
            PlasticTabbedPaneUI.this.maxTabHeight = 0;
            totalWidth = PlasticTabbedPaneUI.this.maxTabWidth;
            rect.y = y;
          } 
          rect.height = PlasticTabbedPaneUI.this.calculateTabHeight(tabPlacement, i, fontHeight);
          totalHeight = rect.y + rect.height;
          PlasticTabbedPaneUI.this.maxTabHeight = Math.max(PlasticTabbedPaneUI.this.maxTabHeight, rect.height);
          rect.x = x;
          rect.width = PlasticTabbedPaneUI.this.maxTabWidth;
        } 
      } 
      padSelectedTab(tabPlacement, selectedIndex);
      if (!leftToRight && !verticalTabRuns) {
        int rightMargin = size.width - insets.right + tabAreaInsets.right;
        for (int j = 0; j < tabCount; j++)
          (PlasticTabbedPaneUI.this.rects[j]).x = rightMargin - (PlasticTabbedPaneUI.this.rects[j]).x - (PlasticTabbedPaneUI.this.rects[j]).width; 
      } 
      PlasticTabbedPaneUI.this.tabScroller.tabPanel.setPreferredSize(new Dimension(totalWidth, totalHeight));
    }
  }
  
  private class ScrollableTabSupport implements ActionListener, ChangeListener {
    public PlasticTabbedPaneUI.ScrollableTabViewport viewport;
    
    public PlasticTabbedPaneUI.ScrollableTabPanel tabPanel;
    
    public JButton scrollForwardButton;
    
    public JButton scrollBackwardButton;
    
    public int leadingTabIndex;
    
    private Point tabViewPosition = new Point(0, 0);
    
    private final PlasticTabbedPaneUI this$0;
    
    ScrollableTabSupport(int tabPlacement) {
      this.viewport = new PlasticTabbedPaneUI.ScrollableTabViewport();
      this.tabPanel = new PlasticTabbedPaneUI.ScrollableTabPanel();
      this.viewport.setView(this.tabPanel);
      this.viewport.addChangeListener(this);
      createButtons();
    }
    
    void createButtons() {
      if (this.scrollForwardButton != null) {
        PlasticTabbedPaneUI.this.tabPane.remove(this.scrollForwardButton);
        this.scrollForwardButton.removeActionListener(this);
        PlasticTabbedPaneUI.this.tabPane.remove(this.scrollBackwardButton);
        this.scrollBackwardButton.removeActionListener(this);
      } 
      int tabPlacement = PlasticTabbedPaneUI.this.tabPane.getTabPlacement();
      int width = UIManager.getInt("ScrollBar.width");
      if (tabPlacement == 1 || tabPlacement == 3) {
        this.scrollForwardButton = new PlasticTabbedPaneUI.ArrowButton(3, width);
        this.scrollBackwardButton = new PlasticTabbedPaneUI.ArrowButton(7, width);
      } else {
        this.scrollForwardButton = new PlasticTabbedPaneUI.ArrowButton(5, width);
        this.scrollBackwardButton = new PlasticTabbedPaneUI.ArrowButton(1, width);
      } 
      this.scrollForwardButton.addActionListener(this);
      this.scrollBackwardButton.addActionListener(this);
      PlasticTabbedPaneUI.this.tabPane.add(this.scrollForwardButton);
      PlasticTabbedPaneUI.this.tabPane.add(this.scrollBackwardButton);
    }
    
    public void scrollForward(int tabPlacement) {
      Dimension viewSize = this.viewport.getViewSize();
      Rectangle viewRect = this.viewport.getViewRect();
      if (tabPlacement == 1 || tabPlacement == 3) {
        if (viewRect.width >= viewSize.width - viewRect.x)
          return; 
      } else if (viewRect.height >= viewSize.height - viewRect.y) {
        return;
      } 
      setLeadingTabIndex(tabPlacement, this.leadingTabIndex + 1);
    }
    
    public void scrollBackward(int tabPlacement) {
      if (this.leadingTabIndex == 0)
        return; 
      setLeadingTabIndex(tabPlacement, this.leadingTabIndex - 1);
    }
    
    public void setLeadingTabIndex(int tabPlacement, int index) {
      this.leadingTabIndex = index;
      Dimension viewSize = this.viewport.getViewSize();
      Rectangle viewRect = this.viewport.getViewRect();
      switch (tabPlacement) {
        case 1:
        case 3:
          this.tabViewPosition.x = (this.leadingTabIndex == 0) ? 0 : ((PlasticTabbedPaneUI.this.rects[this.leadingTabIndex]).x - PlasticTabbedPaneUI.this.renderer.getTabsOverlay());
          if (viewSize.width - this.tabViewPosition.x < viewRect.width) {
            Dimension extentSize = new Dimension(viewSize.width - this.tabViewPosition.x, viewRect.height);
            this.viewport.setExtentSize(extentSize);
          } 
          break;
        case 2:
        case 4:
          this.tabViewPosition.y = (this.leadingTabIndex == 0) ? 0 : (PlasticTabbedPaneUI.this.rects[this.leadingTabIndex]).y;
          if (viewSize.height - this.tabViewPosition.y < viewRect.height) {
            Dimension extentSize = new Dimension(viewRect.width, viewSize.height - this.tabViewPosition.y);
            this.viewport.setExtentSize(extentSize);
          } 
          break;
      } 
      this.viewport.setViewPosition(this.tabViewPosition);
    }
    
    public void stateChanged(ChangeEvent e) {
      JViewport viewport = (JViewport)e.getSource();
      int tabPlacement = PlasticTabbedPaneUI.this.tabPane.getTabPlacement();
      int tabCount = PlasticTabbedPaneUI.this.tabPane.getTabCount();
      Rectangle vpRect = viewport.getBounds();
      Dimension viewSize = viewport.getViewSize();
      Rectangle viewRect = viewport.getViewRect();
      this.leadingTabIndex = PlasticTabbedPaneUI.this.getClosestTab(viewRect.x, viewRect.y);
      if (this.leadingTabIndex + 1 < tabCount)
        switch (tabPlacement) {
          case 1:
          case 3:
            if ((PlasticTabbedPaneUI.this.rects[this.leadingTabIndex]).x < viewRect.x)
              this.leadingTabIndex++; 
            break;
          case 2:
          case 4:
            if ((PlasticTabbedPaneUI.this.rects[this.leadingTabIndex]).y < viewRect.y)
              this.leadingTabIndex++; 
            break;
        }  
      Insets contentInsets = PlasticTabbedPaneUI.this.getContentBorderInsets(tabPlacement);
      switch (tabPlacement) {
        case 2:
          PlasticTabbedPaneUI.this.tabPane.repaint(vpRect.x + vpRect.width, vpRect.y, contentInsets.left, vpRect.height);
          this.scrollBackwardButton.setEnabled((viewRect.y > 0 && this.leadingTabIndex > 0));
          this.scrollForwardButton.setEnabled((this.leadingTabIndex < tabCount - 1 && viewSize.height - viewRect.y > viewRect.height));
          return;
        case 4:
          PlasticTabbedPaneUI.this.tabPane.repaint(vpRect.x - contentInsets.right, vpRect.y, contentInsets.right, vpRect.height);
          this.scrollBackwardButton.setEnabled((viewRect.y > 0 && this.leadingTabIndex > 0));
          this.scrollForwardButton.setEnabled((this.leadingTabIndex < tabCount - 1 && viewSize.height - viewRect.y > viewRect.height));
          return;
        case 3:
          PlasticTabbedPaneUI.this.tabPane.repaint(vpRect.x, vpRect.y - contentInsets.bottom, vpRect.width, contentInsets.bottom);
          this.scrollBackwardButton.setEnabled((viewRect.x > 0 && this.leadingTabIndex > 0));
          this.scrollForwardButton.setEnabled((this.leadingTabIndex < tabCount - 1 && viewSize.width - viewRect.x > viewRect.width));
          return;
      } 
      PlasticTabbedPaneUI.this.tabPane.repaint(vpRect.x, vpRect.y + vpRect.height, vpRect.width, contentInsets.top);
      this.scrollBackwardButton.setEnabled((viewRect.x > 0 && this.leadingTabIndex > 0));
      this.scrollForwardButton.setEnabled((this.leadingTabIndex < tabCount - 1 && viewSize.width - viewRect.x > viewRect.width));
    }
    
    public void actionPerformed(ActionEvent e) {
      ActionMap map = PlasticTabbedPaneUI.this.tabPane.getActionMap();
      if (map != null) {
        String actionKey;
        if (e.getSource() == this.scrollForwardButton) {
          actionKey = "scrollTabsForwardAction";
        } else {
          actionKey = "scrollTabsBackwardAction";
        } 
        Action action = map.get(actionKey);
        if (action != null && action.isEnabled())
          action.actionPerformed(new ActionEvent(PlasticTabbedPaneUI.this.tabPane, 1001, null, e.getWhen(), e.getModifiers())); 
      } 
    }
  }
  
  private class ScrollableTabViewport extends JViewport implements UIResource {
    private final PlasticTabbedPaneUI this$0;
    
    public ScrollableTabViewport() {
      setName("TabbedPane.scrollableViewport");
      setScrollMode(0);
      setOpaque(PlasticTabbedPaneUI.this.tabPane.isOpaque());
      Color bgColor = UIManager.getColor("TabbedPane.tabAreaBackground");
      if (bgColor == null)
        bgColor = PlasticTabbedPaneUI.this.tabPane.getBackground(); 
      setBackground(bgColor);
    }
  }
  
  private class ScrollableTabPanel extends JPanel implements UIResource {
    private final PlasticTabbedPaneUI this$0;
    
    public ScrollableTabPanel() {
      super((LayoutManager)null);
      setOpaque(PlasticTabbedPaneUI.this.tabPane.isOpaque());
      Color bgColor = UIManager.getColor("TabbedPane.tabAreaBackground");
      if (bgColor == null)
        bgColor = PlasticTabbedPaneUI.this.tabPane.getBackground(); 
      setBackground(bgColor);
    }
    
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      PlasticTabbedPaneUI.this.paintTabArea(g, PlasticTabbedPaneUI.this.tabPane.getTabPlacement(), PlasticTabbedPaneUI.this.tabPane.getSelectedIndex());
    }
  }
  
  private static class ArrowButton extends JButton implements UIResource {
    private final int buttonWidth;
    
    private final int direction;
    
    private boolean mouseIsOver;
    
    ArrowButton(int direction, int buttonWidth) {
      this.direction = direction;
      this.buttonWidth = buttonWidth;
      setRequestFocusEnabled(false);
    }
    
    protected void processMouseEvent(MouseEvent e) {
      super.processMouseEvent(e);
      switch (e.getID()) {
        case 504:
          this.mouseIsOver = true;
          revalidate();
          repaint();
          break;
        case 505:
          this.mouseIsOver = false;
          revalidate();
          repaint();
          break;
      } 
    }
    
    protected void paintBorder(Graphics g) {
      if (this.mouseIsOver && isEnabled())
        super.paintBorder(g); 
    }
    
    protected void paintComponent(Graphics g) {
      if (this.mouseIsOver) {
        super.paintComponent(g);
      } else {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
      } 
      paintArrow(g);
    }
    
    private void paintArrow(Graphics g) {
      int arrowWidth, arrowHeight;
      Color oldColor = g.getColor();
      boolean isEnabled = isEnabled();
      g.setColor(isEnabled ? PlasticLookAndFeel.getControlInfo() : PlasticLookAndFeel.getControlDisabled());
      switch (this.direction) {
        case 1:
        case 5:
          arrowWidth = 9;
          arrowHeight = 5;
          break;
        default:
          arrowWidth = 5;
          arrowHeight = 9;
          break;
      } 
      int x = (getWidth() - arrowWidth) / 2;
      int y = (getHeight() - arrowHeight) / 2;
      g.translate(x, y);
      boolean paintShadow = (!this.mouseIsOver || !isEnabled);
      Color shadow = isEnabled ? PlasticLookAndFeel.getControlShadow() : UIManager.getColor("ScrollBar.highlight");
      switch (this.direction) {
        case 1:
          g.fillRect(0, 4, 9, 1);
          g.fillRect(1, 3, 7, 1);
          g.fillRect(2, 2, 5, 1);
          g.fillRect(3, 1, 3, 1);
          g.fillRect(4, 0, 1, 1);
          if (paintShadow) {
            g.setColor(shadow);
            g.fillRect(1, 5, 9, 1);
          } 
          break;
        case 5:
          g.fillRect(0, 0, 9, 1);
          g.fillRect(1, 1, 7, 1);
          g.fillRect(2, 2, 5, 1);
          g.fillRect(3, 3, 3, 1);
          g.fillRect(4, 4, 1, 1);
          if (paintShadow) {
            g.setColor(shadow);
            g.drawLine(5, 4, 8, 1);
            g.drawLine(5, 5, 9, 1);
          } 
          break;
        case 7:
          g.fillRect(0, 4, 1, 1);
          g.fillRect(1, 3, 1, 3);
          g.fillRect(2, 2, 1, 5);
          g.fillRect(3, 1, 1, 7);
          g.fillRect(4, 0, 1, 9);
          if (paintShadow) {
            g.setColor(shadow);
            g.fillRect(5, 1, 1, 9);
          } 
          break;
        case 3:
          g.fillRect(0, 0, 1, 9);
          g.fillRect(1, 1, 1, 7);
          g.fillRect(2, 2, 1, 5);
          g.fillRect(3, 3, 1, 3);
          g.fillRect(4, 4, 1, 1);
          if (paintShadow) {
            g.setColor(shadow);
            g.drawLine(1, 8, 4, 5);
            g.drawLine(1, 9, 5, 5);
          } 
          break;
      } 
      g.translate(-x, -y);
      g.setColor(oldColor);
    }
    
    public Dimension getPreferredSize() {
      return new Dimension(this.buttonWidth, this.buttonWidth);
    }
    
    public Dimension getMinimumSize() {
      return getPreferredSize();
    }
    
    public Dimension getMaximumSize() {
      return new Dimension(2147483647, 2147483647);
    }
  }
  
  private static abstract class AbstractRenderer {
    protected static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
    
    protected static final Insets NORTH_INSETS = new Insets(1, 0, 0, 0);
    
    protected static final Insets WEST_INSETS = new Insets(0, 1, 0, 0);
    
    protected static final Insets SOUTH_INSETS = new Insets(0, 0, 1, 0);
    
    protected static final Insets EAST_INSETS = new Insets(0, 0, 0, 1);
    
    protected final JTabbedPane tabPane;
    
    protected final int tabPlacement;
    
    protected Color shadowColor;
    
    protected Color darkShadow;
    
    protected Color selectColor;
    
    protected Color selectLight;
    
    protected Color selectHighlight;
    
    protected Color lightHighlight;
    
    protected Color focus;
    
    private AbstractRenderer(JTabbedPane tabPane) {
      initColors();
      this.tabPane = tabPane;
      this.tabPlacement = tabPane.getTabPlacement();
    }
    
    private static AbstractRenderer createRenderer(JTabbedPane tabPane) {
      switch (tabPane.getTabPlacement()) {
        case 1:
          return new PlasticTabbedPaneUI.TopRenderer(tabPane);
        case 3:
          return new PlasticTabbedPaneUI.BottomRenderer(tabPane);
        case 2:
          return new PlasticTabbedPaneUI.LeftRenderer(tabPane);
        case 4:
          return new PlasticTabbedPaneUI.RightRenderer(tabPane);
      } 
      return new PlasticTabbedPaneUI.TopRenderer(tabPane);
    }
    
    private static AbstractRenderer createEmbeddedRenderer(JTabbedPane tabPane) {
      switch (tabPane.getTabPlacement()) {
        case 1:
          return new PlasticTabbedPaneUI.TopEmbeddedRenderer(tabPane);
        case 3:
          return new PlasticTabbedPaneUI.BottomEmbeddedRenderer(tabPane);
        case 2:
          return new PlasticTabbedPaneUI.LeftEmbeddedRenderer(tabPane);
        case 4:
          return new PlasticTabbedPaneUI.RightEmbeddedRenderer(tabPane);
      } 
      return new PlasticTabbedPaneUI.TopEmbeddedRenderer(tabPane);
    }
    
    private void initColors() {
      this.shadowColor = UIManager.getColor("TabbedPane.shadow");
      this.darkShadow = UIManager.getColor("TabbedPane.darkShadow");
      this.selectColor = UIManager.getColor("TabbedPane.selected");
      this.focus = UIManager.getColor("TabbedPane.focus");
      this.selectHighlight = UIManager.getColor("TabbedPane.selectHighlight");
      this.lightHighlight = UIManager.getColor("TabbedPane.highlight");
      this.selectLight = new Color((2 * this.selectColor.getRed() + this.selectHighlight.getRed()) / 3, (2 * this.selectColor.getGreen() + this.selectHighlight.getGreen()) / 3, (2 * this.selectColor.getBlue() + this.selectHighlight.getBlue()) / 3);
    }
    
    protected boolean isFirstDisplayedTab(int tabIndex, int position, int paneBorder) {
      return (tabIndex == 0);
    }
    
    protected Insets getTabAreaInsets(Insets defaultInsets) {
      return defaultInsets;
    }
    
    protected Insets getContentBorderInsets(Insets defaultInsets) {
      return defaultInsets;
    }
    
    protected int getTabLabelShiftX(int tabIndex, boolean isSelected) {
      return 0;
    }
    
    protected int getTabLabelShiftY(int tabIndex, boolean isSelected) {
      return 0;
    }
    
    protected int getTabRunOverlay(int tabRunOverlay) {
      return tabRunOverlay;
    }
    
    protected boolean shouldPadTabRun(int run, boolean aPriori) {
      return aPriori;
    }
    
    protected int getTabRunIndent(int run) {
      return 0;
    }
    
    protected Insets getSelectedTabPadInsets() {
      return EMPTY_INSETS;
    }
    
    protected void paintContentBorderTopEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted) {
      if (isContentBorderPainted) {
        g.setColor(this.selectHighlight);
        g.fillRect(x, y, w - 1, 1);
      } 
    }
    
    protected void paintContentBorderBottomEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted) {
      if (isContentBorderPainted) {
        g.setColor(this.darkShadow);
        g.fillRect(x, y + h - 1, w - 1, 1);
      } 
    }
    
    protected void paintContentBorderLeftEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted) {
      if (isContentBorderPainted) {
        g.setColor(this.selectHighlight);
        g.fillRect(x, y, 1, h - 1);
      } 
    }
    
    protected void paintContentBorderRightEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted) {
      if (isContentBorderPainted) {
        g.setColor(this.darkShadow);
        g.fillRect(x + w - 1, y, 1, h);
      } 
    }
    
    protected int getTabsOverlay() {
      return 0;
    }
    
    protected abstract Insets getTabInsets(int param1Int, Insets param1Insets);
    
    protected abstract void paintFocusIndicator(Graphics param1Graphics, Rectangle[] param1ArrayOfRectangle, int param1Int, Rectangle param1Rectangle1, Rectangle param1Rectangle2, boolean param1Boolean);
    
    protected abstract void paintTabBackground(Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, boolean param1Boolean);
    
    protected abstract void paintTabBorder(Graphics param1Graphics, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, boolean param1Boolean);
  }
  
  private static final class BottomEmbeddedRenderer extends AbstractRenderer {
    private BottomEmbeddedRenderer(JTabbedPane tabPane) {
      super(tabPane);
    }
    
    protected Insets getTabAreaInsets(Insets insets) {
      return EMPTY_INSETS;
    }
    
    protected Insets getContentBorderInsets(Insets defaultInsets) {
      return SOUTH_INSETS;
    }
    
    protected Insets getSelectedTabPadInsets() {
      return EMPTY_INSETS;
    }
    
    protected Insets getTabInsets(int tabIndex, Insets tabInsets) {
      return new Insets(tabInsets.top, tabInsets.left, tabInsets.bottom, tabInsets.right);
    }
    
    protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {}
    
    protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
      g.setColor(this.selectColor);
      g.fillRect(x, y, w + 1, h);
    }
    
    protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
      int bottom = h;
      int right = w + 1;
      g.translate(x, y);
      if (isFirstDisplayedTab(tabIndex, x, (this.tabPane.getBounds()).x)) {
        if (isSelected) {
          g.setColor(this.shadowColor);
          g.fillRect(right, 0, 1, bottom - 1);
          g.fillRect(right - 1, bottom - 1, 1, 1);
          g.setColor(this.selectHighlight);
          g.fillRect(0, 0, 1, bottom);
          g.fillRect(right - 1, 0, 1, bottom - 1);
          g.fillRect(1, bottom - 1, right - 2, 1);
        } 
      } else if (isSelected) {
        g.setColor(this.shadowColor);
        g.fillRect(0, 0, 1, bottom - 1);
        g.fillRect(1, bottom - 1, 1, 1);
        g.fillRect(right, 0, 1, bottom - 1);
        g.fillRect(right - 1, bottom - 1, 1, 1);
        g.setColor(this.selectHighlight);
        g.fillRect(1, 0, 1, bottom - 1);
        g.fillRect(right - 1, 0, 1, bottom - 1);
        g.fillRect(2, bottom - 1, right - 3, 1);
      } else {
        g.setColor(this.shadowColor);
        g.fillRect(1, h / 2, 1, h - h / 2);
      } 
      g.translate(-x, -y);
    }
    
    protected void paintContentBorderBottomEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted) {
      g.setColor(this.shadowColor);
      g.fillRect(x, y + h - 1, w, 1);
    }
  }
  
  private static final class BottomRenderer extends AbstractRenderer {
    private BottomRenderer(JTabbedPane tabPane) {
      super(tabPane);
    }
    
    protected Insets getTabAreaInsets(Insets defaultInsets) {
      return new Insets(defaultInsets.top, defaultInsets.left + 5, defaultInsets.bottom, defaultInsets.right);
    }
    
    protected int getTabLabelShiftY(int tabIndex, boolean isSelected) {
      return isSelected ? 0 : -1;
    }
    
    protected int getTabRunOverlay(int tabRunOverlay) {
      return tabRunOverlay - 2;
    }
    
    protected int getTabRunIndent(int run) {
      return 6 * run;
    }
    
    protected Insets getSelectedTabPadInsets() {
      return SOUTH_INSETS;
    }
    
    protected Insets getTabInsets(int tabIndex, Insets tabInsets) {
      return new Insets(tabInsets.top, tabInsets.left - 2, tabInsets.bottom, tabInsets.right - 2);
    }
    
    protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
      if (!this.tabPane.hasFocus() || !isSelected)
        return; 
      Rectangle tabRect = rects[tabIndex];
      int top = tabRect.y;
      int left = tabRect.x + 6;
      int height = tabRect.height - 3;
      int width = tabRect.width - 12;
      g.setColor(this.focus);
      g.drawRect(left, top, width, height);
    }
    
    protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
      g.setColor(this.selectColor);
      g.fillRect(x, y, w, h);
    }
    
    protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
      int bottom = h - 1;
      int right = w + 4;
      g.translate(x - 3, y);
      g.setColor(this.selectHighlight);
      g.fillRect(0, 0, 1, 2);
      g.drawLine(0, 2, 4, bottom - 4);
      g.fillRect(5, bottom - 3, 1, 2);
      g.fillRect(6, bottom - 1, 1, 1);
      g.fillRect(7, bottom, 1, 1);
      g.setColor(this.darkShadow);
      g.fillRect(8, bottom, right - 13, 1);
      g.drawLine(right + 1, 0, right - 3, bottom - 4);
      g.fillRect(right - 4, bottom - 3, 1, 2);
      g.fillRect(right - 5, bottom - 1, 1, 1);
      g.translate(-x + 3, -y);
    }
    
    protected void paintContentBorderBottomEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted) {
      int bottom = y + h - 1;
      int right = x + w - 1;
      g.translate(x, bottom);
      if (drawBroken && selRect.x >= x && selRect.x <= x + w) {
        g.setColor(this.darkShadow);
        g.fillRect(0, 0, selRect.x - x - 2, 1);
        if (selRect.x + selRect.width < x + w - 2) {
          g.setColor(this.darkShadow);
          g.fillRect(selRect.x + selRect.width + 2 - x, 0, right - selRect.x - selRect.width - 2, 1);
        } 
      } else {
        g.setColor(this.darkShadow);
        g.fillRect(0, 0, w - 1, 1);
      } 
      g.translate(-x, -bottom);
    }
    
    protected int getTabsOverlay() {
      return 4;
    }
  }
  
  private static final class LeftEmbeddedRenderer extends AbstractRenderer {
    private LeftEmbeddedRenderer(JTabbedPane tabPane) {
      super(tabPane);
    }
    
    protected Insets getTabAreaInsets(Insets insets) {
      return EMPTY_INSETS;
    }
    
    protected Insets getContentBorderInsets(Insets defaultInsets) {
      return WEST_INSETS;
    }
    
    protected int getTabRunOverlay(int tabRunOverlay) {
      return 0;
    }
    
    protected boolean shouldPadTabRun(int run, boolean aPriori) {
      return false;
    }
    
    protected Insets getTabInsets(int tabIndex, Insets tabInsets) {
      return new Insets(tabInsets.top, tabInsets.left, tabInsets.bottom, tabInsets.right);
    }
    
    protected Insets getSelectedTabPadInsets() {
      return EMPTY_INSETS;
    }
    
    protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {}
    
    protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
      g.setColor(this.selectColor);
      g.fillRect(x, y, w, h);
    }
    
    protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
      int bottom = h;
      int right = w;
      g.translate(x, y);
      if (isFirstDisplayedTab(tabIndex, y, (this.tabPane.getBounds()).y)) {
        if (isSelected) {
          g.setColor(this.selectHighlight);
          g.fillRect(0, 0, right, 1);
          g.fillRect(0, 0, 1, bottom - 1);
          g.fillRect(1, bottom - 1, right - 1, 1);
          g.setColor(this.shadowColor);
          g.fillRect(0, bottom - 1, 1, 1);
          g.fillRect(1, bottom, right - 1, 1);
        } 
      } else if (isSelected) {
        g.setColor(this.selectHighlight);
        g.fillRect(1, 1, right - 1, 1);
        g.fillRect(0, 2, 1, bottom - 2);
        g.fillRect(1, bottom - 1, right - 1, 1);
        g.setColor(this.shadowColor);
        g.fillRect(1, 0, right - 1, 1);
        g.fillRect(0, 1, 1, 1);
        g.fillRect(0, bottom - 1, 1, 1);
        g.fillRect(1, bottom, right - 1, 1);
      } else {
        g.setColor(this.shadowColor);
        g.fillRect(0, 0, right / 3, 1);
      } 
      g.translate(-x, -y);
    }
    
    protected void paintContentBorderLeftEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted) {
      g.setColor(this.shadowColor);
      g.fillRect(x, y, 1, h);
    }
  }
  
  private static final class LeftRenderer extends AbstractRenderer {
    private LeftRenderer(JTabbedPane tabPane) {
      super(tabPane);
    }
    
    protected Insets getTabAreaInsets(Insets defaultInsets) {
      return new Insets(defaultInsets.top + 4, defaultInsets.left, defaultInsets.bottom, defaultInsets.right);
    }
    
    protected int getTabLabelShiftX(int tabIndex, boolean isSelected) {
      return 1;
    }
    
    protected int getTabRunOverlay(int tabRunOverlay) {
      return 1;
    }
    
    protected boolean shouldPadTabRun(int run, boolean aPriori) {
      return false;
    }
    
    protected Insets getTabInsets(int tabIndex, Insets tabInsets) {
      return new Insets(tabInsets.top, tabInsets.left - 5, tabInsets.bottom + 1, tabInsets.right - 5);
    }
    
    protected Insets getSelectedTabPadInsets() {
      return WEST_INSETS;
    }
    
    protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
      if (!this.tabPane.hasFocus() || !isSelected)
        return; 
      Rectangle tabRect = rects[tabIndex];
      int top = tabRect.y + 2;
      int left = tabRect.x + 3;
      int height = tabRect.height - 5;
      int width = tabRect.width - 6;
      g.setColor(this.focus);
      g.drawRect(left, top, width, height);
    }
    
    protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
      if (!isSelected) {
        g.setColor(this.selectLight);
        g.fillRect(x + 1, y + 1, w - 1, h - 2);
      } else {
        g.setColor(this.selectColor);
        g.fillRect(x + 1, y + 1, w - 3, h - 2);
      } 
    }
    
    protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
      int bottom = h - 1;
      int left = 0;
      g.translate(x, y);
      g.setColor(this.selectHighlight);
      g.fillRect(left + 2, 0, w - 2 - left, 1);
      g.fillRect(left + 1, 1, 1, 1);
      g.fillRect(left, 2, 1, bottom - 3);
      g.setColor(this.darkShadow);
      g.fillRect(left + 1, bottom - 1, 1, 1);
      g.fillRect(left + 2, bottom, w - 2 - left, 1);
      g.translate(-x, -y);
    }
    
    protected void paintContentBorderLeftEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted) {
      g.setColor(this.selectHighlight);
      if (drawBroken && selRect.y >= y && selRect.y <= y + h) {
        g.fillRect(x, y, 1, selRect.y + 1 - y);
        if (selRect.y + selRect.height < y + h - 2)
          g.fillRect(x, selRect.y + selRect.height - 1, 1, y + h - selRect.y - selRect.height); 
      } else {
        g.fillRect(x, y, 1, h - 1);
      } 
    }
  }
  
  private static final class RightEmbeddedRenderer extends AbstractRenderer {
    private RightEmbeddedRenderer(JTabbedPane tabPane) {
      super(tabPane);
    }
    
    protected Insets getTabAreaInsets(Insets insets) {
      return EMPTY_INSETS;
    }
    
    protected Insets getContentBorderInsets(Insets defaultInsets) {
      return EAST_INSETS;
    }
    
    protected int getTabRunIndent(int run) {
      return 4 * run;
    }
    
    protected int getTabRunOverlay(int tabRunOverlay) {
      return 0;
    }
    
    protected boolean shouldPadTabRun(int run, boolean aPriori) {
      return false;
    }
    
    protected Insets getTabInsets(int tabIndex, Insets tabInsets) {
      return new Insets(tabInsets.top, tabInsets.left, tabInsets.bottom, tabInsets.right);
    }
    
    protected Insets getSelectedTabPadInsets() {
      return EMPTY_INSETS;
    }
    
    protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {}
    
    protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
      g.setColor(this.selectColor);
      g.fillRect(x, y, w, h);
    }
    
    protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
      int bottom = h;
      int right = w - 1;
      g.translate(x + 1, y);
      if (isFirstDisplayedTab(tabIndex, y, (this.tabPane.getBounds()).y)) {
        if (isSelected) {
          g.setColor(this.shadowColor);
          g.fillRect(right - 1, bottom - 1, 1, 1);
          g.fillRect(0, bottom, right - 1, 1);
          g.setColor(this.selectHighlight);
          g.fillRect(0, 0, right - 1, 1);
          g.fillRect(right - 1, 0, 1, bottom - 1);
          g.fillRect(0, bottom - 1, right - 1, 1);
        } 
      } else if (isSelected) {
        g.setColor(this.shadowColor);
        g.fillRect(0, -1, right - 1, 1);
        g.fillRect(right - 1, 0, 1, 1);
        g.fillRect(right - 1, bottom - 1, 1, 1);
        g.fillRect(0, bottom, right - 1, 1);
        g.setColor(this.selectHighlight);
        g.fillRect(0, 0, right - 1, 1);
        g.fillRect(right - 1, 1, 1, bottom - 2);
        g.fillRect(0, bottom - 1, right - 1, 1);
      } else {
        g.setColor(this.shadowColor);
        g.fillRect(2 * right / 3, 0, right / 3, 1);
      } 
      g.translate(-x - 1, -y);
    }
    
    protected void paintContentBorderRightEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted) {
      g.setColor(this.shadowColor);
      g.fillRect(x + w - 1, y, 1, h);
    }
  }
  
  private static final class RightRenderer extends AbstractRenderer {
    private RightRenderer(JTabbedPane tabPane) {
      super(tabPane);
    }
    
    protected int getTabLabelShiftX(int tabIndex, boolean isSelected) {
      return 1;
    }
    
    protected int getTabRunOverlay(int tabRunOverlay) {
      return 1;
    }
    
    protected boolean shouldPadTabRun(int run, boolean aPriori) {
      return false;
    }
    
    protected Insets getTabInsets(int tabIndex, Insets tabInsets) {
      return new Insets(tabInsets.top, tabInsets.left - 5, tabInsets.bottom + 1, tabInsets.right - 5);
    }
    
    protected Insets getSelectedTabPadInsets() {
      return EAST_INSETS;
    }
    
    protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
      if (!this.tabPane.hasFocus() || !isSelected)
        return; 
      Rectangle tabRect = rects[tabIndex];
      int top = tabRect.y + 2;
      int left = tabRect.x + 3;
      int height = tabRect.height - 5;
      int width = tabRect.width - 6;
      g.setColor(this.focus);
      g.drawRect(left, top, width, height);
    }
    
    protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
      if (!isSelected) {
        g.setColor(this.selectLight);
        g.fillRect(x, y, w, h);
      } else {
        g.setColor(this.selectColor);
        g.fillRect(x + 2, y, w - 2, h);
      } 
    }
    
    protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
      int bottom = h - 1;
      int right = w;
      g.translate(x, y);
      g.setColor(this.selectHighlight);
      g.fillRect(0, 0, right - 1, 1);
      g.setColor(this.darkShadow);
      g.fillRect(right - 1, 1, 1, 1);
      g.fillRect(right, 2, 1, bottom - 3);
      g.fillRect(right - 1, bottom - 1, 1, 1);
      g.fillRect(0, bottom, right - 1, 1);
      g.translate(-x, -y);
    }
    
    protected void paintContentBorderRightEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted) {
      g.setColor(this.darkShadow);
      if (drawBroken && selRect.y >= y && selRect.y <= y + h) {
        g.fillRect(x + w - 1, y, 1, selRect.y - y);
        if (selRect.y + selRect.height < y + h - 2)
          g.fillRect(x + w - 1, selRect.y + selRect.height, 1, y + h - selRect.y - selRect.height); 
      } else {
        g.fillRect(x + w - 1, y, 1, h - 1);
      } 
    }
  }
  
  private static final class TopEmbeddedRenderer extends AbstractRenderer {
    private TopEmbeddedRenderer(JTabbedPane tabPane) {
      super(tabPane);
    }
    
    protected Insets getTabAreaInsets(Insets insets) {
      return EMPTY_INSETS;
    }
    
    protected Insets getContentBorderInsets(Insets defaultInsets) {
      return NORTH_INSETS;
    }
    
    protected Insets getTabInsets(int tabIndex, Insets tabInsets) {
      return new Insets(tabInsets.top, tabInsets.left + 1, tabInsets.bottom, tabInsets.right);
    }
    
    protected Insets getSelectedTabPadInsets() {
      return EMPTY_INSETS;
    }
    
    protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {}
    
    protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
      g.setColor(this.selectColor);
      g.fillRect(x, y, w, h);
    }
    
    protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
      g.translate(x, y);
      int right = w;
      int bottom = h;
      if (isFirstDisplayedTab(tabIndex, x, (this.tabPane.getBounds()).x)) {
        if (isSelected) {
          g.setColor(this.selectHighlight);
          g.fillRect(0, 0, 1, bottom);
          g.fillRect(0, 0, right - 1, 1);
          g.fillRect(right - 1, 0, 1, bottom);
          g.setColor(this.shadowColor);
          g.fillRect(right - 1, 0, 1, 1);
          g.fillRect(right, 1, 1, bottom);
        } 
      } else if (isSelected) {
        g.setColor(this.selectHighlight);
        g.fillRect(1, 1, 1, bottom - 1);
        g.fillRect(2, 0, right - 3, 1);
        g.fillRect(right - 1, 1, 1, bottom - 1);
        g.setColor(this.shadowColor);
        g.fillRect(0, 1, 1, bottom - 1);
        g.fillRect(1, 0, 1, 1);
        g.fillRect(right - 1, 0, 1, 1);
        g.fillRect(right, 1, 1, bottom);
      } else {
        g.setColor(this.shadowColor);
        g.fillRect(0, 0, 1, bottom + 2 - bottom / 2);
      } 
      g.translate(-x, -y);
    }
    
    protected void paintContentBorderTopEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted) {
      g.setColor(this.shadowColor);
      g.fillRect(x, y, w, 1);
    }
  }
  
  private static final class TopRenderer extends AbstractRenderer {
    private TopRenderer(JTabbedPane tabPane) {
      super(tabPane);
    }
    
    protected Insets getTabAreaInsets(Insets defaultInsets) {
      return new Insets(defaultInsets.top, defaultInsets.left + 4, defaultInsets.bottom, defaultInsets.right);
    }
    
    protected int getTabLabelShiftY(int tabIndex, boolean isSelected) {
      return isSelected ? -1 : 0;
    }
    
    protected int getTabRunOverlay(int tabRunOverlay) {
      return tabRunOverlay - 2;
    }
    
    protected int getTabRunIndent(int run) {
      return 6 * run;
    }
    
    protected Insets getSelectedTabPadInsets() {
      return NORTH_INSETS;
    }
    
    protected Insets getTabInsets(int tabIndex, Insets tabInsets) {
      return new Insets(tabInsets.top - 1, tabInsets.left - 4, tabInsets.bottom, tabInsets.right - 4);
    }
    
    protected void paintFocusIndicator(Graphics g, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
      if (!this.tabPane.hasFocus() || !isSelected)
        return; 
      Rectangle tabRect = rects[tabIndex];
      int top = tabRect.y + 1;
      int left = tabRect.x + 4;
      int height = tabRect.height - 3;
      int width = tabRect.width - 9;
      g.setColor(this.focus);
      g.drawRect(left, top, width, height);
    }
    
    protected void paintTabBackground(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
      int sel = isSelected ? 0 : 1;
      g.setColor(this.selectColor);
      g.fillRect(x, y + sel, w, h / 2);
      g.fillRect(x - 1, y + sel + h / 2, w + 2, h - h / 2);
    }
    
    protected void paintTabBorder(Graphics g, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
      g.translate(x - 4, y);
      int top = 0;
      int right = w + 6;
      g.setColor(this.selectHighlight);
      g.drawLine(1, h - 1, 4, top + 4);
      g.fillRect(5, top + 2, 1, 2);
      g.fillRect(6, top + 1, 1, 1);
      g.fillRect(7, top, right - 12, 1);
      g.setColor(this.darkShadow);
      g.drawLine(right, h - 1, right - 3, top + 4);
      g.fillRect(right - 4, top + 2, 1, 2);
      g.fillRect(right - 5, top + 1, 1, 1);
      g.translate(-x + 4, -y);
    }
    
    protected void paintContentBorderTopEdge(Graphics g, int x, int y, int w, int h, boolean drawBroken, Rectangle selRect, boolean isContentBorderPainted) {
      int right = x + w - 1;
      int top = y;
      g.setColor(this.selectHighlight);
      if (drawBroken && selRect.x >= x && selRect.x <= x + w) {
        g.fillRect(x, top, selRect.x - 2 - x, 1);
        if (selRect.x + selRect.width < x + w - 2) {
          g.fillRect(selRect.x + selRect.width + 2, top, right - 2 - selRect.x - selRect.width, 1);
        } else {
          g.fillRect(x + w - 2, top, 1, 1);
        } 
      } else {
        g.fillRect(x, top, w - 1, 1);
      } 
    }
    
    protected int getTabsOverlay() {
      return 6;
    }
  }
}
