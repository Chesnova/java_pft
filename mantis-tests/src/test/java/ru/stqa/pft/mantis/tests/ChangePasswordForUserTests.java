package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.User;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordForUserTests extends TestBase {
  @BeforeMethod
  //добавляем запуск почтового сервера перед методом, чтоб пропадала старая почта
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testChangePasswordForUser() throws IOException, MessagingException {

    String newPassword = "passwordnew";
    //заходим на страницу логин пейдж
    app.getNavigationHelper().goToLoginPage();
    //логинимся админом
    app.getUserHelper().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
    //переходим на страницу управление пользователями
    app.getNavigationHelper().goToManageUserPage();

    // Однако получить информацию об идентификаторе и/или логине пользователя тесты
    // должны самостоятельно во время выполнения. Можно это сделать,
    // например, загрузив информацию о пользователях из базы данных.
    User changedUser = app.getUserHelper().getAnyUserFromBD();
    //Администратор входит в систему, переходит на страницу управления пользователями,
    app.getNavigationHelper().goToUserPage(changedUser.getId());
    // выбирает заданного пользователя и нажимает кнопку Reset Password
    app.getUserHelper().startResetPassword();
    //Отправляется письмо на адрес пользователя, тесты должны получить это письмо,
     List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    // извлечь из него ссылку для смены пароля, пройти по этой ссылке и изменить пароль.
    String confirmationLink = app.mail().findConfirmationLink(mailMessages, changedUser.getEmail());
    app.registration().finish(confirmationLink, newPassword);

    User user = app.getUserHelper().getUserByIdFromBD(changedUser.getId());

    //Затем тесты должны проверить, что пользователь может войти в систему с новым паролем.
    HttpSession sessionUser = app.newSession();
    assertTrue(sessionUser.login(user.getUsername(), newPassword));
    assertTrue(sessionUser.isLoggedInAs(user.getUsername()));

  }
  //останавливаем сервер в любом лучае, если даже тест упал
  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

}
