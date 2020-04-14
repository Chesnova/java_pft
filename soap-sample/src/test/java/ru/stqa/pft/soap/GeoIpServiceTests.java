package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

  @Test
    public void testMyIp() {
    //объект типа геосервис, выбираем тип реализации, и вызываем функцию гет, результат сохраняем в переменную iplоcation
    String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("95.55.109.137");
    assertEquals(ipLocation,"RUS");
  }
}
