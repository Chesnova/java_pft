package ru.stqa.pft.addressbook.generators;

import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
  public static void main(String[] args) throws IOException {
    int count = Integer.parseInt(args[0]);
    File file = new File(args[1]);

    List<ContactData> contacts = generateContacts(count);
    save(contacts, file);
  }

  private static void save(List<ContactData> contacts, File file) throws IOException {
    System.out.println(new File(".").getAbsolutePath());
    Writer writer = new FileWriter(file);
    for (ContactData contact : contacts) {
      writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s\n", contact.getLastName(), contact.getFirstName(), contact.getAddress(), contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone(),
              contact.getEMail(), contact.getEMail2(), contact.getEMail3()));
    }
    writer.close();

  }

  private static List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData()
              .withLastName(String.format("Last %s", i)).withFirstName(String.format("First %s", i))
              .withAddress(String.format("Address %s", i)).withHomePhone(String.format("HomePhone %s", i))
              .withMobilePhone(String.format("MobilePhone %s", i)).withWorkPhone(String.format("WorkPhone %s", i))
              .withEMail(String.format("EMail %s", i)).withEMail2(String.format("EMail2 %s", i))
              .withEMail3(String.format("EMail3 %s", i))); //9
    }
    return contacts;
  }
}
