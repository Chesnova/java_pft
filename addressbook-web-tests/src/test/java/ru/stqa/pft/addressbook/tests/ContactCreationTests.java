package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() {
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[] {new ContactData().withLastName("test").withFirstName("test").withAddress("address").withHomePhone("HomePhone").withMobilePhone("MobilePhone").withWorkPhone("WorkPhone").withEMail2("EMail2").withEMail3("EMail3")});
//    list.add(new Object[] {new ContactData().withLastName("test2").withFirstName("test2").withAddress("address2").withHomePhone("HomePhone2").withMobilePhone("MobilePhone2").withWorkPhone("WorkPhone2").withEMail2("EMail22").withEMail3("EMail32")});
//    list.add(new Object[] {new ContactData().withLastName("test3").withFirstName("test3").withAddress("address3").withHomePhone("HomePhone3").withMobilePhone("MobilePhone3").withWorkPhone("WorkPhone3").withEMail2("EMail23").withEMail3("EMail33")});
    return list.iterator();
  }

  @Test (dataProvider = "validContacts")
  public void testContactCreation(ContactData contact)  {
    Contacts before = app.contact().all(); //считаем количество контактов до добавления?
    app.goTo().addnewPage();
    app.contact().create(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

  @Test ( enabled=false )
  public void testContactCreation2() throws Exception {
    app.goTo().addnewPage();
    File photo = new File("src/test/resources/stru.png");
    ContactData contact = new ContactData()
            .withLastName("test").withFirstName("test")
            .withMobilePhone("+79111111111")
            .withAddress("198000, Saint Petersburg,\n" + "Nevsky prospekt, 25/3-43\n" + "domofon 43")
            .withEMail("test@mail.com").withPhoto(photo).withGroup("test1");
    app.contact().create(contact);
    app.goTo().homePage();
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
