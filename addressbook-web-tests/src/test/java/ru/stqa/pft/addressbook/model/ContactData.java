package ru.stqa.pft.addressbook.model;

public class ContactData {
  private final String firstName;
  private final String lastName;
  private final String telephone;
  private final String EMail;
  private String group;

  public ContactData(String FirstName, String LastName, String Telephone, String EMail, String group) {
    this.firstName = FirstName;
    this.lastName = LastName;
    this.telephone = Telephone;
    this.EMail = EMail;
    this.group = group;
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

  public String getGroup() {
    return group;
  }
}
