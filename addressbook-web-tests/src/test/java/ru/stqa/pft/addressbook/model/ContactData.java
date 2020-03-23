package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.File;
import java.util.Objects;
@XStreamAlias("contact")
public class ContactData {
  @XStreamOmitField
  private int id = Integer.MAX_VALUE;
  @Expose
  private String firstName;
  @Expose
  private String lastName;
  @Expose
  private String telephone;
  @Expose
  private String EMail;
  @Expose
  private String EMail2;
  @Expose
  private String EMail3;
  @Expose
  private String group;
  @Expose
  private String homePhone;
  @Expose
  private String mobilePhone;
  @Expose
  private String workPhone;
  @Expose
  private String allPhones;
  @Expose
  private String allEMails;
  @Expose
  private String address;
  private File photo;

  public File getPhoto() {
    return photo;
  }

  public ContactData withPhoto(File photo) {
    this.photo = photo;
    return this;
  }

  public String getAddress() {
    return address;
  }

  public ContactData withAddress(String address) {
    this.address = address;
    return this;
  }

  public String getAllEMails() {
    return allEMails;
  }

  public ContactData withAllEMails(String allEMails) {
    this.allEMails = allEMails;
    return this;
  }

  public String getAllPhones() {
    return allPhones;
  }

  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public int getId() {
    return id;
  }

  public ContactData withId(int id) {
    this.id = id;
    return this;
  }
  public ContactData withFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }
  public ContactData withLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }


  public ContactData withEMail(String EMail) {
    this.EMail = EMail;
    return this;
  }
  public ContactData withEMail3(String EMail3) {
    this.EMail3 = EMail3;
    return this;
  }
  public ContactData withEMail2(String EMail2) {
    this.EMail2 = EMail2;
    return this;
  }

  public ContactData withTelephone(String telephone) {
    this.telephone = telephone;
    return this;
  }
  public ContactData withHomePhone(String home) {
    this.homePhone = home;
    return this;
  }
  public ContactData withMobilePhone(String mobile) {
    this.mobilePhone = mobile;
    return this;
  }
  public ContactData withWorkPhone(String work) {
    this.workPhone = work;
    return this;
  }

  public ContactData withGroup(String group) {
    this.group = group;
    return this;
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
  public String getEMail2() {
    return EMail2;
  }
  public String getEMail3() {
    return EMail3;
  }


  public String getGroup() {
    return group;
  }

  public String getHomePhone() {
    return homePhone;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public String getWorkPhone() {
    return workPhone;
  }


  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return id == that.id &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName);
  }

}
