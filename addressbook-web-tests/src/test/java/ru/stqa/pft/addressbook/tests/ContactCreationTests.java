package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoAddnewPage();
    app.getContactHelper().fillContactForm(new ContactData("test", "test", "+79111111111", "test@mail.com"));
    app.getContactHelper().submitContactCreation();
    app.getContactHelper().returnToHomePage();
  }


}