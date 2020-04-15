package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {
  @Test
  public void testCommits() throws IOException {
    //устанавливаем соединение с  Github, указываем токен авторизации,
    //который генерируется в настройках профиля в разделе Personal access tokens, ставим галочки на gist, repo
    Github github = new RtGithub("416479b127b4094686f1fb77fc3597e4be07123f");
    //получаем доступ к репозиторию java_pft под юзером Chesnova
    RepoCommits commits = github.repos().get(new Coordinates.Simple("Chesnova", "java_pft")).commits();
    // в iterate() передаем как параметр набор пар, которые описывают условия отбора коммитов (фильтр),
    // нам нужно передать пустое значение, так как нам нужен весь список, поэтому мы строим пустой набор пар
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())){
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}
