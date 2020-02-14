package ru.stqa.pft.sandbox;

public class Point {

 public double a;
 public double b;

 public Point (double a ,double b) {
   this.a = a;
   this.b = b;
 }

  public double distance (Point p2) {
    return Math.sqrt((p2.a - this.a) * (p2.a - this.a) + (p2.b - this.b) * (p2.b - this.b));
 }
}
