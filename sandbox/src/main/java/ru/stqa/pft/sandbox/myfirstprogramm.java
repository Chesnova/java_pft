package ru.stqa.pft.sandbox;

import org.testng.annotations.Test;

public class myfirstprogramm {

  @Test
    public void main(String[] args) {

        Square s = new Square(5);
        System.out.println("Площадь квадрата со стороной" + s.l + "=" + s.area ());

      Point p1 = new Point(6,6);
      Point p2 = new Point(7,8);
      System.out.println(p1.distance(p2));
    }


}