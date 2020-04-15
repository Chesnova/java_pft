package ru.stqa.pft.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;
import ru.stqa.pft.mantis.model.Issue;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

public class TestBase {

  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));


  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
    app.ftp().upload(new File("src/test/resources/config_inc.php"),
            "config_inc.php","config_inc.php.bak");
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.ftp().restore("config_inc.php.bak", "config_inc.php");
    app.stop();
  }
  //В классе TestBase, от которого наследуются все тесты,
  // необходимо реализовать функцию boolean isIssueOpen(int issueId) ,
  // которая должна через Remote API получать из баг-трекера информацию
  // о баг-репорте с заданным идентификатором, и возвращать значение false
  // или true в зависимости от того, помечен он как исправленный или нет

  boolean isIssueOpen(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    //открываем соединение
    Issue issue = app.soap().getIssueById(issueId);
    System.out.println(issue.toString());
    if ((issue.getStatus().equals("resolved")) || (issue.getStatus().equals("closed")) ||
            (issue.getResolution().equals("fixed"))) {
      return false;
    }
    return true;
  }

//и вызывать её в начале нужного теста, чтобы он пропускался, если баг ещё не исправлен.
  public void skipIfNotFixed(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}
