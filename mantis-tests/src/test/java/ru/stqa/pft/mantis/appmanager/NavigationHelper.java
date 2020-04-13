package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class NavigationHelper extends HelperBase {
  public NavigationHelper(ApplicationManager app) {
    super(app);
  }

//страница входа под логином
  public void goToLoginPage() {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
  }
  //страница управления пользователями
  public void goToManageUserPage() {
    wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
  }

  public void goToUserPage(int id) {
    click(By.cssSelector("a[href=\"manage_user_edit_page.php?user_id=" + id +"\"]"));
  }
}
