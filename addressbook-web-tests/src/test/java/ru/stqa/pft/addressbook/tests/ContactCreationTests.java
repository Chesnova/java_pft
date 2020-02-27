package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoHomePage();
    app.getNavigationHelper().gotoAddnewPage();
    app.getContactHelper().createContact(new ContactData("test", "test", "+79111111111", "test@mail.com", "test1"));
  }


}
