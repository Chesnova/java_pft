package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions(){
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
    if (app.db().contacts().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData()
              .withLastName("test1").withFirstName("test").withTelephone("+79111111111").withEMail("test@mail.com"));
    }
  }
  public List<Integer> validGroupAndContactID () {
    Contacts contacts = app.db().contacts();
    Groups groups = app.db().groups();
    List<Integer> validGroupAndContactID = new ArrayList<>();
    for (ContactData contact : contacts) {
      for (GroupData group : groups) {
        if (app.contact().isContactInGroup(contact, group)) {
          validGroupAndContactID.add(group.getId());
          validGroupAndContactID.add(contact.getId());
          return validGroupAndContactID;
        }
      }
    }
    return validGroupAndContactID;
  }

  @Test
  public void testContactAddToGroup(){
    Contacts before = app.db().contacts();
    ContactData contact = before.iterator().next();
    Groups addedGroups = contact.getGroups();
    Groups existGroups = app.db().groups();
    Groups notAdded = new Groups();

    if (existGroups == addedGroups ) {
      app.goTo().groupPage();
      GroupData newGroup = new GroupData().withName("the_new_group");
      app.group().create(newGroup);
      existGroups = app.db().groups();
      GroupData group = newGroup.withId(existGroups.stream().mapToInt((g) -> (g.getId())).max().getAsInt());
    }
    for (GroupData group : existGroups)  {
      if (!addedGroups.contains(group)) {
        notAdded.add(group);
      }
    }
    GroupData group = notAdded.iterator().next();
    app.contact().addToGroup(contact.getId(), group);
    app.goTo().homePage();
    Groups updatedGroups = contact.getGroups();

 //   assertThat(app.contact().count(), equalTo(before.size()));
//    assertThat(updatedGroups, equalTo(existGroups.withAdded(group)));
    verifyContactListInUI();
  }

  @Test
  public void testContactDeleteFromGroup() {
    List<Integer> validID= validGroupAndContactID();
    Contacts before = app.db().contacts();
    Groups groups = app.db().groups();

    ContactData modifiedContact = before.stream().filter(data -> Objects.equals(data.getId(), validID.get(1))).findFirst().get();
    GroupData groupUnassigned = groups.stream().filter(data -> Objects.equals(data.getId(), validID.get(0))).findFirst().get();

    ContactData contact = modifiedContact;


    app.goTo().homePage();
    app.contact().removeFromGroup(contact, groupUnassigned);
    Contacts after = app.db().contacts();
    ContactData contactModifiedDb = after.stream().filter(data -> Objects.equals(data.getId(), modifiedContact.getId())).findFirst().get();
    Assert.assertFalse(app.contact().isContactInGroup(contactModifiedDb, groupUnassigned ));
  }
}
