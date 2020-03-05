package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactModificationTests extends TestBase {
@Test
  public void testContactModification () {
  app.getNavigationHelper().gotoHomePage();
  if (! app.getContactHelper().isThereAContact()) {
    app.getContactHelper().createContact(new ContactData("test", "test", "+79111111111", "test@mail.com", "test1"));
  }
  List<ContactData> before = app.getContactHelper().getContactList();
  app.getContactHelper().selectContact(before.size() - 1);
  app.getContactHelper().initContactModification();
  app.getContactHelper().fillContactForm(new ContactData("test", "test", "+79111111111", "test@mail.com", null), false);
  app.getContactHelper().submitContactModification ();
  app.getContactHelper().returnToHomePage();
  List<ContactData> after = app.getContactHelper().getContactList();
  Assert.assertEquals(after.size(), before.size());
}

}
