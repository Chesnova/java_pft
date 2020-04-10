package ru.stqa.pft.mantis.appmanager;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpSession {
  private CloseableHttpClient httpClient;
  private ApplicationManager app;

  public HttpSession(ApplicationManager app) {
    this.app = app; //создание нового клиента - новой сессии для работы по протоколу http, он будет отправлять запросы к серверу
    httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
    // setRedirectStrategy - установка стратегии перенаправлений
    // без нее ответ будет 302 - и нужно будет обрабатывать перенаправление на другую страницу самостоятельно
    //LaxRedirectStrategy - стратегия при которой перенаправления выполняются автоматически
  }

  public boolean login(String username, String password) throws IOException{
    HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "/login.php"); //создаем post запрос


    List<NameValuePair> params = new ArrayList<NameValuePair>();
    params.add(new BasicNameValuePair("username", username));
    params.add(new BasicNameValuePair("password", password)); //"test"
    params.add(new BasicNameValuePair("secure_session", "on"));
    params.add(new BasicNameValuePair("return", "index.php"));

    //упаковываем параметры запроса и помещаем в запрос
    post.setEntity(new UrlEncodedFormEntity(params));

    //выполнение запроса и получение ответа от сервера в переменную response
    CloseableHttpResponse response = httpClient.execute(post);

    //получение текста ответа на языке html
    String body = geTextFrom(response);

    // проверка, залогинился ли пользователь
    return body.contains(String.format("<span class=\"italic\">%s</span>", username)); //user-info
  }

  private String geTextFrom(CloseableHttpResponse response) throws IOException {
    // читаем тело запроса, после чего закрываем его
    try {
      return EntityUtils.toString(response.getEntity());
    }finally {
      response.close();
    }
  }

  public boolean isLoggedInAs(String username) throws IOException {
    //отправляем get запрос, чтоб перейти на /index.php
    HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/index.php");

    //получает ответ с html кодом страницы
    CloseableHttpResponse response = httpClient.execute(get);

    //html код заносим в переменную body
    String body = geTextFrom(response);

    //проверяем что мы залогинены юзером username
    return body.contains(String.format("<span class=\"italic\">%s</span>", username)); //user-info
  }
}