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
import shapes.DrawedShape;
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

  private void undo() {
    Drawings drawings = Main.window.getDrawings();
    ArrayList<DrawedShape> drawingsShapes = drawings.getShapes();
    ArrayList<DrawedShape> shapes = new ArrayList<DrawedShape>();
    Map<Integer, Integer> drawingsFreeLines = drawings.getBrushLines();
    Map<Integer, Integer> freeLines = new HashMap<Integer, Integer>();
    shapes.addAll(drawingsShapes);
    freeLines.putAll(drawingsFreeLines);
    DrawedShape lastShape = getLastDrawedShape(shapes);
    drawings.clear();

    if (shapes.size() > 0 && lastShape != null) {
      changeDeletionLastLineEllipseRectTriangle(shapes, lastShape, true);
      changeDeletionLastFreeLine(shapes, lastShape, freeLines, true);
      drawingsShapes.addAll(shapes);
      drawingsFreeLines.putAll(freeLines);
    }
  }

  private void redo() {
    Drawings drawings = Main.window.getDrawings();
    ArrayList<DrawedShape> drawingsShapes = drawings.getShapes();
    ArrayList<DrawedShape> shapes = new ArrayList<DrawedShape>();
    Map<Integer, Integer> drawingsFreeLines = drawings.getBrushLines();
    Map<Integer, Integer> freeLines = new HashMap<Integer, Integer>();
    shapes.addAll(drawingsShapes);
    freeLines.putAll(drawingsFreeLines);
    DrawedShape lastShape = getLastUndrawedShape(shapes);

    if (shapes.size() > 0 && lastShape != null) {
      drawings.clear();
      changeDeletionLastLineEllipseRectTriangle(shapes, lastShape, false);
      changeDeletionLastFreeLine(shapes, lastShape, freeLines, false);
      drawingsShapes.addAll(shapes);
      drawingsFreeLines.putAll(freeLines);
    }
  }

  private DrawedShape getLastDrawedShape(ArrayList<DrawedShape> shapes) {
    DrawedShape lastDrawedShape = null;
    if (shapes.size() > 0) {
      int lastIndex = shapes.size() - 1;
      for (lastIndex = shapes.size() - 1; lastIndex > -1; lastIndex--) {
        if (shapes.get(lastIndex).isDeleted() == false) {
          lastDrawedShape = shapes.get(lastIndex);
          break;
        }
      }
    }

    return lastDrawedShape;
  }

  private DrawedShape getLastUndrawedShape(ArrayList<DrawedShape> shapes) {
    DrawedShape lastDrawedShape = getLastDrawedShape(shapes);
    if (shapes.size() > 0 && shapes.get(shapes.size() - 1) != lastDrawedShape) {
      return shapes.get(shapes.indexOf(lastDrawedShape) + 1);
    }

    return null;
  }

  private void changeDeletionLastLineEllipseRectTriangle(ArrayList<DrawedShape> shapes, DrawedShape lastShape, boolean deletion) {
    if (lastShape.getId() == ShapeIDs.Line || lastShape.getId() == ShapeIDs.Circle || lastShape.getId() == ShapeIDs.Rectangle || lastShape instanceof Triangle) {
      lastShape.setDeleted(deletion);
    }
  }

  private void changeDeletionLastFreeLine(ArrayList<DrawedShape> shapes, DrawedShape lastShape, Map<Integer, Integer> freeLines, boolean deletion) {
    if (lastShape.getId() == ShapeIDs.FreeLine) {
      // a freeLine is built by many straight lines
      // freeLines is the map that contains information about the freeLines from drawings. The keys represent the indexes of the first line and the value represent the number of lines that the freeLine has

      ArrayList<Integer> firstLines = new ArrayList<Integer>(); // create an array of a set of the indexes of the first points of all free lines
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

      // get the shapesIndex of last undeleted first line
      int firstLine = 0;

      if (deletion == true) {
        firstLine = getLastUndeletedFirstLineIndex(firstLines, shapes);
      } else {
        firstLine = getLastDeletedFirstLineIndex(firstLines, shapes);
      }

      int numberOfPoints = freeLines.get(firstLine); // value of the firstPoint, which is a key in the map. Number of points is the value and also how many lines does the freeline have
      int lastFreeLine = firstLine + numberOfPoints; // shapeIndex of the last line in the freeLine; freeLine is built by multiple lines

      for (int i = firstLine; i < lastFreeLine; i++) {
        shapes.get(i).setDeleted(deletion);
      }
    }
  }

  private int getLastUndeletedFirstLineIndex(ArrayList<Integer> firstLines, ArrayList<DrawedShape> shapes) {
    int firstLine = 0;
    for (Integer line : firstLines) {
      if (line > firstLine && shapes.get(line).isDeleted() == false) {
        firstLine = line;
      }
    }

    return firstLine;
  }

  private int getLastDeletedFirstLineIndex(ArrayList<Integer> firstLines, ArrayList<DrawedShape> shapes) {
    int lastUndeletedFirstLine = getLastUndeletedFirstLineIndex(firstLines, shapes);
    DrawedShape lastDrawedShape = getLastDrawedShape(shapes);

    if (firstLines.size() >= 0 && lastDrawedShape.getId() == ShapeIDs.FreeLine) {
      return firstLines.get(firstLines.indexOf(lastUndeletedFirstLine) + 1);
    }

    return 0;
  }

}