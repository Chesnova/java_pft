package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromXml() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xStream = new XStream();
      xStream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xStream.fromXML(xml);
      return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json,new TypeToken<List<ContactData>>(){}.getType());
      return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }
  }

  @Test (dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contact)  {
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    app.goTo().addnewPage();
    app.contact().createContact(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));

    verifyContactListInUI();
  }

  @Test
  public void testContactCreation2() throws Exception {
    Groups groups = app.db().groups();
    File photo = new File("src/test/resources/stru.png");
    ContactData contact = new ContactData()
            .withLastName("test").withFirstName("test")
            .withMobilePhone("+79111111111")
            .withAddress("198000, Saint Petersburg,\n" + "Nevsky prospekt, 25/3-43\n" + "domofon 43")
            .withEMail("test@mail.com").withPhoto(photo)
            .inGroup(groups.iterator().next());

    app.goTo().addnewPage();
    app.contact().createContact(contact);
    app.goTo().homePage();
 }

  @Test ( enabled=false )
  public void testBadContactCreation() throws Exception {
    Contacts before = app.contact().all();
    app.goTo().addnewPage();
    ContactData contact = new ContactData()
            .withLastName("test'").withFirstName("test").withTelephone("+79111111111").withEMail("test@mail.com"); //.withGroup("test1")
    app.contact().createContact(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }

}
