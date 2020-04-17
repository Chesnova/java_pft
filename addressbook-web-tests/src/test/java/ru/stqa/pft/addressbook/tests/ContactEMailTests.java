package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEMailTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.contact().createContact(new ContactData()
              .withLastName("test").withFirstName("test").withHomePhone("22 33").withMobilePhone("+79111111111").withWorkPhone("812-333-56-78").withEMail("test3@mail.com")); //.withGroup("test1")
    }
  }

  @Test
  public void testContactEMails() {
    app.goTo().homePage(); //переходим на главную страницу приложения
    ContactData contact = app.contact().all().iterator().next(); //загружаем множество или список контактов и выбираем контакт случайным образом
    ContactData contactInfoFromEditForm = app.contact().contactInfoFromEditForm(contact);

    assertThat(contact.getAllEMails(), equalTo(mergeEMails(contactInfoFromEditForm)));
  }

  private <T> String mergeEMails(ContactData contact) {
    return Arrays.asList(contact.getEMail(), contact.getEMail2(), contact.getEMail3())
            .stream().filter((s) -> ! s.equals(""))
            //.map(ContactEMailTests::cleaned) //.map не применяется, т.к. e-mail в таблице такое же, как в форме заполнения
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned(String email) {
    return email.replaceAll("\\s","").replaceAll("[-.()]","");
  }
}
