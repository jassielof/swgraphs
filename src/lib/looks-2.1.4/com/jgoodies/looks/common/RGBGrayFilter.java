package com.jgoodies.looks.common;

import com.jgoodies.looks.Options;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import javax.swing.GrayFilter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public final class RGBGrayFilter extends RGBImageFilter {
  public static Icon getDisabledIcon(JComponent component, Icon icon) {
    Image img;
    if (icon == null || component == null || icon.getIconWidth() == 0 || icon.getIconHeight() == 0)
      return null; 
    if (icon instanceof ImageIcon) {
      img = ((ImageIcon)icon).getImage();
    } else {
      img = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), 2);
      icon.paintIcon(component, img.getGraphics(), 0, 0);
    } 
    if (!Options.isHiResGrayFilterEnabled() || Boolean.FALSE.equals(component.getClientProperty("generateHiResDisabledIcon")))
      return new ImageIcon(GrayFilter.createDisabledImage(img)); 
    ImageProducer producer = new FilteredImageSource(img.getSource(), new RGBGrayFilter());
    return new ImageIcon(component.createImage(producer));
  }
  
  public int filterRGB(int x, int y, int rgb) {
    float avg = ((rgb >> 16 & 0xFF) / 255.0F + (rgb >> 8 & 0xFF) / 255.0F + (rgb & 0xFF) / 255.0F) / 3.0F;
    float alpha = (rgb >> 24 & 0xFF) / 255.0F;
    avg = Math.min(1.0F, 0.35F + 0.65F * avg);
    int rgbval = (int)(alpha * 255.0F) << 24 | (int)(avg * 255.0F) << 16 | (int)(avg * 255.0F) << 8 | (int)(avg * 255.0F);
    return rgbval;
  }
}
