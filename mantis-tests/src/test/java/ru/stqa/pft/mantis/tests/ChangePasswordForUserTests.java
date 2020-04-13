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
    //Администратор входит в систему, переходит на страницу управления пользователями,
    // выбирает заданного пользователя и нажимает кнопку Reset Password
   // Однако получить информацию об идентификаторе и/или логине пользователя тесты
    // должны самостоятельно во время выполнения. Можно это сделать,
    // например, загрузив информацию о пользователях из базы данных.
    //Шаги 1 и 2 необходимо выполнять через пользовательский интерфейс


    //Отправляется письмо на адрес пользователя, тесты должны получить это письмо,
    // извлечь из него ссылку для смены пароля, пройти по этой ссылке и изменить пароль.

    //Затем тесты должны проверить, что пользователь может войти в систему с новым паролем.
    //шаг 3 можно выполнить на уровне протокола HTTP.

    //Почтовый сервер можно запускать непосредственно внутри тестов.

    //логинимся админом и переходим на страницу управление пользователями
    String newPassword = "passwordnew";
    app.getNavigationHelper().goToLoginPage();
    app.getUserHelper().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
    app.getNavigationHelper().goToManageUserPage();

    User changedUser = app.getUserHelper().getAnyUserFromBD();
    app.getNavigationHelper().goToUserPage(changedUser.getId());
    app.getUserHelper().startResetPassword();
    List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
    String confirmationLink = app.mail().findConfirmationLink(mailMessages, changedUser.getEmail());
    app.registration().finish(confirmationLink, newPassword);

    User user = app.getUserHelper().getUserByIdFromBD(changedUser.getId());

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
