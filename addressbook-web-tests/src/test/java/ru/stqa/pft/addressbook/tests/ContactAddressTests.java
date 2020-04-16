package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase{
  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.contact().createContact(new ContactData()
              .withLastName("test").withFirstName("test")
              .withAddress("198000, Saint Petersburg,\n" + "Nevsky prospekt, 25/3-43\n" + "domofon 43")
              .withMobilePhone("+79111111111")
             // .withGroup("test1")
              .withEMail("test@mail.com"));
    }
  }

  @Test
  public void testContactAddress(){
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().contactInfoFromEditForm(contact);

    assertThat(contact.getAddress(), equalTo(mergeAddress(contactInfoFromEditForm)));
  }

  private <T> String mergeAddress(ContactData contact) {
    return Arrays.asList(contact.getAddress()).stream()
            //.map (ContactAddressTests::cleaned) //.map не применяется, т.к. address в таблице такой же, как в форме заполнения
            .filter((s) -> ! s.equals("")).collect(Collectors.joining("\n"));
  }

  public static String cleaned(String address) {
    return address.replaceAll("\\s", " ").replaceAll("[-.()]","").replaceAll(" *\n *", "\n");
  }
}
