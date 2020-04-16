package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
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
    app.goTo().groupPage();
    if (app.db().groups().size() == 0) { //проверка наличия хотябы одной группы, если нет, то добавляем группу
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test5"));
    }
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) { //проверка наличия хотябы одного контакта, если нет, то добавляем контакт
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
    Contacts before = app.db().contacts(); //формируем список всех контактов перед добавлением в группу
    ContactData contact = before.iterator().next(); //количество контактов до
    Groups addedGroups = contact.getGroups();//сколько групп - выбрали контакт для добавления в группу
    Groups existGroups = app.db().groups(); //количество групп для добавления
    Groups notAdded = new Groups(); //узнали, что не добавленны в группу - добавляем в группу

    if (existGroups == addedGroups ) { //количество групп - количество выбранных групп
      app.goTo().groupPage();  //идем на страницу групп
      GroupData newGroup = new GroupData().withName("the_new_group");
      app.group().create(newGroup);
      existGroups = app.db().groups();
      GroupData group = newGroup.withId(existGroups.stream().mapToInt((g) -> (g.getId())).max().getAsInt());
    }
    for (GroupData group : existGroups)  { // представленных групп
      if (!addedGroups.contains(group)) { // выбранных ноль - выбираем из представленных групп
        notAdded.add(group); // нет выбранных
      }
    }
    GroupData group = notAdded.iterator().next();
    app.contact().addToGroup(contact.getId(), group);
    app.goTo().homePage();
    Groups updatedGroups = contact.getGroups();

 //   assertThat(app.contact().count(), equalTo(before.size()));
//    assertThat(updatedGroups, equalTo(existGroups.withAdded(group)));
    verifyContactListInUI();
    //В тесте добавления контакта в группу, если все контакты добавлены во все группы.
    // В данном случае надо создавать новый контакт или группу.



  }
}
