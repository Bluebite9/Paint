package buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import ids.ShapeIDs;

public class ShapeToggleButton implements ActionListener{
  
  private JToggleButton toggleButton;
  private boolean selected;
  private ShapeIDs shapeId;
  
  private static ArrayList<ShapeToggleButton> allShapeToggleButtons = new ArrayList<ShapeToggleButton>();
  
  public ShapeToggleButton(ImageIcon icon, ShapeIDs id){
    toggleButton = new JToggleButton(icon);
    toggleButton.addActionListener(this);
    this.shapeId = id;
    
    allShapeToggleButtons.add(this);
  }

  public void actionPerformed(ActionEvent e) {
    AbstractButton abstractButton = (AbstractButton) e.getSource();
    deselectAllButtons();
    toggleButton.setSelected(true);
    selected = abstractButton.getModel().isSelected();
  }
  
  public static boolean isButtonSelected(ShapeIDs id){
    for (int i = 0; i < allShapeToggleButtons.size(); i++){
      if (allShapeToggleButtons.get(i).getShapeID() == id){
        return allShapeToggleButtons.get(i).selected;
      }
    }
    return false;
  }
  
  public JToggleButton getShapeToggleButton(){
    return this.toggleButton;
  }
  
  public ShapeIDs getShapeID(){
    return this.shapeId;
  }
  
  public static ArrayList<ShapeToggleButton> getAllShapeToggleButtons(){
    return allShapeToggleButtons;
  }
  
  private static void deselectAllButtons(){
    for(ShapeToggleButton toggleButton : allShapeToggleButtons){
      toggleButton.toggleButton.setSelected(false);
      toggleButton.selected = false;
    }
  }
  
}