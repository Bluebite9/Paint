package shapes;

import java.awt.Color;
import java.awt.Shape;

import ids.ShapeIDs;

public class DrawnShape {

  private Shape shape;
  private Color color;
  private boolean filled = false, wiped = false;
  private ShapeIDs id;

  public Shape getShape() {
    return shape;
  }

  public void setShape(Shape shape) {
    this.shape = shape;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public boolean isFilled() {
    return filled;
  }

  public void setFilled(boolean filled) {
    this.filled = filled;
  }

  public ShapeIDs getId() {
    return id;
  }

  public void setId(ShapeIDs id) {
    this.id = id;
  }

  public boolean isWiped() {
    return wiped;
  }

  public void setWiped(boolean wiped) {
    this.wiped = wiped;
  }

}
