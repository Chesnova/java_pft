package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToHomePage() {
    click(By.linkText("home"));
  }

  public void submitContactCreation() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("mobile"), contactData.getTelephone());
    type(By.name("email"), contactData.getEMail());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void selectedAlert() {

    wd.switchTo().alert().accept();
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  private void initContactModificationById(int id) {
    wd.findElement(By.xpath(String.format("//input[@value='%s']/../../td[8]/a",id))).click();
  }

  public void create(ContactData contact) {
    fillContactForm(contact, true);
    submitContactCreation();
    contactCache = null;
    returnToHomePage();
  }
  public void modify(ContactData contact) {
    selectContactById(contact.getId()); //выбрать контакт
    initContactModificationById(contact.getId()); //начать модификацию
    fillContactForm(contact, false); //заполнить форму
    submitContactModification (); //подтвердить
    contactCache = null;
    returnToHomePage(); //вернуться на главную страницу
  }


 public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    selectedAlert();
    contactCache = null;
    returnToHomePage();
  }


  public boolean isThereAContact() {
    return isElementPresent(By.xpath("//table[@id='maintable']/tbody/tr[2]/td/input"));
  }

  public int getContactCount() {
    return wd.findElements(By.xpath("//table[@id='maintable']/tbody/tr[2]/td/input")).size();
  }

  private Contacts contactCache = null;
 public Contacts all() {
   if (contactCache != null) {
     return new Contacts(contactCache);
   }

   contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      contactCache.add(new ContactData().withId(id).withFirstName(firstname).withLastName(lastname));
    }
    return new Contacts(contactCache);
  }

 }
