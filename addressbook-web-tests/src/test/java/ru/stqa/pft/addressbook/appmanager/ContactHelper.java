package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.List;

import static javafx.beans.binding.Bindings.select;

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
    type(By.name("email2"), contactData.getEMail2());
    type(By.name("email3"), contactData.getEMail3());
    type(By.name("home"),contactData.getHomePhone());
    type(By.name("mobile"),contactData.getMobilePhone());
    type(By.name("work"),contactData.getWorkPhone());
    type(By.name("address"),contactData.getAddress());
//    attach(By.name("photo"),contactData.getPhoto());


    if (creation) {
      //если контакт не в группе, а групп больше нуля, то добавляем в группу
      if (contactData.getGroups().size() > 0) {
        // в две разные группы добавить контакт нельзя, нужно проверить есть ли он в какой-то группе
        Assert.assertTrue(contactData.getGroups().size() == 1);
        //извлекаем какую-то группу и берем у нее имя
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void selectContactById(int id) {
    //wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    //wd.findElement(By.xpath(String.format("//input[@value='%s']/../../td[8]/a",id))).click();
    //wd.findElement((By.cssSelector("a[href*='edit.php?id=" + id + "']"))).click();
    //click(By.cssSelector("select[name='selected[]']>option[value='" +  id + "']"));
    click(By.cssSelector("input[value='" + id + "']"));

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

  public void createContact(ContactData contact) {
    fillContactForm(contact, true);
    submitContactCreation();
    contactCache = null;
    returnToHomePage();
  }
  public void createContact(ContactData contact, boolean creation) {
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

  public void allGroupsOnUserPage() {
    new Select(wd.findElement(By.name("group"))).selectByVisibleText("[all]");
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

  public int count() {
    return wd.findElements(By.xpath("//table[@id='maintable']/tbody/tr[2]/td/input")).size();
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null){
     return new Contacts(contactCache);
    }

    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.name("entry"));
      for (WebElement element : elements) {
       List<WebElement> cells = element.findElements(By.tagName("td"));
       int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
       String lastname = cells.get(1).getText();
       String firstname = cells.get(2).getText();
        String address  = cells.get(3).getText();
        String allEMails = cells.get(4).getText();
       String allPhones = cells.get(5).getText();
        contactCache.add(new ContactData().withId(id).withFirstName(firstname).withLastName(lastname)
                .withAllPhones(allPhones)
                .withAddress(address)
                .withAllEMails(allEMails));
      }
    return new Contacts(contactCache);
  }
  public void choiceGroup(String nameGroup) {
    select(By.name("to_group"), nameGroup);
  }

  public ContactData contactInfoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstName(firstname).withLastName(lastname)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work)
            .withEMail(email).withEMail2(email2).withEMail3(email3)
            .withAddress(address);
  }

    public void addToGroup(int id, GroupData group) { //добавление контакта в группу

    selectContactById(id);  // выбираем чекбокс контакта
    dropDownClick(String.format("//div[@id='content']/form[2]/div[4]/select/option[@value='%s']",group.getId() ));
    click(By.xpath("//*[@id='content']/form[2]/div[4]/input"));

  }

  public boolean isContactInGroup(ContactData contact, GroupData group){
    if(contact.getGroups().size() == 0){
      return false;
    }
    Groups contactGroups = contact.getGroups();
    for (GroupData contactGroup:contactGroups){
      if (contactGroup.equals(group)){
        return true;
      }
    }
    return false;
  }

 public void submitContactDeleteFromGroup() {
   //wd.findElement(By.name("remove")).click(); не подходит
   //wd.findElement(By.xpath("//input[@name='remove']")).click(); не подходит
   click(By.cssSelector("input[type='submit']"));
 }
  private void selectGroupforContact(String groupName){
    new Select(wd.findElement(By.name("group"))).selectByVisibleText(groupName);
  }

  public void selectGroupforContact(ContactData user, GroupData group){
    selectContactById(user.getId());
    String groupId = String.valueOf(group.getId());
    new Select(wd.findElement(By.name("to_group"))).selectByValue(groupId);
    submitContactToGroup();
    contactCache = null;
  }

  public void selectedGroup(ContactData user, GroupData group){
    selectContactById(user.getId());
    String groupId = String.valueOf(group.getId());
    new Select(wd.findElement(By.name("to_group"))).selectByValue(groupId);
    submitContactToGroup();
    contactCache = null;
  }

  public void selectGroupFilterByName(GroupData group) {
    String groupId = String.valueOf(group.getId());
    new Select(wd.findElement(By.name("group"))).selectByValue(groupId);
  }

  public void selectGroupById(ContactData user, GroupData group){
    selectContactById(user.getId());
    String groupId = String.valueOf(group.getId());
    new Select(wd.findElement(By.name("to_group"))).selectByValue(groupId);
    submitContactToGroup();
    contactCache = null;
  }
  public void submitContactToGroup() {
    wd.findElement(By.name("add")).click();
  }

}
