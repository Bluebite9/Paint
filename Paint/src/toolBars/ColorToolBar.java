package toolBars;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JToolBar;

import buttons.ColorToggleButton;
import ids.ColorIDs;

public class ColorToolBar {

	private JToolBar colorToolBar;

	// create a color toolbar and add all buttons to a list
	public ColorToolBar() {
		colorToolBar = new JToolBar();
		colorToolBar.setName("Colors");
		colorToolBar.setFloatable(false);

		ImageIcon blackIcon = new ImageIcon(getClass().getClassLoader().getResource("Resources/black.gif"));
		ImageIcon redIcon = new ImageIcon(getClass().getClassLoader().getResource("Resources/red.gif"));
		ImageIcon blueIcon = new ImageIcon(getClass().getClassLoader().getResource("Resources/blue.gif"));
		ImageIcon yellowIcon = new ImageIcon(getClass().getClassLoader().getResource("Resources/yellow.gif"));
		ImageIcon greenIcon = new ImageIcon(getClass().getClassLoader().getResource("Resources/green.gif"));

		@SuppressWarnings("unused")
		ColorToggleButton blackColorToggleButton = new ColorToggleButton(blackIcon, ColorIDs.Black, Color.black);
		@SuppressWarnings("unused")
		ColorToggleButton redColorToggleButton = new ColorToggleButton(redIcon, ColorIDs.Red, Color.red);
		@SuppressWarnings("unused")
		ColorToggleButton blueColorToggleButton = new ColorToggleButton(blueIcon, ColorIDs.Blue, Color.blue);
		@SuppressWarnings("unused")
		ColorToggleButton yellowColorToggleButton = new ColorToggleButton(yellowIcon, ColorIDs.Yellow, Color.yellow);
		@SuppressWarnings("unused")
		ColorToggleButton greenColorToggleButton = new ColorToggleButton(greenIcon, ColorIDs.Green, Color.green);

		for (ColorToggleButton colorButton : ColorToggleButton.getAllColorToggleButtons()) {
			colorToolBar.add(colorButton.getColorToggleButton());
		}
	}

	public JToolBar getColorToolBar() {
		return this.colorToolBar;
	}

}