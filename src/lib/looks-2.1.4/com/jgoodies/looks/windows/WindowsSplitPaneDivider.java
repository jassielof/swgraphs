package com.jgoodies.looks.windows;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

final class WindowsSplitPaneDivider extends BasicSplitPaneDivider {
  private static final int EXT_ONE_TOUCH_SIZE = 5;
  
  private static final int EXT_ONE_TOUCH_OFFSET = 2;
  
  private static final int EXT_BLOCKSIZE = 6;
  
  public final class ExtWindowsDividerLayout implements LayoutManager {
    private final WindowsSplitPaneDivider this$0;
    
    public void layoutContainer(Container c) {
      JButton theLeftButton = WindowsSplitPaneDivider.this.getLeftButtonFromSuper();
      JButton theRightButton = WindowsSplitPaneDivider.this.getRightButtonFromSuper();
      JSplitPane theSplitPane = WindowsSplitPaneDivider.this.getSplitPaneFromSuper();
      int theOrientation = WindowsSplitPaneDivider.this.getOrientationFromSuper();
      int oneTouchSize = WindowsSplitPaneDivider.this.getOneTouchSize();
      int oneTouchOffset = WindowsSplitPaneDivider.this.getOneTouchOffset();
      int blockSize = 5;
      if (theLeftButton != null && theRightButton != null && c == WindowsSplitPaneDivider.this)
        if (theSplitPane.isOneTouchExpandable()) {
          if (theOrientation == 0) {
            theLeftButton.setBounds(oneTouchOffset, 0, blockSize * 2, blockSize);
            theRightButton.setBounds(oneTouchOffset + oneTouchSize * 2, 0, blockSize * 2, blockSize);
          } else {
            theLeftButton.setBounds(0, oneTouchOffset, blockSize, blockSize * 2);
            theRightButton.setBounds(0, oneTouchOffset + oneTouchSize * 2, blockSize, blockSize * 2);
          } 
        } else {
          theLeftButton.setBounds(-5, -5, 1, 1);
          theRightButton.setBounds(-5, -5, 1, 1);
        }  
    }
    
    public Dimension minimumLayoutSize(Container c) {
      return new Dimension(0, 0);
    }
    
    public Dimension preferredLayoutSize(Container c) {
      return new Dimension(0, 0);
    }
    
    public void removeLayoutComponent(Component c) {}
    
    public void addLayoutComponent(String string, Component c) {}
  }
  
  public WindowsSplitPaneDivider(BasicSplitPaneUI ui) {
    super(ui);
    setLayout(new ExtWindowsDividerLayout());
  }
  
  protected JButton createLeftOneTouchButton() {
    JButton b = new JButton() {
        int[][] buffer = new int[][] { { 0, 0, 0, 2, 2, 0, 0, 0, 0 }, { 0, 0, 2, 1, 1, 1, 0, 0, 0 }, { 0, 2, 1, 1, 1, 1, 1, 0, 0 }, { 2, 1, 1, 1, 1, 1, 1, 1, 0 }, { 0, 3, 3, 3, 3, 3, 3, 3, 3 } };
        
        private final WindowsSplitPaneDivider this$0;
        
        public void setBorder(Border border) {}
        
        public void paint(Graphics g) {
          JSplitPane theSplitPane = WindowsSplitPaneDivider.this.getSplitPaneFromSuper();
          if (theSplitPane != null) {
            int theOrientation = WindowsSplitPaneDivider.this.getOrientationFromSuper();
            int blockSize = this.buffer.length + 1;
            Color[] colors = { getBackground(), UIManager.getColor("controlDkShadow"), Color.black, UIManager.getColor("controlLtHighlight") };
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            if (getModel().isPressed())
              colors[1] = colors[2]; 
            if (theOrientation == 0) {
              for (int i = 1; i <= (this.buffer[0]).length; i++) {
                for (int j = 1; j < blockSize; j++) {
                  if (this.buffer[j - 1][i - 1] != 0) {
                    g.setColor(colors[this.buffer[j - 1][i - 1]]);
                    g.drawLine(i - 1, j, i - 1, j);
                  } 
                } 
              } 
            } else {
              for (int i = 1; i <= (this.buffer[0]).length; i++) {
                for (int j = 1; j < blockSize; j++) {
                  if (this.buffer[j - 1][i - 1] != 0) {
                    g.setColor(colors[this.buffer[j - 1][i - 1]]);
                    g.drawLine(j - 1, i, j - 1, i);
                  } 
                } 
              } 
            } 
          } 
        }
      };
    b.setFocusPainted(false);
    b.setBorderPainted(false);
    b.setFocusable(false);
    b.setOpaque(false);
    return b;
  }
  
  protected JButton createRightOneTouchButton() {
    JButton b = new JButton() {
        int[][] buffer = new int[][] { { 2, 2, 2, 2, 2, 2, 2, 2 }, { 0, 1, 1, 1, 1, 1, 1, 3 }, { 0, 0, 1, 1, 1, 1, 3, 0 }, { 0, 0, 0, 1, 1, 3, 0, 0 }, { 0, 0, 0, 0, 3, 0, 0, 0 } };
        
        private final WindowsSplitPaneDivider this$0;
        
        public void setBorder(Border border) {}
        
        public void paint(Graphics g) {
          JSplitPane theSplitPane = WindowsSplitPaneDivider.this.getSplitPaneFromSuper();
          if (theSplitPane != null) {
            int theOrientation = WindowsSplitPaneDivider.this.getOrientationFromSuper();
            int blockSize = this.buffer.length + 1;
            Color[] colors = { getBackground(), UIManager.getColor("controlDkShadow"), Color.black, UIManager.getColor("controlLtHighlight") };
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            if (getModel().isPressed())
              colors[1] = colors[2]; 
            if (theOrientation == 0) {
              for (int i = 1; i <= (this.buffer[0]).length; i++) {
                for (int j = 1; j < blockSize; j++) {
                  if (this.buffer[j - 1][i - 1] != 0) {
                    g.setColor(colors[this.buffer[j - 1][i - 1]]);
                    g.drawLine(i, j, i, j);
                  } 
                } 
              } 
            } else {
              for (int i = 1; i <= (this.buffer[0]).length; i++) {
                for (int j = 1; j < blockSize; j++) {
                  if (this.buffer[j - 1][i - 1] != 0) {
                    g.setColor(colors[this.buffer[j - 1][i - 1]]);
                    g.drawLine(j - 1, i, j - 1, i);
                  } 
                } 
              } 
            } 
          } 
        }
      };
    b.setFocusPainted(false);
    b.setBorderPainted(false);
    b.setFocusable(false);
    b.setOpaque(false);
    return b;
  }
  
  int getBlockSize() {
    return 6;
  }
  
  int getOneTouchOffset() {
    return 2;
  }
  
  int getOneTouchSize() {
    return 5;
  }
  
  int getOrientationFromSuper() {
    return this.orientation;
  }
  
  JButton getLeftButtonFromSuper() {
    return this.leftButton;
  }
  
  JButton getRightButtonFromSuper() {
    return this.rightButton;
  }
  
  JSplitPane getSplitPaneFromSuper() {
    return this.splitPane;
  }
  
  public void paint(Graphics g) {
    if (this.splitPane.isOpaque()) {
      Color bgColor = this.splitPane.hasFocus() ? UIManager.getColor("SplitPane.shadow") : getBackground();
      if (bgColor != null) {
        g.setColor(bgColor);
        g.fillRect(0, 0, getWidth(), getHeight());
      } 
    } 
    super.paint(g);
  }
}
