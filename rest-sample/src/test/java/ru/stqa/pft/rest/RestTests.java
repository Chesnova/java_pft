package ru.stqa.pft.rest;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;


import static org.testng.Assert.assertEquals;

public class RestTests {
  @Test
  public void testCreateIssue() throws IOException {
    //получаем старый список багрепортов
    Set<Issue> oldIssues = getIssues();
    //создаем новый объект
    Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue");
    //вызываем метод, которы возвращает созданный багрепорт
    int issueId = createIssue(newIssue);
    //получаем новый список
    Set<Issue> newIssues = getIssues();
    //в множество добавляем созданный багрепорт
    oldIssues.add(newIssue.withId(issueId).withStatus("Open")); //смотрим в статусе открыт
    //сравниваем
    assertEquals(newIssues,oldIssues);
  }


  private Set<Issue> getIssues() throws IOException {
    //выполняем get запрос с парамертами с api
    //добавила лимит 500
    String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json?limit=500"))
            .returnContent().asString();
    //анализируем строчку
    // нам нужен кусок ответа, соответствующий ключу "issues"
    //напрямую преобразовать ответ не  получится, поэтому парсим JsonElement,
    JsonElement parsed = JsonParser.parseString(json);
    //вытягиваем кусок соответствующий ключу "issues"
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    //преобразовываем полученный кусок ответа в множество issues (первый параметр).
    //Тип данных указываем как для классов с угловыми скобками через new TypeToken<наше множество>(){}.getType()
    // (второй параметр)
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
  }

  //для авторизации нужно использовать экзекьютор, в который передается запрос.
  //В соответствии с документацией в качестве имени пользователя указываем API key, пароль пустой
  private Executor getExecutor() {
    return Executor.newInstance()
            .auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }

  private int createIssue(Issue newIssue) throws IOException {
    //выполняем POST запрос на создание нового багрепорта
    String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
            //передаем параметры запроса
            .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                    new BasicNameValuePair("description", newIssue.getDescription())))
            .returnContent().asString();
    //парсером анализируем Response Body
    JsonElement parsed = JsonParser.parseString(json);
    //получаем issue_id из Response Body, преобразуем в целое число
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }
}
