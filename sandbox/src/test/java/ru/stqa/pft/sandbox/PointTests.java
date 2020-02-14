package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testDistance (){
    Point p = new Point(4,9);
    Assert.assertEquals ( p.distance(), 9.848857801796104);
  }

}
