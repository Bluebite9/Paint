package toolBars;

import javax.swing.ImageIcon;
import javax.swing.JToolBar;

import buttons.FillToggleButton;
import ids.FillIDs;

public class FillToolBar {

  private JToolBar toolBar;

  public JToolBar getToolBar() {
    return toolBar;
  }

  public void setToolBar(JToolBar toolBar) {
    this.toolBar = toolBar;
  }

  public FillToolBar() {
    toolBar = new JToolBar();
    toolBar.setName("Fill");
    toolBar.setFloatable(false);

    ImageIcon fillIcon = new ImageIcon(getClass().getClassLoader().getResource("Resources/fill.gif"));
    ImageIcon noFillIcon = new ImageIcon(getClass().getClassLoader().getResource("Resources/noFill.gif"));

    @SuppressWarnings("unused")
    FillToggleButton fill = new FillToggleButton(fillIcon, FillIDs.Fill);
    @SuppressWarnings("unused")
    FillToggleButton noFill = new FillToggleButton(noFillIcon, FillIDs.NoFill);

    for (FillToggleButton fillButton : FillToggleButton.getAllFillToggleButtons()) {
      toolBar.add(fillButton.getToggleButton());
    }
  }

}
