package buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import ids.FillIDs;

public class FillToggleButton implements ActionListener {

  private JToggleButton toggleButton;
  private boolean selected;
  private FillIDs fillId;

  private static ArrayList<FillToggleButton> allFillToggleButtons = new ArrayList<FillToggleButton>();

  public FillToggleButton(ImageIcon icon, FillIDs id) {
    toggleButton = new JToggleButton(icon);
    toggleButton.addActionListener(this);
    this.fillId = id;

    allFillToggleButtons.add(this);
  }

  public JToggleButton getToggleButton() {
    return toggleButton;
  }

  public void setToggleButton(JToggleButton toggleButton) {
    this.toggleButton = toggleButton;
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public FillIDs getFillId() {
    return fillId;
  }

  public void setFillId(FillIDs fillId) {
    this.fillId = fillId;
  }

  public void actionPerformed(ActionEvent e) {
    AbstractButton abstractButton = (AbstractButton) e.getSource();
    deselectAllButtons();
    toggleButton.setSelected(true);
    selected = abstractButton.getModel().isSelected();
  }

  public static boolean isButtonSelected(FillIDs id) {
    for (int i = 0; i < allFillToggleButtons.size(); i++) {
      if (allFillToggleButtons.get(i).getFillId() == id) {
        return allFillToggleButtons.get(i).selected;
      }
    }
    return false;
  }

  public static ArrayList<FillToggleButton> getAllFillToggleButtons() {
    return allFillToggleButtons;
  }

  private static void deselectAllButtons() {
    for (FillToggleButton toggleButton : allFillToggleButtons) {
      toggleButton.toggleButton.setSelected(false);
      toggleButton.selected = false;
    }
  }

}
