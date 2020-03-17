package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    Contacts before = app.contact().all(); //считаем количество контактов до добавления?
    app.goTo().addnewPage();
    ContactData contact = new ContactData()
            .withLastName("test").withFirstName("test")
            .withMobilePhone("+79111111111")
            .withAddress("198000, Saint Petersburg,\n" + "Nevsky prospekt, 25/3-43\n" + "domofon 43")
            .withEMail("test@mail.com").withGroup("test1");
    app.contact().create(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }
  @Test ( enabled=false )
  public void testBadContactCreation() throws Exception {
    Contacts before = app.contact().all(); //считаем количество контактов до добавления?
    app.goTo().addnewPage();
    ContactData contact = new ContactData()
            .withLastName("test'").withFirstName("test").withTelephone("+79111111111").withEMail("test@mail.com").withGroup("test1");
    app.contact().create(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }

}
