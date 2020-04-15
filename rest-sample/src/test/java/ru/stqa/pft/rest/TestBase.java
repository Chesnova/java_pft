package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.util.Set;


public class TestBase {

  protected static final ApplicationManager app
          = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));


  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.stop();
  }

  public Set<Issue> getIdIssue(int issueId) throws IOException {
    String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues/" + issueId + ".json?limit=500"))
            .returnContent().asString();
    JsonElement parsed = JsonParser.parseString(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {}.getType());
  }

  boolean isIssueOpen(int issueId) throws IOException {
    //открываем соединение
    Issue issue = getIdIssue(issueId).iterator().next();;
    if(issue.getState_name().equals("Open")){
      return false;
    }
    else {
      return true;
    }
  }

  //и вызывать её в начале нужного теста, чтобы он пропускался, если баг ещё не исправлен.
  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }

  public Set<Issue> getIssues() throws IOException {
  String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json?limit=500"))
          .returnContent().asString();
   JsonElement parsed = JsonParser.parseString(json);
  JsonElement issues = parsed.getAsJsonObject().get("issues");
  return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
}

  public Executor getExecutor() {
    return Executor.newInstance()
            .auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }

  public int createIssue(Issue newIssue) throws IOException {
    //выполняем POST запрос на создание нового багрепорта
    String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
            .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                    new BasicNameValuePair("description", newIssue.getDescription())))
            .returnContent().asString();
    JsonElement parsed = JsonParser.parseString(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }
}
