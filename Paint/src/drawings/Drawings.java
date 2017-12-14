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
import shapes.DrawnShape;
import shapes.Triangle;

public class Drawings extends JPanel {

	private static final long serialVersionUID = -2959224575805930447L;

	private ArrayList<DrawnShape> shapes = new ArrayList<DrawnShape>();
	private ArrayList<DrawnShape> allShapes = new ArrayList<DrawnShape>();
	private Map<Integer, Integer> brushLines = new HashMap<Integer, Integer>();

	private Point2D pointStart = null, pointEnd = null;
	private DrawnShape point, lin, ellipse, rectangle;
	private Graphics2D g2d;
	private Triangle triangle;

	private int lines;

	public ArrayList<DrawnShape> getShapes() {
		return shapes;
	}

	public void setShapes(ArrayList<DrawnShape> shapes) {
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

	public DrawnShape getPoint() {
		return point;
	}

	public void setPoint(DrawnShape point) {
		this.point = point;
	}

	public DrawnShape getLin() {
		return lin;
	}

	public void setLin(DrawnShape lin) {
		this.lin = lin;
	}

	public DrawnShape getEllipse() {
		return ellipse;
	}

	public void setEllipse(DrawnShape ellipse) {
		this.ellipse = ellipse;
	}

	public DrawnShape getRectangle() {
		return rectangle;
	}

	public void setRectangle(DrawnShape rectangle) {
		this.rectangle = rectangle;
	}

	public Graphics2D getG2d() {
		return g2d;
	}

	public void setG2d(Graphics2D g2d) {
		this.g2d = g2d;
	}

	public ArrayList<DrawnShape> getAllShapes() {
		return allShapes;
	}

	public void setAllShapes(ArrayList<DrawnShape> allShapes) {
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
			// mouse adapter
			addMouseListener(new MouseAdapter() {
				// when the mouse is pressed, get the start point
				// if the free line toggle button is selected, initialize the
				public void mousePressed(MouseEvent e) {
					pointStart = e.getPoint();
					if (ShapeToggleButton.isButtonSelected(ShapeIDs.FreeLine)) {
						lines = 0;
					}
				}

				// add the drawn shape to the list
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

					// create the map for free lines. A free line is built by many little lines. The
					// index of the first line is a key in the map. The value of that key is the
					// number of lines that the free line has
					if (ShapeToggleButton.isButtonSelected(ShapeIDs.FreeLine)) {
						brushLines.put(shapes.size() - lines, lines);
					}

					removeTrash();
					reset();
				}
			});

			// mouse motion adapter
			addMouseMotionListener(new MouseMotionAdapter() {

				// get the point where the mouse is at and repaint()
				public void mouseDragged(MouseEvent e) {
					pointEnd = e.getPoint();
					repaint();
				}
			});
		}
	}

	// override the paint method
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

	// clear all
	public void clear() {
		reset();
		shapes.clear();
		brushLines.clear();
		repaint();
	}

	// reset all necessary variables
	private void reset() {
		pointStart = null;
		pointEnd = null;
		lin = null;
		ellipse = null;
		rectangle = null;
		point = null;
		triangle = null;
	}

	// set the color of the new shape
	private void setColor() {
		Color color = ColorToggleButton.getButtonColor();
		if (color != g2d.getColor()) {
			g2d.setColor(color);
		}
	}

	// draw a shape
	private void draw(Graphics2D g2d, DrawnShape shape) {
		g2d.setColor(shape.getColor());
		if (shape.isWiped() == false) {
			if (shape.isFilled()) {
				g2d.fill(shape.getShape());
			} else {
				g2d.draw(shape.getShape());
			}
		}
	}

	// draw a triangle
	private void draw(Graphics2D g2d, Triangle triangle) {
		if (triangle.isWiped() == false) {
			g2d.setColor(triangle.getColor());
			if (triangle.isFilled()) {
				g2d.fillPolygon(triangle.getPolygon());
			} else {
				g2d.drawPolygon(triangle.getPolygon());
			}
		}
	}

	// paint all shapes
	public void paintAll() {
		for (DrawnShape shape : shapes) {
			if (shape instanceof Triangle) {
				draw(g2d, (Triangle) shape);
			} else {
				draw(g2d, shape);
			}
		}
	}

	// create a random line, using the movement of the mouse
	private void createRandomLine() {
		lines++;
		point = new DrawnShape();
		point.setId(ShapeIDs.FreeLine);
		point.setShape(new Line2D.Double(pointStart.getX(), pointStart.getY(), pointEnd.getX(), pointEnd.getY()));
		point.setColor(g2d.getColor());
		shapes.add(point);
		draw(g2d, point);
		pointStart = pointEnd;
	}

	// creates a straight line
	private void createLine() {
		lin = new DrawnShape();
		lin.setShape(new Line2D.Double(pointStart.getX(), pointStart.getY(), pointEnd.getX(), pointEnd.getY()));
		lin.setId(ShapeIDs.Line);
		lin.setColor(g2d.getColor());
		draw(g2d, lin);
	}

	// creates a triangle
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

	// create a ellipse using mathematical quadrant
	private void createEllipse() {
		ellipse = new DrawnShape();
		ellipse.setId(ShapeIDs.Circle);
		ellipse.setFilled(FillToggleButton.isButtonSelected(FillIDs.Fill));
		if (pointEnd.getX() >= pointStart.getX() && pointEnd.getY() >= pointStart.getY())
			ellipse.setShape(new Ellipse2D.Double(pointStart.getX(), pointStart.getY(), pointEnd.getX() - pointStart.getX(),
					pointEnd.getY() - pointStart.getY()));
		if (pointEnd.getX() <= pointStart.getX() && pointEnd.getY() >= pointStart.getY())
			ellipse.setShape(new Ellipse2D.Double(pointEnd.getX(), pointStart.getY(), pointStart.getX() - pointEnd.getX(),
					pointEnd.getY() - pointStart.getY()));
		if (pointEnd.getX() >= pointStart.getX() && pointEnd.getY() <= pointStart.getY())
			ellipse.setShape(new Ellipse2D.Double(pointStart.getX(), pointEnd.getY(), pointEnd.getX() - pointStart.getX(),
					pointStart.getY() - pointEnd.getY()));
		if (pointEnd.getX() <= pointStart.getX() && pointEnd.getY() <= pointStart.getY())
			ellipse.setShape(new Ellipse2D.Double(pointEnd.getX(), pointEnd.getY(), pointStart.getX() - pointEnd.getX(),
					pointStart.getY() - pointEnd.getY()));
		ellipse.setColor(g2d.getColor());
		draw(g2d, ellipse);
	}

	// create a rectangle using mathematical quadrant
	private void createRectangle() {
		rectangle = new DrawnShape();
		rectangle.setId(ShapeIDs.Rectangle);
		rectangle.setFilled(FillToggleButton.isButtonSelected(FillIDs.Fill));
		if (pointEnd.getX() >= pointStart.getX() && pointEnd.getY() >= pointStart.getY())
			rectangle.setShape(new Rectangle2D.Double(pointStart.getX(), pointStart.getY(),
					pointEnd.getX() - pointStart.getX(), pointEnd.getY() - pointStart.getY()));
		if (pointEnd.getX() <= pointStart.getX() && pointEnd.getY() >= pointStart.getY())
			rectangle.setShape(new Rectangle2D.Double(pointEnd.getX(), pointStart.getY(), pointStart.getX() - pointEnd.getX(),
					pointEnd.getY() - pointStart.getY()));
		if (pointEnd.getX() >= pointStart.getX() && pointEnd.getY() <= pointStart.getY())
			rectangle.setShape(new Rectangle2D.Double(pointStart.getX(), pointEnd.getY(), pointEnd.getX() - pointStart.getX(),
					pointStart.getY() - pointEnd.getY()));
		if (pointEnd.getX() <= pointStart.getX() && pointEnd.getY() <= pointStart.getY())
			rectangle.setShape(new Rectangle2D.Double(pointEnd.getX(), pointEnd.getY(), pointStart.getX() - pointEnd.getX(),
					pointStart.getY() - pointEnd.getY()));
		rectangle.setColor(g2d.getColor());
		draw(g2d, rectangle);
	}

	// remove unnecessary shapes
	private void removeTrash() {
		for (int i = 0; i < shapes.size() - 1; i++) {
			if (shapes.get(i).isWiped() == true) {
				shapes.remove(i);
				i--;
			}
		}
	}

}
