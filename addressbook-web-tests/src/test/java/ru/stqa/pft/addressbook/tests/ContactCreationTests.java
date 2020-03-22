package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")));
    String line = reader.readLine();
    while (line != null) {
      String[] split = line.split(";");
      list.add(new Object[] {new ContactData().withLastName(split[0]).withFirstName(split[1]).withAddress(split[2]).withHomePhone(split[3]).withMobilePhone(split[4]).withWorkPhone(split[5]).withEMail(split[6]).withEMail2(split[7]).withEMail3(split[8])});
      line = reader.readLine();
    }
    return list.iterator();
  }

  @Test (dataProvider = "validContacts")
  public void testContactCreation(ContactData contact)  {
    app.goTo().homePage();
    Contacts before = app.contact().all();
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
    Contacts before = app.contact().all();
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
