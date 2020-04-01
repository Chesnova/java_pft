package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

public class ContactAddToGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData()
              .withLastName("test1").withFirstName("test").withTelephone("+79111111111").withEMail("test@mail.com"));
    }
  }

  @Test (enabled = false)
  public void testContactAddToGroup(){
    app.goTo().homePage();
    Contacts before = app.db().contacts();
  }
}
