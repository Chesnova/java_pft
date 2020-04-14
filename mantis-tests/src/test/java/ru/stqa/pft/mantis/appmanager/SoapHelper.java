package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

//подключаем библиотеку compile 'biz.futureware.mantis:mantis-axis-soap-client:1.2.19'
public class SoapHelper {
  private ApplicationManager app;

  public SoapHelper (ApplicationManager app) {
    this.app = app;
  }

  public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
    //открываем соединение
    MantisConnectPortType mc = getMantisConnect();
    //получаем список доступных проектов
    ProjectData[] projects = mc.mc_projects_get_user_accessible("administrator", "root");
    //преобразуем полученные данные в модельные объекты
    return Arrays.asList(projects).stream()
            .map((p) -> new Project().withId(p.getId().intValue())
                    .withName(p.getName())).collect(Collectors.toSet());
  }

  public MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
    //получаем список проектов
    return new MantisConnectLocator()
              .getMantisConnectPort(new URL(app.getProperty("soap.url")));
    //адрес нужно перенести в конфиг файл
  }

  public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
    //открываем соединение
    MantisConnectPortType mc = getMantisConnect();
    //запрашиваем список возможных категорий багрепортов
    String[] categories = mc.mc_project_get_categories("administrator", "root",
            BigInteger.valueOf(issue.getProject().getId()));
    //из своего модельного объекта строим объект требуемой структуры
    IssueData issueData = new IssueData();
    //заполняем объект issueData
    issueData.setSummary(issue.getSummary());
    issueData.setDescription(issue.getDescription());
    //ObjectRef - ссылка на проект, у него два параметра: ид и имя проекта
    issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
    // устанавливаем обязательное поле Category, первую попавшуюся из списка категорий
    issueData.setCategory(categories[0]);
    // передаем объект issueData в метод удаленного интерфейса
    BigInteger issueId = mc.mc_issue_add("administrator", "root", issueData);
    //получаем объект типа IssueData, выполнив запрос:
    IssueData createdIssueData = mc.mc_issue_get("administrator", "root", issueId);
    //преобразуем объект типа IssueData в модельный объект
    return new Issue().withId(createdIssueData.getId().intValue())
            .withSummary(createdIssueData.getSummary())
            .withDescription(createdIssueData.getDescription())
            .withProject(new Project().withId(createdIssueData.getProject().getId().intValue())
            .withName(createdIssueData.getProject().getName()));
  }

  public Issue getIssueById(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mantisConnectPort = getMantisConnect();
    IssueData issue = mantisConnectPort.mc_issue_get("administrator", "root", BigInteger.valueOf(issueId));
    ObjectRef status = issue.getStatus();
    status.getName();
    return  new Issue().withId(issue.getId().intValue()).withSummary(issue.getSummary()).
            withDescription(issue.getDescription()).setStatus(issue.getStatus().getName()).
            withResolution(issue.getResolution().getName()).withProject(new Project().
            withId(issue.getProject().getId().intValue()).
            withName(issue.getProject().getName()));
  }
}
