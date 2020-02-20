package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.gotoAddnewPage();
    app.fillContactForm(new ContactData("test", "test", "+79111111111", "test@mail.com"));
    app.submitContactCreation();
    app.returnToHomePage();
  }


}
