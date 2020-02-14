package ru.stqa.pft.sandbox;

public class MyFirstProgramm {

    public static void main(String[] args) {

        Square s = new Square(5);
        System.out.println("Площадь квадрата со стороной" + s.l + "=" + s.area ());

        Point p = new Point(4,9);
        System.out.println("Расстояние между точками" + "=" + p.distance());
    }


}