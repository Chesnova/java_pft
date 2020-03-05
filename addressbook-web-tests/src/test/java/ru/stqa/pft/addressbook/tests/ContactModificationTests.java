package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {
@Test
  public void testContactModification () {
  app.getNavigationHelper().gotoHomePage();
  int before = app.getContactHelper().getContactCount();
  if (! app.getContactHelper().isThereAContact()) {
    app.getContactHelper().createContact(new ContactData("test", "test", "+79111111111", "test@mail.com", "test1"));
  }
  app.getContactHelper().selectContact();
  app.getContactHelper().initContactModification();
  app.getContactHelper().fillContactForm(new ContactData("test", "test", "+79111111111", "test@mail.com", null), false);
  app.getContactHelper().submitContactModification ();
  app.getContactHelper().returnToHomePage();
  int after = app.getContactHelper().getContactCount();
  Assert.assertEquals(after, before);
}

}
