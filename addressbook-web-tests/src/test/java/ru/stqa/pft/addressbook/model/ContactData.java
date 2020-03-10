package ru.stqa.pft.addressbook.model;

import java.util.Objects;

public class ContactData {
  private int id;
  private final String firstName;
  private final String lastName;
  private final String telephone;
  private final String EMail;
  private String group;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return Objects.equals(lastName, that.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastName);
  }

  public ContactData(String FirstName, String LastName, String Telephone, String EMail, String group) {
    this.id = Integer.MAX_VALUE;
    this.firstName = FirstName;
    this.lastName = LastName;
    this.telephone = Telephone;
    this.EMail = EMail;
    this.group = group;
  }
  public ContactData(int id, String FirstName, String LastName, String Telephone, String EMail, String group) {
    this.id = id;
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

  @Override
  public String toString() {
    return "ContactData{" +
            "id='" + id + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
  }

}
