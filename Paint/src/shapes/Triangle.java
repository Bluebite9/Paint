package shapes;

import java.awt.Polygon;
import java.awt.geom.Point2D;

public class Triangle extends DrawnShape {

  private Point2D up, left, right;
  private int[] x = new int[3], y = new int[3]; // the x and y coordinates of the edges of the triangle
  private Polygon polygon;

  public int[] getX() {
    return x;
  }

  public int[] getY() {
    return y;
  }

  public Point2D getUp() {
    return up;
  }

  public void setUp(Point2D up) {
    x[0] = (int) up.getX();
    y[0] = (int) up.getY();
    this.up = up;
  }

  public Point2D getLeft() {
    return left;
  }

  public void setLeft(Point2D left) {
    x[1] = (int) left.getX();
    y[1] = (int) left.getY();
    this.left = left;
  }

  public Point2D getRight() {
    return right;
  }

  public void setRight(Point2D right) {
    x[2] = (int) right.getX();
    y[2] = (int) right.getY();
    this.right = right;
  }

  public Polygon getPolygon() {
    return polygon;
  }

  public void setPolygon(Polygon polygon) {
    this.polygon = polygon;
  }

}
