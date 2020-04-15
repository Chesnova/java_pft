package ru.stqa.pft.rest;


import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Set;


public class RestTests extends TestBase{
  @Test
  public void testCreateIssue() throws IOException {
    //получаем старый список багрепортов
    Set<Issue> oldIssues = getIssues();
    skipIfNotFixed(oldIssues.iterator().next().getId());
    //создаем новый объект
    Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue");
    //вызываем метод, которы возвращает созданный багрепорт
    int issueId = createIssue(newIssue);
    //получаем новый список
    Set<Issue> newIssues = getIssues();
    //в множество добавляем созданный багрепорт
    oldIssues.add(newIssue.withId(issueId).withStatus("Open")); //смотрим в статусе открыт
  }



}
