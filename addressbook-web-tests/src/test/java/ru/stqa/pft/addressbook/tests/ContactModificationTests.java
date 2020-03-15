package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.Set;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.contact().create(new ContactData()
              .withLastName("test1").withFirstName("test").withTelephone("+79111111111").withEMail("test@mail.com").withGroup("test1"));
    }

  }
  @Test
  public void testContactModification () {
  Set<ContactData> before = app.contact().all();
  ContactData modifiedContact = before.iterator().next();
  ContactData contact = new ContactData()
          .withId(modifiedContact.getId()).withLastName("test1").withFirstName("test").withTelephone("+79111111111").withEMail("test@mail.com").withGroup(null);
  app.contact().modify(contact);
  Set<ContactData> after = app.contact().all();
  Assert.assertEquals(after.size(), before.size()); //количество контактов после модификации

  before.remove(modifiedContact);
  before.add(contact);
  Assert.assertEquals(before, after);
}


}
