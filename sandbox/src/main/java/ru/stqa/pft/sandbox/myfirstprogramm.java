package ru.stqa.pft.sandbox;

public class MyFirstProgramm {

    public static void main(String[] args) {

        Square s = new Square(5);
        System.out.println("Площадь квадрата со стороной" + s.l + "=" + s.area ());

      Point p1 = new Point(3,4);
      Point p2 = new Point(7,8);
      System.out.println(p1.distance(p2));
    }


}