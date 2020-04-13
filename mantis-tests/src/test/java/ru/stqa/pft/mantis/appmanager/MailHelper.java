package ru.stqa.pft.mantis.appmanager;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MailHelper {
  private ApplicationManager app;
  private final Wiser wiser;

  public MailHelper(ApplicationManager app) {
    this.app = app;
    wiser = new Wiser(); //при инициализации создается объект типа wiser
  }


  public List<MailMessage> waitForMail(int count, long timeout) throws MessagingException, IOException {
    long start = System.currentTimeMillis(); // сначала запоминаем текущее время
    //затем устраиваем цикл while, в котором проверяется что новое текущее время не превышает момент старта + таймаут
    while (System.currentTimeMillis() < start + timeout) {
      //если почты пришло достаточно много, то можно прекращать
      if (wiser.getMessages().size() >= count) {
        return wiser.getMessages().stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
      }
      //если почты мало, то проскакиваем проверку и перескакиваем сюда
      try {
        Thread.sleep(1000); // подождать и сделаем новый заход в цикл
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    throw new Error("No mail :(");
  }

  public static MailMessage toModelMail(WiserMessage m) {
    try {
      MimeMessage mm = m.getMimeMessage();
      return new MailMessage(mm.getAllRecipients()[0].toString(), (String) mm.getContent());
    } catch (MessagingException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void start() {
    wiser.start();
  }

  public void stop() {
    wiser.stop();
  }

  public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    //из потока извлекаем объект письмо, у которого получатель - имейл юзера
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    //для получения регулярного выражения подключаем зависимость от библиотеки verbalregex
    //строим выражение, которое содержит "http://".а после него непробельные символы.один или больше
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    //выбираем регулярным выражением ссылку из письма
    return regex.getText(mailMessage.text);
  }
}
