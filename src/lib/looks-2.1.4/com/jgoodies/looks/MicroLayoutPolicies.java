package com.jgoodies.looks;

import javax.swing.UIDefaults;

public final class MicroLayoutPolicies {
  public static MicroLayoutPolicy getDefaultPlasticPolicy() {
    return new DefaultPlasticPolicy();
  }
  
  public static MicroLayoutPolicy getDefaultWindowsPolicy() {
    return new DefaultWindowsPolicy();
  }
  
  private static final class DefaultPlasticPolicy implements MicroLayoutPolicy {
    private DefaultPlasticPolicy() {}
    
    public MicroLayout getMicroLayout(String lafName, UIDefaults table) {
      boolean isClassic = !LookUtils.IS_LAF_WINDOWS_XP_ENABLED;
      boolean isVista = LookUtils.IS_OS_WINDOWS_VISTA;
      boolean isLowRes = LookUtils.IS_LOW_RESOLUTION;
      boolean isPlasticXP = lafName.equals("JGoodies Plastic XP");
      if (isPlasticXP) {
        if (isVista)
          return isClassic ? MicroLayouts.createPlasticXPVistaClassicMicroLayout() : MicroLayouts.createPlasticXPVistaMicroLayout(); 
        return isLowRes ? MicroLayouts.createPlasticXPLowResMicroLayout() : MicroLayouts.createPlasticXPHiResMicroLayout();
      } 
      if (isVista)
        return isClassic ? MicroLayouts.createPlasticVistaClassicMicroLayout() : MicroLayouts.createPlasticVistaMicroLayout(); 
      return isLowRes ? MicroLayouts.createPlasticLowResMicroLayout() : MicroLayouts.createPlasticHiResMicroLayout();
    }
  }
  
  private static final class DefaultWindowsPolicy implements MicroLayoutPolicy {
    private DefaultWindowsPolicy() {}
    
    public MicroLayout getMicroLayout(String lafName, UIDefaults table) {
      boolean isClassic = !LookUtils.IS_LAF_WINDOWS_XP_ENABLED;
      boolean isVista = LookUtils.IS_OS_WINDOWS_VISTA;
      boolean isLowRes = LookUtils.IS_LOW_RESOLUTION;
      if (isClassic)
        return isLowRes ? MicroLayouts.createWindowsClassicLowResMicroLayout() : MicroLayouts.createWindowsClassicHiResMicroLayout(); 
      if (isVista)
        return isLowRes ? MicroLayouts.createWindowsVistaLowResMicroLayout() : MicroLayouts.createWindowsVistaHiResMicroLayout(); 
      return isLowRes ? MicroLayouts.createWindowsXPLowResMicroLayout() : MicroLayouts.createWindowsXPHiResMicroLayout();
    }
  }
}
