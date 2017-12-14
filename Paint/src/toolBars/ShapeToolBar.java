package toolBars;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JToolBar;

import buttons.ShapeToggleButton;
import ids.ShapeIDs;

public class ShapeToolBar {

	private JToolBar shapeToolBar;

	// create a shape toolbar and add all buttons to a list
	public ShapeToolBar() throws IOException {
		shapeToolBar = new JToolBar();
		shapeToolBar.setName("Shapes");
		shapeToolBar.setFloatable(false);

		ImageIcon lineIcon = new ImageIcon(getClass().getClassLoader().getResource("Resources/line.gif"));
		ImageIcon ellipseIcon = new ImageIcon(getClass().getClassLoader().getResource("Resources/circle.gif"));
		ImageIcon rectangleIcon = new ImageIcon(getClass().getClassLoader().getResource("Resources/rectangle.gif"));
		ImageIcon brushIcon = new ImageIcon(getClass().getClassLoader().getResource("Resources/brush.gif"));
		ImageIcon triangleIcon = new ImageIcon(getClass().getClassLoader().getResource("Resources/triangle.gif"));

		@SuppressWarnings("unused")
		ShapeToggleButton lineShapeToggleButton = new ShapeToggleButton(lineIcon, ShapeIDs.Line);
		@SuppressWarnings("unused")
		ShapeToggleButton ellipseShapeToggleButton = new ShapeToggleButton(ellipseIcon, ShapeIDs.Circle);
		@SuppressWarnings("unused")
		ShapeToggleButton rectangleShapeToggleButton = new ShapeToggleButton(rectangleIcon, ShapeIDs.Rectangle);
		@SuppressWarnings("unused")
		ShapeToggleButton brushShapeToggleButton = new ShapeToggleButton(brushIcon, ShapeIDs.FreeLine);
		@SuppressWarnings("unused")
		ShapeToggleButton triangleShapeToggleButton = new ShapeToggleButton(triangleIcon, ShapeIDs.Triangle);

		for (ShapeToggleButton shapeButton : ShapeToggleButton.getAllShapeToggleButtons()) {
			shapeToolBar.add(shapeButton.getShapeToggleButton());
		}
	}

	public JToolBar getShapeToolBar() {
		return this.shapeToolBar;
	}

}