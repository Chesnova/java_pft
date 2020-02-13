package ru.stqa.pft.sandbox;

public class Point {

  int x, y;

  public Point (int x, int y){
    this.x = x;
    this.y = y;
  }

  public static Point scalePoint(Point p, int factor) {
    Point scaledPoint = new Point(p.x * factor, p.y * factor);
    return scaledPoint;
  }

  public static double calculateDistance(Point p1, Point p2){
    double distance = Math.sqrt(p1.x * p2.x + p1.y * p2.y);
    return distance;
  }

  public static void main(String[] args) {
    Point p = new Point(2,3);
    Point scaledPoint = scalePoint(p, 10);
    double distance = calculateDistance(p, scaledPoint);
    System.out.println(distance);
  }
}
