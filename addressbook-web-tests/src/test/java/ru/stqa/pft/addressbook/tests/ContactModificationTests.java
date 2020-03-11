package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {
@Test
  public void testContactModification () {
  app.goTo().gotoHomePage();
  if (! app.getContactHelper().isThereAContact()) {
    app.getContactHelper().createContact(new ContactData("test1", "test", "+79111111111", "test@mail.com", "test1"));
  }
  List<ContactData> before = app.getContactHelper().getContactList(); //количество контактов до модификации
  app.getContactHelper().selectContact(before.size() - 1);
  app.getContactHelper().initContactModification(before.size() - 1);
  ContactData contact = new ContactData(before.get(before.size() - 1).getId(),"test1", "test", "+79111111111", "test@mail.com", null);
  app.getContactHelper().fillContactForm(contact, false);
  app.getContactHelper().submitContactModification ();
  app.getContactHelper().returnToHomePage();
  List<ContactData> after = app.getContactHelper().getContactList();
  Assert.assertEquals(after.size(), before.size()); //количество контактов после модификации

  before.remove(before.size() - 1);
  before.add(contact);
  Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
  before.sort(byId);
  after.sort(byId);
  Assert.assertEquals(before, after);
}

}
