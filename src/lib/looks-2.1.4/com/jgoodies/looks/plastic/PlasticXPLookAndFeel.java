package com.jgoodies.looks.plastic;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.MicroLayout;
import java.awt.Insets;
import javax.swing.UIDefaults;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;

public class PlasticXPLookAndFeel extends Plastic3DLookAndFeel {
  public String getID() {
    return "JGoodies Plastic XP";
  }
  
  public String getName() {
    return "JGoodies Plastic XP";
  }
  
  public String getDescription() {
    return "The JGoodies Plastic XP Look and Feel - © 2001-2007 JGoodies Karsten Lentzsch";
  }
  
  protected void initClassDefaults(UIDefaults table) {
    super.initClassDefaults(table);
    String uiClassnamePrefix = "com.jgoodies.looks.plastic.PlasticXP";
    Object[] uiDefaults = { "CheckBoxUI", "com.jgoodies.looks.plastic.PlasticXPCheckBoxUI", "RadioButtonUI", "com.jgoodies.looks.plastic.PlasticXPRadioButtonUI", "SpinnerUI", "com.jgoodies.looks.plastic.PlasticXPSpinnerUI", "ToolBarUI", "com.jgoodies.looks.plastic.PlasticXPToolBarUI" };
    table.putDefaults(uiDefaults);
  }
  
  protected void initComponentDefaults(UIDefaults table) {
    super.initComponentDefaults(table);
    MicroLayout microLayout = getMicroLayoutPolicy().getMicroLayout(getName(), table);
    Insets buttonBorderInsets = microLayout.getButtonBorderInsets();
    Object buttonBorder = PlasticXPBorders.getButtonBorder(buttonBorderInsets);
    Object toggleButtonBorder = PlasticXPBorders.getToggleButtonBorder(buttonBorderInsets);
    Object checkBoxIcon = PlasticXPIconFactory.getCheckBoxIcon();
    Object comboBoxButtonBorder = PlasticXPBorders.getComboBoxArrowButtonBorder();
    Border comboBoxEditorBorder = PlasticXPBorders.getComboBoxEditorBorder();
    Object radioButtonIcon = PlasticXPIconFactory.getRadioButtonIcon();
    Object scrollPaneBorder = PlasticXPBorders.getScrollPaneBorder();
    Object textFieldBorder = PlasticXPBorders.getTextFieldBorder();
    Object spinnerBorder = PlasticXPBorders.getSpinnerBorder();
    String radioCheckIconName = LookUtils.IS_LOW_RESOLUTION ? "icons/RadioLight5x5.png" : "icons/RadioLight7x7.png";
    Insets comboEditorInsets = microLayout.getComboBoxEditorInsets();
    Insets comboEditorBorderInsets = comboBoxEditorBorder.getBorderInsets(null);
    int comboBorderSize = comboEditorBorderInsets.left;
    int comboPopupBorderSize = microLayout.getComboPopupBorderSize();
    int comboRendererGap = comboEditorInsets.left + comboBorderSize - comboPopupBorderSize;
    Object comboRendererBorder = new EmptyBorder(1, comboRendererGap, 1, comboRendererGap);
    Object comboTableEditorInsets = new Insets(0, 0, 0, 0);
    Object[] defaults = { 
        "Button.border", buttonBorder, "Button.borderPaintsFocus", Boolean.TRUE, "CheckBox.icon", checkBoxIcon, "CheckBox.check", getToggleButtonCheckColor(), "ComboBox.arrowButtonBorder", comboBoxButtonBorder, 
        "ComboBox.editorBorder", comboBoxEditorBorder, "ComboBox.borderPaintsFocus", Boolean.TRUE, "ComboBox.editorBorderInsets", comboEditorBorderInsets, "ComboBox.tableEditorInsets", comboTableEditorInsets, "ComboBox.rendererBorder", comboRendererBorder, 
        "FormattedTextField.border", textFieldBorder, "PasswordField.border", textFieldBorder, "Spinner.border", spinnerBorder, "ScrollPane.border", scrollPaneBorder, "Table.scrollPaneBorder", scrollPaneBorder, 
        "RadioButton.icon", radioButtonIcon, "RadioButton.check", getToggleButtonCheckColor(), "RadioButton.interiorBackground", getControlHighlight(), "RadioButton.checkIcon", makeIcon(getClass(), radioCheckIconName), "TextField.border", textFieldBorder, 
        "ToggleButton.border", toggleButtonBorder, "ToggleButton.borderPaintsFocus", Boolean.TRUE, "Tree.expandedIcon", makeIcon(getClass(), "icons/TreeExpanded.png"), "Tree.collapsedIcon", makeIcon(getClass(), "icons/TreeCollapsed.png") };
    table.putDefaults(defaults);
  }
  
  protected static void installDefaultThemes() {}
  
  private ColorUIResource getToggleButtonCheckColor() {
    return getPlasticTheme().getToggleButtonCheckColor();
  }
}
