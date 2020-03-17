package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
  Contacts before = app.contact().all();
  ContactData modifiedContact = before.iterator().next();
  ContactData contact = new ContactData()
          .withId(modifiedContact.getId()).withLastName("test1")
          .withFirstName("test").withTelephone("+79111111111")
          .withEMail("test@mail.com").withGroup(null);
  app.contact().modify(contact);
  assertThat(app.contact().count(), equalTo(before.size()));
  Contacts after = app.contact().all();
  assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }


}
