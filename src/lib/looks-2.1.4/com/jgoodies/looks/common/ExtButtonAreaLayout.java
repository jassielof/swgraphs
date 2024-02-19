package com.jgoodies.looks.common;

import com.jgoodies.looks.LookUtils;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.plaf.basic.BasicOptionPaneUI;

public final class ExtButtonAreaLayout extends BasicOptionPaneUI.ButtonAreaLayout {
  public ExtButtonAreaLayout(boolean syncAllWidths, int padding) {
    super(syncAllWidths, padding);
  }
  
  public void layoutContainer(Container container) {
    Component[] children = container.getComponents();
    if (children != null && children.length > 0) {
      int numChildren = children.length;
      Dimension[] sizes = new Dimension[numChildren];
      int yLocation = (container.getInsets()).top;
      if (this.syncAllWidths) {
        int xLocation, xOffset, maxWidth = getMinimumButtonWidth();
        int counter;
        for (counter = 0; counter < numChildren; counter++) {
          sizes[counter] = children[counter].getPreferredSize();
          maxWidth = Math.max(maxWidth, (sizes[counter]).width);
        } 
        if (getCentersChildren()) {
          xLocation = ((container.getSize()).width - maxWidth * numChildren + (numChildren - 1) * this.padding) / 2;
          xOffset = this.padding + maxWidth;
        } else if (numChildren > 1) {
          xLocation = 0;
          xOffset = ((container.getSize()).width - maxWidth * numChildren) / (numChildren - 1) + maxWidth;
        } else {
          xLocation = ((container.getSize()).width - maxWidth) / 2;
          xOffset = 0;
        } 
        boolean ltr = container.getComponentOrientation().isLeftToRight();
        for (counter = 0; counter < numChildren; counter++) {
          int index = ltr ? counter : (numChildren - counter - 1);
          children[index].setBounds(xLocation, yLocation, maxWidth, (sizes[index]).height);
          xLocation += xOffset;
        } 
      } else {
        int xOffset, xLocation, totalWidth = 0;
        int counter;
        for (counter = 0; counter < numChildren; counter++) {
          sizes[counter] = children[counter].getPreferredSize();
          totalWidth += (sizes[counter]).width;
        } 
        totalWidth += (numChildren - 1) * this.padding;
        boolean cc = getCentersChildren();
        if (cc) {
          xLocation = ((container.getSize()).width - totalWidth) / 2;
          xOffset = this.padding;
        } else if (numChildren > 1) {
          xOffset = ((container.getSize()).width - totalWidth) / (numChildren - 1);
          xLocation = 0;
        } else {
          xLocation = ((container.getSize()).width - totalWidth) / 2;
          xOffset = 0;
        } 
        boolean ltr = container.getComponentOrientation().isLeftToRight();
        for (counter = 0; counter < numChildren; counter++) {
          int index = ltr ? counter : (numChildren - counter - 1);
          children[index].setBounds(xLocation, yLocation, (sizes[index]).width, (sizes[index]).height);
          xLocation += xOffset + (sizes[index]).width;
        } 
      } 
    } 
  }
  
  public Dimension minimumLayoutSize(Container c) {
    if (c != null) {
      Component[] children = c.getComponents();
      if (children != null && children.length > 0) {
        int numChildren = children.length;
        int height = 0;
        Insets cInsets = c.getInsets();
        int extraHeight = cInsets.top + cInsets.bottom;
        if (this.syncAllWidths) {
          int maxWidth = getMinimumButtonWidth();
          for (int i = 0; i < numChildren; i++) {
            Dimension aSize = children[i].getPreferredSize();
            height = Math.max(height, aSize.height);
            maxWidth = Math.max(maxWidth, aSize.width);
          } 
          return new Dimension(maxWidth * numChildren + (numChildren - 1) * this.padding, extraHeight + height);
        } 
        int totalWidth = 0;
        for (int counter = 0; counter < numChildren; counter++) {
          Dimension aSize = children[counter].getPreferredSize();
          height = Math.max(height, aSize.height);
          totalWidth += aSize.width;
        } 
        totalWidth += (numChildren - 1) * this.padding;
        return new Dimension(totalWidth, extraHeight + height);
      } 
    } 
    return new Dimension(0, 0);
  }
  
  private int getMinimumButtonWidth() {
    return LookUtils.IS_LOW_RESOLUTION ? 75 : 100;
  }
}
