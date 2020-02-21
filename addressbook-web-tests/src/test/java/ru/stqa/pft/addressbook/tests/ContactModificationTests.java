package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {
@Test
  public void testContactModification () {
  app.getContactHelper().selectContact();
  app.getContactHelper().initContactModification();
  app.getContactHelper().fillContactForm(new ContactData("test", "test", "+79111111111", "test@mail.com"));
  app.getContactHelper().submitContactModification ();
  app.getContactHelper().returnToHomePage();
}

}
