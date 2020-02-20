package ru.stqa.pft.addressbook.model;

public class ContactData {
  private final String firstName;
  private final String lastName;
  private final String telephone;
  private final String EMail;

  public ContactData(String FirstName, String LastName, String Telephone, String EMail) {
    firstName = FirstName;
    lastName = LastName;
    telephone = Telephone;
    this.EMail = EMail;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getTelephone() {
    return telephone;
  }

  public String getEMail() {
    return EMail;
  }
}
