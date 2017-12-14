package buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;

import drawings.Drawings;
import ids.ButtonIDs;
import ids.ShapeIDs;
import main.Main;
import shapes.DrawnShape;
import shapes.Triangle;

public class Button implements ActionListener {

	private JButton button;
	private ButtonIDs id;

	public Button(ButtonIDs id) {
		this.id = id;
		if (id == ButtonIDs.Clear) {
			button = new JButton("Clear");
		}
		if (id == ButtonIDs.Undo) {
			button = new JButton("Undo");
		}
		if (id == ButtonIDs.Redo) {
			button = new JButton("Redo");
		}

		button.setSize(50, 10);

		button.addActionListener(this);
	}

	public JButton getButton() {
		return button;
	}

	public void actionPerformed(ActionEvent e) {
		if (id == ButtonIDs.Clear) {
			Main.window.getDrawings().clear();
		}
		if (id == ButtonIDs.Undo) {
			undo();
		}
		if (id == ButtonIDs.Redo) {
			redo();
		}
	}

	// undo the last drawn shape
	private void undo() {
		Drawings drawings = Main.window.getDrawings();
		ArrayList<DrawnShape> drawingsShapes = drawings.getShapes();
		ArrayList<DrawnShape> shapes = new ArrayList<DrawnShape>();
		Map<Integer, Integer> drawingsFreeLines = drawings.getBrushLines();
		Map<Integer, Integer> freeLines = new HashMap<Integer, Integer>();
		shapes.addAll(drawingsShapes);
		freeLines.putAll(drawingsFreeLines);
		DrawnShape lastShape = getLastDrawnShape(shapes);
		drawings.clear();

		if (shapes.size() > 0 && lastShape != null) {
			wipeLastLineEllipseRectTriangle(shapes, lastShape, true);
			wipeLastFreeLine(shapes, lastShape, freeLines, true);
			drawingsShapes.addAll(shapes);
			drawingsFreeLines.putAll(freeLines);
		}
	}

	// redo the last undrawn shape
	private void redo() {
		Drawings drawings = Main.window.getDrawings();
		ArrayList<DrawnShape> drawingsShapes = drawings.getShapes();
		ArrayList<DrawnShape> shapes = new ArrayList<DrawnShape>();
		Map<Integer, Integer> drawingsFreeLines = drawings.getBrushLines();
		Map<Integer, Integer> freeLines = new HashMap<Integer, Integer>();
		shapes.addAll(drawingsShapes);
		freeLines.putAll(drawingsFreeLines);
		DrawnShape lastShape = getLastWipedShape(shapes);

		if (shapes.size() > 0 && lastShape != null) {
			drawings.clear();
			wipeLastLineEllipseRectTriangle(shapes, lastShape, false);
			wipeLastFreeLine(shapes, lastShape, freeLines, false);
			drawingsShapes.addAll(shapes);
			drawingsFreeLines.putAll(freeLines);
		}
	}

	// get the last drawn shape iterating from the last element
	private DrawnShape getLastDrawnShape(ArrayList<DrawnShape> shapes) {
		DrawnShape lastDrawnShape = null;
		if (shapes.size() > 0) {
			int lastIndex = shapes.size() - 1;
			for (lastIndex = shapes.size() - 1; lastIndex > -1; lastIndex--) {
				if (shapes.get(lastIndex).isWiped() == false) {
					lastDrawnShape = shapes.get(lastIndex);
					break;
				}
			}
		}

		return lastDrawnShape;
	}

	// get the last wiped shape
	private DrawnShape getLastWipedShape(ArrayList<DrawnShape> shapes) {
		DrawnShape lastDrawnShape = getLastDrawnShape(shapes);
		if (shapes.size() > 0 && shapes.get(shapes.size() - 1) != lastDrawnShape) {
			return shapes.get(shapes.indexOf(lastDrawnShape) + 1);
		}

		return null;
	}

	// wipe the last line or ellipse or rectangle or triangle
	private void wipeLastLineEllipseRectTriangle(ArrayList<DrawnShape> shapes, DrawnShape lastShape, boolean wipe) {
		if (lastShape.getId() == ShapeIDs.Line || lastShape.getId() == ShapeIDs.Circle
				|| lastShape.getId() == ShapeIDs.Rectangle || lastShape instanceof Triangle) {
			lastShape.setWiped(wipe);
		}
	}

	// wipe the last free line
	private void wipeLastFreeLine(ArrayList<DrawnShape> shapes, DrawnShape lastShape, Map<Integer, Integer> freeLines,
			boolean wipe) {
		if (lastShape.getId() == ShapeIDs.FreeLine) {
			// a freeLine is built by many straight lines
			// freeLines is the map that contains information about the freeLines from
			// drawings. The keys represent the indexes of the first line and the value
			// represent the number of lines that the freeLine has

			ArrayList<Integer> firstLines = new ArrayList<Integer>(); // create an array of a set of the indexes of the first
																																// points of all free lines
			firstLines.addAll(freeLines.keySet());
			int auxLine = 0;

			// sort the array of keys
			for (int i = 0; i < firstLines.size(); i++) {
				for (int j = i; j < firstLines.size(); j++) {
					if (firstLines.get(j) < firstLines.get(i)) {
						auxLine = firstLines.get(j);
						firstLines.set(j, firstLines.get(i));
						firstLines.set(i, auxLine);
					}
				}
			}

			// get the shapesIndex of last drawn first line
			int firstLine = 0;

			if (wipe == true) {
				firstLine = getLastDrawnFirstLineIndex(firstLines, shapes);
			} else {
				firstLine = getLastWipedFirstLineIndex(firstLines, shapes);
			}

			int numberOfPoints = freeLines.get(firstLine); // value of the firstPoint, which is a key in the map. Number of
																											// points is the value and also how many lines does the freeline
																											// have
			int lastFreeLine = firstLine + numberOfPoints; // shapeIndex of the last line in the freeLine; freeLine is built
																											// by multiple lines

			for (int i = firstLine; i < lastFreeLine; i++) {
				shapes.get(i).setWiped(wipe);
			}
		}
	}

	// get the last drawn first line of the of all the first lines
	// the first line represents the first line of the free line
	private int getLastDrawnFirstLineIndex(ArrayList<Integer> firstLines, ArrayList<DrawnShape> shapes) {
		int firstLine = 0;
		for (Integer line : firstLines) {
			if (line > firstLine && shapes.get(line).isWiped() == false) {
				firstLine = line;
			}
		}

		return firstLine;
	}

	// get the last wiped first line of all the first lines
	private int getLastWipedFirstLineIndex(ArrayList<Integer> firstLines, ArrayList<DrawnShape> shapes) {
		int lastDrawnFirstLine = getLastDrawnFirstLineIndex(firstLines, shapes);
		DrawnShape lastDrawnShape = getLastDrawnShape(shapes);

		if (firstLines.size() >= 0 && lastDrawnShape.getId() == ShapeIDs.FreeLine) {
			return firstLines.get(firstLines.indexOf(lastDrawnFirstLine) + 1);
		}

		return 0;
	}

}