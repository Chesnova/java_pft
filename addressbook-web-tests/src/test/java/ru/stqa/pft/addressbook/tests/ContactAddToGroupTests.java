package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroupTests extends TestBase {

  ContactData helpContact = new ContactData()
          .withLastName("test1").withFirstName("test").withTelephone("+79111111111").withEMail("test@mail.com");
  GroupData helpGroup = new GroupData().withName("test8");

  @BeforeClass
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(helpGroup);
    }

    Groups groups = app.db().groups();
    if (app.db().contacts().size() == 0) {
      app.goTo().newContact();
      app.contact().createContact(helpContact.inGroup(groups.iterator().next()), true);
      app.goTo().homePage();
    }
  }

  @Test
  public void testAddContactToGroup() {
    Contacts usersAll = app.db().contacts();
    Groups groupsAll = app.db().groups();

    ContactData userSelect = null;
    GroupData groupSelect = null;
    ContactData userAfter = null;

    for (ContactData currentUser : usersAll) {
      Groups groupsOfSelectedUser = currentUser.getGroups();
      if (groupsOfSelectedUser.size() != groupsAll.size()) {
        groupsAll.removeAll(groupsOfSelectedUser);
        groupSelect = groupsAll.iterator().next();
        userSelect = currentUser;
        break;
      }
    }
    if (groupSelect == null) {
      ContactData user = helpContact;
      app.goTo().newContact();
      app.contact().createContact(user, true);
      app.goTo().homePage();
      Contacts userA = app.db().contacts();
      user.withId(userA.stream().mapToInt((g) -> (g).getId()).max().getAsInt());
      userSelect = user;
      groupSelect = groupsAll.iterator().next();
    }

    app.goTo().homePage();
    app.contact().allGroupsOnUserPage();
    app.contact().selectGroupforContact(userSelect, groupSelect);
    app.goTo().homePage();

    Contacts usersAllAfter = app.db().contacts();
    for (ContactData currentUserAfter : usersAllAfter) {
      if (currentUserAfter.getId() == userSelect.getId()) {
        userAfter = currentUserAfter;
      }
    }

    assertThat(userSelect.getGroups(), equalTo(userAfter.getGroups().without(groupSelect)));
  }

}