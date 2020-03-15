package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().homePage();
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData("test1", "test", "+79111111111", "test@mail.com", "test1"));
    }

  }
  @Test
  public void testContactModification () {
 List<ContactData> before = app.contact().list();//количество контактов до модификации
  int index = before.size() - 1;
  ContactData contact = new ContactData(before.get(index).getId(),"test1", "test", "+79111111111", "test@mail.com", null);
  app.contact().modify(index, contact);
  List<ContactData> after = app.contact().list();
  Assert.assertEquals(after.size(), before.size()); //количество контактов после модификации

  before.remove(index);
  before.add(contact);
  Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
  before.sort(byId);
  after.sort(byId);
  Assert.assertEquals(before, after);
}


}
