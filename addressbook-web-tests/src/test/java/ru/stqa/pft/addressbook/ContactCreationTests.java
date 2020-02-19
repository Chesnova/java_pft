package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    gotoAddnewPage();
    fillContactForm(new ContactData("test", "test", "+79111111111", "test@mail.com"));
    submitContactCreation();
    returnToHomePage();
  }


}
