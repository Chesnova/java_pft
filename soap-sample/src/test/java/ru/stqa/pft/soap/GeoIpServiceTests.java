package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import com.lavasoft.GeoIPServiceSoap;
import org.testng.Assert;
import org.testng.annotations.Test;


public class GeoIpServiceTests {

  @Test
    public void testMyIp() {
    //объект типа геосервис, выбираем тип реализации, и вызываем функцию гет, результат сохраняем в переменную iplоcation
    String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("95.55.109.137");
    //так и должно быть, потому что именно такое составное значение возвращает этот сервис. не объект (как тот, ранее существовавший сервис), а строку с тегами
     //поэтому можно её так целиком и сравнивать
    // если подставить просто RUS, то выдаст expected [RUS] but found [<GeoIP><Country>RU</Country><State>66</State></GeoIP>]
    //java.lang.AssertionError: expected [RUS] but found [<GeoIP><Country>RU</Country><State>66</State></GeoIP>]
    Assert.assertEquals(ipLocation,"<GeoIP><Country>RU</Country><State>66</State></GeoIP>");
  }
}
