package drawings;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import buttons.ColorToggleButton;
import buttons.FillToggleButton;
import buttons.ShapeToggleButton;
import ids.FillIDs;
import ids.ShapeIDs;
import shapes.DrawedShape;
import shapes.Triangle;

public class Drawings extends JPanel {

  private static final long serialVersionUID = -2959224575805930447L;

  private ArrayList<DrawedShape> shapes = new ArrayList<DrawedShape>();
  private ArrayList<DrawedShape> allShapes = new ArrayList<DrawedShape>();
  private Map<Integer, Integer> brushLines = new HashMap<Integer, Integer>();

  private Point2D pointStart = null, pointEnd = null, firstEndPoint = null;
  private DrawedShape point, lin, ellipse, rectangle, movedShape;
  private Graphics2D g2d;
  private Triangle triangle;
  
  private int lines;
  private double startWidth, endWidth, startHeight, endHeight;

  public ArrayList<DrawedShape> getShapes() {
    return shapes;
  }

  public void setShapes(ArrayList<DrawedShape> shapes) {
    this.shapes = shapes;
  }

  public Point2D getPointStart() {
    return pointStart;
  }

  public void setPointStart(Point2D pointStart) {
    this.pointStart = pointStart;
  }

  public Point2D getPointEnd() {
    return pointEnd;
  }

  public void setPointEnd(Point2D pointEnd) {
    this.pointEnd = pointEnd;
  }

  public DrawedShape getPoint() {
    return point;
  }

  public void setPoint(DrawedShape point) {
    this.point = point;
  }

  public DrawedShape getLin() {
    return lin;
  }

  public void setLin(DrawedShape lin) {
    this.lin = lin;
  }

  public DrawedShape getEllipse() {
    return ellipse;
  }

  public void setEllipse(DrawedShape ellipse) {
    this.ellipse = ellipse;
  }

  public DrawedShape getRectangle() {
    return rectangle;
  }

  public void setRectangle(DrawedShape rectangle) {
    this.rectangle = rectangle;
  }

  public Graphics2D getG2d() {
    return g2d;
  }

  public void setG2d(Graphics2D g2d) {
    this.g2d = g2d;
  }

  public ArrayList<DrawedShape> getAllShapes() {
    return allShapes;
  }

  public void setAllShapes(ArrayList<DrawedShape> allShapes) {
    this.allShapes = allShapes;
  }

  public Map<Integer, Integer> getBrushLines() {
    return brushLines;
  }

  public void setBrushLines(Map<Integer, Integer> brushLines) {
    this.brushLines = brushLines;
  }

  public Drawings() {
    {
      addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          pointStart = e.getPoint();
          lines = 0;
//          if(movedShape == null){
//            for (DrawedShape shape : shapes){
//              if (shape.getShape().contains(pointStart)){
//                movedShape = shape;
//                firstEndPoint = pointEnd;
//                startWidth = shape.getShape().getBounds2D().getX();
//                startHeight = shape.getShape().getBounds2D().getY();
//                endWidth = shape.getShape().getBounds2D().getWidth();
//                endHeight = shape.getShape().getBounds2D().getHeight();
//                break;
//              }
//            }  
//          }
        }

        public void mouseReleased(MouseEvent e) {
          if (lin != null) {
            shapes.add(lin);
            allShapes.add(lin);
          }

          if (ellipse != null) {
            shapes.add(ellipse);
            allShapes.add(ellipse);
          }

          if (rectangle != null) {
            shapes.add(rectangle);
            allShapes.add(rectangle);
          }

          if (triangle != null) {
            shapes.add(triangle);
            allShapes.add(triangle);
          }

          if (ShapeToggleButton.isButtonSelected(ShapeIDs.FreeLine)) {
            brushLines.put(shapes.size() - lines, lines);
          }

          removeTrash();
          reset();
        }
      });

      addMouseMotionListener(new MouseMotionAdapter() {
        public void mouseMoved(MouseEvent e) {
          pointEnd = e.getPoint();
        }

        public void mouseDragged(MouseEvent e) {
          pointEnd = e.getPoint();
//          if (movedShape != null){
//            double widthDifference = 
//            movedShape.setShape(new Rectangle2D.Double(startWidth, startHeight, endWidth, endHeight));
//          }
          repaint();
        }
      });
    }
  }

  @Override
  public void paint(Graphics g) {
    g2d = (Graphics2D) g;
    super.paint(g);
    paintAll();
    setColor();

    if (pointStart != null) {
      if (ShapeToggleButton.isButtonSelected(ShapeIDs.FreeLine)) {
        createRandomLine();
      }

      if (ShapeToggleButton.isButtonSelected(ShapeIDs.Triangle)) {
        createTriangle();
      }

      if (ShapeToggleButton.isButtonSelected(ShapeIDs.Line)) {
        createLine();
      }

      if (ShapeToggleButton.isButtonSelected(ShapeIDs.Circle)) {
        createEllipse();
      }

      if (ShapeToggleButton.isButtonSelected(ShapeIDs.Rectangle)) {
        createRectangle();
      }
    }
  }

  public void clear() {
    reset();
    shapes.clear();
    brushLines.clear();
    repaint();
  }

  private void reset() {
    pointStart = null;
    pointEnd = null;
    lin = null;
    ellipse = null;
    rectangle = null;
    point = null;
    triangle = null;
    movedShape = null;
  }

  private void setColor() {
    Color color = ColorToggleButton.getButtonColor();
    if (color != g2d.getColor()) {
      g2d.setColor(color);
    }
  }

  private void draw(Graphics2D g2d, DrawedShape shape) {
    g2d.setColor(shape.getColor());
    if (shape.isDeleted() == false) {
      if (shape.isFilled()) {
        g2d.fill(shape.getShape());
      } else {
        g2d.draw(shape.getShape());
      }
    }
  }

  private void draw(Graphics2D g2d, Triangle triangle) {
    if (triangle.isDeleted() == false) {
      g2d.setColor(triangle.getColor());
      if (triangle.isFilled()) {
        g2d.fillPolygon(triangle.getPolygon());
      } else {
        g2d.drawPolygon(triangle.getPolygon());
      }
    }
  }

  public void paintAll() {
    for (DrawedShape shape : shapes) {
      if (shape instanceof Triangle) {
        draw(g2d, (Triangle) shape);
      } else {
        draw(g2d, shape);
      }
    }
  }

  private void createRandomLine() {
    lines++;
    point = new DrawedShape();
    point.setId(ShapeIDs.FreeLine);
    point.setShape(new Line2D.Double(pointStart.getX(), pointStart.getY(), pointEnd.getX(), pointEnd.getY()));
    point.setColor(g2d.getColor());
    shapes.add(point);
    draw(g2d, point);
    pointStart = pointEnd;
  }

  private void createLine() {
    lin = new DrawedShape();
    lin.setShape(new Line2D.Double(pointStart.getX(), pointStart.getY(), pointEnd.getX(), pointEnd.getY()));
    lin.setId(ShapeIDs.Line);
    lin.setColor(g2d.getColor());
    draw(g2d, lin);
  }

  private void createTriangle() {
    triangle = new Triangle();

    triangle.setUp(pointStart);
    triangle.setLeft(pointEnd);
    triangle.setRight(new Point2D.Double(2 * pointStart.getX() - pointEnd.getX(), pointEnd.getY()));
    triangle.setFilled(FillToggleButton.isButtonSelected(FillIDs.Fill));
    triangle.setColor(g2d.getColor());
    Polygon p = new Polygon((int[]) triangle.getX(), (int[]) triangle.getY(), 3);
    triangle.setPolygon(p);

    draw(g2d, triangle);
  }

  private void createEllipse() {
    ellipse = new DrawedShape();
    ellipse.setId(ShapeIDs.Circle);
    ellipse.setFilled(FillToggleButton.isButtonSelected(FillIDs.Fill));
    if (pointEnd.getX() >= pointStart.getX() && pointEnd.getY() >= pointStart.getY())
      ellipse.setShape(new Ellipse2D.Double(pointStart.getX(), pointStart.getY(), pointEnd.getX() - pointStart.getX(), pointEnd.getY() - pointStart.getY()));
    if (pointEnd.getX() <= pointStart.getX() && pointEnd.getY() >= pointStart.getY())
      ellipse.setShape(new Ellipse2D.Double(pointEnd.getX(), pointStart.getY(), pointStart.getX() - pointEnd.getX(), pointEnd.getY() - pointStart.getY()));
    if (pointEnd.getX() >= pointStart.getX() && pointEnd.getY() <= pointStart.getY())
      ellipse.setShape(new Ellipse2D.Double(pointStart.getX(), pointEnd.getY(), pointEnd.getX() - pointStart.getX(), pointStart.getY() - pointEnd.getY()));
    if (pointEnd.getX() <= pointStart.getX() && pointEnd.getY() <= pointStart.getY())
      ellipse.setShape(new Ellipse2D.Double(pointEnd.getX(), pointEnd.getY(), pointStart.getX() - pointEnd.getX(), pointStart.getY() - pointEnd.getY()));
    ellipse.setColor(g2d.getColor());
    draw(g2d, ellipse);
  }

  private void createRectangle() {
    rectangle = new DrawedShape();
    rectangle.setId(ShapeIDs.Rectangle);
    rectangle.setFilled(FillToggleButton.isButtonSelected(FillIDs.Fill));
    if (pointEnd.getX() >= pointStart.getX() && pointEnd.getY() >= pointStart.getY())
      rectangle.setShape(new Rectangle2D.Double(pointStart.getX(), pointStart.getY(), pointEnd.getX() - pointStart.getX(), pointEnd.getY() - pointStart.getY()));
    if (pointEnd.getX() <= pointStart.getX() && pointEnd.getY() >= pointStart.getY())
      rectangle.setShape(new Rectangle2D.Double(pointEnd.getX(), pointStart.getY(), pointStart.getX() - pointEnd.getX(), pointEnd.getY() - pointStart.getY()));
    if (pointEnd.getX() >= pointStart.getX() && pointEnd.getY() <= pointStart.getY())
      rectangle.setShape(new Rectangle2D.Double(pointStart.getX(), pointEnd.getY(), pointEnd.getX() - pointStart.getX(), pointStart.getY() - pointEnd.getY()));
    if (pointEnd.getX() <= pointStart.getX() && pointEnd.getY() <= pointStart.getY())
      rectangle.setShape(new Rectangle2D.Double(pointEnd.getX(), pointEnd.getY(), pointStart.getX() - pointEnd.getX(), pointStart.getY() - pointEnd.getY()));
    rectangle.setColor(g2d.getColor());
    draw(g2d, rectangle);
  }

  // remove unnecessary shapes
  private void removeTrash() {
    for (int i = 0; i < shapes.size() - 1; i++) {
      if (shapes.get(i).isDeleted() == true) {
        shapes.remove(i);
        i--;
      }
    }
  }

}
