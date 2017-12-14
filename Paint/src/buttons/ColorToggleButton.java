package buttons;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import ids.ColorIDs;

public class ColorToggleButton implements ActionListener {

  private JToggleButton toggleButton;
  private boolean selected;
  private ColorIDs colorId;
  private Color color;

  private static ArrayList<ColorToggleButton> allColorToggleButtons = new ArrayList<ColorToggleButton>();

  public ColorToggleButton(ImageIcon icon, ColorIDs id, Color color) {
    toggleButton = new JToggleButton(icon);
    toggleButton.addActionListener(this);
    this.colorId = id;
    this.color = color;

    allColorToggleButtons.add(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    AbstractButton abstractButton = (AbstractButton) e.getSource();
    selected = abstractButton.getModel().isSelected();
    for (ColorToggleButton toggleButton : allColorToggleButtons) {
      if (toggleButton != this) {
        toggleButton.toggleButton.setSelected(false);
        toggleButton.selected = false;
      }
    }
  }

  public static Color getButtonColor() {
    for (ColorToggleButton colorButton : ColorToggleButton.getAllColorToggleButtons()) {
      if (colorButton.selected) {
        return colorButton.color;
      }
    }

    return Color.black;
  }

  public JToggleButton getColorToggleButton() {
    return this.toggleButton;
  }

  public ColorIDs getColorID() {
    return this.colorId;
  }

  public Color getColor() {
    return this.color;
  }

  public static ArrayList<ColorToggleButton> getAllColorToggleButtons() {
    return allColorToggleButtons;
  }

}
