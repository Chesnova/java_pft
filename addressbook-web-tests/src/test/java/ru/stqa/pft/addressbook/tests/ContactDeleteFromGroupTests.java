package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeleteFromGroupTests extends TestBase{


  @BeforeMethod
  public void ensurePreconditions(){
    if (app.db().groups().size() == 0) { //проверка наличия хотябы одной группы, если нет, то добавляем группу
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test5"));
    }
    if (app.db().contacts().size() == 0) { //проверка наличия хотябы одного контакта, если нет, то добавляем контакт
      app.goTo().homePage();
      app.contact().create(new ContactData()
              .withLastName("test1").withFirstName("test").withTelephone("+79111111111").withEMail("test@mail.com"));
    }

}
  @Test
  public void testContactDeleteFromGroup() {
    ContactData userAfter = null;
    ContactData userSelect;
    GroupData groupSelect = null;

    Groups groups = app.db().groups();//количество групп
    Contacts contacts = app.db().contacts(); // количество контактов
    app.goTo().homePage();//идем на главную страницу
    userSelect = contacts.iterator().next(); // контакт для выбирания

    for (ContactData currentUser : contacts){ // выбраный контакт
      Groups currentGroup = currentUser.getGroups(); // выбранная группа = выбранному контакту
      if (currentGroup.size() > 0){ //если добавленных групп больше нуля, то переходим к удалению
        userSelect = currentUser; // контакт для выбирания равен выбранному контакту
        groupSelect = currentUser.getGroups().iterator().next(); // выбранная группа
        break;
      }
    }

    if (userSelect.getGroups().size() == 0){ // если у выбранного контакта групп ноль
      groupSelect = groups.iterator().next(); // выбранная группа
      app.contact().selectedGroup(userSelect, groupSelect); // добавляем контакт в группу
    }

    app.contact().selectGroupFilterByName(groupSelect);
    app.contact().selectContactById(userSelect.getId());
    app.contact().submitContactDeleteFromGroup(); //удаляем контакт из группы
    app.goTo().homePage();//идем на главную страницу

    Contacts usersAllAfter = app.db().contacts();
    for (ContactData userChoiceAfter : usersAllAfter){
      if (userChoiceAfter.getId() == userSelect.getId()){
        userAfter = userChoiceAfter;
      }
    }

    assertThat(userSelect.getGroups(), equalTo(userAfter.getGroups().withAdded(groupSelect)));
  }

}